package com.example.webflux.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

/**
 * 文件处理工具类
 */
@Slf4j
public class FileUtils {

    //图片格式
    private final static String[] FILETYPES = new String[]{
            ".jpg", ".bmp", ".jpeg", ".png", ".gif",
            ".JPG", ".BMP", ".JPEG", ".PNG", ".GIF"
    };


    /**
     * 校验 url  是否有效 及后缀是否合法
     * @param urlString 图片http url
     * @throws Exception
     */
    public static void verifyUrlIsImages(String urlString) throws Exception {
        String nameSuffix = urlString.substring(urlString.lastIndexOf("."));
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();
        is.close();
        Assert.isTrue(Arrays.stream(FILETYPES).anyMatch(a -> a.equals(nameSuffix)),"Illegal format");
    }
}
