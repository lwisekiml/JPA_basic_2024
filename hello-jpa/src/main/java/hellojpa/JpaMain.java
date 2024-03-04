package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

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

            // 저장
            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
//            member.changeTeam(team); // 이것만 하는 것이 아닌 아래와 같이 team.getMembers().add(member); 도 사용
            em.persist(member);

            team.addMember(member);

            /*
            아래 1번이나 2번을 주석을 하면 for문에서 출력되는 것이 없다.
            1번이나 2번 하나만 사용해도 되지만 보통 flush(), clear()로 DB에 넣기보단
            commit()을 사용하니 1번을 사용
             */
            // 1
//            team.getMembers().add(member); // (이것과 같이) 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자
            // 2 : 아래 findTeam 부분에서 DB에서 다시 조회하게 되므로 JPA가 외래키가 있다는 것을 알아서 for문에 값이 출력이 된다.
            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId()); // 1차 캐시
            List<Member> members = findTeam.getMembers();

            System.out.println("===================");
            for (Member m : members) {
                System.out.println("m = " + m.getUsername()); // 출력되는 것이 없다.
            }
            System.out.println("===================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
