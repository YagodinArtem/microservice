package ru.yagodin.middleware.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;
import ru.yagodin.middleware.entity.Item;
import ru.yagodin.middleware.service.ItemRepositoryService;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepositoryService itemRepositoryService;

    @RabbitListener(queues = "find-item")
    public void getById(String message) {
        itemRepositoryService.workWith(message);
    }

    @RabbitListener(queues = "post-item")
    public void saveItem(String message) {
        itemRepositoryService.saveItem(message);
    }
}
