package online.yuuu.aiknowledgehub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenUtil {

    // 模拟Token存储，实际项目中应使用Redis等
    private static final Map<String, Integer> tokenMap = new ConcurrentHashMap<>();

    @Value("${token.expiration:3600}")
    private Long expiration; // 默认1小时

    /**
     * 生成Token
     */
    public String generateToken(Integer userId, String username) {
        String token = java.util.UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
        tokenMap.put(token, userId);
        return token;
    }

    /**
     * 从Token中获取用户ID
     */
    public Integer getUserIdFromToken(String token) {
        return tokenMap.get(token);
    }

    /**
     * 验证Token是否有效
     */
    public boolean validateToken(String token) {
        return tokenMap.containsKey(token);
    }

    /**
     * 移除Token（登出时使用）
     */
    public void removeToken(String token) {
        tokenMap.remove(token);
    }
}