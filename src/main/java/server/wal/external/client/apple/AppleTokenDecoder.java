package server.wal.external.client.apple;

import org.jetbrains.annotations.NotNull;

public interface AppleTokenDecoder {

    String getSocialIdFromIdToken(@NotNull String idToken);

}
