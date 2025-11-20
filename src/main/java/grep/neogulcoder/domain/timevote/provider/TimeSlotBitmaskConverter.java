package grep.neogulcoder.domain.timevote.provider;

import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.TIME_VOTE_INVALID_TIME_MASK;

import grep.neogulcoder.domain.timevote.TimeVote;
import grep.neogulcoder.domain.timevote.dto.request.TimeVoteDateMaskRequest;
import grep.neogulcoder.global.exception.business.BusinessException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class TimeSlotBitmaskConverter {

  private static final int HOURS_PER_DAY = 24;
  private static final long VALID_MASK_RANGE = (1L << HOURS_PER_DAY) - 1;

  private TimeSlotBitmaskConverter() {}

  // 비트마스크 형식의 시간 정보를 개별 LocalDateTime 리스트로 확장
  public static List<LocalDateTime> expand(List<TimeVoteDateMaskRequest> masks) {
    if (masks == null || masks.isEmpty()) {
      return Collections.emptyList();
    }

    List<LocalDateTime> timeSlots = new ArrayList<>();

    for (TimeVoteDateMaskRequest request : masks) {
      if (request.getDate() == null || request.getTimeMask() == null) {
        throw new BusinessException(TIME_VOTE_INVALID_TIME_MASK);
      }

      long mask = request.getTimeMask();
      validateMask(mask);

      LocalDate date = request.getDate();
      for (int hour = 0; hour < HOURS_PER_DAY; hour++) {
        if ((mask & (1L << hour)) != 0) {
          timeSlots.add(date.atTime(hour, 0));
        }
      }
    }

    Collections.sort(timeSlots);
    return timeSlots;
  }

  // 개별 TimeVote 객체 리스트를 날짜별 비트마스크로 압축
  public static Map<LocalDate, Long> compress(List<TimeVote> votes) {
    if (votes == null || votes.isEmpty()) {
      return Collections.emptyMap();
    }

    Map<LocalDate, Long> compressed = new TreeMap<>();

    for (TimeVote vote : votes) {
      LocalDateTime timeSlot = vote.getTimeSlot();
      LocalDate date = timeSlot.toLocalDate();
      int hour = timeSlot.getHour();

      if (hour < 0 || hour >= HOURS_PER_DAY) {
        throw new BusinessException(TIME_VOTE_INVALID_TIME_MASK);
      }

      long bit = 1L << hour;
      compressed.merge(date, bit, (prev, curr) -> prev | curr);
    }

    return compressed;
  }

  private static void validateMask(long mask) {
    if (mask < 0 || (mask & ~VALID_MASK_RANGE) != 0) {
      throw new BusinessException(TIME_VOTE_INVALID_TIME_MASK);
    }
  }
}
