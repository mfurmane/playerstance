package pl.mfurmane.db.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.mfurmane.db.dto.QUserDTO;
import pl.mfurmane.db.dto.UserDTO;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {

    private static final QUserDTO Q_USER_DTO = QUserDTO.userDTO;

    @Autowired
    private EntityManager entityManager;

    public UserDTO byId(long id) {
        System.out.println("entityManager: "+entityManager);
        JPAQuery<UserDTO> query = new JPAQuery<>(entityManager);
//        Predicate[] predicates = new Predicate[0];
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(Q_USER_DTO.id.eq(id));
        Predicate[] array = predicates.toArray(new Predicate[0]);
        System.out.println(array);
        return query.from(Q_USER_DTO).where(array).fetchFirst();
    }

    public UserDTO byEmail(String email) {

        return null;
    }

}
