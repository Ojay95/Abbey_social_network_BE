package com.social_network_Abbey.service;

import com.social_network_Abbey.entity.ApplicationUser;
import com.social_network_Abbey.entity.Follow;
import com.social_network_Abbey.repository.FollowRepository;
import com.social_network_Abbey.request.FollowRequest;
import com.social_network_Abbey.response.CustomResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    public FollowService(FollowRepository followRepository, ApplicationUserService userService){
        this.followRepository = followRepository;
        this.userService = userService;
    }

    @Autowired
    private final ApplicationUserService userService;

    @Autowired
    private final FollowRepository followRepository;

    public List<Follow> getAllFollows() {
        return followRepository.findAll();
    }

    public List<Follow> getFollowsByFollowedId(Long followedId) {
        return followRepository.findByFollowingId(followedId);
    }

    public Follow getFollowById(Long followId) {
        Optional<Follow> followOptional = followRepository.findById(followId);
        return followOptional.orElse(null);
    }

    public CustomResponse<Object> create(FollowRequest followRequest) {
        if (userService.isFollowing(followRequest.getUserId(), followRequest.getFollowingId())) {
            return new CustomResponse<>(false, true, "already followed", HttpStatus.CONFLICT.value());
        }
        Follow follow = new Follow();
        BeanUtils.copyProperties(followRequest, follow);
        followRepository.save(follow);

        // Increment follower and following counts
        ApplicationUser follower = userService.getUserById(followRequest.getUserId());
        ApplicationUser following = userService.getUserById(followRequest.getFollowingId());

        if (follower != null && following != null) {
            follower.setFollowingCount(follower.getFollowingCount() + 1);
            following.setFollowerCount(following.getFollowerCount() + 1);

            userService.saveUser(follower);
            userService.saveUser(following);
        }

        return new CustomResponse<>(true, false, "successfully followed", HttpStatus.CREATED.value());
    }

    public CustomResponse<Object> delete(FollowRequest followRequest) {
        Follow follow = (Follow) followRepository.findByFollower_IdAndFollowing_Id(followRequest.getUserId(), followRequest.getFollowingId()).orElse(null);
        if (follow != null) {
            followRepository.delete(follow);

            // Decrement follower and following counts
            ApplicationUser follower = userService.getUserById(followRequest.getUserId());
            ApplicationUser following = userService.getUserById(followRequest.getFollowingId());

            if (follower != null && following != null) {
                follower.setFollowingCount(follower.getFollowingCount() - 1);
                following.setFollowerCount(following.getFollowerCount() - 1);

                userService.saveUser(follower);
                userService.saveUser(following);
            }

            return new CustomResponse<>(true, false, "successfully unfollowed", HttpStatus.OK.value());
        }
        return new CustomResponse<>(false, true, "you are not following this user", HttpStatus.CONFLICT.value());
    }
}


