package online.yuuu.aiknowledgehub.service;

import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.dto.ChatRequest;
import online.yuuu.aiknowledgehub.dto.ChatResponse;

public interface IChatService {
    Result<ChatResponse> sendMessage(ChatRequest request);
    
    Result<String> startNewChat(Integer knowledgeBaseId);
}