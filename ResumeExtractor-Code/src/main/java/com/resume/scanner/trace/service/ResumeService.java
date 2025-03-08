package com.resume.scanner.trace.service;

import com.resume.scanner.trace.models.ResumeDetail;
import com.resume.scanner.trace.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
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
	public static final String TECH_SKILL_CONSTANT  = "% of techSkill matched";
	public static final String FORMAT = "%.2f";
	public static final String REGEX = "(0/91)?[7-9][0-9]{9}";
	public static final String REGEX1 = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";

	private final KeywordExtractorService keywordExtractorService;
	private final ScanPdfService scanPdfService;
	private final ResumeRepository resumeRepository;

	/**
		* @param file 
		* @param experience
		* @param techSkill
		* @param jd
		* @return
		* @throws IOException
		*/
	public ResumeDetail generateResumeDetails(MultipartFile file, String experience, String techSkill, String jd) throws IOException{
		
	       	ResumeDetail resumeDetail=new ResumeDetail();
		
	        String pdfContent = scanPdfService.scanPdfFromFile(file);


		       Pattern phoneNO = Pattern.compile(REGEX);
		       Matcher phoneMatch = phoneNO.matcher(pdfContent);
		       Pattern eMailPatter = Pattern.compile(REGEX1);
	       	Matcher eMailMatch = eMailPatter.matcher(pdfContent);

		       phoneNoExtracted(resumeDetail, phoneMatch);
		       emailExtracted(resumeDetail, eMailMatch);

									String Lname = lastName(pdfContent);
	       	String Fname = firstName(pdfContent);
									resumeDetail.setFullName(Fname +" "+ Lname);
		

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
	        }else {
	        	unmatchedKeywords.add(keyword);
	        }
	    }

	  	for (String skill : resumeDetail.getTechSkill()) {
		  	skill = skill.toLowerCase();
		    	if (pdfContent.contains(skill)) {
			       	matchedSkill.add(skill);
		   	}
							else {
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
		resumeRepository.save(new ResumeDetail(resumeDetail.getPhoneNo(),resumeDetail.getFullName(),
																																									resumeDetail.getEmail(),resumeDetail.getSkills_match(),
																																									resumeDetail.getSkills_Unmatch(), resumeDetail.getMatchPercentage(),
																																									resumeDetail.getExperience() ,
																																									resumeDetail.getCreatedDate(),false));
		return resumeDetail;
	}

	/**
		* @param resumeDetail
		* @param eMailMatch
		*/
	private static void emailExtracted(ResumeDetail resumeDetail, Matcher eMailMatch) {
		while (eMailMatch.find()) {
			resumeDetail.setEmail(eMailMatch.group(1));
		}
	}

	/**
		* @param resumeDetail
		* @param phoneMatch
		*/
	private static void phoneNoExtracted(ResumeDetail resumeDetail, Matcher phoneMatch) {
		while (phoneMatch.find()) {
			resumeDetail.setPhoneNo(phoneMatch.group());
		}
	}

	/**
		* @param completeName
		* @return
		*/
 	public static String lastName (String completeName) {
	 	String[] names = completeName.split("\\s+");
		 return names[names.length -1];
	}

	/**
		* @param completeName 
		* @return
		*/
	 public static String firstName (String completeName) {
		 String[] names = completeName.split("\\s+");
		 return names[names.length -2];
	}

}
