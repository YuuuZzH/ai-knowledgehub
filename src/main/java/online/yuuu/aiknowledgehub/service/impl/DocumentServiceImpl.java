package online.yuuu.aiknowledgehub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.entity.Document;
import online.yuuu.aiknowledgehub.entity.KnowledgeBase;
import online.yuuu.aiknowledgehub.mapper.DocumentMapper;
import online.yuuu.aiknowledgehub.mapper.KnowledgeBaseMapper;
import online.yuuu.aiknowledgehub.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    private DocumentMapper documentMapper;
    
    @Autowired
    private KnowledgeBaseMapper knowledgeBaseMapper;

    @Value("${file.upload.path:./uploads}")
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
            document.setStatus("uploading"); // 初始状态为上传中
            document.setFilePath(filePath.toString());
            document.setUploadDate(LocalDateTime.now());
            document.setCreatedAt(LocalDateTime.now());
            document.setUpdatedAt(LocalDateTime.now());

            documentMapper.insert(document);

            // 启动文档处理任务
            processDocument(document.getId());

            return Result.success(document);
        } catch (IOException e) {
            return Result.error("文件上传失败: " + e.getMessage());
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

    @Override
    public Result<Void> processDocument(Integer documentId) {
        // 这里应该启动一个异步任务来处理文档
        // 包括：解析文档内容、分片、向量化、存储到向量数据库等
        
        Document document = documentMapper.selectById(documentId);
        if (document == null) {
            return Result.error("文档不存在");
        }

        // 更新状态为处理中
        document.setStatus("processing");
        document.setUpdatedAt(LocalDateTime.now());
        documentMapper.updateById(document);

        // 模拟文档处理过程（实际实现中这里会涉及文档解析、向量化等复杂操作）
        try {
            // 这里应该调用文档解析和向量化服务
            Thread.sleep(2000); // 模拟处理时间

            // 更新状态为已完成
            document.setStatus("completed");
            document.setUpdatedAt(LocalDateTime.now());
            documentMapper.updateById(document);
        } catch (Exception e) {
            // 更新状态为失败
            document.setStatus("failed");
            document.setUpdatedAt(LocalDateTime.now());
            documentMapper.updateById(document);
            return Result.error("文档处理失败: " + e.getMessage());
        }

        return Result.success("文档处理完成", null);
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