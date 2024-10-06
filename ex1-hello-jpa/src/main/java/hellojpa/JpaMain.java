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

            Member member1 = new Member();
            member1.setUsername("A");

            Member member2 = new Member();
            member2.setUsername("B");

            Member member3 = new Member();
            member3.setUsername("C");

            Member member4 = new Member();
            member4.setUsername("D");

            System.out.println("====================");

            /*
                DB SEQ = 1  |   1
                DB SEQ = 51 |   2
                DB SEQ = 51 |   3
            */

            System.out.println(" = member1 persist 전 = ");
            em.persist(member1); // 1, 51
            System.out.println(" = member1 persist 후 = ");
            System.out.println(" = member2 persist 전 = ");
            em.persist(member2); // MEMORY 호출
            System.out.println(" = member2 persist 후 = ");
            System.out.println(" = member3 persist 전 = ");
            em.persist(member3); // MEMORY 호출
            System.out.println(" = member3 persist 후 = ");
            System.out.println(" = member4 persist 전 = ");
            em.persist(member4); // MEMORY 호출
            System.out.println(" = member4 persist 후 = ");

            System.out.println("\nmember1.id = " + member1.getId());
            System.out.println("member2.id = " + member2.getId());
            System.out.println("member3.id = " + member3.getId());
            System.out.println("member4.id = " + member4.getId());
            System.out.println("====================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
