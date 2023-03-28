package com.example.shop.service.Implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileService {

    @Value("${productsImage.path}")
    private String productsImagePath;

    public File create(String productName, MultipartFile file) throws IOException {
        File uploadFolder = new File(productsImagePath);

        if (!uploadFolder.exists()){
            uploadFolder.mkdir();
        }

        String originFileName = file.getOriginalFilename();

        String fileName = productName + originFileName.substring(originFileName.lastIndexOf('.'));

        File resultFile = new File(productsImagePath + "/" +fileName);

        file.transferTo(resultFile);

        return resultFile;
    }

    public void delete(String productName) throws IOException {
        if(!Files.deleteIfExists(new File(productsImagePath + "/" + productName + ".png").toPath())){
            Files.deleteIfExists(new File(productsImagePath + "/" + productName + ".jpg").toPath());
        }
    }

}
