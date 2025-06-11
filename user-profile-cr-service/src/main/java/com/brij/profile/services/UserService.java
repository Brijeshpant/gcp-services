package com.brij.profile.services;

import com.brij.profile.domain.UserProfile;

public interface UserService {
    UserProfile getProfile(String userId);
    UserProfile createUser(UserProfile profile);
}
