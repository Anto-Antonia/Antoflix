package com.example.Antoflix.repository;

import com.example.Antoflix.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Integer> {
    @Query("select s from Series s where lower(s.title) like lower(concat('%', :title, '%'))")
    List<Series> findSeriesByTitleAndKeyword(@Param("title")String title);
}
