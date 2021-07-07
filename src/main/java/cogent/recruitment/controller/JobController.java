package cogent.recruitment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cogent.recruitment.entities.Job;
import cogent.recruitment.service.JobService;
import cogent.recruitment.util.JobUtil;

@CrossOrigin
@RestController
public class JobController {
	
	@Autowired
	JobService service;
	
	@Autowired
	JobUtil util;
	
	@PreAuthorize("hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/jobs")
	public ResponseEntity<?> getAllJobs() {
		List<Job> jobs = service.getAllJobs();
		ResponseEntity<?> resp = new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
		return resp;
	}
	
	@GetMapping(value = "/jobs/vacant")
	public ResponseEntity<?> getVacantJobs() {
		List<Job> jobs = service.getVacantJobs();
		ResponseEntity<?> resp = new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
		return resp;
	}
	
	@GetMapping(value = "/jobs/{id}")
	public ResponseEntity<?> getJobById(@PathVariable("id") int id) {
		Job job = service.getJobById(id);
		ResponseEntity<?> resp = new ResponseEntity<Job>(job, HttpStatus.OK);
		return resp;
	}
	
	@PreAuthorize("hasAuthority('USER') or hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/jobs/name/{name}")
	public ResponseEntity<?> getJobByName(@PathVariable("name") String name) {
		List<Job> job = service.getJobByName(name);
		ResponseEntity<?> resp = new ResponseEntity<List<Job>>(job, HttpStatus.OK);
		return resp;
	}
	
	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@PostMapping(value = "/jobs")
	public ResponseEntity<?> postJob(@Valid @RequestBody Job job) {
		job.setDatepub(java.time.LocalDate.now());
		String msg = "Job Posted: "+service.postJob(job);
		ResponseEntity<String> resp = new ResponseEntity<String>(msg, HttpStatus.CREATED);
		return resp;
	}
	
	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@PutMapping(value = "/jobs/{id}")
	public ResponseEntity<String> updateJob(@PathVariable int id, @Valid @RequestBody Job job) {
		Job dbJob = service.getJobById(id);
		util.mergeJob(job, dbJob);
		String jobDets = "Updated Job: " + service.updateJob(dbJob);
		ResponseEntity<String> resp = new ResponseEntity<String>(jobDets, HttpStatus.OK);
		return resp;
	}

}
