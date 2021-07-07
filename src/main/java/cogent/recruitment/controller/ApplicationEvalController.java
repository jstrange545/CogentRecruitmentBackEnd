package cogent.recruitment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cogent.recruitment.entities.ApplicationEvaluation;
import cogent.recruitment.service.ApplicationEvaluationImpl;

@CrossOrigin
@RestController
@RequestMapping("/ApplicationEvaluation")
public class ApplicationEvalController {

	@Autowired
	ApplicationEvaluationImpl eserv;
	
	@PreAuthorize("hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<String> saveEvaluation(@Valid @RequestBody ApplicationEvaluation app)
	{
		eserv.saveApplicationEvaluation(app);
		
		return ResponseEntity.ok("Application Evaluation has been saved");
	}

	@PreAuthorize("hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping
	public ResponseEntity<?> getEvaluationById(@RequestParam("id") int id)
	{
		ApplicationEvaluation app = eserv.getApplicationEvaluationById(id);
		return ResponseEntity.ok(app);
	}

	@PreAuthorize("hasAuthority('USER') or hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value="/all")
	public ResponseEntity<?> getEvaluationsByApplicationId(@RequestParam("id") int id)
	{
		List<ApplicationEvaluation> evalList = eserv.getApplicationEvaluationsById(id);
		return ResponseEntity.ok(evalList);
	}

}