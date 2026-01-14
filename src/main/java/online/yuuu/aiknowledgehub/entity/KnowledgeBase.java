package online.yuuu.aiknowledgehub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("knowledge_bases")
public class KnowledgeBase {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String name;
    
    private String description;
    
    private Integer userId; // 所属用户
    
    private Boolean isPublic;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}