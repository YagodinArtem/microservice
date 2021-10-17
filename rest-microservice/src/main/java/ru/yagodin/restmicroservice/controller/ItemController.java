package ru.yagodin.restmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yagodin.restmicroservice.dto.ItemDTO;
import ru.yagodin.restmicroservice.service.MiddlewareService;

@RestController
@RequestMapping(value = "/item")
@RequiredArgsConstructor
public class ItemController {

    private final MiddlewareService middlewareService;

    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ItemDTO> getById(@PathVariable String id) {
        return middlewareService.findById(id);
    }

    @PostMapping
    public ItemDTO saveItem(@RequestBody ItemDTO item) {
        return middlewareService.saveItem(item);
    }

}
