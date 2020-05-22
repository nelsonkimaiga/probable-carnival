package com.iFundi.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.iFundi.models.ApiResponse;
import com.iFundi.models.Job;
import com.iFundi.repositories.JobRepository;
import com.iFundi.services.JobService;

/**
 * Created by CLLSDJACKT013 on 22/11/2019.
 */
@RestController
public class JobController {
	@Autowired
	private JobRepository jobRepository;
	private JobService jobService;
	private Gson gson = new Gson();
	private Logger logger = LoggerFactory.getLogger(JobController.class);

	//create jobs
	@ResponseBody
	@RequestMapping(path = "/jobs/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity saveAccount(@RequestBody String request) {
		logger.info("---------------------[SAVE JOB INIT...]-----------------------");
		try {
			Job account = gson.fromJson(request, Job.class);
			logger.info(gson.toJson(account));
			jobRepository.save(account);
			return ResponseEntity.status(201).body(gson.toJson(new ApiResponse(true, "job saved successfully")));
		} catch (Exception e) {
			logger.info(e.getMessage());
			return ResponseEntity.status(201).body(gson.toJson(new ApiResponse(false, e.getMessage())));
		}

	}

	//not working
	@RequestMapping(path = "/jobs/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity updateJob(@RequestBody String account) {
		logger.info("---------------------[THIS IS THE REQUEST BODY NEEDEDT...]-----------------------",account);
		return ResponseEntity.status(201).body("ok...");
	}

	
	//fetch single job by id
	@RequestMapping(value = "/jobs/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<String> findJobsById(@PathVariable int id)
 {
		logger.info("---------------------[FETCH ONE JOB INIT...]-----------------------",id);
		try {
			Job job = jobRepository.findById(id);
			return ResponseEntity.status(201).body(gson.toJson(new ApiResponse(true, "success", job)));
		} catch (Exception e) {
			logger.info(e.getMessage());
			return ResponseEntity.status(201).body(gson.toJson(new ApiResponse(false, e.getMessage())));
		}

	}
	
	
//fetch all the jobs
	@ResponseBody
	@RequestMapping(path = "/jobs/all", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity getAllAccounts() {
		logger.info("---------------------[FETCH ALL JOBS INIT...]-----------------------");
		try {
			List<Job> jobs = jobRepository.findAll();
			return ResponseEntity.status(201)
					.body(gson.toJson(new ApiResponse(true, "accounts retrieved successfully", jobs)));
		} catch (Exception e) {
			logger.info(e.getMessage());
			return ResponseEntity.status(201).body(gson.toJson(new ApiResponse(false, e.getMessage())));
		}
	}

	
	// remove single job
	@RequestMapping(value = "/jobs/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public  void deleteJobById(@PathVariable("id") int id) {
		try {
			Job job = jobService.deleteJobById(id);
			return;
		} catch (Exception e) {
		
	}		
	
	}

		
		
		
		
}
