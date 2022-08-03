package event_class;

import java.util.ArrayList;
import java.util.Collections;

public class EventList {
	private ArrayList<Event> list;

	public EventList() {
		if (list == null)
		{this.list = new ArrayList<Event>();}
	}

	public void addEvent(Event e) {

		this.list.add(e);
	}
	
	public Event getPendingOil() {
		return list.get(-1);
	}
	
	public Event getPendingMain() {
		return list.get(-2);
	}
	
	public ArrayList<Event> buildTimeline() {

		Collections.reverse(list);
		return list;
		
	}
}
