package com.iFundi.models;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "job_seeker")
public class JobSeeker extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "active")
	private boolean active = false;

	@Column(name = "email")
	private String emailAddress;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "id_number")
	private String idNumber;

	@Column(name = "postal_town")
	private String postalTown;

	@Column(name = "profession_level")
	private String professionLevel;

	@Column(name = "profession")
	private String profession;

	@Column(name = "certificate_of_good_conduct")
	private String certificate;

	@Column(name = "location")
	private String location;

	@Column(name = "estimatePrice")
	private String estimatePrice;

	@Column(name = "expiry_date")
	private String expiryDate;

	@Column(name = "isSubmited", nullable = false)
	private boolean submited = false;
	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "other_skills")
	public String otherSkills;

	@Column(name = "professional_boards")
	public String professionalBoards;

	@Column(name = "rating")
	public String rating;
	
	

	@Column(name = "profile_pic")
	@Lob
	private byte[] profilePic;

	private String fileName;

	private String fileType;


	@Override
	public String toString() {
		return "JobSeeker [active=" + active + ", emailAddress=" + emailAddress + ", fullName=" + fullName
				+ ", idNumber=" + idNumber + ", postalTown=" + postalTown + ", professionLevel=" + professionLevel
				+ ", profession=" + profession + ", certificate=" + certificate + ", location=" + location
				+ ", estimatePrice=" + estimatePrice + ", expiryDate=" + expiryDate + ", submited=" + submited
				+ ", profilePic=" + Arrays.toString(profilePic) + ", phoneNumber=" + phoneNumber + ", otherSkills="
				+ otherSkills + ", professionalBoards=" + professionalBoards + ", rating=" + rating + ", fileName="
				+ fileName + ", fileType=" + fileType + "]";
	}


	public JobSeeker(byte[] profilePic, String fileName, String fileType) {
		super();
		this.profilePic = profilePic;
		this.fileName = fileName;
		this.fileType = fileType;
	}

	
	
	

	public String getFileName() {
		return fileName;
	}




	public void setFileName(String fileName) {
		this.fileName = fileName;
	}




	public String getFileType() {
		return fileType;
	}




	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JobSeeker() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPostalTown() {
		return postalTown;
	}

	public void setPostalTown(String postalTown) {
		this.postalTown = postalTown;
	}



	public String getProfessionLevel() {
		return professionLevel;
	}

	public void setProfessionLevel(String professionLevel) {
		this.professionLevel = professionLevel;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getEstimatePrice() {
		return estimatePrice;
	}

	public void setEstimatePrice(String estimatePrice) {
		this.estimatePrice = estimatePrice;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isSubmited() {
		return submited;
	}

	public void setSubmited(boolean submited) {
		this.submited = submited;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOtherSkills() {
		return otherSkills;
	}

	public void setOtherSkills(String otherSkills) {
		this.otherSkills = otherSkills;
	}

	public String getProfessionalBoards() {
		return professionalBoards;
	}

	public void setProfessionalBoards(String professionalBoards) {
		this.professionalBoards = professionalBoards;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
