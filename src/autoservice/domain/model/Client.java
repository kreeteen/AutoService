package autoservice.domain.model;

public class Client {
    private Long clientId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;

    public Client(Long clientId, String firstName, String lastName, String middleName, String phone) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phone = phone;
    }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
