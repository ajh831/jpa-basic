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
/*
            // 팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // 회원 저장
            Member member = new Member();
            member.setName("Member1");
            member.setTeamId(team.getId());
            em.persist(member);

            // 조회
            Member findMember = em.find(Member.class, member.getId());

            Long findTeamId = findMember.getTeamId();
            // 연관관계가 없음
            Team findTeam = em.find(Team.class, findTeamId);
*/
            // 팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // 회원 저장
            Member member = new Member();
            member.setName("Member1");
            member.setTeam(team); // 단방향 연관관계 설정, 참조 저장
            em.persist(member);

            em.flush();
            em.clear();

            // 조회
            Member findMember = em.find(Member.class, member.getId());
            // 참조를 사용해서 연관관계 조회
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());

            // 수정
            // 팀명 변경
            Team newTeam = em.find(Team.class, team.getId());
            newTeam.setName("TeamB");
            // em.persist(newTeam); 안해도 됨 em.find를 통해 데이터를 찾아 왔기 때문에 새로운 객체를 만든 것이 아니므로 할 필요 없음

            findMember.setTeam(newTeam);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
