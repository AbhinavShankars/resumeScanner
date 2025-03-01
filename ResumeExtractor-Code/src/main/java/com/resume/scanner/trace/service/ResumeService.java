package com.resume.scanner.trace.service;

import com.resume.scanner.trace.models.ResumeDetail;
import com.resume.scanner.trace.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
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
		* @param originalFilename 
		* @param list_OF_Resume
		* @param jd
		* @param techSkill
		* @return
		* @throws IOException
		*/
	public List<ResumeDetail> generateResumeDetails(List<String> originalFilename, List<MultipartFile> list_OF_Resume, String jd, String techSkill) throws IOException {

		ResumeDetail resumeDetail = new ResumeDetail();
		List<String> pdfContent = scanPdfService.scanPdfFromFile(list_OF_Resume);
		List<ResumeDetail> dataset = new ArrayList<>();
		
		resumeDetail.setTotalKeywords(keywordExtractorService.extractKeywords(jd));

			for (int i=0;i<pdfContent.size();i++) {
				resumeDetail.setFileName(originalFilename.get(i));
				dataset.add(i,resumeDetail);
			}
			
		return dataset;
	}

	/**
		* @param resumeDetail
		* @param eMailMatch
		*/
	private static void emailExtracted(ResumeDetail resumeDetail, Matcher eMailMatch) {
		while (eMailMatch.find()) {
			resumeDetail.setSetEmail(eMailMatch.group(1));
		}
	}

	/**
		* @param resumeDetail
		* @param phoneMatch
		*/
	private static void phoneNoExtracted(ResumeDetail resumeDetail, Matcher phoneMatch) {
		while (phoneMatch.find()) {
			resumeDetail.setSetPhoneNo(phoneMatch.group());
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
