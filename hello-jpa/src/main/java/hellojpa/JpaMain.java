package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Movie movie = new Movie();
            movie.setDirector("aaa");
            movie.setActor("bbbb");
            movie.setName("바람과함계사라지다");
            movie.setPrice(10000);

            em.persist(movie); // insert 2번(item, movie)

            em.flush();
            em.clear();

            // 이 경우 union을 사용해서 복잡한 쿼리가 나가게 된다.
            // item id만 아는 경우, 테이블 3개를 다 select 해봐야 알 수 있다.
            // 비효율적으로 동작
            Item item = em.find(Movie.class, movie.getId());
            System.out.println("item = " + item);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
