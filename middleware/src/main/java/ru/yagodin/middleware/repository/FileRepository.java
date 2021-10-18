package ru.yagodin.middleware.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.yagodin.middleware.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}
