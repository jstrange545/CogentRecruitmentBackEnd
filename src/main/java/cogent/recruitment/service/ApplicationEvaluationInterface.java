package cogent.recruitment.service;

import java.util.List;

import cogent.recruitment.entities.ApplicationEvaluation;

public interface ApplicationEvaluationInterface {
	
	public void saveApplicationEvaluation(ApplicationEvaluation app);
	public ApplicationEvaluation getApplicationEvaluationById(int id);
	public List<ApplicationEvaluation> getAllApplicationEvaluation();
	public List<ApplicationEvaluation> getApplicationEvaluationsById(int id);
	
	

}
