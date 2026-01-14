package online.yuuu.aiknowledgehub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.yuuu.aiknowledgehub.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}