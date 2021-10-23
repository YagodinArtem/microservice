package ru.yagodin.middleware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yagodin.middleware.entity.FileEntity;
import ru.yagodin.middleware.repository.FileRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public List<File> findAllFiles() {
        List<FileEntity> allFiles = fileRepository.findAll();
        for (FileEntity entity : allFiles) {

            try (FileOutputStream fos = new FileOutputStream("temp/" + entity.getDescription())) {
                fos.write(entity.getFile());
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File folder = new File("temp");
        assert folder.listFiles() != null;
        return List.of(folder.listFiles());
    }

}
