package cogent.recruitment.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import cogent.recruitment.entities.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import cogent.recruitment.entities.Application;
import cogent.recruitment.service.ApplicationService;
import cogent.recruitment.util.ApplicationUtil;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
public class ApplicationController {

	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	ApplicationUtil util;

	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/applications")
	public ResponseEntity<?> getAllApplications() {

		List<Application> applications = applicationService.getAllApplications();
		ResponseEntity<?> resp = new ResponseEntity<List<Application>>(applications, HttpStatus.OK);
		return resp;

	}

	@PreAuthorize("hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/applications/{id}")
	public ResponseEntity<?> getApplicationById(@PathVariable("id") @Min(1) int id) {

		Application application = applicationService.getApplicationById(id);
		ResponseEntity<?> resp = new ResponseEntity<Application>(application, HttpStatus.OK);
		return resp;

	}
	
	@PreAuthorize("hasAuthority('USER') or hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/applications/user/{id}")
	public ResponseEntity<?> getApplicationsByUserId(@PathVariable("id") @Min(1) int id) {
		
		Set<Application> applications = applicationService.getApplicationsByUserId(id);
		TreeSet<Application> appTree = new TreeSet<>();
		appTree.addAll(applications);
		ResponseEntity<?> resp = new ResponseEntity<TreeSet<Application>>(appTree, HttpStatus.OK);
		return resp;

	}
	
	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/applications/job/{id}")
	public ResponseEntity<?> getApplicationByJobId(@PathVariable("id") @Min(1) int id) {

		List<Application> applications = applicationService.getApplicationsByJobId(id);
		ResponseEntity<?> resp = new ResponseEntity<List<Application>>(applications, HttpStatus.OK);
		return resp;
	}

	@PreAuthorize("hasAuthority('USER') or hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@PostMapping(value = "/applications")
	public ResponseEntity<String> newApplication(@RequestParam("application") String applicationJSON,
												 @RequestParam("resume")MultipartFile resume,
												 @RequestParam(value = "coverLetter", required = false) MultipartFile coverLetter) throws IOException {
		Application application = objectMapper.readValue(applicationJSON,Application.class);
		Document resumeObj = new Document("Resume",resume.getBytes(),application);
		Set<Document> documents = new HashSet<>();
		documents.add(resumeObj);
		if(coverLetter!=null) {
			Document coverLetterObj = new Document("CoverLetter", coverLetter.getBytes(), application);
			documents.add(coverLetterObj);
		}
		application.setDocuments(documents);
		String applicationDetails = applicationService.saveApplication(application);
		ResponseEntity<String> resp = new ResponseEntity<String>("Inserted successfully " + applicationDetails, HttpStatus.CREATED);
		return resp;

	}

	@PreAuthorize("hasAuthority('USER') or hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@PutMapping(value = "/applications/{id}")
	public ResponseEntity<String> updateapplication(@PathVariable @Min(1) Integer id,
			@Valid @RequestBody Application reqApplication) {

		Application dbApplication = applicationService.getApplicationById(id);
		// copy non-null values from request to Database application
		util.copyNonNullValues(reqApplication, dbApplication);
		String applicationDetails = applicationService.updateApplication(dbApplication);
		ResponseEntity<String> resp = new ResponseEntity<String>(applicationDetails, HttpStatus.RESET_CONTENT);

		return resp;
	}

}
