package server.wal.external.client.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import server.wal.common.util.HttpHeaderUtils;
import server.wal.external.client.kakao.dto.KakaoAuthResponse;

@FeignClient(name = "kakaoAuthApiClient", url = "https://kapi.kakao.com")
public interface KakaoApiClient {

    @GetMapping("/v2/user/me")
    KakaoAuthResponse getKakaoUserProfile(@RequestHeader(HttpHeaderUtils.AUTH_HEADER) String accessToken);

}
