package config;

import manager.FileResourceManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileResourceManagerAutoConfiguration {

    @Bean
    public FileResourceManager fileResourceManager() {
        return new FileResourceManager();
    }
}
