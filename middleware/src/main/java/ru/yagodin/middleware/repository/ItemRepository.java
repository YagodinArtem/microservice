package ru.yagodin.middleware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yagodin.middleware.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

}
