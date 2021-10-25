package ru.yagodin.restmicroservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yagodin.restmicroservice.dto.ItemDTO;
import ru.yagodin.restmicroservice.service.ItemServiceMiddleware;
import ru.yagodin.restmicroservice.service.ItemServiceRabbitMQ;
import ru.yagodin.restmicroservice.service.ItemServiceReactive;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemServiceMiddleware itemServiceMiddleware;

    private final ItemServiceReactive itemServiceReactive;

    private final ItemServiceRabbitMQ rabbitService;


    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String getById(@PathVariable String id) {
        return new String(rabbitService.sendFindByIdQue(id).getBody());
    }

    @PostMapping
    public void saveItem(@RequestBody ItemDTO item) {
        rabbitService.save(item);
    }

    @RabbitListener(queues = "post-response")
    public void postResponseListener(Message message) {
        String content = new String(message.getBody(), StandardCharsets.UTF_8);
        log.debug("received {}", content);
    }
}
