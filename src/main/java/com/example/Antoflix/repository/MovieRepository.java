package com.example.Antoflix.repository;

import com.example.Antoflix.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>{

    @Query("select m from Movie m where lower(e.title) like lower(concat('%', :keyword, '%'))")
    List<Movie> findByTitleContainingKeyword(@Param("keyword") String keyword);

    @Query("select m from Movie m where lower(e.title) like lower(concat('%', :title, '%'))")
    List<Movie> findByTitleIgnoreCare(@Param("title")String title);

    @Query("select m from Movie m where lower(e.title) like lower(concat('%', :genre, '%'))")
    List<Movie> findByGenre(@Param("genre")String genre);
}
