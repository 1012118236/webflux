package com.example.webflux.controller;

import com.example.webflux.beans.Result;
import com.example.webflux.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

/**
 * 图像识别请求
 */
@RestController
@Slf4j
public class ImageIdentificationController {

    /**
     * 根据image url标注图片
     * @param imageUrl 图片url 校验url合法性  以及url有效性
     * @return
     */
    @GetMapping("/imageIdentificationUrl")
    public Mono imageIdentificationUrl(@RequestParam String imageUrl) {
        try {
            FileUtils.verifyUrlIsImages(imageUrl);
            return Mono.just(Result.ok());
        } catch (Exception e) {
            log.error(" file download error is {} .  url is : {}",e,imageUrl);
            return Mono.error(e);
        }
    }

    /**
     * 批量接收图片上传操作 并进行标注服务
     * @param
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flux requestBodyFlux(@RequestPart("file") Flux<FilePart> file) {
        return file.flatMap(filePart -> {
            AsynchronousFileChannel channel = null;
            try {
                //接收数据到本地文件
                log.debug("images name is ：{}",filePart.filename());
                String filename = filePart.filename();
                String nameSuffix = filename.substring(filename.lastIndexOf("."));
                Path tempFile = Files.createTempFile("test", UUID.randomUUID() + nameSuffix);
                channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
                DataBufferUtils.write(filePart.content(), channel, 0).subscribe();
                return Mono.just(Result.ok());
            } catch (Exception e) {
                log.error("{}",e);
                return Flux.error(e);
            } finally {
                if(channel!=null){
                    try {
                        channel.close();
                    } catch (IOException e) {
                        log.error("{}",e);
                    }
                }
            }
        });
    }

}
