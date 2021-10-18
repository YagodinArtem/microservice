package ru.yagodin.restmicroservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.yagodin.restmicroservice.dto.FileDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final RestTemplate restTemplate;

    private final static String MIDDLEWARE_URI = "http://middleware/";

    public ResponseEntity<String> uploadFile(String fileDescription, MultipartFile file) {

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        body.add("file", file.getResource());
        body.add("fileDescription", fileDescription);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        return restTemplate
                .postForEntity(MIDDLEWARE_URI + "/file", requestEntity, String.class);

    }

    public List<FileDTO> getAll() {
        //todo
        return null;
    }
}
