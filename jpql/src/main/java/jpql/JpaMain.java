package jpql;

import jakarta.persistence.*;

import java.util.Collection;
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

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Team teamC = new Team();
            teamC.setName("팀C");
            em.persist(teamC);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setType(MemberType.USER);

            member1.changeTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setType(MemberType.USER);

            member2.changeTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setType(MemberType.USER);

            member3.changeTeam(teamB);
            em.persist(member3);

            Member member4 = new Member();
            member4.setUsername("회원4");
            member4.setType(MemberType.USER);

            em.persist(member4);

            em.flush();
            em.clear();

/*//            String query = "select m from Member m join fetch m.team";
            String query = "select t from Team t join fetch t.members";
            List<Team> members = em.createQuery(query, Team.class)
                    .getResultList();

            for (Team team : members) {
//                System.out.println("member = "+ member);
                System.out.println("team = "+ team.getName() + " | " + team.getMembers().size() + "명");
                // 회원1, 팀A(SQL)
                // 회원2, 팀A(1차캐시)
                // 회원3, 팀B(SQL)
                // 회원4, 팀X(NullPointerException)

                // 회원 100명 -> N(첫번째 쿼리로 얻은 결과만큼 N번) + 1(회원을 가지고 오기위해서 날린 쿼리)
            }*/

            String jpql = "select t from Team t";
            List<Team> result = em.createQuery(jpql, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("result = " + result.size());

            for(Team team : result) {
                System.out.println("team = " + team.getName() + " | members = " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
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
