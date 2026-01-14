package online.yuuu.aiknowledgehub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.yuuu.aiknowledgehub.entity.Document;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DocumentMapper extends BaseMapper<Document> {
}