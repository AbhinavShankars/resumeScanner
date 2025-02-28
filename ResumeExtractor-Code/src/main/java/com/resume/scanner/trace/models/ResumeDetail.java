package com.resume.scanner.trace.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
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
	@Column(name = "ResumeID")
	private Long resumeId;
	
	private Set<String> totalKeywords;
	private Set<String> unMatchedKeywords;
	@Column(name = "MATCH PERCENTAGE")
	private String matchPercentage;
	private Set<String> techSkill;
	@Column(name = "CANDIDATE FULL NAME")
	private String setFullName;
	@Column(name = "SKILL MATCH")
	private String skills_match;
	@Column(name = "SKILL UNMATCH")
	private String skills_Unmatch;
	@Column(name = "CANDIDATE PHONE NUMBER")
	private String setPhoneNo;
	@Column(name = "CANDIDATE EMAIL")
	private String setEmail;
	@Column(name = "CANDIDATE ROLE")
	private String setRole;
	@Column(name = "EXPERIENCE")
	private String setExperience;
	@Column(name = "IDE TOOL")
	private String setIDETool;
	@Column(name = "CREATED DATE")
	private String createdDate;
	@Column(name = "FILE NAME")
	private String fileName;

	public ResumeDetail(){}

	/**
		* @param setPhoneNo phoneNO
		* @param setFullName   Full Name
		* @param setEmail      Candidate email
		* @param skills_match   matched skill
		* @param skills_Unmatch   unmatched skill
		* @param matchPercentage   matched percentage
		* @param createdDate     Created Date
		* @param b
		*/
 public ResumeDetail( String setPhoneNo, String setFullName, String setEmail,
																					String skills_match,String skills_Unmatch,String matchPercentage,String createdDate,String fileName,boolean b) {
		this.setPhoneNo=setPhoneNo;
		this.setEmail=setEmail    ;
		this.setFullName=setFullName;
		this.skills_match=skills_match;
		this.skills_Unmatch=skills_Unmatch;
		this.matchPercentage=matchPercentage;
		this.createdDate=createdDate;
		this.fileName=fileName;
 }
}
