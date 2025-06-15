package com.brij.profile.services;

import com.brij.profile.domain.UserProfile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    final static HashMap<String, UserProfile> staticProfiles = loadProfiles();

    private static HashMap<String, UserProfile> loadProfiles() {
        HashMap<String, UserProfile> profiles = new HashMap<>();
        Integer[] ids = {1, 2, 3, 4, 5};
        Arrays.stream(ids).forEach(id -> profiles.put(id.toString(), createProfile(id)));
        return profiles;
    }

    @Override
    public UserProfile getProfile(String userId) {
        return Optional.of(staticProfiles.get(userId)).orElse(null);
    }

    @Override
    public UserProfile createUser(UserProfile profile) {
        staticProfiles.put(profile.id(), profile);
        return profile;
    }

    private static UserProfile createProfile(Integer userid) {
        return new UserProfile(userid.toString(),
                String.format("Dummy user %s", userid),
                String.format("dummyU%s@test.com", userid)
        );
    }
}
