package hellojpa;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne//(fetch = FetchType.LAZY) // 없으면 한번에 join해서 가져온다.
    @JoinColumn(name = "TEAM_ID") // join해야 되는 컬럼이 뭐냐
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void changeTeam(Team team) { // 연관 관계 편의 메소드를 사용해도 된다.
        this.team = team;
        team.getMembers().add(this);
    }
}
