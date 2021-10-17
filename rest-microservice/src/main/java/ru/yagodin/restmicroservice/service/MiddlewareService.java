package ru.yagodin.restmicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yagodin.restmicroservice.dto.ItemDTO;


@Service
@RequiredArgsConstructor
public class MiddlewareService {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final static String MIDDLEWARE_URI = "http://middleware/";

    public ResponseEntity<ItemDTO> findById(String id) {
        ItemDTO itemDTO = restTemplate.getForEntity(MIDDLEWARE_URI + "/" + id,
                ItemDTO.class).getBody();

        assert itemDTO != null;
        HttpStatus status = (itemDTO.getTitle() == null) ?  HttpStatus.NOT_FOUND : HttpStatus.OK;

        return new ResponseEntity<>(itemDTO, status);
    }

    public ItemDTO saveItem(ItemDTO item) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(
                        objectMapper.valueToTree(item).toString(), headers);

        String itemResult = restTemplate.postForObject(MIDDLEWARE_URI, request, String.class);
        try {
            return objectMapper.readValue(itemResult, ItemDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ItemDTO();
    }
}
