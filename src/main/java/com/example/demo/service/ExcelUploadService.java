package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Post;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PostRepository;

@Service
public class ExcelUploadService {

//	private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//	static String[] HEADERs = { "post_description", "title", "category_id" };
//	private static final String SHEET_NAME = "Sheet1";

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	
	public ResponseEntity<?> importExcelToDatabase(MultipartFile multipartFile)
			throws IOException, InvalidFormatException {
		ResponseDTO responseDTO = new ResponseDTO();
		try {

			if (multipartFile == null) {
				responseDTO.setError(true);
				responseDTO.setStatusCode(400);
				responseDTO.setResult("Please Provide Excel File");
				return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
			}

			File myFile = new File(multipartFile.getOriginalFilename());
			try (FileOutputStream fileOutputStream = new FileOutputStream(myFile)) {
				FileInputStream fileInputStream = new FileInputStream(myFile);

				fileOutputStream.write(multipartFile.getBytes());

				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

				XSSFSheet sheet = workbook.getSheetAt(1);

				List<Post> posts = new ArrayList<>();

				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue;
					}

					Post post = new Post();

					Cell cell = row.getCell(0);
					if (cell != null) {
						if (cell.getCellType() == CellType.STRING) {
							post.setTitle(cell.getStringCellValue());
						}

					}
					cell = row.getCell(1);
					if (cell != null) {
						if (cell.getCellType() == CellType.STRING) {
							post.setPostDescription(cell.getStringCellValue());
						}
					}

					cell = row.getCell(2);
					if (cell != null) {
						if (cell.getCellType() == CellType.NUMERIC) {

							long categoryId = (long) cell.getNumericCellValue();

							System.out.println("categoryId----------->" + categoryId);

							Category category = categoryRepository.findById(categoryId).orElse(null);

							System.out.println("category===========>" + category);
//							
//							if (category != null) {
//					            System.out.println("Found category: " + category);
//					        } else {
//					            System.out.println("Category with ID " + categoryId + " not found.");
//					        }
//							

							post.setCategory(category);
						} else if (cell.getCellType() == CellType.STRING) {
							String categoryIdString = cell.getStringCellValue();
							try {
								long categoryId = Long.parseLong(categoryIdString.trim());
								Category category = categoryRepository.findById(categoryId).orElse(null);

//								if (category != null) {
//					                System.out.println("Found category: " + category);
//					            } else {
//					                System.out.println("Category with ID " + categoryId + " not found.");
//					            }

								post.setCategory(category);
							} catch (NumberFormatException e) {
								System.err.println("Invalid Category ID format: " + categoryIdString);

							}

						}
					}

					posts.add(post);

					System.out.println("post========>>>" + post);

					System.out.println("excels --------->" + posts);

				}
				postRepository.saveAll(posts);

				System.out.println("data----------->" + posts);
			}

			responseDTO.setError(false);
			responseDTO.setStatusCode(200);
			responseDTO.setResult("excel saved Successfully");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} catch (

		Exception e) {
			e.printStackTrace();
			responseDTO.setError(true);
			responseDTO.setStatusCode(500);
			responseDTO.setResult("internal server error");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} finally {
			File myFile = new File(multipartFile.getOriginalFilename());
			if (myFile.exists()) {
				myFile.delete();
			}

		}

	}
}