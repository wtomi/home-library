package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tommy on 7/6/2017.
 */
@Getter
@Setter
@MappedSuperclass
public class AbstractDomainClass implements DomainObject{

    @Id
    @GeneratedValue
    Integer id;

    @Version
    private Integer version;

    private Date dateCreated;
    private Date lastUpdated;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = new Date();
        if(dateCreated == null) {
            dateCreated = new Date();
        }
    }
}
