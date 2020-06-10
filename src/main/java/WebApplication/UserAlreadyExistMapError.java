package WebApplication;

import WebApplication.Users.UserAlreadyExistException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
//Questa classe mappa un'errore HTTP in JAVA
@Provider
 class UserAlreadyExistExceptionMapper implements ExceptionMapper<UserAlreadyExistException> {

    @Override
    public Response toResponse(UserAlreadyExistException ex) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("reason", "username " + ex.getUsername() + " already exist...")
                .build();
    }

}

