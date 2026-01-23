package online.yuuu.aiknowledgehub.controller;

import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.dto.ChatRequest;
import online.yuuu.aiknowledgehub.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private IChatService chatService;

    @PostMapping
    public Result<String> sendMessage(@RequestBody ChatRequest request) {
        return chatService.sendMessage(request);
    }

    @PostMapping("/start")
    public Result<String> startNewChat(@RequestParam(required = false) Integer knowledgeBaseId) {
        return chatService.startNewChat(knowledgeBaseId);
    }

    @GetMapping("/history/{sessionId}")
    public Result<?> getChatHistory(@PathVariable String sessionId) {
        // 这里需要根据实际需求实现聊天历史功能
        return Result.success("聊天历史功能待实现");
    }
}