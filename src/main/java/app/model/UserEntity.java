package app.model;

import java.util.ArrayList;

public class UserEntity {
    private Long id;
    private  String name;
    private String surname;
    private String password;
    private  String email;
    private String phone;
    private  String address;
    private ArrayList<BookEntity> books;
    private ArrayList<CourseEntity> courses;
    private ArrayList<UniversityEntity> universities;


    public UserEntity() {
    }

    public UserEntity(Long id, String name, String surname, String password, String email, String phone, String address, ArrayList<BookEntity> books, ArrayList<CourseEntity> courses, ArrayList<UniversityEntity> universities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.books = books;
        this.courses = courses;
        this.universities = universities;
    }

    public ArrayList<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<BookEntity> books) {
        this.books = books;
    }

    public ArrayList<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<CourseEntity> courses) {
        this.courses = courses;
    }

    public ArrayList<UniversityEntity> getUniversities() {
        return universities;
    }

    public void setUniversities(ArrayList<UniversityEntity> universities) {
        this.universities = universities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
