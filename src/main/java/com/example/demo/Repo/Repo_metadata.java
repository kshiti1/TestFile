package com.example.demo.Repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Metadata;



public interface Repo_metadata  extends CrudRepository<Metadata, Integer> {
	public List<Metadata> findAllByfileName(String name);
	public Metadata findByid(int id);
}
