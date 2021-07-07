package cogent.recruitment.service;

import java.util.List;

import cogent.recruitment.entities.Job;

public interface JobService {
	
	public List<Job> getAllJobs();
	
	public List<Job> getVacantJobs();
	
	public List<Job> getJobByName(String name);
	
	public Job getJobById(int id);
	
	public String postJob(Job job);
	
	public String updateJob(Job job);

}
