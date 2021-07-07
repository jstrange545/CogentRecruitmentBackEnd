package cogent.recruitment.controller;


import cogent.recruitment.entities.Interview;
import cogent.recruitment.service.InterviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
public class InterviewController {

    private Logger logger = LoggerFactory.getLogger(InterviewController.class);
    @Autowired
    InterviewService interviewService;

    @PreAuthorize("hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
    @PostMapping(value = "/interview")
    public ResponseEntity<?> postInterview(@RequestBody Interview interview){
        logger.info(interview.toString());
        interviewService.scheduleInterview(interview);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
    @GetMapping(value = "/interview")
    public ResponseEntity<?> getAllInterviewsByApplicant(@RequestParam("id") int id){
        List<Interview> interviewList = interviewService.getAllInterviewsByApplicant(id);
        logger.info(interviewList.toString());
        return new ResponseEntity<>(interviewList, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('USER') or hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
    @GetMapping(value = "/interview/appcompleted")
    public ResponseEntity<?> getCompletedInterviewsByApplication(@RequestParam("id") int id){
        List<Interview> interviewList = interviewService.getCompletedInterviewsByApplication(id);
        logger.info(interviewList.toString());
        return new ResponseEntity<>(interviewList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
    @GetMapping(value = "/interview/appincoming")
    public ResponseEntity<?> getIncomingInterviewsByApplication(@RequestParam("id") int id){
        List<Interview> interviewList = interviewService.getIncomingInterviewByApplication(id);
        logger.info(interviewList.toString());
        return new ResponseEntity<>(interviewList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
    @GetMapping(value = "/interview/completed")
    public ResponseEntity<?> getCompletedInterviewsByApplicant(@RequestParam("id") int id){
        List<Interview> interviewList = interviewService.getCompletedInterviewsByApplicant(id);
        logger.info(interviewList.toString());
        return new ResponseEntity<>(interviewList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
    @GetMapping(value = "/interview/incoming")
    public ResponseEntity<?> getIncomingInterviewsByApplicant(@RequestParam("id") int id){
        List<Interview> interviewList = interviewService.getIncomingInterviewByApplicant(id);
        logger.info(interviewList.toString());
        return new ResponseEntity<>(interviewList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
    @DeleteMapping(value = "/interview")
    public ResponseEntity<?> deleteInterviewById(@RequestParam("id") int id){
        interviewService.deleteInterviewById(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
    @PutMapping(value = "/interview")
    public ResponseEntity<?> updateInterview(@RequestBody Interview interview){
        logger.info(interview.toString());
        interviewService.updateInterview(interview.getId(),interview.getStartTime(),interview.getEndTime());
        return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
    }

}
