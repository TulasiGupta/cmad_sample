/**
 * 
 */
package com.cisco.cmad.event.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cisco.cmad.event.dao.Event;
import com.cisco.cmad.event.dao.EventType;
import com.cisco.cmad.event.dao.EventTypeWithCount;

/**
 * @author tcheedel
 *
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {

    @Query("SELECT new com.cisco.cmad.event.dao.EventTypeWithCount(e.type as type, COUNT(e.id) as cnt) FROM Event e GROUP BY e.type")
    List<EventTypeWithCount> getCountGroupByType();

    @Query("SELECT new com.cisco.cmad.event.dao.EventType(e) FROM Event e where e.type = :eventType")
    List<EventType> getEventByType(@Param("eventType") String eventType);

    @Query(value = "SELECT * FROM SYSLOGEVENTS S WHERE S.TYPE = :type", nativeQuery = true)
    List<Event> findByEventType(@Param("type") String type, Pageable pg);
    
    
    @Query(value="SELECT * FROM SYSLOGEVENTS WHERE type= :filterValue", nativeQuery=true)
    List<Event> findByFilterValue(@Param("filterValue") String filterValue, Pageable pg);
    
    @Query(value="SELECT * FROM SYSLOGEVENTS WHERE type= :filterValue", nativeQuery=true)
    List<Event> sortByFilterValue(@Param("filterValue") String filterValue, Pageable pg);

}