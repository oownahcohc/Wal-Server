package server.wal.domain.todayWal.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import server.wal.domain.common.entity.AuditingTimeEntity;
import server.wal.domain.common.enumerate.ShowStatus;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodayWal extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(nullable = false)
    private String contents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalCategoryType categoryType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalTimeType timeType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalStatus walStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShowStatus showStatus;

    @Builder(access = AccessLevel.PACKAGE)
    private TodayWal(Long userId, Long reservationId, String contents, WalCategoryType categoryType, WalTimeType timeType, WalStatus walStatus) {
        this.userId = userId;
        this.reservationId = reservationId;
        this.contents = contents;
        this.categoryType = categoryType;
        this.timeType = timeType;
        this.walStatus = walStatus;
        this.showStatus = ShowStatus.CLOSED;
    }

    public static TodayWal newInstance(Long userId, @Nullable Long reservationId, String contents, WalCategoryType categoryType, WalTimeType timeType, WalStatus walStatus) {
        return TodayWal.builder()
                .userId(userId)
                .reservationId(reservationId)
                .contents(contents)
                .categoryType(categoryType)
                .timeType(timeType)
                .walStatus(walStatus)
                .build();
    }

    public void updateShowStatus() {
        this.showStatus = ShowStatus.OPEN;
    }

}
