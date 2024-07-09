package com.example.demo.Excel;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.example.demo.entity.Post;
import com.example.demo.repository.CommentsRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class ExcelExporterr {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Post> posts;
	@SuppressWarnings("unused")
	private CommentsRepository commentRepository;

	public ExcelExporterr(List<Post> posts, CommentsRepository commentRepository) {
		this.posts = posts;
		this.commentRepository = commentRepository;
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Users");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "id", style);
		createCell(row, 1, "Title", style);
		createCell(row, 2, "Post description", style);
		createCell(row, 3, "Category", style);
		createCell(row, 4, "Created Date", style);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
	    sheet.autoSizeColumn(columnCount);
	    Cell cell = row.createCell(columnCount);
	    if (value instanceof Integer) {
	        cell.setCellValue((Integer) value);
	    } else if (value instanceof Boolean) {
	        cell.setCellValue((Boolean) value);
	    } else if (value instanceof Long) {
	        cell.setCellValue(String.valueOf(value));
	    } else if (value instanceof java.sql.Timestamp) { // Handle Timestamp
	        String formattedDate = value.toString(); // Convert Timestamp to String
	        cell.setCellValue(formattedDate);
	    } else {
	        cell.setCellValue((String) value);
	    }
	    cell.setCellStyle(style);
	}


	private void writeDataLines() {
	    int rowCount = 1;

	    CellStyle style = workbook.createCellStyle();
	    XSSFFont font = workbook.createFont();
	    font.setFontHeight(14);
	    style.setFont(font);

	    for (Post p : posts) {
	        Row row = sheet.createRow(rowCount++);
	        int columnCount = 0;

	        createCell(row, columnCount++, p.getId(), style);
	        createCell(row, columnCount++, p.getTitle(), style);
	        createCell(row, columnCount++, p.getPostDescription(), style);
	        
	        if (p.getCategory() != null) {
	            createCell(row, columnCount++, p.getCategory().getId().toString(), style);
	        } else {
	            createCell(row, columnCount++, "", style);  
	        }

	        if (p.getCreatedDate() != null) {
	            createCell(row, columnCount++, p.getCreatedDate().toString(), style);
	        } else {
	            createCell(row, columnCount++, "", style); 
	        }
	    }
	}

	

	public void export(HttpServletResponse response, String fileName) throws IOException {
		writeHeaderLine();
		writeDataLines();

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx\"");
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();
	}

}