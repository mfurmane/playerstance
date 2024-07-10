package pl.mfurmane.db.dao;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
import pl.mfurmane.db.dto.QPlayerDTO;
import pl.mfurmane.db.dto.PlayerDTO;
import pl.mfurmane.rest.utils.Repository;

@Component
public class PlayerDAO {

    private static final QPlayerDTO PLAYER = QPlayerDTO.playerDTO;

    @PersistenceContext
    private EntityManager entityManager;


    //JPAQueryFactory queryFactory

    public PlayerDTO byId(long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
        return queryFactory.selectFrom(PLAYER).where(PLAYER.id.eq(id)).fetchFirst();
    }

    public PlayerDTO byEmail(String email) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
        return queryFactory.selectFrom(PLAYER).where(PLAYER.email.eq(email)).fetchFirst();
    }

    @Transactional
    public void registerPlayer(PlayerDTO player) {
//        JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
        entityManager.persist(player);
    }

}
