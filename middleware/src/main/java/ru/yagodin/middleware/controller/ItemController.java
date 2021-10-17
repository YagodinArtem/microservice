package ru.yagodin.middleware.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yagodin.middleware.entity.Item;
import ru.yagodin.middleware.service.ItemRepositoryService;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepositoryService itemRepositoryService;

    @GetMapping(value = "/{id}")
    public Item getById(@PathVariable Integer id) {
        return itemRepositoryService.findById(id);
    }

    @PostMapping
    public Item saveItem(@RequestBody String item) {
        return itemRepositoryService.saveItem(item);
    }
}
