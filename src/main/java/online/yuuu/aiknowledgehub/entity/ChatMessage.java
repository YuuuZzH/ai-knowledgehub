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
@TableName("chat_messages")
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long sessionId;

    private Role role;

    private String content;

    private LocalDateTime createdAt;

    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum Role implements BaseEnum<Role, Integer> {
        USER(0, "用户"),
        ASSISTANT(1, "助手");

        private final Integer value;

        private final String description;

        public Role getEnumByValue(Integer value) {
            for (Role role : values()) {
                if (role.getValue().equals(value)) {
                    return role;
                }
            }
            return null;
        }
    }
}
