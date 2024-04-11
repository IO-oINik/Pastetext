package ru.edu.pasteservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "paste")
public class PasteMeta {
    @Id
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Visibility visibility;

    private LocalDateTime lastVisited;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = {@JoinColumn(referencedColumnName = "id", nullable = false)},
    inverseJoinColumns = {@JoinColumn(referencedColumnName = "id", nullable = false)})
    private Set<User> permissions;
}
