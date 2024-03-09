package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
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
//
//            Team teamB = new Team();
//            teamB.setName("teamA");
//            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setUsername("member2");
//            member2.setTeam(teamB);
//            em.persist(member2);

            em.flush();
            em.clear();

//            Member m = em.find(Member.class, member1.getId()); // DB에서 Team도 다 가져온다.

            // SQL : select * from Member
            // SQL : select * from Team where TEAM_ID = xxx
            // 이렇게 두 번 쿼리가 나간다.
            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }

    private static void printerMember(Member member) {
        System.out.println("member = " + member.getUsername());
    }
}
