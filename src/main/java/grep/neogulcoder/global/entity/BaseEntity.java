package grep.neogulcoder.global.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    protected boolean activated = true;

    @CreatedDate
    protected LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedDate
    protected LocalDateTime modifiedDate = LocalDateTime.now();

    public void unActivated() {
        this.activated = false;
    }

}
