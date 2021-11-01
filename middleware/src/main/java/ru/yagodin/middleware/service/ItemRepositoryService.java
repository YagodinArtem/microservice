package ru.yagodin.middleware.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.yagodin.middleware.dto.ItemMessage;
import ru.yagodin.middleware.entity.Item;
import ru.yagodin.middleware.repository.ItemRepository;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemRepositoryService {

    private final ItemRepository itemRepository;

    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    private final String EXCHANGE = "microservice";
    private final String ROUTING_KEY_SAVE = "response";
    private final String ROUTING_KEY_FIND = "find-response";

    public Item findById(Integer id) {
        return itemRepository.findById(id).orElse(new Item());
    }

    public void saveItem(String item) {
        try {
            log.debug("received {}",item);
            Item inputItem = objectMapper.readValue(item, Item.class);
            inputItem.setCreated(new Date());
            itemRepository.save(inputItem);
            rabbitTemplate.send(EXCHANGE, ROUTING_KEY_SAVE,
                    new Message((inputItem + " was successfully saved")
                            .getBytes(StandardCharsets.UTF_8)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void findByIdAndSend(ItemMessage message) {
        Item item = itemRepository.findById(Integer.parseInt(message.getId())).orElse(new Item());
        log.debug(item.toString());
        try {
            rabbitTemplate.send(EXCHANGE, ROUTING_KEY_FIND, new Message(objectMapper.writeValueAsBytes(item)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void workWith(String message) {
        try {
            ItemMessage itemMessage = objectMapper.readValue(message, ItemMessage.class);
            switch (itemMessage.getCommand())
            {
                case FIND: findByIdAndSend(itemMessage);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
