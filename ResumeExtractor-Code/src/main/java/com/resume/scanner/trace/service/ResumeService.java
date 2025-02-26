package com.resume.scanner.trace.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.resume.scanner.trace.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.resume.scanner.trace.models.ResumeDetail;

import lombok.RequiredArgsConstructor;
/**
	* @Author: Abhinav Shankar
	* @Description: Service to calculate and looked into PDF/Doc file and fetch appropriate result.
	* @Date: 22 Feb;2025
	*/
@Service
@RequiredArgsConstructor
public class ResumeService {
	
	private final KeywordExtractorService keywordExtractorService;
	private final ScanPdfService scanPdfService;
	private final ResumeRepository resumeRepository;
	
	public ResumeDetail generateResumeDetails(MultipartFile file, String jd, String techSkill) throws IOException{
		
		ResumeDetail resumeDetail=new ResumeDetail();
		
	    String pdfContent = scanPdfService.scanPdfFromFile(file);
					System.out.println(pdfContent);

   		Pattern phoneNO = Pattern.compile("(0/91)?[7-9][0-9]{9}");
					Matcher phoneMatch = phoneNO.matcher(pdfContent);
	   	Pattern eMailPatter = Pattern.compile("([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})");
		   Matcher eMailMatch = eMailPatter.matcher(pdfContent);

					while (phoneMatch.find()) {
		       	System.out.println("PhoneNumber ::: "+phoneMatch.group());
										resumeDetail.setSetPhoneNo(phoneMatch.group());
	       	}
										
					while (eMailMatch.find()) {
									System.out.println("Email ::: "+eMailMatch.group(1));
									resumeDetail.setSetEmail(eMailMatch.group(1));
		      }

									String Lname = lastName(pdfContent);
	       	String Fname = firstName(pdfContent);
	      	 System.out.println("First Name ::: "+ Fname);
		       System.out.println("Last Name ::: "+ Lname);
	       	resumeDetail.setSetFullName(Fname +" "+ Lname);
		

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
	    double percentage = (double) matchedKeywords.size() / resumeDetail.getTotalKeywords().size() * 100;
	   	double skill_Percentage = (double) matchedSkill.size() / resumeDetail.getTechSkill().size() * 100;
	    resumeDetail.setMatchPercentage(String.format("%.2f", percentage) + "% of JD matched" +"-->" +String.format(
							"%" + ".2f", skill_Percentage) + "% of techSkill matched");

	
		resumeDetail.setSkills_match(matchedSkill.toString());
		resumeDetail.setSkills_Unmatch(unMatchedSkill.toString());
		
		resumeRepository
				.save(new ResumeDetail(resumeDetail.getSetPhoneNo(),resumeDetail.getSetFullName(),resumeDetail.getSetEmail(),
																											resumeDetail.getSkills_match(),resumeDetail.getSkills_Unmatch(),
																											resumeDetail.getMatchPercentage(),false));
		
	    return resumeDetail;
	}


 	public static String lastName (String completeName) {
	 	String[] names = completeName.split("\\s+");
		 return names[names.length -1];
	}

	 public static String firstName (String completeName) {
		 String[] names = completeName.split("\\s+");
		 return names[names.length -2];
	}

}
