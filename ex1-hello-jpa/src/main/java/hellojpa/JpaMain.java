package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Hibernate;

import javax.swing.*;
import java.time.LocalDateTime;
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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setName("member1");
            member1.chagneTeam(team);
            em.persist(member1);

            em.flush();
            em.clear(); // 준영속상태로 만듦

            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();


//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember = " + findMember.getTeam().getClass()); // Proxy
//
//            System.out.println("====================");
//            findMember.getTeam().getName(); // proxy 초기화 및 team 정보를 DB에서 가지고 옴
//            System.out.println("====================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();

    }
}
