package cogent.recruitment.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cogent.recruitment.entities.ApplicationEvaluation;

@Repository
public interface ApplicationEvaluationDao extends JpaRepository<ApplicationEvaluation, Integer> {
	@Query("select a from ApplicationEvaluation a where a.app.id = :id")
	Optional<List<ApplicationEvaluation>> getEvalsByApplicationId(@Param("id") Integer id);
}