package com.resume.scanner.trace.service;

import com.resume.scanner.trace.models.ResumeDetail;
import com.resume.scanner.trace.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
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


	public Set<ResumeDetail> generateResumeDetails(List<String> originalFilename, List<MultipartFile> list_OF_Resume,
																																														String jd, String techSkill) throws IOException {

		ResumeDetail resumeDetail = new ResumeDetail();
		ResumeDetail resumeDetail1 = new ResumeDetail();
		List<String> pdfContent = scanPdfService.scanPdfFromFile1(list_OF_Resume);
		Set<ResumeDetail> dataset = new HashSet<>();

		Set<String> unmatchedKeywords = new HashSet<>();
		Set<String> matchedKeywords = new HashSet<>();
		resumeDetail.setTotalKeywords(keywordExtractorService.extractKeywords(jd));
		

			for (int i=0;i<pdfContent.size();i++) {
				for (String keyword : resumeDetail.getTotalKeywords()) {
					keyword = keyword.toLowerCase();
					
			    	if (pdfContent.get(i).contains(keyword)) {
				     	matchedKeywords.add(keyword);
				     }
			      	else {
				     	unmatchedKeywords.add(keyword);
				      }
			}
					double jdPercentage = (double) matchedKeywords.size() / resumeDetail.getTotalKeywords().size() * 100;
					String matchPercentage = String.format(FORMAT, jdPercentage) + "% matched.";

					Pattern phoneNO = Pattern.compile(REGEX);
					Matcher phoneMatch = phoneNO.matcher(pdfContent.get(i));
					Pattern eMailPatter = Pattern.compile(REGEX1);
					Matcher eMailMatch = eMailPatter.matcher(pdfContent.get(i));
					phoneNoExtracted(resumeDetail, phoneMatch); // setting phone-NO
					emailExtracted(resumeDetail, eMailMatch);  // setting Email id

					String Lname = lastName(pdfContent.get(i));
					String Fname = firstName(pdfContent.get(i));

					resumeDetail.setSetFullName(Fname + " " + Lname);
					resumeDetail.setMatchPercentage(matchPercentage); // setting percentage
					resumeDetail.setFileName(originalFilename.get(i));    // setting file name

					dataset.add(resumeDetail);
					
		}
		

		return dataset;
	}

	private static void emailExtracted(ResumeDetail resumeDetail, Matcher eMailMatch) {
		while (eMailMatch.find()) {
			resumeDetail.setSetEmail(eMailMatch.group(1));
		}
	}

	private static void phoneNoExtracted(ResumeDetail resumeDetail, Matcher phoneMatch) {
		while (phoneMatch.find()) {
			resumeDetail.setSetPhoneNo(phoneMatch.group());
		}
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
