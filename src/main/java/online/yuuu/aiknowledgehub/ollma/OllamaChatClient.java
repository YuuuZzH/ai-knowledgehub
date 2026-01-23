package online.yuuu.aiknowledgehub.ollma;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OllamaChatClient {

    @Value("${ollama.base-url}")
    private String baseUrl;

    @Value("${ollama.chat.model}")
    private String model;

    @Value("${ollama.chat.temperature}")
    private double temperature;

    private final RestTemplate restTemplate = new RestTemplate();

    public String chat(List<Map<String, String>> messages) {
        Map<String, Object> req = Map.of(
                "model", model,
                "messages", messages,
                "temperature", temperature,
                "stream", false
        );

        Map resp = restTemplate.postForObject(
                baseUrl + "/api/chat",
                req,
                Map.class
        );

        Map message = (Map) resp.get("message");
        return (String) message.get("content");
    }
}
