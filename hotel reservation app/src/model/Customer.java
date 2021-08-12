package model;
import java.util.regex.*;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private final String emailRegex = "^(.+)@(.+).com$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email) {
        if(!pattern.matcher(email).matches()){
         throw new IllegalArgumentException("Invalid email format. ");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString(){
        return "First Name: "+firstName+"\nLast name: "+lastName+"\nemail: "+email+"\n";
    }
}