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

            Team team = new Team();
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            // 1. 상태 필드 : 경로 탐색의 끝, 탐색X / m.username
//            String query = "select m.username from Member m";

            // 2. 단일 값 연관 경로 : 묵시적 내부 조인, 탐색 O -> 내부 조인이 발생하게 짜면 안된다.(튜닝의 어려움)/ m.team
//            String query = "select m.team from Member m";
//            List<Team> result = em.createQuery(query, Team.class)
//                    .getResultList();
//
//            for (Team s : result) {
//                System.out.println("s = " + s);
//            }

            // 3. 컬렉션 값 연관 경로 : 묵시적 내부 조인 발생, 탐색 X
            // t.members에서 .으로 다른 값을 사용 불가능 하다.
//            String query = "select t.members from Team t";
            // 그래서 아래와 같이 사용해야 한다.
            String query = "select m.username from Team t join t.members m";

            List<Collection> result = em.createQuery(query, Collection.class)
                    .getResultList();

            System.out.println("result = " + result);

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
