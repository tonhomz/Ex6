/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex6;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.DatabaseHandler;
/**
 *
 * @author Prueksa
 */
public class StudentTable {
     public static int updateStudent(DatabaseHandler dbHandler, Student student) {
        String sql = "update student set name=?, GPA=? where id=?";
        int rowUpdated;
        try {
            rowUpdated = dbHandler.update(sql, student.getName(), student.getGPA(), student.getId());
        }
        catch (SQLException ex ) {
            
            rowUpdated = 0;
        }
        
        return rowUpdated;
    }
     public static int removeStudent(DatabaseHandler dbHandler, Student student) {
         String sql ="delete from student where id = ?";
         
         int rowDeleted;
         try {
            rowDeleted = dbHandler.update(sql, student.getId());
         }
         catch (SQLException ex ) {
             rowDeleted = 0;
         }
        return rowDeleted;
    }
    public static int insertStudent(DatabaseHandler dbHandler, Student student) {
         String sql = "insert into student (id, name, GPA)" + 
               " values (?,?,?)";
         
         int rowInserted;
         try {
             rowInserted = dbHandler.update(sql, student.getId(), student.getName(), student.getGPA());
         }
         catch(SQLException ex ) {
             rowInserted = 0;
         }
         return rowInserted;
    } 
     public static Student findStudentById(DatabaseHandler dbHandler, int id) throws SQLException {
        String sql = "select * from student where id = ?";
        ResultSet rs;
        Student student = null;
        rs = dbHandler.query(sql, id);
        if (rs.next()) {
           student = new Student();
           student.setId(rs.getInt("id"));
           student.setName(rs.getString("name"));
           student.setGPA(rs.getDouble("GPA"));
       }
        return student;
        
    }
    public static ArrayList<Student> findStudentByName(DatabaseHandler dbHandler, String name) throws SQLException {
        String sql = "select * from student where name = ?";
        ResultSet rs;
        ArrayList<Student> studentList = null;
        rs = dbHandler.query(sql, name);
        studentList = extractStudent(rs);
        return studentList;
        
    } 
    public static ArrayList<Student> findAllStudent(DatabaseHandler dbHandler) throws SQLException {
        String sql = "select * from student order by id";
        ResultSet rs; 
        ArrayList<Student> studentList = null;
        rs = dbHandler.query(sql);
        studentList = extractStudent(rs);
        return studentList;
    }
    private static ArrayList<Student> extractStudent(ResultSet rs) {
        ArrayList<Student> studentList = new ArrayList<>();
        try {
            while(rs.next()) {
                Student std = new Student();
                try {
                    std.setId(rs.getInt("id"));
                    std.setName(rs.getString("name"));
                    std.setGPA(rs.getDouble("GPA"));
                } catch (SQLException ex) {
                    Logger.getLogger(StudentTable.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                studentList.add(std);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(studentList.size() == 0) {
            studentList = null;
        }
        return studentList;
    }
}
