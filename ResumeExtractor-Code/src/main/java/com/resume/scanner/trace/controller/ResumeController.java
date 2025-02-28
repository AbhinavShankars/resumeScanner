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
	public ResumeDetail generateResumeDetails(@RequestParam("file") MultipartFile[] file,
																																											@RequestParam("jd") String jd , @RequestParam("techSkill") String techSkill  ) throws IOException{
		List<MultipartFile> list_OF_Resume= new ArrayList<>();
		for (MultipartFile file1 : file) {
			if (!file1.isEmpty()) {
				String fileName = file1.getOriginalFilename();
				String contentType = file1.getContentType();
				Long fileSize = file1.getSize();
				byte[] fileData = file1.getBytes();

				// Use the injected FileService instance to save the file
			//	FileEntity savedFile = fileService.saveFile(fileName, contentType, fileSize, fileData);

				fileUploadCount++;

				long fileSizeKB = file1.getSize() / 1024;
				System.out.println("*************************/n");
				// Print file info for each file
				System.out.println(fileUploadCount + " File uploaded | File Size: " + fileSizeKB + " KB | File Name: "
																		+ file1.getOriginalFilename());
				System.out.println("*************************/n");
				list_OF_Resume.add(file1);
			}
		}

		return resumeService.generateResumeDetails1(list_OF_Resume,jd,techSkill);
	}
}