package com.example.Antoflix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int seasonNr;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @OneToMany(mappedBy = "season",cascade = CascadeType.ALL)
    private List<Episode> episodes = new ArrayList<>();
}
