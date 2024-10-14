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

            // FLUSH 자동 호출
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            System.out.println("resultCount = " + resultCount);

            // 문제점 : 영속성 컨텍스트에는 반영되지 않았음
            System.out.println("==================== BEFORE ====================");
            System.out.println("member1.getAget() = " + member1.getAge());
            System.out.println("member2.getAget() = " + member2.getAge());
            System.out.println("member3.getAget() = " + member3.getAge());
            System.out.println("member4.getAget() = " + member4.getAge());

            // 해결 : em.clear();로 영속성 컨텍스트 초기화 수행이 필요
            System.out.println("==================== AFTER ====================");

            em.clear();

            Member findMember1 = em.find(Member.class, member1.getId());
            Member findMember2 = em.find(Member.class, member2.getId());
            Member findMember3 = em.find(Member.class, member3.getId());
            Member findMember4 = em.find(Member.class, member4.getId());


            System.out.println("findMember1.getAget() = " + findMember1.getAge());
            System.out.println("findMember2.getAget() = " + findMember2.getAge());
            System.out.println("findMember3.getAget() = " + findMember3.getAge());
            System.out.println("findMember4.getAget() = " + findMember4.getAge());


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
