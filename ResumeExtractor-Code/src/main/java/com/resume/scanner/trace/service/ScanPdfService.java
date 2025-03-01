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

	/**
		* @param list_OF_Resume 
		* @return
		* @throws IOException
		*/
	public List<String> scanPdfFromFile(List<MultipartFile> list_OF_Resume) throws IOException {
		PDDocument document =null ;
		PDFTextStripper stripper = new PDFTextStripper();

		List<String> listofcontent = new ArrayList<>();
		List<PDDocument> docs = new ArrayList<>();

		for (MultipartFile file : list_OF_Resume) {
			document = PDDocument.load(file.getInputStream());
			docs.add(document);
		}
		
			for (int i=0;i<docs.size();i++)  {
				String content = stripper.getText(docs.get(i));
				listofcontent.add(content.toLowerCase());
			}
			
		document.close();
		return listofcontent;
	}

}
