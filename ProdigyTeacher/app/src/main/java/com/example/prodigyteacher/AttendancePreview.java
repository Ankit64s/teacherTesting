package com.example.prodigyteacher;

public class AttendancePreview {
    private String Name, Roll, Classe, Section, Attendance;


    public AttendancePreview() {
    }

    public AttendancePreview(String name, String roll, String classe, String section, String attendance) {
        Name = name;
        Roll = roll;
        Classe = classe;
        Section = section;
        Attendance = attendance;
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
}
