package ru.yagodin.restmicroservice.service;


import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadServiceMiddleware {

    private static final File tempDir;

    static {
        tempDir = new File(System.getProperty("user.home") + "/Desktop" + "/temp");
        if (!tempDir.exists()) {
            var mkdir = tempDir.mkdir();
        }
    }

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

    public String getAll() {
        ResponseEntity<List<File>> result
                = restTemplate.exchange(
                MIDDLEWARE_URI + "/file",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });


        if (result.getStatusCode() == HttpStatus.OK && result.getBody() != null) {
            final StringBuilder sb = new StringBuilder();
            sb.setLength(0);
            List<File> body = result.getBody();
            body.forEach(f -> {
                sb
                        .append(f.getName())
                        .append(" ")
                        .append(f.length())
                        .append(" ")
                        .append(f.isFile())
                        .append(" ")
                        .append("\n");
                try {
                    Files.copy(f, new File(tempDir + "/" + f.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return sb.toString();
        }
        return "";
    }
}
