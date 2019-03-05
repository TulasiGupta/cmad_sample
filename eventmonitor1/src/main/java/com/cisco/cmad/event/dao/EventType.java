package com.cisco.cmad.event.dao;

/**
 * @author tcheedel
 *
 */
public class EventType {
	
	Event event;

	public EventType(Event event) {
		super();
		this.event = event;
	}

	public EventType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	

}
