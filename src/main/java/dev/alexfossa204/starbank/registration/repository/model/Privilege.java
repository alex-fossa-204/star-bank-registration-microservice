package dev.alexfossa204.starbank.registration.repository.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"id", "roles"})
@EqualsAndHashCode(exclude = {"id", "roles"})

@Entity
@Table(name = "privilege")
public class Privilege implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "privilege")
    private String privilegeName;

    @ManyToMany(mappedBy = "privileges", cascade = CascadeType.MERGE)
    private Collection<Role> roles;
}
