package com.example.Antoflix.repository;

import com.example.Antoflix.entity.User;
import com.example.Antoflix.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Integer> {
    List<Watchlist> findByUser(User user);
}
