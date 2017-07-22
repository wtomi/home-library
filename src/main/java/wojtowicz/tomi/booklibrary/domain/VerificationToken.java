package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tommy on 7/16/2017.
 */
@Getter
@Setter
@Entity
public class VerificationToken extends AbstractDomainClass {
    private static final int EXPIRY_TIME_IN_MINUTES = 24 * 60;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @NotNull
    private Date expiryDate;

    public VerificationToken() {
        super();
    }

    public VerificationToken(User user, String token) {
        super();

        this.user = user;
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRY_TIME_IN_MINUTES);
    }

    private Date calculateExpiryDate(final int expireTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expireTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
}
