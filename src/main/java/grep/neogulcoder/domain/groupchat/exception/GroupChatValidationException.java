package grep.neogulcoder.domain.groupchat.exception;

import grep.neogulcoder.global.exception.validation.ValidationException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class GroupChatValidationException extends ValidationException {
  public GroupChatValidationException(ErrorCode errorCode) {
    super(errorCode);
  }
}
