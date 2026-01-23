package online.yuuu.aiknowledgehub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.yuuu.aiknowledgehub.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yuuu
 */
@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    @Select("""
        SELECT *
        FROM chat_messages
        WHERE session_id = #{sessionId}
        ORDER BY created_at ASC
        LIMIT #{limit}
    """)
    List<ChatMessage> selectRecentMessages(
            @Param("sessionId") Long sessionId,
            @Param("limit") int limit
    );
}
