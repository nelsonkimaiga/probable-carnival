package com.iFundi.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import com.iFundi.handlers.CustomResponse;
import com.iFundi.handlers.MyFileNotFoundException;
import com.iFundi.handlers.UploadFileResponse;
import com.iFundi.handlers.UserResponse;
import com.iFundi.models.Customer;
import com.iFundi.models.Customer;
import com.iFundi.repositories.CustomerRepository;
import com.iFundi.services.CustomerService;

@RestController
//@RequestMapping(value = ResourceConfig.iFundi_API_v1)
public class CustomerController {
	private Logger logger = LoggerFactory.getLogger(CustomerController.class);
	private Logger x = LoggerFactory.getLogger(Customer.class);

	@Autowired
	private CustomerRepository customerRepository;
	 @Autowired
	private CustomerService customerService;

	@GetMapping("/customers/all")
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId)
			throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
		return ResponseEntity.ok().body(customer);
	}

	@PostMapping("/customers/new")
	public Customer createCustomer(@Valid @RequestBody Customer customer) {
		return customerRepository.save(customer);
	}

	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerId,
			@Valid @RequestBody Customer customerDetails) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
		logger.info("---------------------[SAVE JOB INIT...]-----------------------",customer);


		customer.setEmailAddress(customerDetails.getEmailAddress());
		customer.setFullName(customerDetails.getFullName());
		customer.setIdNumber(customerDetails.getIdNumber());
		customer.setPostalTown(customerDetails.getPostalTown());
		customer.setSubLocation(customerDetails.getSubLocation());
		customer.setPhoneNumber(customerDetails.getPhoneNumber());
		customer.setProfilePic(customerDetails.getProfilePic());
		final Customer updatedCustomer = customerRepository.save(customer);
		return ResponseEntity.ok(updatedCustomer);
	}

	@DeleteMapping("/customers/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId)
			throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));

		customerRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}


	@PostMapping("/uploadContractorFile/{id}")
	public UploadFileResponse uploadFile(@PathVariable(value = "id") @RequestParam("file") MultipartFile file, Long fileId) throws ResourceNotFoundException{
		String TOMCAT_HOME = System.getProperty("catalina.base");
		String path = TOMCAT_HOME + "/lct_uploads/eclaims/";
		customerRepository.findById(fileId)
		.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
		
		Customer profilePic = customerService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(path)
				.path(profilePic.getId())
				.toUriString();

		return new UploadFileResponse(profilePic.getFileName(), fileDownloadUri,
				file.getContentType(), file.getSize());
	}


}