package cogent.recruitment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cogent.recruitment.dao.JobDao;
import cogent.recruitment.entities.Job;

@Service
public class JobServiceImpl implements JobService{
	
	@Autowired
	JobDao dao;

	@Override
	public List<Job> getAllJobs() {
		return dao.findAll();
	}
	
	@Override
	public List<Job> getVacantJobs() {
		return dao.findVacantJobs().get();
	}

	@Override
	public List<Job> getJobByName(String name) {
		return dao.findByname(name).get();
	}

	@Override
	public Job getJobById(int id) {
		return dao.findById(id).get();
	}

	@Override
	public String postJob(Job job) {
		dao.save(job);
		return job.toString();
	}

	@Override
	public String updateJob(Job job) {
		dao.save(job);
		return job.toString();
	}
	
	

}
