package autoservice.domain.model;
import java.time.LocalDate;

public class Employee {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String address;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String position;
    private Double salary;
    private Integer experience;
    private String workSchedule;
    private Double seniorityBonus;

    public Employee(Long employeeId,
                    String firstName,
                    String lastName,
                    String middleName,
                    String address,
                    LocalDate dateOfBirth,
                    String phoneNumber,
                    String position,
                    Double salary,
                    Integer experience,
                    String workSchedule,
                    Double seniorityBonus) {
        this.employeeId = (Long) employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.salary = salary;
        this.experience = experience;
        this.workSchedule = workSchedule;
        this.seniorityBonus = seniorityBonus;
    }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public Double getSalary() { return (Double) salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    public Integer getExperience() { return (Integer) experience; }
    public void setExperience(Integer experience) { this.experience = experience; }
    public String getWorkSchedule() { return workSchedule; }
    public void setWorkSchedule(String workSchedule) { this.workSchedule = workSchedule; }
    public double getSeniorityBonus() { return seniorityBonus; }
    public void setSeniorityBonus(Double seniorityBonus) { this.seniorityBonus = seniorityBonus; }
}
