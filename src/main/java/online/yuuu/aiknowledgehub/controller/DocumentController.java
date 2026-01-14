package online.yuuu.aiknowledgehub.controller;

import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.entity.Document;
import online.yuuu.aiknowledgehub.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private IDocumentService documentService;

    @GetMapping
    public Result<List<Document>> getDocuments(@RequestParam(required = false) Integer knowledgeBaseId) {
        if (knowledgeBaseId != null) {
            return documentService.getDocumentsByKnowledgeBaseId(knowledgeBaseId);
        } else {
            // 如果没有指定知识库ID，可以返回所有文档或抛出异常
            return documentService.getDocumentsByKnowledgeBaseId(null); // 这里可能需要调整
        }
    }

    @GetMapping("/{id}")
    public Result<Document> getDocument(@PathVariable Integer id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping("/upload")
    public Result<Document> uploadDocument(@RequestParam("file") MultipartFile file,
                                          @RequestParam("knowledgeBaseId") Integer knowledgeBaseId) {
        return documentService.uploadDocument(file, knowledgeBaseId);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteDocument(@PathVariable Integer id) {
        return documentService.deleteDocument(id);
    }
}