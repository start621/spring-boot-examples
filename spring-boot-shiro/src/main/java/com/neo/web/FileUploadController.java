package com.neo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * spring-boot-shiro-FileUploadController
 * 提供文件上传下载能力
 *
 * @author yh
 * @since 0.0.1 2017-09-09 16:38
 */
@Slf4j
@Controller
public class FileUploadController {

    private static final String FILE_SAVE_PATH = "Y:\\";

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                byte[] bytes = file.getBytes();
                Path path = Paths.get(FILE_SAVE_PATH + file.getOriginalFilename());
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            return "上传成功";
        } else {
            return "上传失败，因为文件是空的.";
        }
    }

    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    public @ResponseBody
    String batchUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file;

        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    // stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                    // stream.write(bytes);
                    // stream.close();
                    Path path = Paths.get(FILE_SAVE_PATH + file.getOriginalFilename());
                    Files.write(path, bytes);
                } catch (Exception e) {
                    // stream.close();
                    return "上传第" + i + "个文件失败. \n" + e.getMessage();
                }
            } else {
                return "上传第" + (i + 1) + "个文件失败，因为文件是空的.";
            }
        }
        return "上传成功";
    }
}
