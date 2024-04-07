package ru.edu.generatorurl.models;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@Setter
@Getter
@Entity
public class ShortUrl {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true, nullable = false, length = 12)
    private String url;

    public ShortUrl(String url) {
        this.url = url;
    }
}
