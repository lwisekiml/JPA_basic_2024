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

//            Member member1 = new Member();
//            member1.setUsername("A");
//
//            Member member2 = new Member();
//            member2.setUsername("B");
//
//            Member member3 = new Member();
//            member3.setUsername("C");
//
//            System.out.println("==============");
//
//
//            em.persist(member1);
//            em.persist(member2);
//            em.persist(member3);
//
//            System.out.println("member1.id = " + member1.getId()); // 내부적으로 insert하는 시점에 id값을 알 수 있어서 select문은 안나온다.
//            System.out.println("member2.id = " + member2.getId()); // 내부적으로 insert하는 시점에 id값을 알 수 있어서 select문은 안나온다.
//            System.out.println("member3.id = " + member3.getId()); // 내부적으로 insert하는 시점에 id값을 알 수 있어서 select문은 안나온다.
//
//            System.out.println("==============");

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");

            // member1을 teamA에 소속 시키고 싶다.
            member.setTeamId(team.getId());
            em.persist(member);

            // 조회
            Member findMember = em.find(Member.class, member.getId());
            // find한 member가 어느 팀인지 알고 싶다...연관 관계라는게 없어 DB에 계속 물어봐야 한다.
            // 테이블은 외개키로 조인을 사용해서 연관된 테이블을 찾을 수 있다.
            // 객체는 참조를 사용해서 연관된 객체를 찾는다.
            Long findTeamId = findMember.getTeamId();
            Team findTeam = em.find(Team.class, findTeamId);



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
