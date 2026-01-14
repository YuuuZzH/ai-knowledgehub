package online.yuuu.aiknowledgehub.service;

import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.dto.KnowledgeBaseCreateRequest;
import online.yuuu.aiknowledgehub.dto.KnowledgeBaseUpdateRequest;
import online.yuuu.aiknowledgehub.entity.KnowledgeBase;

import java.util.List;

public interface IKnowledgeBaseService {
    Result<List<KnowledgeBase>> getAllKnowledgeBases();
    
    Result<KnowledgeBase> getKnowledgeBaseById(Integer id);
    
    Result<KnowledgeBase> createKnowledgeBase(KnowledgeBaseCreateRequest request, Integer userId);
    
    Result<KnowledgeBase> updateKnowledgeBase(KnowledgeBaseUpdateRequest request, Integer userId);
    
    Result<Void> deleteKnowledgeBase(Integer id, Integer userId);
}