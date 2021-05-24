package com.example.webflux.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MultipartController {

    @PostMapping(value = "/upload1",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Flux<String> upload(@RequestBody Flux<Part> parts) {
        return parts
                .filter(p -> p instanceof FilePart)
                .cast(FilePart.class)
                .flatMap(this::saveFile)
                .map(File::getName);
    }

    private Mono<File> saveFile(FilePart filePart) {
        Path target = Paths.get("").resolve(filePart.filename());
        try {
            Files.deleteIfExists(target);
            File file = Files.createFile(target).toFile();
            return filePart.transferTo(file).map(r -> file);
        } catch (IOException e) {
//			log.info("An error occurred while saving the file...");
            return Mono.empty();
        }
    }
}