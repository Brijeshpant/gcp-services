package com.brij.profile.controller;

import com.brij.profile.domain.UserProfile;
import com.brij.profile.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/profile")
@Slf4j
public class UserProfileController {
    UserService service;

    public UserProfileController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    UserProfile getProfile(@PathVariable String userId) {
        UserProfile profile = service.getProfile(userId);
        log.info("User profile {}", profile);
        return profile;
    }

    @PostMapping
    UserProfile createProfile(@RequestBody UserProfile profile) {
        UserProfile user = service.createUser(profile);
        log.info("Profile created successfully with Id {}", profile.id());
        return user;
    }

}
