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
@TableName("document_process_tasks")
@AllArgsConstructor
@NoArgsConstructor
public class DocumentProcessTask {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer documentId;

    private Integer knowledgeBaseId;

    /**
     * INGEST / REBUILD / DELETE
     */
    private TaskType taskType;

    /**
     * PENDING / RUNNING / SUCCESS / FAILED
     */
    private TaskStatus status;

    private String errorMessage;

    private Integer retryCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum TaskType implements BaseEnum<TaskType, Integer> {
        INGEST(0,"提取"),
        REBUILD(1,"重建"),
        DELETE(2,"删除");

        private final Integer value;

        private final String description;

        public TaskType getEnumByValue(Integer value) {
            for (TaskType type : values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            return null;
        }
    }


    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum TaskStatus implements BaseEnum<TaskStatus, Integer> {
        PENDING(0, "待处理"),
        RUNNING(1, "处理中"),
        SUCCESS(2, "处理成功"),
        FAILED(3, "处理失败");

        private final Integer value;

        private final String description;

        public TaskStatus getEnumByValue(Integer value) {
            for (TaskStatus status : values()) {
                if (status.getValue().equals(value)) {
                    return status;
                }
            }
            return null;
        }
    }
}
