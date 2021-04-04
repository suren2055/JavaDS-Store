package Models;

import CollectionsADT.LinkedList;

import java.util.Date;
import java.util.Objects;

public class User implements Comparable<User> {

    private String name;
    private String surname;
    private String email;
    private String password;
    private Date dateOfBirth;

    private LinkedList<Address> addresses;
    private LinkedList<CreditCard> creditCards;

    public User(){}
    public User(String name, String surname, String email, String password, Date dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }


    @Override
    public String toString() {
        return
                "" + name  +
                " " + surname +
                ", " + email +
                ", " + dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && Objects.equals(dateOfBirth, user.dateOfBirth);
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    @Override
    public int compareTo(User o) {
        if (this.getName().equals(o.getName()) && this.getSurname().equals(o.getSurname()) && this.getDateOfBirth().equals(o.getDateOfBirth()))
            return 0;
        return -1;
    }
}
