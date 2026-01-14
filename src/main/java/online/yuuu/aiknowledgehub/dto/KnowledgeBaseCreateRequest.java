package online.yuuu.aiknowledgehub.dto;

import lombok.Data;

@Data
public class KnowledgeBaseCreateRequest {
    private String name;
    private String description;
    private Boolean isPublic;
}