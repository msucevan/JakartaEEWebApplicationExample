package WebApplication.Users;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="user")
public class User {
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
