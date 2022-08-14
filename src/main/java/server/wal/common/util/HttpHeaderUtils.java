package server.wal.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpHeaderUtils {

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_TOKEN = "Bearer ";

    public static String withBearerToken(String token) {
        return BEARER_TOKEN.concat(token);
    }

}
