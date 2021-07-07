package cogent.recruitment.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import cogent.recruitment.entities.Job;

@Repository
@CrossOrigin
public interface JobDao extends JpaRepository<Job, Integer> {
	
	@Query(value = "from Job j where j.name LIKE CONCAT('%',:text,'%')")
	Optional<List<Job>> findByname(@Param("text") String name);
	
	@Query(value = "from Job j where j.vacancies > 0")
	Optional<List<Job>> findVacantJobs();
	
}
