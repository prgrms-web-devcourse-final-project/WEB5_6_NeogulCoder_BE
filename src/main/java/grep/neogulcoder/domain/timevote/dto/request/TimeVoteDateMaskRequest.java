package grep.neogulcoder.domain.timevote.dto.request;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "단일 일자에 대한 시간대 비트마스크 DTO")
public class TimeVoteDateMaskRequest {

  @NotNull(message = "날짜는 비어 있을 수 없습니다.")
  @Schema(description = "투표 일자", example = "2025-08-11")
  private LocalDate date;

  @NotNull(message = "시간 비트마스크는 비어 있을 수 없습니다.")
  @Schema(
      description = "시간 비트마스크 (LSB=0시, 1시간 단위, 10진수 표현)",
      example = "1024"
  )
  private Long timeMask;

  @Builder
  private TimeVoteDateMaskRequest(LocalDate date, Long timeMask) {
    this.date = date;
    this.timeMask = timeMask;
  }
}
