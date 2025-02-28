package com.resume.scanner.trace.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
/**
	* @Author: Abhinav Shankar
	* @Description: read the PDF or Doc File.
	* @Date: 22 Feb;2025
	*/
@Service
public class ScanPdfService {

	public String scanPdfFromFile(MultipartFile[] file) throws IOException {
		PDDocument document =null ;

		for (MultipartFile file1 : file) {
			document = PDDocument.load(file1.getInputStream());
		}

		PDFTextStripper stripper = new PDFTextStripper();
		String content = stripper.getText(document);
		content = content.toLowerCase();
		document.close();
		return content;
	}

	public List<String> scanPdfFromFile1(List<MultipartFile> list_OF_Resume) throws IOException {
		PDDocument document =null ;
		List<String> ss = new ArrayList<>();
		List<PDDocument> docs = new ArrayList<>();

		for (MultipartFile file2 : list_OF_Resume) {
			document = PDDocument.load(file2.getInputStream());
			docs.add(document);

		}
			PDFTextStripper stripper1 = new PDFTextStripper();

			for (int i=0;i<docs.size();i++)  {
				String content1 = stripper1.getText(docs.get(i));
				ss.add(content1.toLowerCase());
			}
			
		document.close();
		return ss;
	}

}
