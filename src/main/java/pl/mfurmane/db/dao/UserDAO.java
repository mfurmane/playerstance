package pl.mfurmane.db.dao;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.stereotype.Repository;
import pl.mfurmane.db.dto.QUserDTO;
import pl.mfurmane.db.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {

    private static final QUserDTO Q_USER_DTO = QUserDTO.userDTO;

    @Autowired
    private EntityManager entityManager;

    public UserDTO byId(long id) {
        System.out.println("entityManager: "+entityManager);
        JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
//        queryFactory.from()
//        JpaQueryCreator creator = new JpaQueryCreator(sasa)
//        JPAQuery<UserDTO> query = new JPAQuery<>(entityManager);
////        Predicate[] predicates = new Predicate[0];
//        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(Q_USER_DTO.id.eq(id));
//        Predicate[] array = predicates.toArray(new Predicate[0]);
//        System.out.println(array);
//        return query.from(Q_USER_DTO).where(array).fetchFirst();
        return null;
    }

    public UserDTO byEmail(String email) {

        return null;
    }

}
