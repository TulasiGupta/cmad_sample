/**
 * 
 */
package com.cisco.cmad.event.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cisco.cmad.event.dao.JsonEvent;
import com.cisco.cmad.event.enums.EventTypeEnum;
import com.cisco.cmad.event.exceptions.InvalidParameterException;
import com.cisco.cmad.event.logger.CMADLogger;
import com.cisco.cmad.event.services.EventService;

/**
 * @author tcheedel
 * @author sakahuja
 *
 */
@CrossOrigin
@RestController
@ComponentScan(basePackages = { "com.cisco.cmad.event" })
public class EventsController {

    @Autowired
    EventService eventService;
    
    @RequestMapping(value="/generateEvents", method = RequestMethod.PUT)
    public ResponseEntity<String> generateEvents() {
        eventService.generateEvents();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Generating Events");
    }
    
    @RequestMapping(value="/deleteEvents", method = RequestMethod.PUT)
    public ResponseEntity<String> deleteEvents() {
        eventService.deleteEvents();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Started deleting events");
    }

    @RequestMapping("/events")
    public ResponseEntity<List<JsonEvent>> getEvents(@RequestParam("sort") String sortColumnValue,
            @RequestParam("desc") String descValue, @RequestParam("page") int page, @RequestParam("limit") int limit,
            @RequestParam(value = "filter", required = false, defaultValue = "NA") String filterValue) {

        List<JsonEvent> events = null;

        if (StringUtils.isEmpty(sortColumnValue) || sortColumnValue.equalsIgnoreCase("null")) {
            throw new InvalidParameterException("sort param should not be null");
        }

        if (StringUtils.isEmpty(descValue) || descValue.equalsIgnoreCase("null")) {
            throw new InvalidParameterException("desc param should not be null");
        }

        if (StringUtils.isEmpty(page) || page < 0) {
            throw new InvalidParameterException("page param should be positive integer");
        }

        if (StringUtils.isEmpty(limit) || limit < 0) {
            throw new InvalidParameterException("limit param should positive integer");
        }

        if (StringUtils.isEmpty(filterValue) || filterValue.equalsIgnoreCase("null")) {
            throw new InvalidParameterException("filter param should not be null");
        }

        CMADLogger.logInfo(EventsController.class.getName(), "Param validation done");
        
        if (sortColumnValue.equalsIgnoreCase("NA") && filterValue.equalsIgnoreCase("NA")) {
            events = eventService.getEvents(EventTypeEnum.ALL, page, limit);
        } else if (sortColumnValue.equalsIgnoreCase("NA")) {
            events = eventService.getEvents(EventTypeEnum.ALL, filterValue, page, limit);
        } else if (!sortColumnValue.equalsIgnoreCase("NA") && filterValue.equalsIgnoreCase("NA")) {
            events = eventService.sortEvents(sortColumnValue, descValue, page, limit);
        } else if (!sortColumnValue.equalsIgnoreCase("NA")) {
            System.out.println("calling filtervalue with " + filterValue);
            events = eventService.sortEvents(sortColumnValue, filterValue, descValue, page, limit);
        } else {
            events = eventService.getEvents(EventTypeEnum.ALL, page, limit);
        }
        return ResponseEntity.status(HttpStatus.OK).body(events);

    }

    @RequestMapping("/countbytype")
    public ResponseEntity<Set<Entry<String, Long>>> getEventCountByType() {
        Map<String, Long> eventCountByType = eventService.getEventCountByType();
        return ResponseEntity.status(HttpStatus.OK).body(eventCountByType.entrySet());

    }

    @RequestMapping(value = "/type/{eventType}/events", method = RequestMethod.GET)
    public ResponseEntity<List<JsonEvent>> getEventsByType(@PathVariable("eventType") String eventType,
            UriComponentsBuilder builder) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventsByType(eventType));
    }

}
