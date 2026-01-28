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

    List<ChunkScoreDTO> searchTopK(
            @Param("embedding") Object embedding,
            @Param("kbId") Integer knowledgeBaseId,
            @Param("topK") Integer topK
    );
}
