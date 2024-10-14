package jpql;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

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
            member1.setUsername("user");
            member1.setAge(10);
            member1.setType(MemberType.USER);

            member1.changeTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("admin");
            member2.setAge(10);
            member2.setType(MemberType.ADMIN);

            member2.changeTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "select function('group_concat', m.username) from Member m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            System.out.println("result = "+ result);
            for (String s : result) {
            }

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
