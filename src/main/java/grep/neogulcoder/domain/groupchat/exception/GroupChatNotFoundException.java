package grep.neogulcoder.domain.groupchat.exception;

import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class GroupChatNotFoundException extends NotFoundException {
  public GroupChatNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
