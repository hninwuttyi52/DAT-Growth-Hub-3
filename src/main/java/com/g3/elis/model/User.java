package com.g3.elis.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String division;
	private String staffId;
	private String name;
	private String doorLogNo;
	private String dept;
	private String team;
	private String email;
	private String status;
	private String password;
	private String gender;

	@Column(nullable = false)
	private boolean enabled;

	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private Set<Course> courses = new HashSet<>();

	@OneToOne(mappedBy = "users", fetch = FetchType.LAZY)
	private Profile profiles;

	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private Set<EnrolledCourse> enrolledCourses = new HashSet<>();

	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private Set<Report> reports = new HashSet<>();

	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private Set<CourseCategory> courseCategories = new HashSet<>();

	public User() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDoorLogNo() {
		return doorLogNo;
	}

	public void setDoorLogNo(String doorLogNo) {
		this.doorLogNo = doorLogNo;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Profile getProfiles() {
		return profiles;
	}

	public void setProfiles(Profile profiles) {
		this.profiles = profiles;
	}

	public Set<EnrolledCourse> getEnrolledCourses() {
		return enrolledCourses;
	}

	public void setEnrolledCourses(Set<EnrolledCourse> enrolledCourses) {
		this.enrolledCourses = enrolledCourses;
	}

	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports = reports;
	}

	public Set<CourseCategory> getCourseCategories() {
		return courseCategories;
	}

	public void setCourseCategories(Set<CourseCategory> courseCategories) {
		this.courseCategories = courseCategories;
	}

	public User(int id, String division, String staffId, String name, String doorLogNo, String dept, String team,
			String email, String status, String password, String gender, boolean enabled, Set<Role> roles,
			Set<Course> courses, Profile profiles, Set<EnrolledCourse> enrolledCourses, Set<Report> reports,
			Set<CourseCategory> courseCategories) {
		super();
		this.id = id;
		this.division = division;
		this.staffId = staffId;
		this.name = name;
		this.doorLogNo = doorLogNo;
		this.dept = dept;
		this.team = team;
		this.email = email;
		this.status = status;
		this.password = password;
		this.gender = gender;
		this.enabled = enabled;
		this.roles = roles;
		this.courses = courses;
		this.profiles = profiles;
		this.enrolledCourses = enrolledCourses;
		this.reports = reports;
		this.courseCategories = courseCategories;
	}
	
}
