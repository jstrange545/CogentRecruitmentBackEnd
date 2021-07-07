package cogent.recruitment.service;

import java.util.List;
import java.util.Set;

import cogent.recruitment.entities.Application;

public interface ApplicationService {

	List<Application> getAllApplications();

	Application getApplicationById(Integer id);

	Set<Application> getApplicationsByUserId(Integer id);
	
	List<Application> getApplicationsByJobId(Integer id);
	
	String saveApplication(Application application);

	String updateApplication(Application application);

	boolean doesApplicationExist(Integer id);

}
