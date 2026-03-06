/*
-- FUTURETECH ERP SYSTEM - DATABASE QUERIES
-- Database Name: erp_system
-- Table Name: course
-- =================================================================

-- 1.(Create Database):-

CREATE DATABASE IF NOT EXISTS erp_system;
USE erp_system;
=========================================================================
-- 2. TABLE TAYAR KARNYASATHI (Create Table)
-- (Jar table aadhi pasun asel tar hi query chalvu naye)
CREATE TABLE course (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    domain VARCHAR(200),
    description TEXT,
    duration VARCHAR(100),
    fee VARCHAR(50),
    students INT,
    modules INT
);
=====================================================================================================
-- 3. NAVIN COURSE ADD KARNYASATHI (Insert Query - Admin Dashboard)

-- i. Java Full Stack Development
INSERT INTO course (title, domain, description, duration, fee, students, modules) 
VALUES ('Java Full Stack Development', 'Java Development', 'Complete Java development with Spring Boot, Hibernate, and React', '1 Month', '3000', 0, 10);

-- ii. Python
INSERT INTO course (title, domain, description, duration, fee, students, modules) 
VALUES ('Python', 'Data Science', 'Python, Machine Learning, Data Analysis, and Visualization', '1 Months', '2000', 0, 10);

-- ii. Web Development
INSERT INTO course (title, domain, description, duration, fee, students, modules) 
VALUES ('Web Development', 'Frontend Development', 'HTML, CSS, JavaScript, and modern frameworks', '3 month', '3000', 0, 10);

-- iv. Mobile App Development 
-- (Note: Screenshot madhye yachi duration ani fee cut jhali ahe, tyamule mi '2 Months' ani '4000' takle ahe, tu te badalu shaktos)
INSERT INTO course (title, domain, description, duration, fee, students, modules) 
VALUES ('Mobile App Development', 'Mobile Development', 'React Native and Flutter for cross-platform development', '2 Months', '4000', 0, 10);
==============================================================================================
-- 4. SAGLE COURSES BAGHNYASATHI (Select Query - Load Courses)

SELECT * FROM course;
======================================================================
-- 5. STUDENT DIRECTORY (Jar student table asel tar tyachi query)
-- SELECT COUNT(*) FROM student;
*/




import java.io.IOException;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/CourseServlet")
public class CourseServlet extends HttpServlet {

    // 1. GET Method - Data HTML la pathavnyasathi
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        CourseDAO dao = new CourseDAO();
        List<String[]> list = dao.getCourses();
        int totalStudents = dao.getTotalStudents(); 
        
        StringBuilder json = new StringBuilder("{");
        json.append("\"totalStudentCount\":").append(totalStudents).append(",");
        json.append("\"courses\":[");
        
        for (int i = 0; i < list.size(); i++) {
            String[] c = list.get(i);
            json.append("{")
                .append("\"title\":\"").append(c[0]).append("\",")
                .append("\"domain\":\"").append(c[1]).append("\",")
                .append("\"desc\":\"").append(c[2].replace("\"", "\\\"")).append("\",")
                .append("\"duration\":\"").append(c[3]).append("\",")
                .append("\"fee\":\"").append(c[4]).append("\",")
                .append("\"students\":").append(c[5]).append(",")
                .append("\"modules\":").append(c[6])
                .append("}");
            if (i < list.size() - 1) json.append(",");
        }
        json.append("]}");
        response.getWriter().print(json.toString());
    }

    // 2. POST Method - Add, Update, Delete karnyashati 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action"); 
        CourseDAO dao = new CourseDAO();
        boolean status = false;

        if ("delete".equals(action)) {
            String title = request.getParameter("title");
            status = dao.deleteCourse(title);
        } 
        else if ("update".equals(action)) {
            String oldTitle = request.getParameter("oldTitle");
            String newTitle = request.getParameter("course_name");
            String domain = request.getParameter("domain");
            String duration = request.getParameter("duration");
            String description = request.getParameter("description");
            String fee = request.getParameter("fee");
            status = dao.updateCourse(oldTitle, newTitle, domain, duration, description, fee);
        } 
        else if ("add".equals(action)) { 
            String name = request.getParameter("course_name");
            String domain = request.getParameter("domain");
            String duration = request.getParameter("duration");
            String description = request.getParameter("description");
            String fee = request.getParameter("fee");
            status = dao.addCourse(name, domain, duration, description, fee);
        }

        response.setContentType("text/plain");
        response.getWriter().write(status ? "success" : "error");
    }
}