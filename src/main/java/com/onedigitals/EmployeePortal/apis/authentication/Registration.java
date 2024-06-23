package com.onedigitals.EmployeePortal.apis.authentication;

import com.onedigitals.EmployeePortal.dao.EmployeeDAO;
import com.onedigitals.EmployeePortal.models.EmployeeModel;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/register")
public class Registration {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(EmployeeModel employee) {
        try {
            // Create a new EmployeeDAO object
            EmployeeDAO employeeDAO = new EmployeeDAO();

            // Use the addEmployee method to register the user
            employeeDAO.addEmployee(employee);

            // Return a success message
            return Response.ok("Registration successful").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Registration failed: " + e.getMessage()).build();
        }
    }
}