/**
 * https://examples.javacodegeeks.com/enterprise-java/spring/data/spring-data-jparepository-example/
 */
package com.cisco.cmad.event.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cisco.cmad.event.constants.DummyConstants;
import com.cisco.cmad.event.dao.Event;
import com.cisco.cmad.event.dao.EventType;
import com.cisco.cmad.event.dao.EventTypeWithCount;
import com.cisco.cmad.event.dao.JsonEvent;
import com.cisco.cmad.event.enums.EventTypeEnum;
import com.cisco.cmad.event.logger.CMADLogger;
import com.cisco.cmad.event.mongo.dao.IPAddresses;
import com.cisco.cmad.event.repositories.EventRepository;
import com.cisco.cmad.event.repositories.IPAddressRepository;

/**
 * @author sakahuja
 * @author tcheedel
 */
@Repository
public class EventService implements EventServiceIntf {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private IPAddressRepository ipAddressRepository;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    private DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public EventService() {
        System.out.println("jdbcTemplate " + jdbcTemplate);
    }
    
    public void deleteEvents() {
        try {
            eventRepository.deleteAll();
        } catch(Exception e) {
            e.printStackTrace();
            CMADLogger.logError(this.getClass().getName(), "Exception while deleting the events "+e.getMessage());
        }
    }

    public void generateEvents() {
        try {
            List<IPAddresses> ipAddresses = ipAddressRepository.findAll();

            short min = 0;
            int max = ipAddresses.size() - 1;

            List<String> ipaddrs = ipAddresses.stream().map(ipaddress -> ipaddress.getIpaddress())
                    .collect(Collectors.toList());
            Random r = new Random();

            int totalcount = (int) eventRepository.count();

            List<String> messages = new DummyConstants().getMessages();

            List<Event> events = new ArrayList<Event>();
            for (int i = 0; i < 20; i++) {
                Event event = new Event();
                System.out.println(r.nextInt(10));
                System.out.println(messages);
                String msg = messages.get(r.nextInt(10));

                event.setId(++totalcount);
                event.setIpaddress(ipaddrs.get(r.nextInt(max - min)));
                event.setTimestamp(System.currentTimeMillis());
                event.setMessage(msg.split(":")[0]);
                event.setType(msg.split(":")[1]);
                events.add(event);
            }
            eventRepository.saveAll(events);
        } catch (Exception e) {
            e.printStackTrace();
            CMADLogger.logError(this.getClass().getName(), "Exception while generating Events " + e.getMessage());
        }
    }

    @Override
    public void addEvent(Event event) {

        /*
         * List<School> schoollist = new ArrayList<School>();
         * 
         * for(School school: city.getSchools()) { school.setCity(city);
         * schoollist.add(school); } city.setSchools(new HashSet<School>(schoollist));
         */
        eventRepository.save(event);
    }

    @Override
    public List<JsonEvent> getEvents(EventTypeEnum eventTypeEnum, int page, int limit) {
        List<JsonEvent> eventsList = null;
        if (EventTypeEnum.ALL == eventTypeEnum) {

            PageRequest pg = PageRequest.of(page, limit, Direction.ASC, "id");

            Iterable<Event> events = eventRepository.findAll(pg);
            eventsList = new ArrayList<JsonEvent>();
            for (Event event : events) {
                eventsList.add(this.getJsonEvent(event));
            }
        }
        return eventsList;
    }

    @Override
    public Map<String, Long> getEventCountByType() {

        List<EventTypeWithCount> listofEventTypesWithCount = eventRepository.getCountGroupByType();

        Map<String, Long> eventCountMapByType = new HashMap<String, Long>();
        for (EventTypeWithCount eventTypeByCount : listofEventTypesWithCount) {
            eventCountMapByType.put(eventTypeByCount.getType(), eventTypeByCount.getCnt());
        }

        return eventCountMapByType;
    }

    @Override
    public List<JsonEvent> getEventsByType(String eventType) {
        List<EventType> eventsByType = eventRepository.getEventByType(eventType);
        List<JsonEvent> events = new ArrayList<JsonEvent>();

        for (EventType event : eventsByType) {
            events.add(this.getJsonEvent(event.getEvent()));
        }

        return events;
    }

    @Override
    public List<JsonEvent> sortEvents(String sortColumn, String sortByDesc, int page, int limit) {

        PageRequest pg = null;

        if (sortByDesc.equalsIgnoreCase("true")) {
            pg = PageRequest.of(page, limit, Direction.DESC, sortColumn);
        } else {
            pg = PageRequest.of(page, limit, Direction.ASC, sortColumn);
        }
        List<JsonEvent> events = new ArrayList<JsonEvent>();
        Page<Event> pageEvents = eventRepository.findAll(pg);

        for (Event event : pageEvents) {

            events.add(this.getJsonEvent(event));
        }

        return events;
    }

    @Override
    public List<JsonEvent> getEvents(EventTypeEnum eventTypeEnum, String filter, int page, int limit) {
        System.out.println("in get....");
        List<JsonEvent> eventsList = null;
        PageRequest pg = PageRequest.of(page, limit);
        if (EventTypeEnum.ALL == eventTypeEnum) {
            System.out.println("Filter: " + filter);
            Iterable<Event> events = eventRepository.findByFilterValue(filter, pg);
            eventsList = new ArrayList<JsonEvent>();
            for (Event event : events) {
                eventsList.add(this.getJsonEvent(event));
            }
        }
        return eventsList;
    }

    @Override
    public List<JsonEvent> sortEvents(String sortColumn, String filter, String sortByDesc, int page, int limit) {

        String sortBy = "ASC";

        if (sortByDesc.equalsIgnoreCase("true")) {
            sortBy = "DESC";
        }

        String sql = "SELECT * FROM SYSLOGEVENTS WHERE TYPE LIKE '%" + filter + "%' OR MESSAGE LIKE '%" + filter
                + "%' OR IPADDRESS LIKE '%" + filter + "%' OR ID LIKE '%" + filter + "%' ORDER BY " + sortColumn + " "
                + sortBy + " LIMIT " + page + ", " + limit;

        System.out.println("sql " + sql);
        List<Map<String, Object>> eventMap = getJdbcTemplate().queryForList(sql);

        List<JsonEvent> events = new ArrayList<JsonEvent>();

        for (Map<String, Object> event : eventMap) {
            JsonEvent js = new JsonEvent();
            js.setId((int) event.get("id"));
            js.setIpaddress((String) event.get("ipaddress"));
            js.setType((String) event.get("type"));
            js.setMessage((String) event.get("message"));
            js.setTimestamp(df.format(new Date((long) event.get("timestamp"))));
            events.add(js);
        }
        return events;

    }

    private JsonEvent getJsonEvent(Event event) {
        JsonEvent js = new JsonEvent();
        System.out.println(js);
        js.setId(event.getId());
        js.setIpaddress(event.getIpaddress());
        js.setMessage(event.getMessage());
        js.setType(event.getType());
        js.setTimestamp(df.format(new Date(event.getTimestamp())));
        return js;
    }

}