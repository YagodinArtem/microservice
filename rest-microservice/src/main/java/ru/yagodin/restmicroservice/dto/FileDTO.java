package ru.yagodin.restmicroservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Data
public class FileDTO {

    private MultipartFile file;

    private String fileName;

}
