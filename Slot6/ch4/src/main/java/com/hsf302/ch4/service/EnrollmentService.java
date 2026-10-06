package com.hsf302.ch4.service;

public interface EnrollmentService {
    // ===== TODO 12 =====
    void enrollStudentToCourse(String studentCode, String courseCode);
    // ===== TODO 13 =====
    void unenrollStudentFromCourse(String studentCode, String courseCode);
    // ===== TODO 14 =====
    java.util.List<com.hsf302.ch4.pojo.Course> getCoursesByStudent(String studentCode);
    // ===== TODO 15 =====
    java.util.List<com.hsf302.ch4.pojo.Student> getStudentsByCourse(String courseCode);
}