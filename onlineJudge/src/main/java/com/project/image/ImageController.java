package com.project.image;

import com.project.dto.FileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class ImageController {
    @Autowired
    MessageSource messageSource;
    Locale locale = new Locale("KOREA");

    @PostMapping("/images")
    @ResponseBody
    public Map<String, Object> uploadImageFile(ImageData imageData, Model model) throws IOException {
        MultipartFile file = imageData.getImages()[0];

        String originalName = file.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
        String uuid = UUID.randomUUID().toString();

        String messageCode = "path.images." + imageData.getType();
        String uploadPath = messageSource.getMessage(messageCode,null, locale);
        String uuidName = uuid + "_" + fileName;
        String saveFileName = uploadPath + File.separator + uuidName;
        Path savePath = Paths.get(saveFileName);
        file.transferTo(savePath);

        Map<String, Object> result = new HashMap<>();
        result.put("name", uuidName);
        return result;
    }
}
