package com.example.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.MultipartHttpMessageReader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by chenjq on 03/05/2018.
 */


@ActiveProfiles("integTest")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FileUploadControllerTest {

  @Autowired
  WebTestClient webTestClient;

  @Test
  public void testUpload() throws IOException, URISyntaxException {

    webTestClient.post().uri("/upload")
        .syncBody(generateBody2())
        .exchange().expectStatus().isOk()
        .expectBody().consumeWith(result -> {
    });
  }

  @Test
  public void requestPart() {
    webTestClient
        .post()
        .uri("/api/requestPart")
        .syncBody(generateBody2())
        .exchange().expectStatus().isOk();
  }

  private MultiValueMap<String, HttpEntity<?>> generateBody2() {
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("file", new ClassPathResource("/testfile.xlsx", MultipartHttpMessageReader.class));
    return builder.build();
  }

}
