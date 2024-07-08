package com.social_network_Abbey.repository;

import com.social_network_Abbey.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findAllByPostId(Long postId);
    List<Like> findAllByUserId(Long userId);
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
}
