package ru.yagodin.restmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yagodin.restmicroservice.dto.ItemDTO;
import ru.yagodin.restmicroservice.service.ItemServiceMiddleware;
import ru.yagodin.restmicroservice.service.ItemServiceReactive;

@RestController
@RequestMapping(value = "/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemServiceMiddleware itemServiceMiddleware;

    private final ItemServiceReactive itemServiceReactive;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ItemDTO getById(@PathVariable String id) {
        return itemServiceReactive.findById(id);
    }

    @PostMapping
    public ItemDTO saveItem(@RequestBody ItemDTO item) {
        return itemServiceReactive.saveItem(item);
    }

}
