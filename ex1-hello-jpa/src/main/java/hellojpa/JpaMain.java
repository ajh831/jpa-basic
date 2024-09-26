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
            /* 비영속 상태: 아직 엔티티가 영속성 컨텍스트에 관리되지 않음 */
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            /* 영속 상태: 영속성 컨텍스트에 엔티티가 저장됨, 하지만 아직 DB에 저장되지 않음 */
            System.out.println("=== BEFORE ===");
            em.persist(member); // 영속성 컨텍스트에 member를 저장
            /*em.detach(member); // 영속성 컨텍스트에서 member를 삭제*/
            System.out.println("=== AFTER ===");

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
