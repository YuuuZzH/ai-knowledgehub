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

@Data
@TableName("users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String username;
    
    private String email;
    
    private String password;
    
    private Role role;
    
    private String avatar;
    
    private LocalDateTime lastLogin;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum Role implements BaseEnum<Role, Integer> {
        USER(0, "用户"),
        ADMIN(1, "管理员"),
        ;

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