package WebApplication.Users;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@NamedQueries({
        @NamedQuery(name = User.FIND_ALL, query = "select e from User e order by e.lastName"),
        @NamedQuery(name = User.FIND_BY_USR_PWD, query = "select e from User e where e.usr= :usr and e.pwd= :pwd"),
        @NamedQuery(name = User.FIND_BY_USR, query = "select e from User e where e.usr= :usr"),
        @NamedQuery(name = User.SEARCH, query = "select e from User e where e.firstName like :fname or e.lastName like :lname or e.usr like :usr")
})
@Entity
@Table(name="user")
public class User implements Serializable {
    public static final String FIND_ALL = "User.findAll";
    public static final String FIND_BY_USR_PWD = "User.findByUserPwd";
    public static final String FIND_BY_USR = "User.findByUser";
    public static final String SEARCH = "User.search";

    @NotEmpty
    @Column(name = "usr", nullable = false, unique = true)
    private String usr;
    @NotEmpty
    @Column(name = "pwd", nullable = false)
    private String pwd;
    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;
    @NotEmpty
    @Column(name = "surname", nullable = false)
    private String surname;
    @NotEmpty
    @Column(name = "birth_date" )
    private String birthDate;

    public User(@NotEmpty String usr, @NotEmpty String pwd, @NotEmpty String name, @NotEmpty String surname) {
        this.usr = usr;
        this.pwd = pwd;
        this.name = name;
        this.surname = surname;

    }

    public User() {
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "usr='" + usr + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate='" + birthDate.format(String.valueOf(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) + '\'' +
                '}';
    }
}
