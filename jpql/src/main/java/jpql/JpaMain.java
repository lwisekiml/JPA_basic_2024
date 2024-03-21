package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select t from Team t join fetch t.members m";
            /*
            WARN: HHH90003004: firstResult/maxResults specified with collection fetch; applying in memory
            아래와 같이 쿼리에 페이징 관련 쿼리가 없다. 그래서 DB에서 이 팀에 대한 데이터를 다 끌고 온 것이다.
            select
                t1_0.id,
                t1_0.age,
                m1_0.TEAM_ID,
                m1_0.id,
                m1_0.age,
                m1_0.type,
                m1_0.username,
                t1_0.name
            from
                Team t1_0
            join
                Member m1_0
                    on t1_0.id=m1_0.TEAM_ID
            */
            List<Team> result = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getResultList();

            System.out.println("result.size() = " + result.size());

            for (Team team : result) {
                System.out.println("member = " + team.getName() + "| members = " + team.getMembers().size());
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

        em.close();
        emf.close();
    }
}
