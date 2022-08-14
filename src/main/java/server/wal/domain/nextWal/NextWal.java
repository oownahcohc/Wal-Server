package server.wal.domain.nextWal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.wal.domain.common.enumerate.WalCategoryType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NextWal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private WalCategoryType categoryType;

    @Column(nullable = false)
    private double nextItemId;

    private NextWal(Long userId, WalCategoryType categoryType, double nextItemId) {
        this.userId = userId;
        this.categoryType = categoryType;
        this.nextItemId = nextItemId;
    }

    public static NextWal newInstance(Long userId, WalCategoryType categoryType, double nextItemId) {
        return new NextWal(userId, categoryType, nextItemId);
    }

    public void updateNextItemId(double nextItemId) {
        this.nextItemId = nextItemId;
    }

}
