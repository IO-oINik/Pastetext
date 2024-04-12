package ru.edu.pasteservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class InvalidatedToken {
    @Id
    private String token;
    @CreatedDate
    private LocalDate addedAt;

    public InvalidatedToken(String token) {
        this.token = token;
    }
}
