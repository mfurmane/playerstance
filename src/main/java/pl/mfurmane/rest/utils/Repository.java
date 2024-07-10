package pl.mfurmane.rest.utils;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class Repository<T, ID> extends SimpleJpaRepository<T, ID>
        /*implements BaseRepository<T, ID>*/ {
//    public abstract class BaseRepositoryImpl<T, ID>
//            extends SimpleJpaRepository<T, ID>
//            implements BaseRepository<T, ID> {

        private EntityManager em;
        private JPAQueryFactory queryFactory;

        public Repository(Class<T> domainClass, EntityManager em) {
            super(domainClass, em);
            this.em = em;
            this.queryFactory = new JPAQueryFactory(em);
        }

    public JPAQueryFactory getQueryFactory() {
        return queryFactory;
    }
}
//}
