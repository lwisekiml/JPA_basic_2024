package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            // select 서브 쿼리
            String query = "select (select avg(m1.age) from Member m1) as avgAge from Member m left join Team t on m.username = t.name";
            // from 절의 서브 쿼리는 JPQL에서 불가능 / 조인으로 풀 수 있으면 풀어서 해결
//            String query = "select mm.age, mm.username" +
//                    " from (select m.age, m.username from Member m) as avgAge from Member m left join Team t on m.username = t.name";
            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();
            System.out.println("result.size = " + result.size());

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
