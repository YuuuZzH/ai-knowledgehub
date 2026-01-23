package online.yuuu.aiknowledgehub.service.impl;

import lombok.RequiredArgsConstructor;
import online.yuuu.aiknowledgehub.dto.ChunkResult;
import online.yuuu.aiknowledgehub.entity.DocumentChunk;
import online.yuuu.aiknowledgehub.entity.DocumentProcessTask;
import online.yuuu.aiknowledgehub.mapper.DocumentChunkMapper;
import online.yuuu.aiknowledgehub.mapper.DocumentMapper;
import online.yuuu.aiknowledgehub.mapper.DocumentProcessTaskMapper;
import online.yuuu.aiknowledgehub.ollma.OllamaEmbeddingClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import online.yuuu.aiknowledgehub.entity.Document;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * @author yuuu
 */
@Service
@RequiredArgsConstructor
public class DocumentIngestWorker {

    private final DocumentMapper documentMapper;
    private final DocumentChunkMapper chunkMapper;
    private final DocumentProcessTaskMapper taskMapper;
    private final TokenChunker chunker;
    private final OllamaEmbeddingClient embeddingClient;
    private final TxtTextExtractor textExtractor;

    @Value("${rag.chunk.size}")
    private int chunkSize;

    @Value("${rag.chunk.overlap}")
    private int overlap;

    @Async
    @Transactional
    public void process(Long taskId) {
        DocumentProcessTask task = taskMapper.selectById(taskId);
        try {
            task.setStatus(DocumentProcessTask.TaskStatus.RUNNING);
            taskMapper.updateById(task);

            Document doc = documentMapper.selectById(task.getDocumentId());
            File file = new File(doc.getFilePath());

            String text = textExtractor.extract(file);
            List<ChunkResult> chunks = chunker.chunk(text, chunkSize, overlap);

            for (ChunkResult chunk : chunks) {

                DocumentChunk dc = new DocumentChunk();
                dc.setKnowledgeBaseId(doc.getKnowledgeBaseId());
                dc.setDocumentId(doc.getId());
                dc.setContent(chunk.getContent());
                dc.setTokenCount(chunk.getTokenCount());
                dc.setChunkIndex(chunk.getIndex());
                dc.setEmbedding(embeddingClient.embed(chunk.getContent()));

                chunkMapper.insert(dc);
            }

            task.setStatus(DocumentProcessTask.TaskStatus.SUCCESS);
            taskMapper.updateById(task);

            doc.setStatus(Document.DocumentStatus.COMPLETED);
            documentMapper.updateById(doc);

        } catch (Exception e) {
            task.setStatus(DocumentProcessTask.TaskStatus.FAILED);
            task.setErrorMessage(e.getMessage());
            taskMapper.updateById(task);
        }
    }
}
