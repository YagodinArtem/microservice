package ru.yagodin.restmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yagodin.restmicroservice.dto.ItemDTO;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceReactive {

    private final WebClient webClient;

    public ItemDTO findById(String id) {
        try {
            return webClient
                    .get()
                    .uri(id)
                    .retrieve()
                    .bodyToMono(ItemDTO.class)
                    .doOnError(error -> log.error("Obtain an error " + Arrays.toString(error.getStackTrace())))
                    .toFuture()
                    .whenCompleteAsync((itemDTO, throwable) -> log.debug("Complete " + itemDTO))
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ItemDTO();
        }
    }

    public ItemDTO saveItem(ItemDTO item) {
        try {
            return webClient
                    .post()
                    .uri("")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(item), ItemDTO.class)
                    .retrieve()
                    .bodyToMono(ItemDTO.class)
                    .toFuture()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ItemDTO();
        }
    }
}
