package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        /* EntityManagerFactory는 로딩 시점에 단 하나만 만들어야 됨 */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        /* JPA에서는 모든 데이터를 변경하는 작업은 꼭 Transaction 안에서 수행해야 됨 */
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        /* code 작성 */
        try {
            /* 영속 상태 */
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);
            System.out.println("====================================");

            /* 트랜잭션 커밋 시점에 실제 DB에 insert 쿼리가 실행됨 */
            tx.commit(); // DB에 SQL 쿼리가 실행되고, 트랜잭션이 커밋됨
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
