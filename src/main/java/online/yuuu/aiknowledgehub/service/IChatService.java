package online.yuuu.aiknowledgehub.service;

import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.dto.ChatRequest;

/**
 * @author yuuu
 */
public interface IChatService {
    Result<String> sendMessage(ChatRequest request);
    
    Result<String> startNewChat(Integer knowledgeBaseId);
}