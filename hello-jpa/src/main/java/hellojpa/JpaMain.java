package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        int a = 10;
        int b = a;
        a = 20; // b 값은 변화x
        System.out .println( "a = " + a); // 20
        System.out .println( "b = " + b); // 10

        /*
        new Integer(n); 은 호출할 때마다 매번 새로운 객체를 생성
        Integer.valueOf(n); 은 이미 생성한 객체가 있으면 그 객체를 재활용
         */
        Integer c = new Integer(10);
        Integer d = c;
        c = 20; // a = Integer.parseInt(20); 과 동일

        System.out.println("c = " + c);
        System.out.println("d = " + d);
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
