package cogent.recruitment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cogent.recruitment.entities.ApplicationStatus;

@Repository
public interface ApplicationStatusDao extends JpaRepository<ApplicationStatus, Integer> {

}
