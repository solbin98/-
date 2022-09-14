package com.project.image;

import com.project.dto.FileDto;
import com.project.file.FileService;
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
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ImageController {
    @Autowired
    MessageSource messageSource;
    @Autowired
    FileService fileService;

    Locale locale = new Locale("KOREA");

    @PostMapping("/images")
    @ResponseBody
    public Map<String, Object> uploadImageFile(ImageData imageData, Model model) throws Exception {
        MultipartFile file = imageData.getImages()[0];

        String originalName = file.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
        String uuid = UUID.randomUUID().toString();

        String uuidName = uuid + "_" + fileName;
        String messageCode = "path.images." + imageData.getType();
        String uploadPath = messageSource.getMessage(messageCode,null, locale);
        String saveFileName = uploadPath + File.separator + uuidName;
        fileService.addFile(new FileDto(0, uuidName, uploadPath+"\\"+saveFileName, imageData.getType(), LocalDateTime.now(), false));
        int fileId = fileService.getLastFileId();
        Path savePath = Paths.get(saveFileName);
        file.transferTo(savePath);

        Map<String, Object> result = new HashMap<>();
        result.put("fileName", uuidName);
        result.put("fileId", fileId);
        return result;
    }
}
