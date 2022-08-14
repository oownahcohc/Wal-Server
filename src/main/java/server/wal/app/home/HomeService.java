package server.wal.app.home;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.wal.app.home.dto.response.HomeResponse;
import server.wal.domain.todayWal.entity.TodayWal;
import server.wal.domain.todayWal.repository.TodayWalRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final TodayWalRepository todayWalRepository;

    @Transactional(readOnly = true)
    public List<HomeResponse> getMainHome(Long userId) {
        return todayWalRepository.findByUserId(userId).stream()
                .map(HomeResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateShowStatus(Long todayWalId, Long userId) {
        TodayWal todayWal = todayWalRepository.findByTodayWalIdAndUserId(todayWalId, userId);
        todayWal.updateShowStatus();
    }

}
