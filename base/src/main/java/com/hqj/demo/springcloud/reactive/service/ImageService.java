package com.hqj.demo.springcloud.reactive.service;

import com.hqj.demo.springcloud.reactive.dao.ImageRepository;
import com.hqj.demo.springcloud.reactive.model.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${demo.reactive.images.uploadDir:uploadDir}")
    private String UPLOAD_ROOT;

    private final ResourceLoader resourceLoader;

    private final ImageRepository imageRepository;

    public ImageService(ResourceLoader resourceLoader, ImageRepository imageRepository) {
        this.resourceLoader = resourceLoader;
        this.imageRepository = imageRepository;
    }

    public Flux<Image> getAllImages(){
       return imageRepository.findAll();
    }

    public Mono<Void> createImage(Flux<FilePart> files) {
        return files.flatMap(file -> {
            Mono<Image> saveImage2Mongo = imageRepository.save(new Image(UUID.randomUUID().toString(), file.filename()));
            Mono<Void> copyImageFile = Mono.just(Paths.get(UPLOAD_ROOT, file.filename()).toFile())
                    .log("About to create dest image file")
                    .map(destFile -> {
                        try{
                            destFile.createNewFile();
                            return destFile;
                        }catch(IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .log("created dest file")
                    .flatMap(file::transferTo)
                    .log("fininshed image file copy");
            return Mono.when(saveImage2Mongo, copyImageFile);
        })
        .then();
    }

    public Mono<Void> deleteImage(String fileName) {
        Mono<Void> deleteFromRepository = imageRepository.findByName(fileName).flatMap(imageRepository::delete);
        Mono<Void> deleteImageFile = Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(Paths.get(UPLOAD_ROOT, fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return Mono.when(deleteFromRepository, deleteImageFile).then();
    }

    public CommandLineRunner setup() throws IOException {
        return args -> {
            FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));
            Files.createDirectory(Paths.get(UPLOAD_ROOT));
            FileCopyUtils.copy("Test file", new FileWriter(UPLOAD_ROOT + "/learning-spring-boot-cover.jpg"));
            FileCopyUtils.copy("Test file2", new FileWriter(UPLOAD_ROOT + "/learning-spring-boot-2nd-edition-cover.jpg"));
            FileCopyUtils.copy("Test file3", new FileWriter(UPLOAD_ROOT + "/bazinga.png"));
        };
    }
}
