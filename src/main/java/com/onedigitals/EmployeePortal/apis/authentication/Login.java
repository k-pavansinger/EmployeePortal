package com.onedigitals.EmployeePortal.apis.authentication;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.mindrot.jbcrypt.BCrypt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.onedigitals.EmployeePortal.customExceptions.AuthenticationException;
import com.onedigitals.EmployeePortal.dao.EmployeeDAO;
import com.onedigitals.EmployeePortal.models.EmployeeModel;

@Path("/login")
public class Login {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(EmployeeModel employee) throws AuthenticationException {
            // Create a new EmployeeDAO object
            EmployeeDAO employeeDAO = new EmployeeDAO();

            // Retrieve the user's record from the database
            EmployeeModel storedEmployee = employeeDAO.getEmployee(employee.getEmail());
            

            // Validate the user's credentials
            // Compare the provided password with the stored hashed password
            if (storedEmployee != null && BCrypt.checkpw(employee.getPassword(), storedEmployee.getPassword())) {
                // If the credentials are valid, issue a token for the user
                String token = issueToken(storedEmployee.getId(), storedEmployee.getFirstName(), storedEmployee.getRole());

                // Return the token on the response
                return Response.ok(token).build();
            } else {
                throw new AuthenticationException("Login failed: Invalid credentials");
            }

    }

    private String issueToken(int id, String firstName, String role) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be able to be associated to the user
        // Here is where you should implement your token generation logic

        // For now we will use a simple JWT token:
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
            .withClaim("id", id)
            .withClaim("firstName", firstName)
            .withClaim("role", role)
            .sign(algorithm);

        return token;
    }
}