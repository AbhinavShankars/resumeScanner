package com.resume.scanner.trace.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
/**
	* @Author: Abhinav Shankar
	* @Description: read the PDF or Doc File.
	* @Date: 22 Feb;2025
	*/
@Service
public class ScanPdfService {

	/**
		* @param file 
		* @return
		* @throws IOException
		*/
	public String scanPdfFromFile(MultipartFile file) throws IOException {
		
		PDDocument document = PDDocument.load(file.getInputStream());
	    PDFTextStripper stripper = new PDFTextStripper();
	    String content = stripper.getText(document);
	    content = content.toLowerCase();
	    document.close();
	   	return content;
	}

}
