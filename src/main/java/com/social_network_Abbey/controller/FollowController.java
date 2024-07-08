package com.social_network_Abbey.controller;

import com.social_network_Abbey.entity.Follow;
import com.social_network_Abbey.request.FollowRequest;
import com.social_network_Abbey.response.CustomResponse;
import com.social_network_Abbey.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/follows")
public class FollowController {

    @Autowired
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    // Endpoint to retrieve all follows
    @GetMapping
    public List<Follow> getAllFollows() {
        return followService.getAllFollows();
    }

    @GetMapping("/{followId}")
    public ResponseEntity<Follow> getFollowById(@PathVariable Long followId) {
        Follow follow = followService.getFollowById(followId);
        if (follow != null) {
            return ResponseEntity.ok(follow);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/followed/{followedId}")
    public List<Follow> getFollowsByFollowedId(@PathVariable Long followedId) {
        return followService.getFollowsByFollowedId(followedId);
    }

    // Endpoint to create a new follow
    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Object>> create(@RequestBody FollowRequest followRequest){
        return new ResponseEntity<>(followService.create(followRequest), HttpStatus.OK);
    }


    // Endpoint to delete an existing follow
    @PostMapping("/delete")
    public ResponseEntity<CustomResponse<Object>> delete(@RequestBody FollowRequest followRequest){
        return new ResponseEntity<>(followService.delete(followRequest),HttpStatus.OK);
    }
}
