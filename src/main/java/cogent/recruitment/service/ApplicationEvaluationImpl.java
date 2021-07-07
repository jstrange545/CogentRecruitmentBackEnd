package cogent.recruitment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import cogent.recruitment.dao.ApplicationEvaluationDao;
import cogent.recruitment.entities.Application;
import cogent.recruitment.entities.ApplicationEvaluation;
import cogent.recruitment.entities.Job;
import cogent.recruitment.entities.MailMessage;
import cogent.recruitment.exception.ResourceNotFoundException;

@Service
public class ApplicationEvaluationImpl implements ApplicationEvaluationInterface{
	@Autowired
	ApplicationEvaluationDao applicationevaluationdao;
	
	@Autowired
	private MailService notificationService;
	
	@Autowired
	UserService uServ;
	
	@Autowired
	JobService jServ;
	
	@Autowired
	ApplicationService aServ;
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	private MailMessage message;

	public void saveApplicationEvaluation(ApplicationEvaluation app)
	{
		applicationevaluationdao.save(app);
		ApplicationEvaluation dbApp = applicationevaluationdao.getById(app.getId());
		Application thisApp = applicationService.getApplicationById(dbApp.getApp().getId());
		if (dbApp.getAppStatus().getId()==5) {
			Job newJob = thisApp.getJob();
			newJob.setVacancies(newJob.getVacancies()-1);
			jServ.postJob(newJob);
			System.out.println("the job"+newJob.toString());
		}
		Application application = applicationService.getApplicationById(app.getApp().getId());
		message.setEmailAddress(uServ.getApplicantfromUsers(application.getUsers()).getEmail());
		message.setSubject(app.getAppStatus().getStat().name().toString().replace('_', ' '));
		message.setBodyText(app.getAppStatus().getStat().getMsgStatus());
		try {
			notificationService.sendEmail(message);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
	}
	
	public ApplicationEvaluation getApplicationEvaluationById(int id)
	{
		return applicationevaluationdao.getById(id);
	}

	public List<ApplicationEvaluation> getAllApplicationEvaluation()
	{
		return applicationevaluationdao.findAll();
	}

	public List<ApplicationEvaluation> getApplicationEvaluationsById(int id)
	{
		List<ApplicationEvaluation> appList;
		appList = applicationevaluationdao.getEvalsByApplicationId(id)
				.orElseThrow(() -> new ResourceNotFoundException("No evaluations associated with this application."));
		return appList;
	}



}

