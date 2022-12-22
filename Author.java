package pl.recstudio.library;

import java.util.Objects;

public class Author {

    private String firstName;
    private String lastName;
    private int authorID;
    private static int ID = 0;

    public Author(String firstName, String lastName){
        this.authorID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        ID++;
    }


    public String getInfo(){
        return authorID + " " + firstName + " " + lastName;
    }

    public static void decreaseAuthorID(){
        ID--;
    }

    public static int getNextID(){return ID;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}

