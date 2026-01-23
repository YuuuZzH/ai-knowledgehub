package online.yuuu.aiknowledgehub.config;

import jakarta.annotation.Resource;
import online.yuuu.aiknowledgehub.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取Token
        String token = request.getHeader("Authorization");
        
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 移除 "Bearer " 前缀
        } else {
            // 如果请求头中没有Token，尝试从参数中获取
            token = request.getParameter("token");
        }

        if (token == null || token.isEmpty()) {
            // 返回未授权错误
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error("缺少认证Token")));
            return false;
        }

        // 验证Token
        if (!tokenUtil.validateToken(token)) {
            // Token无效
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error("Token无效或已过期")));
            return false;
        }

        // Token有效，继续执行
        return true;
    }
}