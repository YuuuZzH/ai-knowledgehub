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
@TableName("chat_sessions")
@AllArgsConstructor
@NoArgsConstructor
public class ChatSession {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer userId;

    private Integer knowledgeBaseId;

    private String title;

    private LocalDateTime createdAt;
}
