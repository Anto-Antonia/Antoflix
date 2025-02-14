package com.example.Antoflix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private int episodeNr;
    private String description;
    private String duration;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;
}
