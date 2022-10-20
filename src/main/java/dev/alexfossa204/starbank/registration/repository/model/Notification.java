package dev.alexfossa204.starbank.registration.repository.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_email_notification_enabled")
    private Boolean isEmailNotificationEnabled;

    @Column(name = "is_sms_notification_enabled")
    private Boolean isSmsNotificationEnabled;

    @Column(name = "is_push_notification_enabled")
    private Boolean isPushNotificationEnabled;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

}
