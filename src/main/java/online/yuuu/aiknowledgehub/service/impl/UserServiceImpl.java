package online.yuuu.aiknowledgehub.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.dto.LoginRequest;
import online.yuuu.aiknowledgehub.dto.RegisterRequest;
import online.yuuu.aiknowledgehub.dto.UpdateProfileRequest;
import online.yuuu.aiknowledgehub.entity.User;
import online.yuuu.aiknowledgehub.mapper.UserMapper;
import online.yuuu.aiknowledgehub.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<User> login(LoginRequest request) {
        // 查询用户
        User user = userMapper.selectOne(
            new QueryWrapper<User>()
                .eq("username", request.getUsername())
        );

        if (user == null) {
            return Result.error("用户名不存在");
        }

        // 验证密码
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            return Result.error("密码错误");
        }

        // 更新最后登录时间
        user.setLastLogin(LocalDateTime.now());
        userMapper.updateById(user);

        // 返回用户信息（不包含密码）
        user.setPassword(null);
        return Result.success(user);
    }

    @Override
    public Result<User> register(RegisterRequest request) {
        // 检查用户名是否已存在
        User existingUser = userMapper.selectOne(
            new QueryWrapper<User>()
                .eq("username", request.getUsername())
        );
        if (existingUser != null) {
            return Result.error("用户名已存在");
        }

        // 检查邮箱是否已存在
        existingUser = userMapper.selectOne(
            new QueryWrapper<User>()
                .eq("email", request.getEmail())
        );
        if (existingUser != null) {
            return Result.error("邮箱已被注册");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setRole("user"); // 默认角色
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userMapper.insert(user);

        // 不返回密码
        user.setPassword(null);
        return Result.success(user);
    }

    @Override
    public Result<User> getCurrentUser(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        // 不返回密码
        user.setPassword(null);
        return Result.success(user);
    }

    @Override
    public Result<User> updateProfile(Integer userId, UpdateProfileRequest request) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 更新用户信息
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }
        user.setUpdatedAt(LocalDateTime.now());

        userMapper.updateById(user);

        // 不返回密码
        user.setPassword(null);
        return Result.success(user);
    }

    @Override
    public Result<Void> changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 验证旧密码
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            return Result.error("原密码错误");
        }

        // 更新密码
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updateById(user);

        return Result.success("密码修改成功", null);
    }
}