package online.yuuu.aiknowledgehub.service.impl;

import online.yuuu.aiknowledgehub.dto.ChunkResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yuuu
 */
@Component
public class TokenChunker {

    public List<ChunkResult> chunk(String text, int chunkSize, int overlap) {
        List<String> tokens = Arrays.asList(text.split("\\s+"));
        List<ChunkResult> results = new ArrayList<>();

        int index = 0;
        for (int start = 0; start < tokens.size(); start += (chunkSize - overlap)) {
            int end = Math.min(start + chunkSize, tokens.size());
            List<String> sub = tokens.subList(start, end);

            results.add(new ChunkResult(
                    String.join(" ", sub),
                    sub.size(),
                    index++
            ));

            if (end == tokens.size()) {
                break;
            }
        }
        return results;
    }
}
