package online.yuuu.aiknowledgehub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author yuuu
 */
@Data
@TableName("embedding_versions")
@AllArgsConstructor
@NoArgsConstructor
public class EmbeddingVersion {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String modelName;

    private Integer dimension;

    private Integer chunkSize;

    private Integer chunkOverlap;

    private Boolean isActive;

    private LocalDateTime createdAt;
}
