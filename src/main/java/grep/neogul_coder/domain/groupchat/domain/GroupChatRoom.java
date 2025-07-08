//package grep.neogul_coder.domain.groupchat.domain;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
//import java.time.LocalDateTime;
//
//@Entity
//public class GroupChatRoom {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long roomId;
//
//    @OneToOne
//    @JoinColumn(name = "study_id")
//    private Study study;
//
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
//}
//
