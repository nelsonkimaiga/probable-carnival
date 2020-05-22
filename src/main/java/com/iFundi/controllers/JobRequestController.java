package com.iFundi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iFundi.models.JobRequest;
import com.iFundi.repositories.JobRequestRepository;

@RestController

public class JobRequestController {
	@Autowired
	private JobRequestRepository jobRequestRepository;
	private Logger logger = LoggerFactory.getLogger(JobRequestController.class);

	
	@GetMapping("/jobrequests/all")
	public List<JobRequest> getAllJobRequests() {
		return jobRequestRepository.findAll();
	}

	@GetMapping("/jobrequests/{id}")
	public ResponseEntity<JobRequest> getJobRequestById(@PathVariable(value = "id") Long jobRequestId)
			throws ResourceNotFoundException {
		JobRequest jobRequest = jobRequestRepository.findById(jobRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("JobRequest not found for this id :: " + jobRequestId));
		return ResponseEntity.ok().body(jobRequest);
	}

	@PostMapping("/jobrequests/new")
	public JobRequest createJobRequest(@Valid @RequestBody JobRequest jobRequest) {
		return jobRequestRepository.save(jobRequest);
	}

	@PutMapping("/jobrequests/{id}")
	public ResponseEntity<JobRequest> updateJobRequest(@PathVariable(value = "id") Long jobRequestId,
			@Valid @RequestBody JobRequest jobRequestDetails) throws ResourceNotFoundException {
		JobRequest jobRequest = jobRequestRepository.findById(jobRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("JobRequest not found for this id :: " + jobRequestId));
		logger.info("---------------------[SAVE JOB INIT...]-----------------------",jobRequest);
		
		
		jobRequest.setApplicantName(jobRequestDetails.getApplicantName());
		jobRequest.setJobPoster(jobRequestDetails.getJobPoster());
		jobRequest.setApplicantName(jobRequestDetails.getApplicantName());
		jobRequest.setJobDescription(jobRequestDetails.getJobDescription());
		jobRequest.setJobLocation(jobRequestDetails.getJobLocation());
		final JobRequest updatedJobRequest = jobRequestRepository.save(jobRequest);
		return ResponseEntity.ok(updatedJobRequest);
	}

	@DeleteMapping("/jobrequests/{id}")
	public Map<String, Boolean> deleteJobRequest(@PathVariable(value = "id") Long jobRequestId)
			throws ResourceNotFoundException {
		JobRequest jobRequest = jobRequestRepository.findById(jobRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("JobRequest not found for this id :: " + jobRequestId));

		jobRequestRepository.delete(jobRequest);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	
}
