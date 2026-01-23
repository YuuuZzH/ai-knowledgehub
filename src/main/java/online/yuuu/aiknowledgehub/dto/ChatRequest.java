package online.yuuu.aiknowledgehub.dto;

import lombok.Data;

/**
 * @author yuuu
 */
@Data
public class ChatRequest {
    private Integer userId;
    private String message;
    private Integer knowledgeBaseId;
    private Long sessionId;
}