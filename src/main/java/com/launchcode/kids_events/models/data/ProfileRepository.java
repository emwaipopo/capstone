package com.launchcode.kids_events.models.data;

import com.launchcode.kids_events.models.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProfileRepository extends CrudRepository<Profile, Integer> {
}
