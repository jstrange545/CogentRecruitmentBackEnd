package cogent.recruitment.service;
import cogent.recruitment.dao.InterviewDao;
import cogent.recruitment.entities.Interview;
import cogent.recruitment.entities.MailMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class InterviewService {

    @Autowired
    InterviewDao interviewDao;
    
    @Autowired
    ApplicationService appService;
    
    @Autowired
	private MailService notificationService;
	
	@Autowired
	private MailMessage message;
	
	@Autowired
	UserService uServ;

    /**
     * post a interview to the database
     * @param interview object that is passed from front-end
     */
    @Transactional
    public void scheduleInterview(Interview interview) {
        interviewDao.save(interview);
        Interview dbInterview = interviewDao.getById(interview.getId());
        dbInterview.setApplication(appService.getApplicationById(dbInterview.getApplication().getId()));
        message.setEmailAddress(uServ.getApplicantfromUsers(dbInterview.getApplication().getUsers()).getEmail());
		message.setSubject(dbInterview.getName());
		message.setBodyText("Your "+dbInterview.getName()+
							" is scheduled for "+dbInterview.getStartTime().getDayOfWeek()+  " "+ dbInterview.getStartTime().toLocalDate()+
							" at "+dbInterview.getStartTime().toLocalTime().truncatedTo(ChronoUnit.MINUTES));
		try {
			notificationService.sendEmail(message);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
    }

    /**
     * get all interviews by the applicant id
     * @param id
     * @return
     */
    @Transactional
    public List<Interview> getAllInterviewsByApplicant(int id) {
        List<Interview> interviewList = interviewDao.findAllByApplicationUsersId(id);
        return interviewList;
    }
    
    /**
     * get all completed interviews by application id
     * @param id
     * @return
     */
    @Transactional
    public List<Interview> getCompletedInterviewsByApplication(int id) {
        List<Interview> interviewList = interviewDao.findAllByApplicationIdAndEndTimeBefore(id,LocalDateTime.now());
        return interviewList;
    }

    /**
     * get all incoming interviews by application id
     * @param id
     * @return
     */
    @Transactional
    public List<Interview> getIncomingInterviewByApplication(int id) {
        List<Interview> interviewList = interviewDao.findAllByApplicationIdAndStartTimeAfter(id, LocalDateTime.now());
        return interviewList;
    }

    /**
     * get all completed interviews by applicant id
     * @param id
     * @return
     */
    @Transactional
    public List<Interview> getCompletedInterviewsByApplicant(int id) {
        List<Interview> interviewList = interviewDao.findAllByApplicationUsersIdAndEndTimeBefore(id,LocalDateTime.now());
        return interviewList;
    }

    /**
     * get all incoming interviews by applicant id
     * @param id
     * @return
     */
    @Transactional
    public List<Interview> getIncomingInterviewByApplicant(int id) {
        List<Interview> interviewList = interviewDao.findAllByApplicationUsersIdAndStartTimeAfter(id, LocalDateTime.now());
        return interviewList;
    }

    /**
     * delete interview by interview id
     * @param id
     */
    @Transactional
    public void deleteInterviewById(int id) {
        interviewDao.deleteById(id);
    }

    /**
     * update hte interview start time and end time
     * @param id
     * @param newStart
     * @param newEnd
     */
    @Transactional
    public void updateInterview(int id, LocalDateTime newStart, LocalDateTime newEnd){
        interviewDao.updateInterviewById(newStart,newEnd,id);
        Interview dbInterview = interviewDao.getById(id);
		message.setEmailAddress(uServ.getApplicantfromUsers(dbInterview.getApplication().getUsers()).getEmail());
		message.setSubject(dbInterview.getName());
		message.setBodyText("Your "+dbInterview.getName()+
							" is now scheduled for "+dbInterview.getStartTime().getDayOfWeek()+ " "+ dbInterview.getStartTime().toLocalDate()+
							" at "+newStart.toLocalTime().truncatedTo(ChronoUnit.MINUTES));
		try {
			notificationService.sendEmail(message);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
    }

}
