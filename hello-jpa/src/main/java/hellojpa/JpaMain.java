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

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember.getClass() = " + refMember.getClass()); // Proxy

            em.detach(refMember); // 영속성 컨텍스트로 관리x
//            em.close(); // 약간 다르게 나온다.
//            em.clear();

            // 프록시도 결국 영속성 컨텍스트 위에서 사용하는 객체인데 영속성 컨텍스트를 비워버리면 프록시 객체가 없어 초기화 요청을 할 수 없다.
            // 영속성 컨텍스트에 도움을 받아서 실제 데이터를 불러와야 한다. 초기화 해야 한다.
            refMember.getUsername(); // could not initialize proxy

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
