package com.onedigitals.EmployeePortal.apis.employee_apis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.onedigitals.EmployeePortal.models.EmployeeModel;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("employee")
public class Employee {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws ParseException 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EmployeeModel> getIt() throws ParseException {
   	
   	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    Date dob = dateFormat.parse("1990-01-01T00:00:00.000+00:00");
    Date startDate = dateFormat.parse("2022-01-01T00:00:00.000+00:00");

    EmployeeModel employee = new EmployeeModel(
        1,                          // id
        "John",                     // firstName
        "Doe",                      // lastName
        "Software Engineer",        // role
        "john.doe@example.com",     // email
        "Male",                     // gender
        dob,                        // dob
        startDate,                  // startDate
        "123 Main St, City, State", // address
        "password123",              // password
        10                          // leaveBalance
    );
    
    
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    Date dob2 = dateFormat.parse("1992-02-02T00:00:00.000+00:00");
    Date startDate2 = dateFormat.parse("2023-02-02T00:00:00.000+00:00");

    EmployeeModel employee2 = new EmployeeModel(
        2,                          // id
        "Jane",                     // firstName
        "Smith",                    // lastName
        "Data Analyst",             // role
        "jane.smith@example.com",   // email
        "Female",                   // gender
        dob,                        // dob
        startDate,                  // startDate
        "456 Main St, City, State", // address
        "password456",              // password
        15                          // leaveBalance
    );
    	
    	return List.of(employee,employee2);
    }
}
