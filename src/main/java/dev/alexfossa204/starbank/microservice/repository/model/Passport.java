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
@EqualsAndHashCode(exclude = {"id"}, callSuper = false)
@Builder

@Entity
@Table(name = "passport")
public class Passport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "public_uuid", unique = true)
    private UUID publicUuid;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday_date")
    private Date birthdayDate;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "passport_serial")
    private String passportSerialNumber;

    @Column(name = "is_us_resident")
    private Boolean isUsResident;

}
