package com.example.prodigyteacher;

public class student_list_adapter {

    private String Name, Roll, Classe, Section, Attendance, Comments;

    public student_list_adapter() {
    }

    public student_list_adapter(String name, String roll, String classe, String section, String attendance, String comments) {
        Name = name;
        Roll = roll;
        Classe = classe;
        Section = section;
        Attendance = attendance;
        Comments = comments;
    }

    public String getName() {
        return Name;
    }

    public String getRoll() {
        return Roll;
    }

    public String getClasse() {
        return Classe;
    }

    public String getSection() {
        return Section;
    }

    public String getAttendance() {
        return Attendance;
    }

    public String getComments() {
        return Comments;
    }
}
