package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            em.flush();
            em.clear();

            System.out.println("=================1=================");
            Member m1 = em.find(Member.class, member1.getId());
            System.out.println("=================2=================");
            Member m2 = em.find(Member.class, member2.getId());
            System.out.println("=================3=================");
            // 2번때문에 이미 영속성 컨텍스트에 있기 때문에 m2와 같은 member가 된다.
            Member m3 = em.getReference(Member.class, member2.getId());
            System.out.println("==================================");
            // 2번을 getReference()로 하고 m3을 find()로 하면 m2, m3은 Proxy가 된다.

            System.out.println("m1 = " + m1.getClass()); // m1 = class hellojpa.Member
            System.out.println("m2 = " + m2.getClass()); // m2 = class hellojpa.Member
            System.out.println("m3 = " + m3.getClass()); // m3 = class hellojpa.Member
            System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass())); // true
            System.out.println("m1 == m3 : " + (m1.getClass() == m3.getClass())); // true

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
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
