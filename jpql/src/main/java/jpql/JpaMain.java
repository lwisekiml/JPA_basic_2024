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

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // 아래와 같이 하면 join을 하게되지만 아래와 같이 코드를 작성할 경우 join을 할 것이라는 것이 한눈에 안보인다.
//            List<Team> result = em.createQuery("select m.team from  Member m", Team.class)
//                    .getResultList();
            // 그래서 아래와 같이 작성하여 조인이 들어가는 것을 나타내는 것이 코드를 보기에도 좋다.
            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
                    .getResultList();

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
