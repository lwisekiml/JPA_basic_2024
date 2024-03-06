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

//            Member member = em.find(Member.class, 1L);
//
//            printerMember(member);
//
//            printMemberAndTeam(member);

            Member member = new Member();
            member.setUsername("hello");

            em.persist(member);

            em.flush();
            em.clear();

            //
//            Member findMember = em.find(Member.class, member.getId()); // join 을 해서 가져온다.
            Member findMember = em.getReference(Member.class, member.getId());
            // 프록시 객체 findMember = class hellojpa.Member$HibernateProxy$tx5DdrBv
            System.out.println("before findMember = " + findMember.getClass());
            // 위에 getId()해서 가져온것이 있어 쿼리 없음
            System.out.println("findMember.id = " + findMember.getId());
            // 쿼리 날림
            // 프록시 객체는 처음 사용할 때 한 번만 초기화
            // 프록시 객체를 초기화할 때 프록시 객체가 실제 엔티티로 바뀌는 것은 아니다.(before/after 확인)
            // 초기화되면 프록시 객체를 통해 실제 엔티티 접근 가능
            System.out.println("findMember.username = " + findMember.getUsername());
            // 쿼리x  (프록시 객체를 통해 실제 엔티티 접근)
            System.out.println("findMember.username = " + findMember.getUsername());
            System.out.println("after findMember = " + findMember.getClass());

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
