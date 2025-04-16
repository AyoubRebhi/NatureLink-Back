package com.example.naturelink.Repository;

import com.example.naturelink.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    // You can define custom query methods here if necessary
}