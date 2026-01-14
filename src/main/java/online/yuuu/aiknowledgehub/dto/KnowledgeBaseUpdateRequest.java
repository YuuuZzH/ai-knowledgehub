package online.yuuu.aiknowledgehub.dto;

import lombok.Data;

@Data
public class KnowledgeBaseUpdateRequest {
    private Integer id;
    private String name;
    private String description;
    private Boolean isPublic;
}