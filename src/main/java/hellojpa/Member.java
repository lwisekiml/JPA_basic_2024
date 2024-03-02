package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

//@Table(name = "MBR")
@Entity // (name = "Member")
@Table(uniqueConstraints = )
public class Member {
    @Id
    private Long id;

    @Column(name = "name")
    private String username;
//    private int age2; // update, validate 테스트

    private int age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private LocalDate testLocalDate;
    private LocalDateTime testLocalDateTime;


    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    @Transient
    private int temp;

    public Member() {
    }
}
