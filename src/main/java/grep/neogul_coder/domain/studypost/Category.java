package grep.neogul_coder.domain.studypost;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "카테고리: NOTICE(공지), FREE(자유)")
public enum Category {
  NOTICE("공지"),
  FREE("자유");

  private final String korean;

  Category(String korean) { this.korean = korean; }

  @JsonValue
  public String toJson() {
    return korean;
  }
}
