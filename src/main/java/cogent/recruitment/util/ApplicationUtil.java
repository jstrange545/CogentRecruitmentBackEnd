package cogent.recruitment.util;

import org.springframework.stereotype.Component;

import cogent.recruitment.entities.Application;

@Component
public class ApplicationUtil {
	public void copyNonNullValues(Application req, Application db) {

		if (req.isUSCitizen()) {
			db.setUSCitizen(req.isUSCitizen());
		}
		
		if(!(req.isUSCitizen())) {
			db.setUSCitizen(false);
		}

		if (req.getLocation() != null) {
			db.setLocation(req.getLocation());
		}

		if (req.getVisaStatus() != null) {
			db.setVisaStatus(req.getVisaStatus());
		}

		if (req.getDegree() != null) {
			db.setDegree(req.getDegree());
		}
		
		if (req.getCoreJavaRating() > 0 && req.getCoreJavaRating() <= 10) {
			db.setCoreJavaRating(req.getCoreJavaRating());
		}

		if (req.getAdvancedJavaRating() > 0 && req.getAdvancedJavaRating() <= 10) {
			db.setAdvancedJavaRating(req.getAdvancedJavaRating());
		}

		if (req.getOopsRating() > 0 && req.getOopsRating() <= 10) {
			db.setOopsRating(req.getOopsRating());
		}
		
		if (req.getExceptionHandlingRating() > 0 && req.getExceptionHandlingRating() <= 10) {
			db.setExceptionHandlingRating(req.getExceptionHandlingRating());
		}
		
		if (req.getMultithreadingRating() > 0 && req.getMultithreadingRating() <= 10) {
			db.setMultithreadingRating(req.getMultithreadingRating());
		}
		
		if (req.getCollectionFrameworkRating() > 0 && req.getCollectionFrameworkRating() <= 10) {
			db.setCollectionFrameworkRating(req.getCollectionFrameworkRating());
		}
		
		if (req.getJdbcRating() > 0 && req.getJdbcRating() <= 10) {
			db.setJdbcRating(req.getJdbcRating());
		}
		
		if(req.getUsers() != null) {
			db.setUsers(req.getUsers());
		}
		
		if(req.getJob() != null) {
			db.setJob(req.getJob());
		}
	}
}
