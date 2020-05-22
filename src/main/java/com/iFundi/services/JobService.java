package com.iFundi.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.iFundi.handlers.FileStorageException;
import com.iFundi.handlers.MyFileNotFoundException;
import com.iFundi.models.Customer;
import com.iFundi.models.Job;
import com.iFundi.repositories.JobRepository;;

@Service
public class JobService {
	@Autowired
	private JobRepository jobRepository;

//	public String findImageByAccount(String account) {
//		return jobRepository.findImageByAccount(account);
//	}
	
	
	public Job deleteJobById(int id) {
		jobRepository.deleteJobById(id);
		return null;
	}


}
