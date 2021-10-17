package ru.yagodin.middleware.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yagodin.middleware.entity.Item;
import ru.yagodin.middleware.repository.ItemRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ItemRepositoryService {

    private final ItemRepository itemRepository;

    private final ObjectMapper objectMapper;

    public Item findById(Integer id) {
        return itemRepository.findById(id).orElse(new Item());
    }

    public Item saveItem(String item) {
        try {
            Item inputItem = objectMapper.readValue(item, Item.class);
            inputItem.setCreated(new Date());
            return itemRepository.save(inputItem);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new Item();
    }

}
