package online.yuuu.aiknowledgehub.service.impl;

import lombok.RequiredArgsConstructor;
import online.yuuu.aiknowledgehub.dto.ChunkScoreDTO;
import online.yuuu.aiknowledgehub.mapper.DocumentChunkMapper;
import online.yuuu.aiknowledgehub.ollma.OllamaEmbeddingClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuuu
 */
@Service
@RequiredArgsConstructor
public class RagQueryService {

    private final OllamaEmbeddingClient embeddingClient;
    private final DocumentChunkMapper chunkMapper;

    @Value("${rag.retrieval.top-k}")
    private int topK;

    @Value("${rag.retrieval.score-threshold}")
    private double scoreThreshold;

    @Value("${rag.prompt-template}")
    private String promptTemplate;

    public String buildRagPrompt(
            Integer knowledgeBaseId,
            String question
    ) {

        float[] embed = embeddingClient.embed(question);

        List<ChunkScoreDTO> chunks =
                chunkMapper.searchTopK(embed, knowledgeBaseId, topK);

        List<ChunkScoreDTO> filtered = chunks.stream()
                .filter(c -> c.getScore() >= scoreThreshold)
                .toList();

        if (filtered.isEmpty()) {
            return "当前知识库中没有找到相关信息，请明确问题或补充文档。";
        }

        String context = buildContext(filtered);

        return promptTemplate
                .replace("{context}", context)
                .replace("{question}", question);
    }

    private String buildContext(List<ChunkScoreDTO> chunks) {
        StringBuilder sb = new StringBuilder();
        for (ChunkScoreDTO chunk : chunks) {
            sb.append(chunk.getContent()).append("\n");
        }
        return sb.toString();
    }
}
