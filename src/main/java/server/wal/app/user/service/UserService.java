package server.wal.app.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.wal.app.user.dto.request.CreateUserDto;
import server.wal.app.user.dto.response.NicknameResponse;
import server.wal.domain.user.entity.User;
import server.wal.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User registerUser(CreateUserDto requestDto) {
        UserServiceUtils.validateNotExistsUser(userRepository, requestDto.getSocialId(), requestDto.getSocialType());
        return userRepository.save(User.newInstance(requestDto));
    }

    @Transactional
    public NicknameResponse changeNickname(String newNickname, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        user.changeNickname(newNickname);
        return NicknameResponse.of(user.getNickname());
    }

    @Transactional
    public void resignUser(Long userId) {
        userRepository.delete(UserServiceUtils.findUserById(userRepository, userId));
    }

}
