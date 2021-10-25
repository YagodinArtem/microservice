package ru.yagodin.restmicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.yagodin.restmicroservice.dto.ItemDTO;
import ru.yagodin.restmicroservice.dto.ItemMessage;
import ru.yagodin.restmicroservice.util.Command;


@Service
@RequiredArgsConstructor
public class ItemServiceRabbitMQ {

    private final RabbitTemplate rabbit;

    private final ObjectMapper mapper;

    private final String EXCHANGE = "microservice";
    private final String ROUTING_KEY_FIND = "find";
    private final String ROUTING_KEY_POST = "post";


    public Message sendFindByIdQue(String id) {
        rabbit.send(EXCHANGE, ROUTING_KEY_FIND, new Message(convertToItemMessage(id)));
        return rabbit.receive("find-response", 10000);
    }

    public void save(ItemDTO itemDTO) {
        try {
            rabbit.convertAndSend(EXCHANGE, ROUTING_KEY_POST, mapper.writeValueAsString(itemDTO));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private byte[] convertToItemMessage(String id) {
        try {
            return mapper.writeValueAsBytes(new ItemMessage(id, Command.FIND));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
