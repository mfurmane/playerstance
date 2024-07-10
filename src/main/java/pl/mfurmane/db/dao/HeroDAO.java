package pl.mfurmane.db.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.mfurmane.db.dto.QHeroDTO;
import pl.mfurmane.db.dto.PlayerDTO;

@Repository
public class HeroDAO {

    private static final QHeroDTO Q_HERO_DTO = QHeroDTO.heroDTO;

    @Autowired
    private EntityManager entityManager;

    public PlayerDTO byId(long id) {

        return null;
    }

    public PlayerDTO byPlayerId(long playerId) {

        return null;
    }

}
