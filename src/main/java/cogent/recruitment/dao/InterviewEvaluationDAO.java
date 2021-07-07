package cogent.recruitment.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cogent.recruitment.entities.InterviewEvaluation;

@Repository
public interface InterviewEvaluationDAO extends JpaRepository<InterviewEvaluation, Integer>{
	
	//To find Evaluations by Interview ID
	@Query("select i from InterviewEvaluation i where i.interview.id = :id")
	Optional<List<InterviewEvaluation>> findByInterviewId(Integer id);
	
	
}
