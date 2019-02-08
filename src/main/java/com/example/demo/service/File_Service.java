package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repo.Repo_metadata;
import com.example.demo.model.Metadata;

@Service
public class File_Service {

	@Autowired
	Repo_metadata repo;

	public int save_metadata(File f) {

		try {
			BasicFileAttributes attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class,
					LinkOption.NOFOLLOW_LINKS);
			Metadata md = new Metadata(f.getName(), attr.lastAccessTime().toString(),
					attr.lastModifiedTime().toString(), attr.size(), attr.creationTime().toString());
			return repo.save(md).getId();
			

		} catch (IllegalStateException | IOException e1) {
			e1.printStackTrace();
			return -1;
		}

	}
	
	public List<Metadata> getMetadata_name(String name) {
		return repo.findAllByfileName(name);
	}
	
	public Metadata getMetadata_id(int id) {
		return repo.findByid(id);
	}
	
	
	
}
