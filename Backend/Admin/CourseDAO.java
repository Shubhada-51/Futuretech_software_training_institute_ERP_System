

import java.sql.*;
import java.util.*;

public class CourseDAO {

    // 1. Course Add karnyāthī Method
    public boolean addCourse(String name, String domain, String duration, String description, String fee) {
        boolean status = false;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO course(title,domain,description,duration,fee,students,modules) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, domain);
            ps.setString(3, description);
            ps.setString(4, duration);
            ps.setString(5, fee);
            ps.setInt(6, 0);
            ps.setInt(7, 10);
            if (ps.executeUpdate() > 0) status = true;
        } catch (Exception e) { e.printStackTrace(); }
        return status;
    }
    public int getTotalStudents() {
        int total = 0;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM student";
            ResultSet rs = con.createStatement().executeQuery(sql);
            if (rs.next()) total = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return total;
    }
    // 2. Sagle Course Load karnyāthī Method
    public List<String[]> getCourses() {
        List<String[]> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT title,domain,description,duration,fee,students,modules FROM course";
            ResultSet rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                list.add(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 3. Course Delete karnyāthī Method
    public boolean deleteCourse(String title) {
        boolean status = false;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM course WHERE title=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            if (ps.executeUpdate() > 0) status = true;
        } catch (Exception e) { e.printStackTrace(); }
        return status;
    }

//4. Course Update karnyāthī Method
  public boolean updateCourse(String oldTitle, String newTitle, String domain, String duration, String description, String fee) {
  boolean status = false;
  try (Connection con = DBConnection.getConnection()) {
     String sql = "UPDATE course SET title=?, domain=?, description=?, duration=?, fee=? WHERE title=?";
     PreparedStatement ps = con.prepareStatement(sql);
     ps.setString(1, newTitle);
     ps.setString(2, domain);
     ps.setString(3, description);
     ps.setString(4, duration);
     ps.setString(5, fee);
     ps.setString(6, oldTitle);
     if (ps.executeUpdate() > 0) status = true;
 } catch (Exception e) { e.printStackTrace(); }
 return status;
 

 }
}