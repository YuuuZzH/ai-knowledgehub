package online.yuuu.aiknowledgehub.ollma;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OllamaEmbeddingClient {

    @Value("${ollama.base-url}")
    private String baseUrl;

    @Value("${ollama.embedding.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public float[] embed(String text) {
        Map<String, Object> req = Map.of(
                "model", model,
                "prompt", text
        );

        Map resp = restTemplate.postForObject(
                baseUrl + "/api/embeddings",
                req,
                Map.class
        );

        List<Double> embedding = (List<Double>) resp.get("embedding");
        float[] vector = new float[embedding.size()];
        for (int i = 0; i < embedding.size(); i++) {
            vector[i] = embedding.get(i).floatValue();
        }
        return vector;
    }
}
