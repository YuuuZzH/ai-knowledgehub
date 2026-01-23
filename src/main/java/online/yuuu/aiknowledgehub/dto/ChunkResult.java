package online.yuuu.aiknowledgehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuuu
 */
@Data
@AllArgsConstructor
public class ChunkResult {
    private String content;
    private int tokenCount;
    private int index;
}
