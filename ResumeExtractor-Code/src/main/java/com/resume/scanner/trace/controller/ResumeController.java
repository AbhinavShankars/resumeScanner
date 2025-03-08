package com.resume.scanner.trace.controller;

import com.resume.scanner.trace.models.ResumeDetail;
import com.resume.scanner.trace.service.ResumeService;
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


 /**
  * @param file       :upload pdf
  * @param experience put experience
  * @param techSkill  : put skill
  * @param jd         : put JD
  * @return : object of ResumeDetails
  * @throws IOException Exception
  */
 @PostMapping("/check-resume")
 public ResumeDetail generateResumeDetails(@RequestParam("file") MultipartFile file, @RequestParam("experience") String experience, @RequestParam("techSkill") String techSkill, @RequestParam("jd") String jd) throws IOException {
  return resumeService.generateResumeDetails(file, experience, techSkill, jd);
 }
}