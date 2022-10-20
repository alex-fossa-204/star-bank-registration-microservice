package dev.alexfossa204.starbank.registration.repository.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"id", "roles"})
@EqualsAndHashCode(exclude = {"id", "roles"})

@Entity
@Table(name = "privilege")
public class Privilege implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "public_uuid", unique = true)
    private UUID publicUuid;

    @Column(name = "privilege")
    private String privilegeName;

    @ManyToMany(mappedBy = "privileges", cascade = CascadeType.MERGE)
    private Collection<Role> roles;
}
