package com.fileUpload.fileUpload.controllers;

import com.fileUpload.fileUpload.Services.FileUploadService;
import com.fileUpload.fileUpload.modal.FileDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FileController {
    @Autowired
    FileUploadService fileUploadService;
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestParam MultipartFile file) throws IOException {
        fileUploadService.unzip(file.getInputStream(), file.getOriginalFilename());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/upload")
    public ResponseEntity<?> getUploadFiles()  {
        List<FileDetails> files = fileUploadService.getAllFile();
        return  new ResponseEntity<>(files, HttpStatus.CREATED);
    }


}














