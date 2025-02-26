package com.resume.scanner.trace.controller;

import com.resume.scanner.trace.models.ResumeDetail;
import com.resume.scanner.trace.repository.ResumeRepository;
import com.resume.scanner.trace.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	private final ResumeRepository resumeRepository;
	
	@PostMapping("/check-resume")
	public ResumeDetail generateResumeDetails(@RequestParam("file") MultipartFile file,
																																											@RequestParam("jd") String jd , @RequestParam("techSkill") String techSkill  ) throws IOException{
		return resumeService.generateResumeDetails(file,jd,techSkill);
	}


	@PostMapping("/save-resume")
	public ResponseEntity<ResumeDetail> saveResumeDetails(@RequestBody ResumeDetail data) {
		try {
			ResumeDetail resumeDetail = resumeRepository
					.save(new ResumeDetail(data.getTechSkill(), data.getSetPhoneNo(),data
							.getSetFullName(),data.getSetEmail(),false));
			return new ResponseEntity<>(resumeDetail, HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}