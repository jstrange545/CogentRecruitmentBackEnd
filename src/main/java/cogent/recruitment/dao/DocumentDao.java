package cogent.recruitment.dao;


import cogent.recruitment.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentDao extends JpaRepository<Document,Integer> {
    Optional<Document> findByApplicationIdAndNameContainingIgnoreCase(int id,String name);
}
