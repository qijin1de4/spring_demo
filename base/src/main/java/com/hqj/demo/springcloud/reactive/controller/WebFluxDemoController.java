package com.hqj.demo.springcloud.reactive.controller;

import com.hqj.demo.springcloud.reactive.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class WebFluxDemoController {

    @GetMapping("flux/images")
    public Flux<Image> getImages() {
        return Flux.just(new Image("1", "first image"),
                new Image("2", "second image"),
                new Image("3", "third image"));
    }

    @PostMapping("flux/images")
    public Mono<Void> createImages(@RequestBody Flux<Image> images) {
        return images
                .map(image -> {
                    log.info("We're going to create {} soon!", image);
                    return image;
                })
                .then();
    }
}
