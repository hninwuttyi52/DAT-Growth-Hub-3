package com.g3.elis.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/admin-dashboard")
	public String home(Model model) {
		model.addAttribute("content", "admin/admin-dashboard");
		return "/admin/admin-layout";
	}

	@GetMapping("/admin-instructor-request")
	public String adminInstructorRequest(Model model) {
		model.addAttribute("content", "admin/admin-instructor-request");
		return "/admin/admin-layout";
	}

	@GetMapping("/admin-certificate-design")
	public String adminCertificateDesign(Model model) {
		model.addAttribute("content", "admin/admin-certificate-design");
		return "/admin/admin-layout";
	}

	@GetMapping("/admin-setting")
	public String adminSetting(Model model) {
		model.addAttribute("content", "admin/admin-setting");
		return "/admin/admin-layout";
	}

		
//	@GetMapping("/admin-create-course")
//	public String adminCreateCourse(Model model) {
//		return "/admin/admin-create-course";
//	}
	@GetMapping("/admin-quiz")
	public String adminQuiz(Model model) {
		return "/admin/admin-quiz";
	}
	
	
	

}