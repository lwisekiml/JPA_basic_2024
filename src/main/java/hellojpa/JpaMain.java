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

//        // 2. 수정
//        try {
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());
//            findMember.setName("HelloJPA");
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        emf.close();

        try {
//            // 비영속
//            Member member = new Member();
//            member.setId(100L);
//            member.setName("HelloJPA");
//
//            // 영속
//            System.out.println("===BEFORE ===");
//            em.persist(member);
//            System.out.println("=== AFTER ===");
//
//            Member findMember = em.find(Member.class, 100L);
//
//            // 1차 캐시에 있는 데이터 조회
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

            // 영속 엔티티 동일성 보장
//            Member findMember1 = em.find(Member.class, 100L);
//            Member findMember2 = em.find(Member.class, 100L);
//            System.out.println("result = " + (findMember1 == findMember2)); // true
//
//            // 쓰기 지연
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2);
//
//            System.out.println("===========================");

            // 엔티티 수정
//            Member member = em.find(Member.class, 150L);
//            member.setName("zzzzzz"); // 바로 적용되어 persist를 할 필요가가 없다.

            // flush - 영속성 컨텍스트의 변경내용을 DB에 동기화, 영속성 컨텍스트를 비우지 않음
//            Member member = new Member(200L, "member200");
//            em.persist(member);
//
//            em.flush();
//            System.out.println("================");

            // 준영속
//            Member member = em.find(Member.class, 150L);
//            member.setName("zzzzzz");
//
//            // 1. detach()
////            em.detach(member); // 특정 엔티티만 준영속 상태로 전화
//            // 2. clear()
//            em.clear(); // 영속성 컨텍스트 완전히 초기화(아래서 다시 find하면 DB에서 조회한다.)
//            Member member1 = em.find(Member.class, 150L);
//
//
//            System.out.println("=========");

            Member member = new Member();
            member.setUsername("C");

            System.out.println("==============");
            em.persist(member); // GenerationType.IDENTITY 는 여기서 insert
            System.out.println("member.id = " + member.getId()); // 내부적으로 insert하는 시점에 id값을 알 수 있어서 select문은 안나온다.
            System.out.println("==============");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
