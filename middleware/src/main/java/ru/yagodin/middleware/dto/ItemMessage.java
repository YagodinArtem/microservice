package ru.yagodin.middleware.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yagodin.middleware.util.Command;


@Data
@AllArgsConstructor
public class ItemMessage {

    private String id;
    private Command command;
    private final String entity = "item";
}
