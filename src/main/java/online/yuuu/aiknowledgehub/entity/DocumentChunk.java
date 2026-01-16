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
@TableName("document_chunks")
@AllArgsConstructor
@NoArgsConstructor
public class DocumentChunk {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer knowledgeBaseId;

    private Integer documentId;

    private String content; // 切片文本

    /**
     * 向量字段
     * pgvector：VECTOR(768)
     */
    private String embedding;

    private Integer tokenCount;

    private Integer chunkIndex;

    private LocalDateTime createdAt;

    private Integer embeddingVersionId;

}
