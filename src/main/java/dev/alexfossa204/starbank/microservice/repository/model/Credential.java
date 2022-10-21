package dev.alexfossa204.starbank.microservice.repository.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"})
@Builder

@Entity
@Table(name = "credential")
public class Credential implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "public_uuid", unique = true)
    private UUID publicUuid;

    @Column(name = "phone_login")
    private String phoneLogin;

    @Column(name = "passport_login")
    private String passportLogin;

    @Column(name = "password")
    private String password;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(name = "secret_question")
    private String secretQuestion;

    @Column(name = "secret_question_answer")
    private String secretQuestionAnswer;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_non_locked")
    private Boolean isNonLocked;

    @Column(name = "is_credential_non_expired")
    private Boolean isCredentialNonExpired;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
