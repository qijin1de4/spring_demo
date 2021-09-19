package com.hqj.demo.springcloud.reactive.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "images")
public class Image {

    @Id
    final private String id;
    final private String name;
}