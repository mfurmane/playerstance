package pl.mfurmane.db.dto;


import jakarta.persistence.*;

@Entity
@Table(name="hero")
public class HeroDTO implements Comparable<HeroDTO> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="name", length=100, nullable=false, unique=true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private PlayerDTO player;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeroDTO )) return false;
        return id == ((HeroDTO) o).getId();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    @Override
    public int compareTo(HeroDTO other) {
        return name.compareTo(other.getName());
    }
}
