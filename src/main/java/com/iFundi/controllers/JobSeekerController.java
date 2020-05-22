package com.iFundi.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.gson.Gson;
import com.iFundi.handlers.MyFileNotFoundException;
import com.iFundi.handlers.UploadFileResponse;
import com.iFundi.models.JobSeeker;
import com.iFundi.repositories.JobSeekerRepository;
import com.iFundi.services.JobSeekerService;
//import com.sun.jersey.multipart.FormDataParam;

@RestController
public class JobSeekerController {

	@Autowired
	private JobSeekerRepository jobSeekerRepository;
	@Autowired
	private JobSeekerService jobSeekerService;
	private Gson gson = new Gson();
	private Logger logger = LoggerFactory.getLogger(JobSeekerController.class);
	// private Logger x = LoggerFactory.getLogger(FileController.class);

	@GetMapping("/jobseeker/all")
	public List<JobSeeker> getAllJobSeekers() {
		return jobSeekerRepository.findAll();
	}

	@GetMapping("/jobseeker/{id}")
	public ResponseEntity<JobSeeker> getJobSeekerById(@PathVariable(value = "id") Long jobSeekerId)
			throws ResourceNotFoundException {
		JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
				.orElseThrow(() -> new ResourceNotFoundException("JobSeeker not found for this id :: " + jobSeekerId));
		return ResponseEntity.ok().body(jobSeeker);
	}

	@PostMapping("/jobseeker/new")
	public JobSeeker createJobSeeker(@Valid @RequestBody JobSeeker jobSeeker) {
		return jobSeekerRepository.save(jobSeeker);
	}

	@PutMapping("/jobseeker/{id}")
	public ResponseEntity<JobSeeker> updateJobSeeker(@PathVariable(value = "id") Long jobSeekerId,
			@Valid @RequestBody JobSeeker jobSeekerDetails) throws ResourceNotFoundException {
		JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
				.orElseThrow(() -> new ResourceNotFoundException("JobSeeker not found for this id :: " + jobSeekerId));
		logger.info("---------------------[SAVE JOB INIT...]-----------------------", jobSeeker);

		jobSeeker.setEmailAddress(jobSeekerDetails.getEmailAddress());
		jobSeeker.setFullName(jobSeekerDetails.getFullName());
		jobSeeker.setIdNumber(jobSeekerDetails.getIdNumber());
		jobSeeker.setPostalTown(jobSeekerDetails.getPostalTown());
		jobSeeker.setProfessionLevel(jobSeekerDetails.getProfessionLevel());
		jobSeeker.setCertificate(jobSeekerDetails.getCertificate());
		jobSeeker.setLocation(jobSeekerDetails.getLocation());
		jobSeeker.setProfession(jobSeekerDetails.getProfession());
		jobSeeker.setEstimatePrice(jobSeekerDetails.getEstimatePrice());
		jobSeeker.setExpiryDate(jobSeekerDetails.getExpiryDate());
		jobSeeker.setProfilePic(jobSeeker.getProfilePic());
		jobSeeker.setSubmited(jobSeekerDetails.isSubmited());
		jobSeeker.setPhoneNumber(jobSeekerDetails.getPhoneNumber());
		jobSeeker.setOtherSkills(jobSeekerDetails.getOtherSkills());
		jobSeeker.setProfessionalBoards(jobSeekerDetails.getProfessionalBoards());
		jobSeeker.setRating(jobSeekerDetails.getRating());
		// jobSeeker.setOtherSkills(jobSeekerDetails.getOtherSkills());
		final JobSeeker updatedJobSeeker = jobSeekerRepository.save(jobSeeker);
		return ResponseEntity.ok(updatedJobSeeker);
	}

	@PostMapping("/uploadFile/{id}")
	public UploadFileResponse uploadFile(@PathVariable(value = "id") @RequestParam("file") MultipartFile file,
			Long fileId) throws ResourceNotFoundException {
		String TOMCAT_HOME = System.getProperty("catalina.base");
		String path = TOMCAT_HOME + "/lct_uploads/eclaims/";
		jobSeekerRepository.findById(fileId)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
		JobSeeker profilePic = jobSeekerService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("path")
				.path(profilePic.getId()).toUriString();
		return new UploadFileResponse(profilePic.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
	}

	@DeleteMapping("/jobseeker/{id}")
	public Map<String, Boolean> deleteJobSeeker(@PathVariable(value = "id") Long jobSeekerId)
			throws ResourceNotFoundException {
		JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
				.orElseThrow(() -> new ResourceNotFoundException("JobSeeker not found for this id :: " + jobSeekerId));

		jobSeekerRepository.delete(jobSeeker);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
