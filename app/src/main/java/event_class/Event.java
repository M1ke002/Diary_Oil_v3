package event_class;

import android.util.Log;
import android.util.Pair;

import com.example.diary_oil_v3.R;

import java.io.Serializable;
import java.time.LocalTime;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import env.Utils;


public class Event implements Serializable {
	private int inttype;
	private int distance;
	private int days;
	private Calendar due;

	private String type;

	public int getResourceid() {
		return resourceid;
	}




	private int resourceid;


	private int status;

	public void setStatus(int status) {
		this.status = status;
	}

	private Map<Date, Integer> snapshot;
	public String odo;
	public String date;


	public Event(int type, Date date, String odo) {
		String typename;
		switch (type) {
			case 0:
				typename = "Record";
				this.resourceid = R.drawable.ic_baseline_photo_camera_24;
				this.status=0;
				break;

			case 1:
				typename = "Oil Change";
				this.resourceid = R.drawable.oil;
				this.status=3;
				break;

			case 2:
				typename = "Maintenance";
				this.resourceid = R.drawable.main;
				this.status=3;

				break;

			case 3:
				typename = "Fuel Change";
				this.resourceid = R.drawable.ic_baseline_local_gas_station_24;
				this.status=0;
				break;


			default:
				typename = "How do you get this ?";
				this.resourceid = R.drawable.hero;
				this.status=0;

				break;


		}

		this.type = typename;
		this.inttype=type;
		this.snapshot = new HashMap<Date, Integer>();
		this.snapshot.put(date, Integer.valueOf(odo));
		this.odo = odo;
		this.date = (Utils.Date_to_String(date));



	}

	public Iterator<Map.Entry<Date,Integer>> Snap_date()
	{
		Iterator<Map.Entry<Date,Integer>> a =  snapshot.entrySet().iterator();
		return a;

	}

	public void newSnapshot(Date date,Integer odo) {
	this.snapshot.put(date,odo);


	}


	public void update_due(Date date) {
	this.due=DateTest.Cal_Create2(date);


	}
	public void clear_snap()
	{
		this.snapshot= new HashMap<Date, Integer>();
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

	public int getInttype() {
		return inttype;
	}

	public void setInttype(int inttype) {
		this.inttype = inttype;
	}

	public Date getDue() {
		return due.getTime();
	}
	public Calendar CalgetDue() {
		return due;
	}
}
