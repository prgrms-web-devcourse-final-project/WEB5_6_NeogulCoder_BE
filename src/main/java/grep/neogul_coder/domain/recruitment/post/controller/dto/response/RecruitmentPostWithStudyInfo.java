package grep.neogul_coder.domain.recruitment.post.controller.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
public class RecruitmentPostWithStudyInfo {

    @Schema(example = "닉네임", description = "작성자 회원 닉네임")
    private String nickname;

    @Schema(example = "3", description = "모집글 식별자")
    private final long recruitmentPostId;

    @Schema(example = "너굴 코더 스터디를 모집 합니다", description = "제목")
    private String subject;

    @Schema(example = "이펙티브 자바를 정독 하는 것을 목표로 하는 스터디 입니다", description = "내용")
    private String content;

    @Schema(example = "8", description = "모집 인원")
    private int recruitmentCount;

    @Schema(example = "2025-07-09", description = "생성 날짜")
    private LocalDateTime createdDate;

    @Schema(example = "2025-07-17", description = "모집 마감일")
    private LocalDateTime expiredDate;

    @Schema(example = "IT", description = "스터디 카테고리")
    private String category;

    @Schema(example = "서울", description = "스터디 장소")
    private String location;

    @Schema(example = "OFFLINE", description = "스터디 진행 방식")
    private String studyType;

    @Schema(example = "2025-07-09", description = "스터디 시작 날짜")
    private LocalDateTime startedDate;

    @Schema(example = "2025-07-10", description = "스터디 종료 날짜")
    private LocalDateTime endDate;

    @QueryProjection
    public RecruitmentPostWithStudyInfo(String nickname, long recruitmentPostId, String subject,
                                        String content, int recruitmentCount, LocalDateTime createdDate,
                                        LocalDateTime expiredDate, Category category, String location,
                                        StudyType studyType, LocalDateTime startedDate, LocalDateTime endDate) {
        this.nickname = nickname;
        this.recruitmentPostId = recruitmentPostId;
        this.subject = subject;
        this.content = content;
        this.recruitmentCount = recruitmentCount;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
        this.category = category.name();
        this.location = location;
        this.studyType = studyType.name();
        this.startedDate = startedDate;
        this.endDate = endDate;
    }
}
