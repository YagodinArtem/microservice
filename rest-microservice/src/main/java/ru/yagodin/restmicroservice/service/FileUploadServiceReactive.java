package ru.yagodin.restmicroservice.service;


import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FileUploadServiceReactive {

    private final WebClient webClient;

    private final ResourceLoader resourceLoader;

    public static final File tempDir;


    static {
        tempDir = new File(System.getProperty("user.home") + "/Desktop" + "/temp");
        if (!tempDir.exists()) {
            var mkdir = tempDir.mkdir();
        }
    }

    public List<File> getAll() throws ExecutionException, InterruptedException {
       return webClient
                .get()
                .uri("/file")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<File>() {
                })
                .doOnNext(this::makeCopyAndAdd)
                .collectList()
                .toFuture()
                .get();
    }

    public Flux<String> uploadFile(FilePart file) {
        return null;
    }



    private void makeCopyAndAdd(File file) {
        try {
            Files.copy(file, new File(tempDir + "/" + file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
