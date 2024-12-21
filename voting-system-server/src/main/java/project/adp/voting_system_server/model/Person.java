package project.adp.voting_system_server.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(indexes = {
        @Index(name = "index_aadhaarNumber", columnList = "aadhaarNumber")
})
public class Person {

    @Id
    @Column(unique = true, nullable = false)
    private String aadhaarNumber; // Unique Aadhaar number (Primary key)

    @Column(nullable = false)
    private String fullName; // Full name of the person

    @Column(nullable = false)
    private String gender; // Gender of the person

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth; // Date of birth

    @Column(nullable = false)
    private String addressLine1; // First line of the address

    private String addressLine2; // Second line of the address (optional)

    @Column(nullable = false)
    private String city; // City/Town/Village

    @Column(nullable = false)
    private String state; // State of the person

    @Column(nullable = false)
    private String pinCode; // PIN/Postal Code

    @Column(nullable = false)
    private String country; // Country (India)

    private String photograph; // URL or Binary data (Photo)

    private String mobileNumber; // Mobile number (optional)

    private String emailAddress; // Email address (optional)

    private String fatherName; // Father's name (optional)

    private String motherName; // Mother's name (optional)

    private String residenceType; // Urban or Rural

    // Getters and Setters

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhotograph() {
        return photograph;
    }

    public void setPhotograph(String photograph) {
        this.photograph = photograph;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    @Override
    public String toString() {
        return "Person{" + "\n" +
                "  aadhaarNumber: '" + aadhaarNumber + '\'' + "\n" +
                "  fullName: '" + fullName + '\'' + "\n" +
                "  gender: '" + gender + '\'' + "\n" +
                "  dateOfBirth: " + dateOfBirth + "\n" +
                "  address: '" + addressLine1 +
                (addressLine2 != null && !addressLine2.isEmpty() ? ", " + addressLine2 : "") +
                ", " + city + ", " + state + ", " + pinCode + ", " + country + '\'' + "\n" +
                "  photograph: '" + photograph + '\'' + "\n" +
                "  mobileNumber: '" + mobileNumber + '\'' + "\n" +
                "  emailAddress: '" + emailAddress + '\'' + "\n" +
                "  fatherName: '" + fatherName + '\'' + "\n" +
                "  motherName: '" + motherName + '\'' + "\n" +
                "  residenceType: '" + residenceType + '\'' + "\n" +
                '}';
    }
}
