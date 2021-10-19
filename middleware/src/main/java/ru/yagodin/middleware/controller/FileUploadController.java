package ru.yagodin.middleware.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yagodin.middleware.service.FileRepositoryService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileRepositoryService fileRepositoryService;

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public String uploadFile(@RequestParam String fileDescription, @RequestPart MultipartFile file) throws IOException {
        return fileRepositoryService.saveFile(fileDescription, file);
    }

    @GetMapping
    public List<File> findAll() {
       return fileRepositoryService.findAllFiles();
    }
}
