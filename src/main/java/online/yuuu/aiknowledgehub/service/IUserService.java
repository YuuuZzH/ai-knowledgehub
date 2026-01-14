package online.yuuu.aiknowledgehub.service;

import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.dto.LoginRequest;
import online.yuuu.aiknowledgehub.dto.RegisterRequest;
import online.yuuu.aiknowledgehub.dto.UpdateProfileRequest;
import online.yuuu.aiknowledgehub.entity.User;

public interface IUserService {
    Result<User> login(LoginRequest request);
    
    Result<User> register(RegisterRequest request);
    
    Result<User> getCurrentUser(Integer userId);
    
    Result<User> updateProfile(Integer userId, UpdateProfileRequest request);
    
    Result<Void> changePassword(Integer userId, String oldPassword, String newPassword);
}