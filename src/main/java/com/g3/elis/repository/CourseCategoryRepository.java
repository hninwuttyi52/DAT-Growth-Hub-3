package com.g3.elis.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g3.elis.model.CourseCategory;

@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Integer> {

	void deleteById(Long id);

	CourseCategory findById(Long id);

	List<CourseCategory> findByCategoryNameContainingIgnoreCase(String categoryName);

	Page<CourseCategory> findAll(Pageable pageable);

}
