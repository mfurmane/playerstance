package pl.mfurmane.db.dto;

import jakarta.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name="user")
public class UserDTO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="email", length=100, nullable=false, unique=true)
    private String email;

    @Column(name="password", length=100, nullable=false, unique=true)
    private String password;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<HeroDTO> heroes = new TreeSet<>();

//    public void addComment(HeroDTO comment) {
//        heroes.add(comment);
//        comment.setUser(this);
//    }
//
//    public void removeComment(HeroDTO comment) {
//        heroes.remove(comment);
//        comment.setUser(null);
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Set<HeroDTO> getHeroes() {
//        return heroes;
//    }
//
//    public void setHeroes(Set<HeroDTO> heroes) {
//        this.heroes = heroes;
//    }
}
