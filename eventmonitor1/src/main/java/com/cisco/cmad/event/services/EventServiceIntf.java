package com.cisco.cmad.event.services;

import java.util.List;
import java.util.Map;

import com.cisco.cmad.event.dao.Event;
import com.cisco.cmad.event.dao.JsonEvent;
import com.cisco.cmad.event.enums.EventTypeEnum;

/**
 * @author tcheedel
 *
 */
public interface EventServiceIntf {

    public void addEvent(Event event);

    List<JsonEvent> getEvents(EventTypeEnum eventTypeEnum, int page, int limit);
    
    List<JsonEvent> getEvents(EventTypeEnum eventTypeEnum, String filter, int page, int limit);

    Map<String, Long> getEventCountByType();

    List<JsonEvent> getEventsByType(String eventType);

    List<JsonEvent> sortEvents(String sortColumn, String sortByDesc, int page, int limit);
    
    List<JsonEvent> sortEvents(String sortColumn, String sortByDesc, String filter, int page, int limit);

}
