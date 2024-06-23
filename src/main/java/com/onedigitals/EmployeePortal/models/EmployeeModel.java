package com.onedigitals.EmployeePortal.models;

import java.util.Date;

import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeModel {
    private int id;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String gender;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", locale = "UTC")
    private Date dob;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", locale = "UTC")
    private Date startDate;
    private String address;
    private String password;
    private int leaveBalance;

}