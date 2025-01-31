package event_class;

import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;

import com.example.diary_oil_v3.CameraVieActivity;
import com.example.diary_oil_v3.MainActivity;
import com.example.diary_oil_v3.R;

import java.io.Serializable;
import java.time.Duration;
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
	public boolean DoantohonCong;
	public int cash;
	public int difference;
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
	public String color_code;


	public Event(int type, Date date, String odo) {
		String typename;
		switch (type) {
			case 0:
				typename = "Record";
				this.resourceid = R.drawable.ic_baseline_photo_camera_24;
				this.color_code = "#56D7BC";
				this.status=0;
				break;

			case 1:
				typename = "Oil Change";
				this.resourceid = R.drawable.oil;
				this.color_code = "#44CCFC";
				this.status=3;
				break;

			case 2:
				typename = "Maintenance";
				this.resourceid = R.drawable.main;
				this.status=3;
				this.color_code = "#BB86FC";
				break;

			case 3:
				typename = "Fuel Change";

				this.color_code = "#D75656";
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
		Log.e("cum",date.toString());
	this.due=DateTest.Cal_Create2(date);

	}

	public void real_update_due(DateTest predictor)
	{
		Log.e("12345 Predictor",predictor.toString());
		Calendar today = Calendar.getInstance();
		Date a = (Date) snapshot.keySet().toArray()[0];
		today.setTime(a);
		Calendar calendar1 = predictor.predicted_by_odometer(getDistance()+Integer.valueOf(odo));
		today.add(Calendar.DATE,getDays());
		Log.e("12345",getType());
		Log.e("12345",getDistance()+Integer.valueOf(odo)+"\n"+ calendar1.getTime().toString());
		Log.e("12345",a.toString()+"\n"+ today.getTime().toString());
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND,0);
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);
		calendar1.set(Calendar.MILLISECOND,0);
		long inct = Duration.between(today.toInstant(),calendar1.toInstant()).toDays();


		if (inct>0)
		{
			this.due=(Calendar) today.clone();
			this.DoantohonCong = true;
		}
		else
		{
			this.due=(Calendar) calendar1.clone();
			this.DoantohonCong = false;
		}
		Log.e("cum",this.due.getTime().toString());
	}

	public void updateStatus()
	{
		Calendar today = Calendar.getInstance();
		Log.e("cum",this.due.getTime().toString());
		Log.e("cum 2",today.getTime().toString());
		if (this.due.compareTo(today)==1)
		{
			this.status=3;
		}
		else
		{
			this.status=1;
		}

		Log.e("cum 3", String.valueOf(this.status));
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


	public int getStatusNumber()
	{
		return this.status;
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
		Log.e("der",due.toString());
		return due.getTime();
	}

	public Calendar getCalDue()
	{
		return  due;
	}
	public Calendar CalgetDue() {
		return due;
	}
}
