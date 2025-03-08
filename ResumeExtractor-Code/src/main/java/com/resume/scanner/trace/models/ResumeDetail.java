package com.resume.scanner.trace.models;

import jakarta.persistence.*;
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
 @Column(name = "ResumeID")
 private Long resumeId;

 private Set<String> totalKeywords;
 private Set<String> unMatchedKeywords;
 @Column(name = "MATCH PERCENTAGE")
 private String matchPercentage;
 @Column(name = "UNMATCH PERCENTAGE")
 private String unMatchPercentage;
 private Set<String> techSkill;
 @Column(name = "CANDIDATE FULL NAME")
 private String fullName;
 @Column(name = "SKILL MATCH")
 private String skills_match;
 @Column(name = "SKILL UNMATCH")
 private String skills_Unmatch;
 @Column(name = "CANDIDATE PHONE NUMBER")
 private String phoneNo;
 @Column(name = "CANDIDATE EMAIL")
 private String email;
 @Column(name = "CANDIDATE ROLE")
 private String Role;
 @Column(name = "EXPERIENCE")
 private String experience;
 @Column(name = "CREATED DATE")
 private String createdDate;
 private String hardSkill;
 private String softSkill;
 private String degree;
 private String wordsFound;


 public ResumeDetail() {
 }

 /**
  * @param phoneNo         phoneNO
  * @param fullName        Full Name
  * @param email           Candidate email
  * @param skills_match    matched skill
  * @param skills_Unmatch  unmatched skill
  * @param matchPercentage matched percentage
  * @param createdDate     Created Date
  * @param b
  */
 public ResumeDetail(String phoneNo, String fullName, String email, String skills_match, String skills_Unmatch, String matchPercentage, String createdDate) {
  this.phoneNo = phoneNo;
  this.email = email;
  this.fullName = fullName;
  this.skills_match = skills_match;
  this.skills_Unmatch = skills_Unmatch;
  this.matchPercentage = matchPercentage;
  this.createdDate = createdDate;
 }

 public ResumeDetail(String setPhoneNo, String setFullName, String setEmail, String skills_match, String skills_Unmatch, String matchPercentage, String experience, String createdDate) {
  this.phoneNo = setPhoneNo;
  this.email = setEmail;
  this.fullName = setFullName;
  this.skills_match = skills_match;
  this.skills_Unmatch = skills_Unmatch;
  this.matchPercentage = matchPercentage;
  this.experience = experience;
  this.createdDate = createdDate;
 }
}
