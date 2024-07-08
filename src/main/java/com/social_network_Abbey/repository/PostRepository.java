package com.social_network_Abbey.repository;

import com.social_network_Abbey.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Method to find posts by author ID
    List<Post> findAllByAuthor_Id(Long author_Id);

}
