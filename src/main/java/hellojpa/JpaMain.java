package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        // EntityManagerFactory는 웹서버가 올라오는 시점에 딱 하나만 생성이 된다. / "hello"는 resource/META-INF/persistence.xml에 persistence-unit name="hello" 을 뜻하고, 이것을 가지고 온다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // EntityManager는 고객의 요청이 올 때마다 계속 썻따가 close했다 한다.
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 1. 등록
//        try {
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("HelloA");
//
//            em.persist(member);
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }

        // 2. 수정
        try {
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());
            findMember.setName("HelloJPA");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
