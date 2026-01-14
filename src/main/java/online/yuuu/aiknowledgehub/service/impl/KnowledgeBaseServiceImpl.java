package online.yuuu.aiknowledgehub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.dto.KnowledgeBaseCreateRequest;
import online.yuuu.aiknowledgehub.dto.KnowledgeBaseUpdateRequest;
import online.yuuu.aiknowledgehub.entity.KnowledgeBase;
import online.yuuu.aiknowledgehub.mapper.KnowledgeBaseMapper;
import online.yuuu.aiknowledgehub.service.IKnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KnowledgeBaseServiceImpl implements IKnowledgeBaseService {

    @Autowired
    private KnowledgeBaseMapper knowledgeBaseMapper;

    @Override
    public Result<List<KnowledgeBase>> getAllKnowledgeBases() {
        List<KnowledgeBase> knowledgeBases = knowledgeBaseMapper.selectList(new QueryWrapper<>());
        return Result.success(knowledgeBases);
    }

    @Override
    public Result<KnowledgeBase> getKnowledgeBaseById(Integer id) {
        KnowledgeBase knowledgeBase = knowledgeBaseMapper.selectById(id);
        if (knowledgeBase == null) {
            return Result.error("知识库不存在");
        }
        return Result.success(knowledgeBase);
    }

    @Override
    public Result<KnowledgeBase> createKnowledgeBase(KnowledgeBaseCreateRequest request, Integer userId) {
        // 检查知识库名称是否已存在
        KnowledgeBase existingKB = knowledgeBaseMapper.selectOne(
            new QueryWrapper<KnowledgeBase>()
                .eq("name", request.getName())
                .eq("user_id", userId)
        );
        if (existingKB != null) {
            return Result.error("知识库名称已存在");
        }

        // 创建新知识库
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        knowledgeBase.setName(request.getName());
        knowledgeBase.setDescription(request.getDescription());
        knowledgeBase.setUserId(userId);
        knowledgeBase.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : false);
        knowledgeBase.setCreatedAt(LocalDateTime.now());
        knowledgeBase.setUpdatedAt(LocalDateTime.now());

        knowledgeBaseMapper.insert(knowledgeBase);

        return Result.success(knowledgeBase);
    }

    @Override
    public Result<KnowledgeBase> updateKnowledgeBase(KnowledgeBaseUpdateRequest request, Integer userId) {
        KnowledgeBase knowledgeBase = knowledgeBaseMapper.selectById(request.getId());
        if (knowledgeBase == null) {
            return Result.error("知识库不存在");
        }

        // 检查权限（只有创建者才能修改）
        if (!knowledgeBase.getUserId().equals(userId)) {
            return Result.error("没有权限修改此知识库");
        }

        // 检查名称是否被其他知识库使用
        KnowledgeBase existingKB = knowledgeBaseMapper.selectOne(
            new QueryWrapper<KnowledgeBase>()
                .eq("name", request.getName())
                .eq("user_id", userId)
                .ne("id", request.getId())
        );
        if (existingKB != null) {
            return Result.error("知识库名称已存在");
        }

        // 更新知识库
        knowledgeBase.setName(request.getName());
        knowledgeBase.setDescription(request.getDescription());
        knowledgeBase.setIsPublic(request.getIsPublic());
        knowledgeBase.setUpdatedAt(LocalDateTime.now());

        knowledgeBaseMapper.updateById(knowledgeBase);

        return Result.success(knowledgeBase);
    }

    @Override
    public Result<Void> deleteKnowledgeBase(Integer id, Integer userId) {
        KnowledgeBase knowledgeBase = knowledgeBaseMapper.selectById(id);
        if (knowledgeBase == null) {
            return Result.error("知识库不存在");
        }

        // 检查权限（只有创建者才能删除）
        if (!knowledgeBase.getUserId().equals(userId)) {
            return Result.error("没有权限删除此知识库");
        }

        knowledgeBaseMapper.deleteById(id);

        return Result.success("知识库删除成功", null);
    }
}