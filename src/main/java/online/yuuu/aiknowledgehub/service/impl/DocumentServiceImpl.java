package online.yuuu.aiknowledgehub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.entity.Document;
import online.yuuu.aiknowledgehub.entity.DocumentProcessTask;
import online.yuuu.aiknowledgehub.entity.KnowledgeBase;
import online.yuuu.aiknowledgehub.mapper.DocumentMapper;
import online.yuuu.aiknowledgehub.mapper.DocumentProcessTaskMapper;
import online.yuuu.aiknowledgehub.mapper.KnowledgeBaseMapper;
import online.yuuu.aiknowledgehub.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements IDocumentService {

    private final DocumentMapper documentMapper;

    private final KnowledgeBaseMapper knowledgeBaseMapper;

    private final DocumentProcessTaskMapper taskMapper;

    private final DocumentIngestWorker ingestService;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public Result<List<Document>> getDocumentsByKnowledgeBaseId(Integer knowledgeBaseId) {
        List<Document> documents = documentMapper.selectList(
            new QueryWrapper<Document>()
                .eq("knowledge_base_id", knowledgeBaseId)
        );
        return Result.success(documents);
    }

    @Override
    public Result<Document> getDocumentById(Integer id) {
        Document document = documentMapper.selectById(id);
        if (document == null) {
            return Result.error("文档不存在");
        }
        return Result.success(document);
    }

    @Override
    @Transactional
    public Result<Document> uploadDocument(MultipartFile file, Integer knowledgeBaseId) {
        // 验证知识库是否存在
        KnowledgeBase knowledgeBase = knowledgeBaseMapper.selectById(knowledgeBaseId);
        if (knowledgeBase == null) {
            return Result.error("知识库不存在");
        }

        // 检查文件类型
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return Result.error("文件名不能为空");
        }

        String extension = getFileExtension(originalFilename);
        if (!isValidFileType(extension)) {
            return Result.error("不支持的文件类型: " + extension + ", 支持的类型: pdf, doc, docx, txt, md");
        }

        try {
            // 创建上传目录
            Path uploadDir = Paths.get(uploadPath);
            Files.createDirectories(uploadDir);

            // 生成唯一文件名
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            Path filePath = uploadDir.resolve(fileName);

            // 保存文件
            file.transferTo(filePath.toFile());

            // 创建文档记录
            Document document = new Document();
            document.setOriginalName(originalFilename);
            document.setFilename(fileName);
            document.setSize(file.getSize());
            document.setKnowledgeBaseId(knowledgeBaseId);
            document.setStatus(Document.DocumentStatus.UPLOADING);
            document.setFilePath(filePath.toString());
            document.setUploadDate(LocalDateTime.now());
            document.setCreatedAt(LocalDateTime.now());
            document.setUpdatedAt(LocalDateTime.now());

            documentMapper.insert(document);

            // 创建文档处理任务
            DocumentProcessTask task = new DocumentProcessTask();
            task.setDocumentId(document.getId());
            task.setKnowledgeBaseId(knowledgeBaseId);
            task.setTaskType(DocumentProcessTask.TaskType.INGEST);
            task.setStatus(DocumentProcessTask.TaskStatus.PENDING);
            taskMapper.insert(task);
            // 提交后再异步
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                            ingestService.process(task.getId());
                        }
                    }
            );
            return Result.success(document);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件上传失败");
        }
    }

    @Override
    public Result<Void> deleteDocument(Integer id) {
        Document document = documentMapper.selectById(id);
        if (document == null) {
            return Result.error("文档不存在");
        }

        // 删除物理文件
        try {
            Path filePath = Paths.get(document.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // 记录错误但继续删除数据库记录
            System.err.println("删除文件失败: " + e.getMessage());
        }

        // 删除数据库记录
        documentMapper.deleteById(id);

        return Result.success("文档删除成功", null);
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    /**
     * 验证文件类型是否支持
     */
    private boolean isValidFileType(String extension) {
        return extension.equals("pdf") ||
               extension.equals("doc") ||
               extension.equals("docx") ||
               extension.equals("txt") ||
               extension.equals("md");
    }
}