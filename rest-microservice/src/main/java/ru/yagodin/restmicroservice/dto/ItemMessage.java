package ru.yagodin.restmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yagodin.restmicroservice.util.Command;


@Data
@AllArgsConstructor
public class ItemMessage {

    private String id;
    private Command command;
    private final String entity = "item";
}
