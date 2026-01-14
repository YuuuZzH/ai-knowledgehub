package online.yuuu.aiknowledgehub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("documents")
public class Document {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String filename; // 存储文件名
    
    private String originalName; // 原始文件名
    
    private Long size; // 文件大小，单位字节
    
    private Integer knowledgeBaseId; // 所属知识库
    
    private String status; // 上传状态：uploading, processing, completed, failed
    
    private String filePath; // 文件存储路径
    
    private LocalDateTime uploadDate;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}