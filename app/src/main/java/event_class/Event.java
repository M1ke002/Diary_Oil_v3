package event_class;

import android.widget.ImageView;

import com.example.diary_oil_v3.R;

import java.time.LocalTime;

import java.util.HashMap;
import java.util.Map;


public class Event {
	private int distance;
	private int days;
	private LocalTime due;
	private String type;

	public int getResourceid() {
		return resourceid;
	}

	public void setResourceid(int resourceid) {
		this.resourceid = resourceid;
	}

	private int resourceid;



	private String[] status;
	private Map<String,String> snapshot;
	public String date,odo;

	
	

	public Event(int type, String date, String odo ) {
		String typename;
		switch (type){
			case 0:
			typename = "Record";
			this.resourceid = R.drawable.hero_snap;
			break;

			case 1:
				typename = "Oil Change";
				this.resourceid = R.drawable.hero_oil;
				break;

			case 2:
				typename = "Maintenance";
				this.resourceid = R.drawable.hero_main;

				break;

			default:
				typename = "How do you get this ?";
				this.resourceid = R.drawable.hero;

				break;



		}

		this.type = typename;
		this.snapshot = new HashMap<String, String>();;
		this.snapshot.put(date,odo);
		this.date=date;
		this.odo=odo;
		
		
	}
	
	public void newSnapshot(String[] details) {
		
	}
	
	public void update() {
		
	}

	public int getDistance() {
		return this.distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDays() {
		return this.days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getStatus() {
		return this.status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}
	
}
