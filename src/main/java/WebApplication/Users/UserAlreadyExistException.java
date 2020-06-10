package WebApplication.Users;

import javax.ejb.EJBException;

public class UserAlreadyExistException extends EJBException {

    private final String username;

    public UserAlreadyExistException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


}
