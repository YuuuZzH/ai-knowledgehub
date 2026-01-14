package online.yuuu.aiknowledgehub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("online.yuuu.aiknowledgehub.mapper")
public class AiKnowledgehubApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiKnowledgehubApplication.class, args);
    }

}
