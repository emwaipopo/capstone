package com.launchcode.kids_events.models.data;

import com.launchcode.kids_events.models.Events;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends CrudRepository<Events, Integer> {
}
