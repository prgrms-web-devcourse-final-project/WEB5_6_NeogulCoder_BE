package grep.neogul_coder.domain.groupchat.repository;

import grep.neogul_coder.domain.groupchat.entity.GroupChatRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository : 기본적인 CRUD 자동제공
public interface GroupChatRoomRepository extends JpaRepository<GroupChatRoom, Long> {

    // 스터디 ID로 채팅방을 찾는 메서드
    // 스터디는 1개당 채팅방 1개가 연결되어 있으므로,
    // 이 메서드는 service 단에서 studyId를 기반으로 채팅방을 찾아올 때 사용됨!


    // Optional을 쓰는 이유 : studyId에 해당하는 채팅방이 없을 수 도 있기 때문
    // Optional.empty()로 감싸 null 에러 방지
    Optional<GroupChatRoom> findByStudyId(Long studyId);
}
