package com.resume.ats.check.controller;

import com.resume.ats.check.models.ResumeDetail;
import com.resume.ats.check.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
	* @Author: Abhinav Shankar
	* @Description: controller to get the input from UI
	* @Date: 22 Feb;2025
	*/
@RestController
@RequiredArgsConstructor
public class ResumeController {
	
	private final ResumeService resumeService;
	
	@PostMapping("/check-resume")
	public ResumeDetail generateResumeDetails(@RequestParam("file") MultipartFile file,
																																											@RequestParam("jd") String jd , @RequestParam("techSkill") String techSkill  ) throws IOException{
		return resumeService.generateResumeDetails(file,jd,techSkill);
	}
}