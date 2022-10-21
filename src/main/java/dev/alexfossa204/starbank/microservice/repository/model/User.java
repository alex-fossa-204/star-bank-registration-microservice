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
@Table(name = "api_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "public_uuid", unique = true)
    private UUID publicUuid;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "passport_id")
    private Passport passport;

    @Column(name = "image_url")
    private String imageUrl;
}
