package ru.yagodin.middleware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yagodin.middleware.entity.FileEntity;
import ru.yagodin.middleware.repository.FileRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileRepositoryService {

    private final FileRepository fileRepository;

    public String saveFile(String fileDescription, MultipartFile file) throws IOException {
        FileEntity fileEntity = new FileEntity();

        fileEntity.setFile(file.getBytes());
        fileEntity.setDescription(fileDescription);

        fileRepository.save(fileEntity);

        return file.getName() + " " + file.getSize() + " " + "Successfully stored";
    }

    public List<FileEntity> findAllFiles() {
        //todo
        return null;
    }

}
