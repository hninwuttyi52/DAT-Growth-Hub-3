package com.g3.elis.controller.admin;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

import com.g3.elis.dto.form.CourseCategoryDto;
import com.g3.elis.model.CourseCategory;
import com.g3.elis.model.User;
import com.g3.elis.security.LoginUserDetail;
import com.g3.elis.service.CourseCategoryService;

@Controller
@RequestMapping("/admin")
public class CourseCategoryController {
	@Autowired
	private CourseCategoryService courseCategoryService;

	@GetMapping("/admin-course-category")
	public String getAllCourseCategories(Model model) {
	    List<CourseCategory> courseCategories = courseCategoryService.getAllCourseCategories();
	    int categoryCount = courseCategories.size();
	    
	    model.addAttribute("courseCategories", courseCategories);
	    model.addAttribute("content", "admin/admin-course-category");
	    model.addAttribute("categoryCount", categoryCount);
	    return "/admin/admin-layout";
	}

	@PostMapping("/admin-course-category")
	public String addCourseCategory(@RequestParam("categoryName") String categoryName, Model model, Authentication authentication) {
	    LoginUserDetail loginUser = (LoginUserDetail) authentication.getPrincipal();
	    User user = loginUser.getUser();
	    int user_id = user.getId();
	    CourseCategory courseCategory = new CourseCategory();
	    courseCategory.setCategoryName(categoryName);
	    courseCategory.setUsers(user);
	    model.addAttribute("user_id", user_id);
	    courseCategory.getUsers();
	    courseCategoryService.saveCourseCategory(courseCategory);

	    return "redirect:/admin/admin-course-category";
	}


	@GetMapping("/admin-course-category/edit")
	public String editCourseCategory(@PathVariable("id") int id, Model model) {
		CourseCategory courseCategory = courseCategoryService.getCourseCategoryById(id);
		CourseCategoryDto courseCategoryDto = new CourseCategoryDto();
		courseCategoryDto.setCategoryName(courseCategory.getCategoryName()); // Corrected line
		model.addAttribute("courseCategoryDto", courseCategoryDto);
		model.addAttribute("categoryId", id); // Added to pass the category ID
		return "admin/admin-course-category";
	}

	@PostMapping("/admin-course-category/edit")
	public String editCourseCategory(@RequestParam("categoryId")int id, @ModelAttribute CourseCategoryDto courseCategoryDto) {
		// Update the course category
		courseCategoryService.updateCourseCategory(id, courseCategoryDto);
		return "redirect:/admin/admin-course-category";
	}

	@PostMapping("/admin-course-category/delete/{id}")
	public String deleteCourseCategory(@PathVariable("id") Long id) {
		courseCategoryService.deleteCourseCategoryById(id);
		return "redirect:/admin/admin-course-category";
	}

	@GetMapping("/admin-course-category/search")
	public String getCourseCategories(@RequestParam(value = "search", required = false) String search, Model model) {
	    if (search != null && !search.isEmpty()) {
	        List<CourseCategory> courseCategories = courseCategoryService.searchCourseCategoriesByName(search);
	        int categoryCount = courseCategories.size();
	        model.addAttribute("courseCategories", courseCategories);
	        model.addAttribute("categoryCount", categoryCount);
	    } else {
	        List<CourseCategory> courseCategories = courseCategoryService.getAllCourseCategories();
	        int categoryCount = courseCategories.size();
	        model.addAttribute("courseCategories", courseCategories);
	        model.addAttribute("categoryCount", categoryCount);
	    }
	    model.addAttribute("content", "admin/admin-course-category");
	    return "/admin/admin-layout";
	}


}
