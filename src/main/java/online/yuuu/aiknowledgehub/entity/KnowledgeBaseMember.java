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
@TableName("knowledge_base_members")
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeBaseMember {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer knowledgeBaseId;

    private Integer userId;

    /**
     * OWNER / EDITOR / VIEWER
     */
    private Role role;

    private LocalDateTime createdAt;

    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum Role implements BaseEnum<Role, Integer> {
        OWNER(0, "所有者"),
        EDITOR(1, "编辑者"),
        VIEWER(2, "浏览者");

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
