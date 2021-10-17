package ru.yagodin.restmicroservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Data
public class ItemDTO {

    private Integer id;

    private String title;

    private Date created;
}
