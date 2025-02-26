package com.resume.scanner.trace.repository;

import com.resume.scanner.trace.models.ResumeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @Author: Abhinav Shankar
 * @Description: repository to save data from UI
 * @Date: 26 Feb;2025
 */
@Repository
public interface ResumeRepository extends JpaRepository<ResumeDetail,Long> {}
