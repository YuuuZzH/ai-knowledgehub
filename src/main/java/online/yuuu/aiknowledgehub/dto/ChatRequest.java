package online.yuuu.aiknowledgehub.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String message;
    private Integer knowledgeBaseId;
    private String sessionId;
}