package grep.neogul_coder.domain.admin.service;

import grep.neogul_coder.domain.admin.controller.dto.response.AdminUserResponse;
import grep.neogul_coder.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public Page<AdminUserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(AdminUserResponse::from);
    }

}
