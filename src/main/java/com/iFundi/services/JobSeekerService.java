package com.iFundi.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.iFundi.handlers.FileStorageException;
import com.iFundi.handlers.MyFileNotFoundException;
import com.iFundi.models.JobSeeker;
import com.iFundi.repositories.JobSeekerRepository;

@Service
public class JobSeekerService {
	
	@Autowired
	private JobSeekerRepository jobSeekerRepository;
	
	public JobSeeker storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			JobSeeker profilePic = new JobSeeker(file.getBytes(), file.getContentType(), fileName);

			return jobSeekerRepository.save(profilePic);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public JobSeeker getFile(Long fileId) {
		return jobSeekerRepository.findById(fileId)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
	}
}
