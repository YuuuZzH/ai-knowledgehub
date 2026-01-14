package online.yuuu.aiknowledgehub.service;

import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDocumentService {
    Result<List<Document>> getDocumentsByKnowledgeBaseId(Integer knowledgeBaseId);
    
    Result<Document> getDocumentById(Integer id);
    
    Result<Document> uploadDocument(MultipartFile file, Integer knowledgeBaseId);
    
    Result<Void> deleteDocument(Integer id);
    
    Result<Void> processDocument(Integer documentId);
}