package server.wal.app.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class CalenderDateResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;

    private CalenderDateResponse() {}

    private CalenderDateResponse(LocalDateTime reservedDate) {
        this.reservedDate = reservedDate;
    }

    public static CalenderDateResponse of(LocalDateTime reservedDate) {
        return new CalenderDateResponse(reservedDate);
    }

}
