package com.iFundi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iFundi.models.Customer;
import com.iFundi.repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.iFundi.handlers.*;

@Service
public class CustomerService {

	// private static String UPLOAD_ROOT = "compas_uploads";
	@Autowired
	private CustomerRepository customerRepository;

	// @Autowired
	// private CustomerBioRepository customerbio;

	@Autowired
	public CustomerService(CustomerRepository customerRepository, ResourceLoader resourceLoader) {
		super();
		this.customerRepository = customerRepository;
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer findById(Long id) {
		return customerRepository.getCustomerById(id);
	}

	public Customer getCustomer(String idNumber) {
		// return cutomerRepository.findByAccountNumber(accountNumber);
		return customerRepository.getCustomerByIdNo(idNumber);
	}

	public void updateCustomerDetails(Customer customer) {
		customerRepository.updateCustomerDetails(customer.isActive(), customer.getEmailAddress(),
				customer.getFullName(), customer.getIdNumber(), customer.getPostalTown(), customer.getPhoneNumber(),
				customer.getSubLocation(), customer.getProfilePic());
	}

	public Customer addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return customerRepository.save(customer);
	}

	public Customer storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			Customer profilePic = new Customer(fileName, file.getContentType(), file.getBytes());

			return customerRepository.save(profilePic);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Customer getFile(Long fileId) {
		return customerRepository.findById(fileId)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
	}

}