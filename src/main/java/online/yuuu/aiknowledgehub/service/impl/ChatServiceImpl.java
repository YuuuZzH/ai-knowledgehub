package online.yuuu.aiknowledgehub.service.impl;

import lombok.RequiredArgsConstructor;
import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.dto.ChatRequest;
import online.yuuu.aiknowledgehub.entity.ChatMessage;
import online.yuuu.aiknowledgehub.entity.ChatSession;
import online.yuuu.aiknowledgehub.mapper.ChatMessageMapper;
import online.yuuu.aiknowledgehub.mapper.ChatSessionMapper;
import online.yuuu.aiknowledgehub.ollma.OllamaChatClient;
import online.yuuu.aiknowledgehub.service.IChatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author yuuu
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements IChatService {


    private final ChatSessionMapper sessionMapper;
    private final ChatMessageMapper messageMapper;
    private final RagQueryService ragQueryService;
    private final OllamaChatClient chatClient;

    private static final int HISTORY_LIMIT = 10;

    @Override
    public Result<String> sendMessage(ChatRequest request) {
        Integer userId = request.getUserId();
        Integer knowledgeBaseId = request.getKnowledgeBaseId();
        Long sessionId = request.getSessionId();
        String message = request.getMessage();
        // 1️⃣ session 处理
        ChatSession session = getOrCreateSession(userId, knowledgeBaseId, sessionId);

        // 2️⃣ 保存 user 消息
        saveMessage(session.getId(), ChatMessage.Role.USER, message);

        // 3️⃣ RAG prompt（只负责知识）
        String ragPrompt = ragQueryService.buildRagPrompt(
                knowledgeBaseId, message
        );

        // 4️⃣ 历史对话
        List<ChatMessage> history =
                messageMapper.selectRecentMessages(session.getId(), HISTORY_LIMIT);

        List<Map<String, String>> messages = new ArrayList<>();

        // system（知识约束）
        messages.add(Map.of(
                "role", "system",
                "content", ragPrompt
        ));

        // history
        for (ChatMessage msg : history) {
            messages.add(Map.of(
                    "role", msg.getRole().name(),
                    "content", msg.getContent()
            ));
        }

        // 5️⃣ 调用 LLM
        String answer = chatClient.chat(messages);

        // 6️⃣ 保存 assistant 消息
        saveMessage(session.getId(), ChatMessage.Role.ASSISTANT, answer);

        return Result.success(answer);
    }

    private ChatSession getOrCreateSession(
            Integer userId,
            Integer knowledgeBaseId,
            Long sessionId
    ) {
        if (sessionId != null) {
            return sessionMapper.selectById(sessionId);
        }

        ChatSession session = new ChatSession();
        session.setUserId(userId);
        session.setKnowledgeBaseId(knowledgeBaseId);
        session.setTitle("新对话");
        sessionMapper.insert(session);
        return session;
    }

    private void saveMessage(Long sessionId, ChatMessage.Role role, String content) {
        ChatMessage msg = new ChatMessage();
        msg.setSessionId(sessionId);
        msg.setRole(role);
        msg.setContent(content);
        messageMapper.insert(msg);
    }

    @Override
    public Result<String> startNewChat(Integer knowledgeBaseId) {
        // 创建新的聊天会话
        String sessionId = UUID.randomUUID().toString();
        // 这里可能会在数据库中记录会话信息
        
        return Result.success(sessionId);
    }
}