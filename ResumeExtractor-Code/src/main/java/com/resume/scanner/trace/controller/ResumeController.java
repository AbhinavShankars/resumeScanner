package com.resume.scanner.trace.controller;

import com.resume.scanner.trace.models.ResumeDetail;
import com.resume.scanner.trace.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
	* @Author: Abhinav Shankar
	* @Description: controller to get the input from UI
	* @Date: 22 Feb;2025
	*/
@RestController
@RequiredArgsConstructor
public class ResumeController {
	
	private final ResumeService resumeService;

	private static int fileUploadCount = 0;
	
	@PostMapping("/check-resume")
	public Set<ResumeDetail> generateResumeDetails(@RequestParam("file") MultipartFile[] file,
																																																@RequestParam("jd") String jd , @RequestParam("techSkill") String techSkill) throws IOException{
		List<MultipartFile> list_OF_Resume= new ArrayList<>();
		for (MultipartFile multipartFile : file) {
			if (!multipartFile.isEmpty()) {
				String fileName = multipartFile.getOriginalFilename();
				String contentType = multipartFile.getContentType();
				Long fileSize = multipartFile.getSize();
				byte[] fileData = multipartFile.getBytes();
				
				fileUploadCount++;

				long fileSizeKB = multipartFile.getSize() / 1024;
				list_OF_Resume.add(multipartFile);
			}
		}
		String fileName ;
		List<String> listFIleName = new ArrayList<>();
		for (int i=0;i< list_OF_Resume.size();i++){
			fileName = list_OF_Resume.get(i).getOriginalFilename();
			listFIleName.add(fileName);
		}

		Set<ResumeDetail> details = resumeService.generateResumeDetails(listFIleName, list_OF_Resume, jd, techSkill);
		return details;
	}
}