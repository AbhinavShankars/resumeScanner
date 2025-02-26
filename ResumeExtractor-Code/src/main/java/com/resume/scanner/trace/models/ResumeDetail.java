package com.resume.scanner.trace.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Set;

/**
	* @Author: Abhinav Shankar
	* @Description: Pojo object to bind the data and display as json.
	* @Date: 22 Feb;2025
	*/

@Data
@Entity
public class ResumeDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long resumeId;
	
	private Set<String> totalKeywords;
	private Set<String> unMatchedKeywords;
	private String matchPercentage;

	private Set<String> techSkill;

	private String setFullName;
	private String setPhoneNo;
	private String setEmail;

	private String setRole;
	private String setExperience;
	private String setIDETool;

	public ResumeDetail(){}

 public ResumeDetail(Set<String> techSkill, String setPhoneNo, String setFullName, String setEmail, boolean b) {
		this.techSkill=techSkill;
		this.setPhoneNo=setPhoneNo;
		this.setEmail=setEmail    ;
		this.setFullName=setFullName;
 }
}
