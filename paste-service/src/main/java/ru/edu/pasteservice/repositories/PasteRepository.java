package ru.edu.pasteservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.pasteservice.models.PasteMeta;

@Repository
public interface PasteRepository extends JpaRepository<PasteMeta, String> {

}
