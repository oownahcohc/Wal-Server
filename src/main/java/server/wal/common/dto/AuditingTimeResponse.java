package server.wal.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import server.wal.domain.common.entity.AuditingTimeEntity;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AuditingTimeResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    protected void setBaseTime(AuditingTimeEntity auditingTimeEntity) {
        this.createdAt = auditingTimeEntity.getCreatedAt();
        this.updatedAt = auditingTimeEntity.getUpdatedAt();
    }

}
