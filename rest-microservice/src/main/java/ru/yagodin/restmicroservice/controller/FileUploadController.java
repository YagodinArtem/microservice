package ru.yagodin.restmicroservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yagodin.restmicroservice.dto.FileDTO;
import ru.yagodin.restmicroservice.service.FileUploadService;

import java.util.List;

@RestController
@RequestMapping(value = "/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> uploadFile(@RequestParam String fileDescription, @RequestPart MultipartFile file) {
        return fileUploadService.uploadFile(fileDescription, file);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public String getAll() {
        return fileUploadService.getAll();
    }

}
