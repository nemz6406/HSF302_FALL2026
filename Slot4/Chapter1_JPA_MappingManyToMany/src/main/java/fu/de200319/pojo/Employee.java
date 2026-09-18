package fu.de200319.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private BigDecimal salary;
    private LocalDate hireDate;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean active = true;

    // Chuẩn bị cho TODO 5.2. Dùng @Transient để code chạy được trong TODO 5.1
    @Transient
    private Set<Project> projects = new HashSet<>();

    public Employee() {}

    public Employee(String fullName, BigDecimal salary, LocalDate hireDate, String email, Gender gender, boolean active) {
        this.fullName = fullName;
        this.salary = salary;
        this.hireDate = hireDate;
        this.email = email;
        this.gender = gender;
        this.active = active;
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public Set<Project> getProjects() { return projects; }
    public void setProjects(Set<Project> projects) { this.projects = projects; }
}