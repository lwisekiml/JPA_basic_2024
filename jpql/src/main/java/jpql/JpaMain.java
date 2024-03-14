package jpql;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // NoResultException
            TypedQuery<Member> query = em.createQuery("select m from  Member m", Member.class);

            Member result = query.getSingleResult();
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
