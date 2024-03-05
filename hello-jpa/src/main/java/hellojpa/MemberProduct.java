package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MemberProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    // 아래 처럼 추가가 가능해진다.
    private int count;
    private int price;

    private LocalDateTime orderDateTime;

}
