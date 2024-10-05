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
            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");

//            em.detach(member); // 영속성 컨텍스트에서 분리시켜 준영속 상태로 만듦
//            em.clear(); // EntityManager 내부의 영속성 컨텍스트를 완전히 초기화
            em.close(); // 영속성 컨텍스트를 종료하여 EntityManager 사용 불가능

            Member member2 = em.find(Member.class, 150L);

            System.out.println("====================================");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
