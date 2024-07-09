package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ResponseMessage;
import com.example.demo.service.ExcelUploadService;

@RestController
@RequestMapping("/api")
public class ExcelUploadController {

	public ExcelUploadService getExcelService() {
		return excelService;
	}

	public void setExcelService(ExcelUploadService excelService) {
		this.excelService = excelService;
	}

	@Autowired
	private ExcelUploadService excelService;

	@PostMapping("/import/excel")

	public ResponseEntity<ResponseMessage> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
		excelService.importExcelToDatabase(file);
		ResponseMessage responseMessage = new ResponseMessage("Excel file imported successfully.");
		return ResponseEntity.ok(responseMessage);

	}
}