package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Metadata;
import com.example.demo.service.File_Service;

	@RestController
	public class File_Controller {
		
		@Autowired
		File_Service fser;
		
		//POST API to Save metadata of file to H2 and save file to our file Syatem.
		@RequestMapping(value="/upload", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
			File convertFile = new File("C:\\"+file.getOriginalFilename()); //change where you want to save and also change at line 58(filename)
			convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(file.getBytes());
			fout.close();
			int id=fser.save_metadata(convertFile);
			return new ResponseEntity<>("File is uploaded successfully\n Your File ID is = "+id ,HttpStatus.OK);
		}
		
		//GET API to get MetaData by file name along with extention
		@RequestMapping(value="/metabyname/{name}", method=RequestMethod.GET)
		public List<Metadata> getMetadata_name(@PathVariable String name){
			return fser.getMetadata_name(name);
		}
		
		//GET API to get Metadata of file by file ID (Id Generated during uploading)
		@RequestMapping(value="/metabyid/{id}", method=RequestMethod.GET)
		public Metadata getMetadata_id(@PathVariable int id){
			return fser.getMetadata_id(id);
		}
		
		//GET API to Download the file by its name.
		@RequestMapping(value="/download/{name}", method=RequestMethod.GET) 
		public ResponseEntity<Object> downloadFile(@PathVariable String name) throws IOException  {
			String filename = "C:\\"+name;	// path define at line 34
			try {
			
			File file = new File(filename);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			
			ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
			return responseEntity;
			}
			catch(IOException e) {
				return new ResponseEntity<>("File with this name is not present \nor try to Enter the file name with extention ",HttpStatus.OK);
			}
			
		}	
	}