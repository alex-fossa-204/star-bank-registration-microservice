package dev.alexfossa204.starbank.microservice.repository.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"}, callSuper = false)
@Builder

@Entity
@Table(name = "api_user_contact")
public class UserContact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "skype")
    private String skype;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
