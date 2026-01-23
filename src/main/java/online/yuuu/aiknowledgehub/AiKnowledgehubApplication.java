package online.yuuu.aiknowledgehub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("online.yuuu.aiknowledgehub.mapper")
@EnableAsync
public class AiKnowledgehubApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiKnowledgehubApplication.class, args);
    }

}
