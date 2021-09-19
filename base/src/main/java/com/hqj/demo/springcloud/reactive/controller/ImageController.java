package com.hqj.demo.springcloud.reactive.controller;

import com.hqj.demo.springcloud.reactive.model.Image;
import com.hqj.demo.springcloud.reactive.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/reactive")
public class ImageController {

    final private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    //@PostMapping(value = "/uploadImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/uploadImages")
    public Mono<Void> uploadImages(@RequestPart Flux<FilePart> images) {
       return imageService.createImage(images);
    }

    @GetMapping("/getAllImages")
    public Flux<Image> getAllImages() {
       return imageService.getAllImages();
    }
}
