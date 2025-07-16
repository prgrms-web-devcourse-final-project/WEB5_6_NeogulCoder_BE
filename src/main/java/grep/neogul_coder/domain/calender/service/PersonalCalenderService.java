package grep.neogul_coder.domain.calender.service;

import grep.neogul_coder.domain.calender.controller.dto.requset.PersonalCalenderRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.PersonalCalenderResponse;
import grep.neogul_coder.domain.calender.entity.PersonalCalender;
import grep.neogul_coder.domain.calender.repository.PersonalCalenderRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalCalenderService {

    private final PersonalCalenderRepository personalCalenderRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(Long userId, PersonalCalenderRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        PersonalCalender calender = PersonalCalender.builder()
            .user(user)
            .title(request.getTitle())
            .content(request.getDescription())
            .startAt(request.getStartTime())
            .endAt(request.getEndTime())
            .build();

        personalCalenderRepository.save(calender);
    }

    @Transactional(readOnly = true)
    public List<PersonalCalenderResponse> getCalenders(Long userId, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return personalCalenderRepository.findOverlappingSchedules(userId, start, end)
            .stream()
            .map(PersonalCalenderResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long calenderId, Long userId, PersonalCalenderRequest request) {
        PersonalCalender calender = personalCalenderRepository.findById(calenderId)
            .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        if (!calender.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        calender.update(request.getTitle(), request.getContent(), request.getStartAt(), request.getEndAt());
    }

    @Transactional
    public void delete(Long calenderId, Long userId) {
        PersonalCalender calender = personalCalenderRepository.findById(calenderId)
            .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        if (!calender.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        personalCalenderRepository.delete(calender);
    }
}
