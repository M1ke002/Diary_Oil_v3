package event_class;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import env.Utils;

public class EventList {
	private ArrayList<Event> list;
	private Event PendingOil;
	private Event PendingMain;

	public void setPendingOil(Event pendingOil) {
		PendingOil = pendingOil;
	}

	public void setPendingMain(Event pendingMain) {
		PendingMain = pendingMain;
	}

	public EventList() {
		if (list == null)
		{this.list = new ArrayList<Event>();}

	}

	public void addEvent(Event e){
		int type = e.getInttype();
		Iterator<Map.Entry<Date,Integer>> snap = e.Snap_date();
		Map.Entry<Date,Integer> vf = snap.next();
		Date date = vf.getKey();
		int odo = vf.getValue();
		PendingOil.newSnapshot(date,odo);
		PendingMain.newSnapshot(date,odo);

		DateTest predictor = init_predict();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		predictor.add_data(calendar,odo);

		Calendar calendar1 = predictor.predicted_by_odometer(PendingOil.getDistance()+odo);
		String sDate1=PendingOil.date;
		Log.e("eor",sDate1);
		Date date1= null;
		SimpleDateFormat sdf= new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.ENGLISH);
		try {
			date1 = sdf.parse(sDate1);
			date1 = sdf2.parse(sdf2.format(date1));
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		Log.e("eor",String.valueOf(date1));
		calendar.setTime(date1);
		calendar.add(Calendar.DATE,PendingOil.getDays());
		int compare = calendar.compareTo(calendar1);
		if (compare==1)
		{
			calendar=calendar1;
			Date v=calendar.getTime();
			try {

				v = sdf2.parse(sdf2.format(v));
				Log.e("Do this phapp",v.toString());

			} catch (ParseException ex) {
				ex.printStackTrace();
			}

			PendingOil.update_due(v);
		}
		else
		{
			Date v = calendar.getTime();
			PendingOil.update_due(v);
		}

		Calendar calendar2 = Calendar.getInstance();
		if (calendar.compareTo(calendar2)==1)
		{
			if (type==1)
			{
				PendingOil.setStatus(0);
			}
		}
		else
		{
			if (type==0 || type==3 || type==2)
			{
				PendingOil.setStatus(1);
			}
			else
			{
				PendingOil.setStatus(2);
			}
		}


		Calendar calendar3 = predictor.predicted_by_odometer(PendingMain.getDistance()+odo);
		String sDate2=PendingMain.date;
		Date date2= null;
		try {
			date2 = sdf.parse(sDate2);
			date2 = sdf2.parse(sdf2.format(date2));

		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		calendar.setTime(date2);
		calendar.add(Calendar.DATE,PendingMain.getDays());

		if (calendar.compareTo(calendar3)==1)
		{
			calendar=calendar3;
			Date v=calendar.getTime();
			try {

				v = sdf2.parse(sdf2.format(v));

			} catch (ParseException ex) {
				ex.printStackTrace();
			}

			PendingMain.update_due(v);
		}
		else
		{
			Date v = calendar3.getTime();
			PendingMain.update_due(v);
		}

		if (calendar.compareTo(calendar2)==1)
		{
			if (type==2)
			{
				PendingMain.setStatus(0);
			}
		}
		else
		{
			if (type==0 || type==3 || type==1)
			{
				PendingMain.setStatus(1);
			}
			else
			{
				PendingMain.setStatus(2);
			}
		}



		if (type==0 || type==3)
		{
			this.list.add(0,e);

		}
		if (type==1)
		{
			PendingOil.date=Utils.Date_to_String(calendar2.getTime());
			PendingOil.odo=Utils.formatstring(Integer.toString(odo));
			this.list.add(0,PendingOil);
			PendingOil=e;
		}
		if (type==2)
		{
			PendingMain.date=Utils.Date_to_String(calendar2.getTime());
			PendingMain.odo=Utils.formatstring(Integer.toString(odo));
			this.list.add(0,PendingMain);
			PendingMain=e;
		}


		//
	}

	public DateTest init_predict()
	{
		DateTest predictor = new DateTest();
		Iterator<Event> it = list.iterator();
		while(it.hasNext()) {
			Event element = it.next();
			Map.Entry<Date,Integer>  a = element.Snap_date().next();

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


	
	public Event getPendingMain() {
		return PendingMain;
	}
	
	public ArrayList<Event> buildTimeline() {

		//Collections.reverse(list);
		return list;
		
	}
}
