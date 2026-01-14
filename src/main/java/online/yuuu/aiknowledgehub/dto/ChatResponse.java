package online.yuuu.aiknowledgehub.dto;

import lombok.Data;
import java.util.List;

@Data
public class ChatResponse {
    private String id;
    private String content;
    private String sessionId;
    private List<String> sources;
    private String timestamp;
}