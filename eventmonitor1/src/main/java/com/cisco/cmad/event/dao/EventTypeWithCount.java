/**
 * 
 */
package com.cisco.cmad.event.dao;

/**
 * @author tcheedel
 *
 */
public class EventTypeWithCount {

	@Override
	public String toString() {
		return "EventTypeWithCount [type=" + type + ", cnt=" + cnt + "]";
	}

	private String type;
	private Long cnt;

	public EventTypeWithCount(String type, Long cnt) {
		super();
		this.type = type;
		this.cnt = cnt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}

}
