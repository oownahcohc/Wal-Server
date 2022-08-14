package server.wal.app.auth.service;

import server.wal.app.auth.dto.request.LoginDto;
import server.wal.domain.user.entity.User;

public interface AuthService {
    User login(LoginDto request);
}
