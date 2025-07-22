package grep.neogul_coder.domain.groupchat.repository;

import grep.neogul_coder.domain.groupchat.entity.GroupChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupChatMessageRepository extends JpaRepository<GroupChatMessage, Long> {

    // 채팅방(roomId)에 속한 메시지를 전송 시간 내림차순으로 페이징 조회
    @Query("SELECT m FROM GroupChatMessage m " +
        "WHERE m.groupChatRoom.roomId = :roomId " +
        "ORDER BY m.sentAt ASC")
    Page<GroupChatMessage> findMessagesByRoomIdAsc(@Param("roomId") Long roomId, Pageable pageable);

}