package com.example.Antoflix.repository;

import com.example.Antoflix.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>{
    @Query("select m from Movie m where lower(m.title) like lower(concat('%', :keyword, '%'))")
    List<Movie> findByTitleContainingKeyword(@Param("keyword") String keyword);

    @Query("select m from Movie m where lower(m.title) like lower(concat('%', :title, '%'))")
    List<Movie> findByTitleIgnoreCase(@Param("title")String title);

    @Query("select m from Movie m join m.genres g where lower(g.genreName) like lower(concat('%', :genre, '%'))")
    List<Movie> findByGenre(@Param("genre")String genre);

    List<Movie> findAllByOrderByTitleAsc(); // method to return movies alphabetically
    @Query("select m from Movie m join m.genres g where lower(g.genreName) like lower(concat('%', :genreName, '%'))")
    List<Movie> findByGenres_IgnoreCase(@Param("genreName")String genreName); // query to fetch movies by genre name

    @Query("select m from Movie m order by m.id desc") // query to return the movies in descending order by id
    List<Movie> findRecentMovie(Pageable pageable);
}
