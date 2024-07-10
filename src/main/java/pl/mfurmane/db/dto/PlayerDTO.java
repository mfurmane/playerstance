package pl.mfurmane.db.dto;

import jakarta.persistence.*;
import pl.mfurmane.rest.model.Role;

import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name="player")
public class PlayerDTO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="email", length=100, nullable=false, unique=true)
    private String email;

    @Column(name="password", length=100, nullable=false)
    private String password;

    @Column(name="role", nullable=false)
    private String role;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HeroDTO> heroes = new TreeSet<>();

    public PlayerDTO() {
    }

    public PlayerDTO(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public PlayerDTO(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = "USER";
    }

    public void addHero(HeroDTO hero) {
        heroes.add(hero);
        hero.setPlayer(this);
    }

    public void removeHero(HeroDTO hero) {
        heroes.remove(hero);
        hero.setPlayer(null);
    }

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

    public Set<HeroDTO> getHeroes() {
        return heroes;
    }

    public void setHeroes(Set<HeroDTO> heroes) {
        this.heroes = heroes;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
