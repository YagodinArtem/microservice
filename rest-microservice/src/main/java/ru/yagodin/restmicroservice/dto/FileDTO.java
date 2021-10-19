package ru.yagodin.restmicroservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.File;


@RequiredArgsConstructor
@Data
public class FileDTO {

    private Integer id;

    private File file;

    private String description;

}
