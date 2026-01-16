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
@TableName("document_vectors")
@AllArgsConstructor
@NoArgsConstructor
public class DocumentVector {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long chunkId;

    private Integer knowledgeBaseId;

    private String embedding; // pgvector 类型

    private LocalDateTime createdAt;
}
