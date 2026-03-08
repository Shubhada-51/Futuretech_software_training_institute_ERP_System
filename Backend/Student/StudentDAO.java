package Student;
import java.sql.*;

public class StudentDAO {
    // Database madhun profile load karne
    public Student getStudentProfile(String email) {
        Student s = null;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM student WHERE email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = new Student();
                s.setName(rs.getString("name"));
                s.setDomain(rs.getString("domain"));
                s.setBatch(rs.getString("batch"));
                s.setContact(rs.getString("contact"));
                s.setEmail(rs.getString("email"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return s;
    }

    // Profile update karne (Fakt Editable fields: batch, contact, email)
    public boolean updateProfile(Student s, String oldEmail) {
        boolean status = false;
        try (Connection con = DBConnection.getConnection()) {
            // SQL query madhye batch, contact ani email update hotat
            String sql = "UPDATE student SET batch=?, contact=?, email=? WHERE email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getBatch());
            ps.setString(2, s.getContact());
            ps.setString(3, s.getEmail()); 
            ps.setString(4, oldEmail);    
            
            if (ps.executeUpdate() > 0) status = true;
        } catch (Exception e) { e.printStackTrace(); }
        return status;
    }
}