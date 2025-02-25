package com.resume.ats.check.models;

import java.util.Set;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
	* @Author: Abhinav Shankar
	* @Description: Pojo object to bind the data and display as json.
	* @Date: 22 Feb;2025
	*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDetail {

	@Id
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
	
}
