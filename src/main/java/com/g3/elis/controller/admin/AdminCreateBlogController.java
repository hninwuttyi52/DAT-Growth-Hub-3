package com.g3.elis.controller.admin;

import java.io.IOException;
import java.sql.Timestamp;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.g3.elis.dto.form.BlogPostDto;
import com.g3.elis.model.BlogPost;
import com.g3.elis.model.User;
import com.g3.elis.security.LoginUserDetail;
import com.g3.elis.service.BlogPostService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminCreateBlogController {

	private final String HTML_FILE_PATH = "src/main/resources/static/private/blog/blog-files/";
	private final String IMAGE_FILE_PATH = "src/main/resources/static/private/blog/blog-images/";

	@Autowired
	private BlogPostService blogPostService;

	@GetMapping("/admin-view-blog")
	public String adminViewBlog(Model model) {
		List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();
		model.addAttribute("blogPosts", blogPosts);
		model.addAttribute("content", "admin/admin-view-blog");
		return "/admin/admin-layout";
	}

	@SuppressWarnings("unused")
	@GetMapping("/blog-detail/{id}")
	public String adminViewBlogDetail(@PathVariable("id") int id, Model model) throws IOException {
		BlogPost blogPost = blogPostService.findById(id);
		if (blogPost != null) {
			LocalDateTime createdAt = blogPost.getCreatedAt().toLocalDateTime(); // If getCreatedAt() returns a
																					// Timestamp
			LocalDateTime now = LocalDateTime.now();
			Duration duration = Duration.between(createdAt, now);
			long days = duration.toDays();
			long hours = duration.toHours() % 24;
			long minutes = duration.toMinutes() % 60;
			String timeAgo = String.format("%d days, %d hours, %d minutes ago", days, hours, minutes);

			model.addAttribute("timeAgo", timeAgo);
			// Read HTML file content
			Path htmlFilePath = Paths.get(HTML_FILE_PATH + blogPost.getHtmlFileName());
			String htmlContent = Files.readString(htmlFilePath);
			model.addAttribute("htmlContent", htmlContent);
			model.addAttribute("blogPost", blogPost);
			return "/authenticated-user/blog-detail";
		}

		return "redirect:/admin/admin-view-blog";
	}

	@GetMapping("/blog-html/{fileName}")
	@ResponseBody
	public String getBlogHtml(@PathVariable String fileName) throws IOException {
		Path htmlFilePath = Paths.get(HTML_FILE_PATH + fileName);
		return Files.readString(htmlFilePath);
	}

	@GetMapping("/admin-create-blog")
	public String adminCreateBlog(Model model) {
		model.addAttribute("blogPostDto", new BlogPostDto());
		return "/admin/admin-create-blog";
	}

	@PostMapping("/admin-save-blog")
	public String adminCreateBlog(@Valid @ModelAttribute("blogPostDto") BlogPostDto blogPostDto,
			@RequestParam String content, @RequestParam(name = "img-file", required = false) MultipartFile imgFile,
			BindingResult result, Authentication authentication, Model model) throws IOException {
		LoginUserDetail userDetail = (LoginUserDetail) authentication.getPrincipal();
		User user = userDetail.getUser();
		if (result.hasErrors()) {
			model.addAttribute("blogPostDto", blogPostDto);
			return "/admin/admin-create-blog";
		}

		// Handle image file upload
		if (!imgFile.isEmpty()) {
			try {
				String originalFileName = imgFile.getOriginalFilename();
				String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
				String newImageFileName = dateTime + "_" + originalFileName;
				String imagePath = IMAGE_FILE_PATH + newImageFileName;

				Files.write(Paths.get(imagePath), imgFile.getBytes());
				blogPostDto.setImageFile(newImageFileName);

			} catch (IOException e) {
				e.printStackTrace();
				return "redirect:/admin-create-blog";
			}
		}

		// Handle HTML file upload
		BlogPost blogPost;
		if (blogPostDto.getId() > 0) {
			blogPost = blogPostService.findById(blogPostDto.getId());
			if (blogPost == null) {
				return "redirect:/edit/{id}";
			}
		} else {
			blogPost = new BlogPost();
			String fileName = UUID.randomUUID().toString() + ".html";
			blogPostDto.setHtmlFileName(fileName);
		}

		String filePath = HTML_FILE_PATH + blogPostDto.getHtmlFileName();
		if (Files.exists(Paths.get(filePath))) {
			blogPostDto.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			Files.write(Paths.get(filePath), content.getBytes());
		} else {
			blogPostDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			blogPostDto.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			blogPostDto.setUsers(user);

			Files.write(Paths.get(filePath), content.getBytes());
		}

		blogPostService.saveBlogPost(blogPostDto);

		return "redirect:/admin/admin-view-blog";
	}

	@GetMapping("/admin-delete-blog/{id}")
	public String adminDeleteBlog(@PathVariable int id) throws IOException {
		blogPostService.deleteBlogPost(id);
		return "redirect:/admin/admin-view-blog";
	}
}
