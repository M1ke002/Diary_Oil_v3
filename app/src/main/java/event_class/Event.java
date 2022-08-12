package event_class;

import com.example.diary_oil_v3.R;

import java.io.Serializable;
import java.time.LocalTime;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Event implements Serializable {
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


	private int status;

	private Map<String, String> snapshot;
	public String date, odo;


	public Event(int type, String date, String odo) {
		String typename;
		switch (type) {
			case 0:
				typename = "Record";
				this.resourceid = R.drawable.ic_baseline_photo_camera_24;
				break;

			case 1:
				typename = "Oil Change";
				this.resourceid = R.drawable.oil;
				break;

			case 2:
				typename = "Maintenance";
				this.resourceid = R.drawable.main;

				break;

			case 3:
				typename = "Fuel Change";
				this.resourceid = R.drawable.ic_baseline_local_gas_station_24;

				break;


			default:
				typename = "How do you get this ?";
				this.resourceid = R.drawable.hero;

				break;


		}

		this.type = typename;
		this.snapshot = new HashMap<String, String>();
		;
		this.snapshot.put(date, odo);
		this.date = date;
		this.odo = odo;
		Random rand = new Random();
		this.status = rand.nextInt(4);


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


	public int getStatus() {
		switch (this.status) {
			case 0:
				return R.drawable.hero;


			case 1:
				return R.drawable.icon_miss;


			case 2:
				return R.drawable.icon_late;


			case 3:
				return R.drawable.icon_pending;


			default:
				return R.drawable.record_icon;


		}

	}

	public int getColor() {
		switch (this.status) {
			case 0:
				return R.color.Home_color;


			case 1:
				return R.color.yellow;


			case 2:
				return R.color.fab_color;


			case 3:
				return R.color.purple_3;


			default:
				return R.color.Theme_second;


		}

	}

	public String getStatusName() {
		switch (this.status) {
			case 0:
				return "Completed";


			case 1:
				return "Missing...";


			case 2:
				return "Late";


			case 3:
				return "Pending...";


			default:
				return "Stuff";


		}


	}
}
