package server.wal.app.home.dto.response;

import lombok.*;
import server.wal.common.util.TimeUtils;
import server.wal.domain.common.enumerate.ShowStatus;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.todayWal.entity.TodayWal;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HomeResponse {

    private Long todayWalId;
    private WalTimeType timeType;
    private WalCategoryType categoryType;
    private String contents;
    private ShowStatus showStatus;
    private String voiceUrl;
    private String imageUrl;
    private OpenStatus openStatus;

    @Builder(access = AccessLevel.PACKAGE)
    private HomeResponse(Long todayWalId, WalTimeType timeType, WalCategoryType categoryType, String contents, ShowStatus showStatus,
                        String voiceUrl, String imageUrl, OpenStatus openStatus) {
        this.todayWalId = todayWalId;
        this.timeType = timeType;
        this.categoryType = categoryType;
        this.contents = contents;
        this.showStatus = showStatus;
        this.voiceUrl = voiceUrl;
        this.imageUrl = imageUrl;
        this.openStatus = openStatus;
    }

    public static HomeResponse from(TodayWal todayWal) {
        OpenStatus openStatus = setOpenStatus(todayWal.getTimeType());
        return HomeResponse.builder()
                .todayWalId(todayWal.getId())
                .timeType(todayWal.getTimeType())
                .categoryType(todayWal.getCategoryType())
                .contents(todayWal.getContents())
                .showStatus(todayWal.getShowStatus())
//                .voiceUrl(todayWal.getVoiceUrl())
//                .voiceUrl(todayWal.getVoiceUrl())
                .openStatus(openStatus)
                .build();
    }

    private static OpenStatus setOpenStatus(WalTimeType walTimeType) {
        return walTimeType.getSendDueDate()
                .isBefore(TimeUtils.getNow())
                ? OpenStatus.ABLE
                : OpenStatus.UNABLE;
    }

}
