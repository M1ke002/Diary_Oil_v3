package event_class;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
		Log.e("Predictor after adding event",predictor.toString());


		///TODO fix this mess

		PendingOil.real_update_due(predictor);
		Log.e("testing",this.PendingOil.getDue().toString());

		PendingOil.updateStatus();


		PendingMain.real_update_due(predictor);
		Log.e("testing",this.PendingMain.getDue().toString());

		PendingMain.updateStatus();
		Calendar calendar2 = Calendar.getInstance();



		if (type==0 || type==3)
		{
			this.list.add(0,e);

		}
		if (type==1)
		{
			PendingOil.date=Utils.Date_to_String(calendar2.getTime());
			PendingOil.odo=Utils.formatstring(Integer.toString(odo));
			Log.e("Vo ly",PendingOil.getStatusName());
			if (PendingOil.getStatusNumber()==3)
			{
				PendingOil.setStatus(0);
			}
			else
			{
				PendingOil.setStatus(2);
			}
			Log.e("Vo ly",PendingOil.getStatusName());
			this.list.add(0,PendingOil);
			e.real_update_due(predictor);
			PendingOil=e;
		}
		if (type==2)
		{
			PendingMain.date=Utils.Date_to_String(calendar2.getTime());
			PendingMain.odo=Utils.formatstring(Integer.toString(odo));

			Log.e("Vo ly 2",PendingMain.getStatusName());
			if (PendingMain.getStatusNumber()==3)
			{
				PendingMain.setStatus(0);
			}
			else
			{
				PendingMain.setStatus(2);
			}
			Log.e("Vo ly 2",PendingMain.getStatusName());
			this.list.add(0,PendingMain);
			e.real_update_due(predictor);
			PendingMain=e;
		}


		//
	}

	public DateTest init_predict()
	{
		ArrayList<Event> listrev;
		DateTest predictor = new DateTest();
		listrev = (ArrayList<Event>) list.clone();
		Collections.reverse(listrev);
		Iterator<Event> it = listrev.iterator();
		while(it.hasNext()) {
			Event element = it.next();
			Date date = null;
			try {
				Log.e("???",element.date);
				date = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).parse(element.date);

			} catch (ParseException e) {
				e.printStackTrace();
				Log.e("xxx","whyyyyyyyyyyyyyyyyyyyy");
			}





			Integer odo = Integer.valueOf(element.odo);



			Calendar d = Calendar.getInstance();
			d.setTime(date);
			Log.e("2345",d.getTime().toString() + "\n" + odo.toString());
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
