package com.abhinash.shoppingdashboard.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class RefreshToken {

    @Id
    private Long id;

    private String token;

    private Instant createdDate;
}
