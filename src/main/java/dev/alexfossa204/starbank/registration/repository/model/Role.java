package dev.alexfossa204.starbank.registration.repository.model;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"id", "privileges"})
@EqualsAndHashCode(exclude = {"id", "privileges"})
@Builder

@Entity
@Table(name = "user_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "public_uuid", unique = true)
    private UUID publicUuid;

    @Column(name = "user_role_name")
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "role_privilege",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

}
