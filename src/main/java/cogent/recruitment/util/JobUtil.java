package cogent.recruitment.util;

import org.springframework.stereotype.Component;

import cogent.recruitment.entities.Job;

@Component
public class JobUtil {
	
public void mergeJob(Job newJob, Job oldJob) {
		
		if(newJob.getBatch() !=null) {
			oldJob.setBatch(newJob.getBatch());
		}
		
		if(newJob.getName() !=null) {
			oldJob.setName(newJob.getName());
		}
		
		if(newJob.getDescription() !=null) {
			oldJob.setDescription(newJob.getDescription());
		}
		
		if(newJob.getVacancies() != 0) {
			oldJob.setVacancies(newJob.getVacancies());
		}
		
		if(newJob.getDatestart() !=null) {
			oldJob.setDatestart(newJob.getDatestart());
		}
		
	}
	
}
