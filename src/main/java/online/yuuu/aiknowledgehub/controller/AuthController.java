package online.yuuu.aiknowledgehub.controller;

import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.config.TokenUtil;
import online.yuuu.aiknowledgehub.dto.LoginRequest;
import online.yuuu.aiknowledgehub.dto.RegisterRequest;
import online.yuuu.aiknowledgehub.entity.User;
import online.yuuu.aiknowledgehub.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IUserService userService;
    
    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Result<User> loginResult = userService.login(request);
        
        if (loginResult.getCode() == 200) {
            User user = loginResult.getData();
            
            // 生成Token
            String token = tokenUtil.generateToken(user.getId(), user.getUsername());
            
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("access_token", token);
            responseData.put("token_type", "Bearer");
            responseData.put("expires_in", 3600); // 1小时过期
            responseData.put("user", user);
            
            return Result.success(responseData);
        } else {
            return Result.error(loginResult.getMessage());
        }
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }
}