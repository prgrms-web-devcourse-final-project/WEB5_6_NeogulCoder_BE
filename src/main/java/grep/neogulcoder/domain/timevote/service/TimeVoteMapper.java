package grep.neogulcoder.domain.timevote.service;

import grep.neogulcoder.domain.timevote.dto.request.TimeVoteCreateRequest;
import grep.neogulcoder.domain.timevote.dto.request.TimeVotePeriodCreateRequest;
import grep.neogulcoder.domain.timevote.dto.request.TimeVoteUpdateRequest;
import grep.neogulcoder.domain.timevote.TimeVote;
import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeVoteMapper {

  public TimeVotePeriod toEntity(TimeVotePeriodCreateRequest request, Long studyId, LocalDateTime adjustedEndDate) {
    return TimeVotePeriod.builder()
        .studyId(studyId)
        .startDate(request.getStartDate())
        .endDate(adjustedEndDate)
        .build();
  }

  public List<TimeVote> toEntities(TimeVoteCreateRequest request, TimeVotePeriod period, Long studyMemberId) {
    return request.getTimeSlots().stream()
        .map(slot -> TimeVote.builder()
            .period(period)
            .studyMemberId(studyMemberId)
            .timeSlot(slot)
            .build())
        .collect(Collectors.toList());
  }

  public List<TimeVote> toEntities(TimeVoteUpdateRequest request, TimeVotePeriod period, Long studyMemberId) {
    return request.getTimeSlots().stream()
        .map(slot -> TimeVote.builder()
            .period(period)
            .studyMemberId(studyMemberId)
            .timeSlot(slot)
            .build())
        .collect(Collectors.toList());
  }
}
