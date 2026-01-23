package online.yuuu.aiknowledgehub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.yuuu.aiknowledgehub.dto.ChunkScoreDTO;
import online.yuuu.aiknowledgehub.entity.DocumentChunk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yuuu
 */
@Mapper
public interface DocumentChunkMapper extends BaseMapper<DocumentChunk> {
    @Select("""
        SELECT
            id,
            content,
            1 - (embedding <=> #{embedding}) AS score
        FROM document_chunks
        WHERE knowledge_base_id = #{kbId}
        ORDER BY embedding <=> #{embedding}
        LIMIT #{topK}
    """)
    List<ChunkScoreDTO> searchTopK(
            @Param("embedding") Object embedding,
            @Param("kbId") Integer knowledgeBaseId,
            @Param("topK") Integer topK
    );
}
