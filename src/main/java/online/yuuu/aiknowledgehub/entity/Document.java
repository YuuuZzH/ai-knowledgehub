package online.yuuu.aiknowledgehub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.yuuu.aiknowledgehub.base.enums.BaseEnum;

import java.time.LocalDateTime;

/**
 * @author yuuu
 */
@Data
@TableName("documents")
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String filename; // 存储文件名
    
    private String originalName; // 原始文件名
    
    private Long size; // 文件大小，单位字节
    
    private Integer knowledgeBaseId; // 所属知识库
    
    private DocumentStatus status; // 上传状态：uploading, processing, completed, failed
    
    private String filePath; // 文件存储路径
    
    private LocalDateTime uploadDate;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum DocumentStatus implements BaseEnum<DocumentStatus, Integer> {

        UPLOADING(0, "上传中"),
        PROCESSING(1, "处理中"),
        COMPLETED(2, "已完成"),
        FAILED(3, "失败"),
        DELETED(4, "待处理"),
        ;

        private final Integer value;

        private final String description;

        public DocumentStatus getEnumByValue(Integer value) {
            for (DocumentStatus status : values()) {
                if (status.getValue().equals(value)) {
                    return status;
                }
            }
            return null;
        }
    }
}