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

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setAge(0);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(0);
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(0);
            member3.setTeam(teamB);
            em.persist(member3);

            // 아래 코드로 flush 가 자동 호출된다. (flush 자동 호출 : commit, query)
            /*
            벌크 연산 주의
                벌크 연산을 영속성 컨텍스트를 무시하고 DB에 직접 쿼리
                1. 벌크 연산을 먼저 실행
                2. 벌크 연산 수행 후 영속성 컨텍스트 초기화
             */
            // 영향받은 엔티티 수 반환
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            System.out.println("resultCount = " + resultCount);

            // flush 된다고 연속성 컨텍스트에 값이 없어지는 것이 아니기 때문에 연속성 컨텍스트에 있는 값인 0이 출력된다.
            System.out.println("member1.getAge() = " + member1.getAge()); // 0
            System.out.println("member2.getAge() = " + member2.getAge()); // 0
            System.out.println("member3.getAge() = " + member3.getAge()); // 0

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
