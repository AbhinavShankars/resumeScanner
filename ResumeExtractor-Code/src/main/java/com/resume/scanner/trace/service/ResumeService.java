package com.resume.scanner.trace.service;

import ch.qos.logback.core.subst.Tokenizer;
import com.resume.scanner.trace.models.ResumeDetail;
import com.resume.scanner.trace.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Abhinav Shankar
 * @Description: Service to calculate and looked into PDF/Doc file and fetch appropriate result.
 * @Date: 22 Feb;2025
 */
@Service
@RequiredArgsConstructor
public class ResumeService {
 public static final String JD_CONSTANT = "% of JD matched and ";
 public static final String TECH_SKILL_CONSTANT = "% of techSkill matched";
 public static final String FORMAT = "%.2f";
 public static final String REGEX = "(0/91)?[7-9][0-9]{9}";
 public static final String REGEX1 = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";

 private final KeywordExtractorService keywordExtractorService;
 private final ScanPdfService scanPdfService;
 private final ResumeRepository resumeRepository;

 /**
  * @param resumeDetail ResumeDetail
  * @param pdfContent   String
  */
 private static void extractContent(ResumeDetail resumeDetail, String pdfContent) {
  Pattern phoneNO = Pattern.compile(REGEX);
  Matcher phoneMatch = phoneNO.matcher(pdfContent);
  Pattern eMailPatter = Pattern.compile(REGEX1);
  Matcher eMailMatch = eMailPatter.matcher(pdfContent);

  phoneNoExtracted(resumeDetail, phoneMatch);
  emailExtracted(resumeDetail, eMailMatch);

  String Lname = lastName(pdfContent);
  String Fname = firstName(pdfContent);
  resumeDetail.setFullName(Fname + " " + Lname);
 }

 /**
  * @param resumeDetail ResumeDetail
  * @param eMailMatch   Matcher
  */
 private static void emailExtracted(ResumeDetail resumeDetail, Matcher eMailMatch) {
  while (eMailMatch.find()) {
   resumeDetail.setEmail(eMailMatch.group(1));
  }
 }

 /**
  * @param resumeDetail ResumeDetail
  * @param phoneMatch   Matcher
  */
 private static void phoneNoExtracted(ResumeDetail resumeDetail, Matcher phoneMatch) {
  while (phoneMatch.find()) {
   resumeDetail.setPhoneNo(phoneMatch.group());
  }
 }

 /**
  * @param completeName String
  * @return String
  */
 public static String lastName(String completeName) {
  String[] names = completeName.split("\\s+");
  return names[names.length - 1];
 }

 /**
  * @param completeName String
  * @return String
  */
 public static String firstName(String completeName) {
  String[] names = completeName.split("\\s+");
  return names[names.length - 2];
 }

 /**
  * @param file       MultipartFile
  * @param experience String
  * @param techSkill  String
  * @param jd         String
  * @return ResumeDetail Object
  * @throws IOException Exception
  */
 public ResumeDetail generateResumeDetails(MultipartFile file, String experience, String techSkill, String jd) throws IOException {

  ResumeDetail resumeDetail = new ResumeDetail();
  String pdfContent = scanPdfService.scanPdfFromFile(file);

  extractContent(resumeDetail, pdfContent);
  
  resumeDetail.setTotalKeywords(keywordExtractorService.extractKeywords(jd));
  resumeDetail.setTechSkill(keywordExtractorService.extractKeywords(techSkill));

  Set<String> matchedSkill = new HashSet<>();
  Set<String> unMatchedSkill = new HashSet<>();

  Set<String> unmatchedKeywords = new HashSet<>();
  Set<String> matchedKeywords = new HashSet<>();
  
  
  for (String keyword : resumeDetail.getTotalKeywords()) {
   keyword = keyword.toLowerCase();
   if (pdfContent.contains(keyword)) {
    matchedKeywords.add(keyword);
   } else {
    unmatchedKeywords.add(keyword);
   }
  }
  countKeywordsInFile(resumeDetail.getTotalKeywords(), pdfContent,resumeDetail);
  for (String skill : resumeDetail.getTechSkill()) {
   skill = skill.toLowerCase();
   if (pdfContent.contains(skill)) {
    matchedSkill.add(skill);
   } else {
    unMatchedSkill.add(skill);
   }
  }

  resumeDetail.setUnMatchedKeywords(unmatchedKeywords);
  double jdPercentage = (double) matchedKeywords.size() / resumeDetail.getTotalKeywords().size() * 100;
  double skill_Percentage = (double) matchedSkill.size() / resumeDetail.getTechSkill().size() * 100;
  String matchPercentage = String.format(FORMAT, jdPercentage) + JD_CONSTANT + String.format(FORMAT, skill_Percentage) + TECH_SKILL_CONSTANT;

  resumeDetail.setMatchPercentage(matchPercentage);
  resumeDetail.setSkills_match(matchedSkill.toString());
  resumeDetail.setSkills_Unmatch(unMatchedSkill.toString());


  LocalDateTime localDateTime = LocalDateTime.now();
  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
  String formattedDate = localDateTime.format(dateTimeFormatter);

  resumeDetail.setCreatedDate(formattedDate);
  resumeDetail.setExperience(experience);

  // Perform DB Operations
  resumeRepository.save(new ResumeDetail(resumeDetail.getPhoneNo(), resumeDetail.getFullName(), resumeDetail.getEmail(), resumeDetail.getSkills_match(), resumeDetail.getSkills_Unmatch(), resumeDetail.getMatchPercentage(), resumeDetail.getExperience(), resumeDetail.getCreatedDate()));
  return resumeDetail;
 }

 /**
  * @param keyword      String
  * @param file         String
  * @param resumeDetail ResumeDetail
  * @throws IOException Exception
  */
 public void countKeywordsInFile(Set<String> keyword, String file, ResumeDetail resumeDetail) throws IOException {
  String[] strArry = new String[0];
  int count = 0;

  for (String word : keyword) {
   word = word.toLowerCase();
    strArry = file.split(word);
   if (strArry.length > 1) {
    count = count + strArry.length - 1;
   } else {
    if (file==word) {
     count++;
    }
   }
   System.out.println("2.0........."+word +" came "+count);
  }
 }
 
}
