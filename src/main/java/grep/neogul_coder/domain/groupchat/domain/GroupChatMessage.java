//package grep.neogul_coder.domain.groupchat.domain;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import java.time.LocalDateTime;
//
//@Entity
//public class  GroupChatMessage {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long messageId;
//
//    @ManyToOne
//    @JoinColumn(name = "room_id")
//    private GroupChatRoom room;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User sender;
//
//    private String content;
//    private LocalDateTime sentAt;
//}
//
