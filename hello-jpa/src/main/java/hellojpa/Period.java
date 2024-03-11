package hellojpa;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable // 값 타입을 정의하는 곳에 표시
public class Period {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
