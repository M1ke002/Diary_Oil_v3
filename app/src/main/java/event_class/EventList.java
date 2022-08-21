package event_class;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class EventList {
	private ArrayList<Event> list;
	private Event PendingOil;
	private Event PendingMain;

	public EventList() {
		if (list == null)
		{this.list = new ArrayList<Event>();}
	}

	public void addEvent(Event e) {

		this.list.add(0,e);
	}

	public DateTest init_predict()
	{
		DateTest predictor = new DateTest();
		Iterator<Event> it = list.iterator();
		while(it.hasNext()) {
			Event element = it.next();
			Map.Entry<Date,Integer>  a = element.Snap_date();
			Date date = a.getKey();
			Integer odo = a.getValue();
			Calendar d = Calendar.getInstance();
			d.setTime(date);
			predictor.add_data(d,odo);
		}
		return predictor;
	}
	
	public Event getPendingOil() {
		return PendingOil;
	}

	public void new_record(int type,Date date,int odo)
	{

	}
	
	public Event getPendingMain() {
		return PendingMain;
	}
	
	public ArrayList<Event> buildTimeline() {

		//Collections.reverse(list);
		return list;
		
	}
}
