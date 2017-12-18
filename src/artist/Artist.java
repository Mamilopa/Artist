
package artist;


public class Artist {
    int id;
    String firstname;
    String lastname;
    int age;

    public Artist(int id, String firstname, String lastname, int age) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public int getId() {
        return id;
    }
    
    
}
