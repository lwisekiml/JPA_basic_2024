package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // 기간 Period
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // 주소
    private String city;
    private String street;
    private String zipcode;
}
