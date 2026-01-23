package online.yuuu.aiknowledgehub.service.impl;

import online.yuuu.aiknowledgehub.service.TextExtractor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author yuuu
 */
@Component
public class TxtTextExtractor implements TextExtractor {

    @Override
    public String extract(File file) {
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("读取 TXT 失败", e);
        }
    }
}
