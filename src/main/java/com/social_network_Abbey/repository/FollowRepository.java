package com.social_network_Abbey.repository;

import com.social_network_Abbey.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerId(Long followerId);
    List<Follow> findByFollowingId(Long followedId);
    Optional<Object> findByFollower_IdAndFollowing_Id(Long userId, Long followingId);
}
