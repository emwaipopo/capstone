package com.launchcode.kids_events.models.data;

import com.launchcode.kids_events.models.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {
}
