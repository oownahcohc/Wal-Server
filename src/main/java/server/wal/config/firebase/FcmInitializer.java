package server.wal.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import server.wal.common.util.YamlPropertySourceFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@PropertySource(value = "classpath:application-firebase.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
public class FcmInitializer {

    @Value("${firebase.fcm.config.path}")
    private String firebaseConfigPath;

//    @Value("${firebase.fcm.scope}")
//    private static String serviceScope;

    @PostConstruct
    public void initFirebaseApp() throws IOException {
        ClassPathResource resource = new ClassPathResource(firebaseConfigPath);
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(resource.getInputStream());
//                .createScoped(List.of(serviceScope));
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
            log.info("FirebaseApp Initialization Complete");
        }
    }

}
