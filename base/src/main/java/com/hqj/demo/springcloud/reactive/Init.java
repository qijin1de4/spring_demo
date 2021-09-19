package com.hqj.demo.springcloud.reactive;

import com.hqj.demo.springcloud.reactive.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Init {

    @Bean
    public CommandLineRunner initMongoDb(ReactiveMongoOperations operations){
        return args -> {
            operations.dropCollection(Image.class).subscribe();
            operations.insert(new Image("1", "firstImage.jpg")).subscribe();
            operations.insert(new Image("2", "secondImage.jpg")).subscribe();
            operations.insert(new Image("3", "thirdImage.jpg")).subscribe();
            operations.findAll(Image.class).subscribe(image -> log.info("find image : {}", image));
        };
    }
}
