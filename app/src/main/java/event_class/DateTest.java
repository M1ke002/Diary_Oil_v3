package event_class;






import java.util.*;

public class DateTest {
    double slope, intercept;
    ArrayList<Calendar> dates_list = new ArrayList<>();
    ArrayList<Integer> odometers_list = new ArrayList<>();
    ArrayList<Integer> intervals_list = new ArrayList<>();


    public void add_data(Calendar dates, int odometers) {
        Calendar cal = dates;
        dates_list.add(dates);
        long period = dates.getTime().getTime() - dates_list.get(0).getTime().getTime();
        long period_processed = period / (1000 * 60 * 60 * 24);
        System.out.println(intervals_list.toString());

        intervals_list.add((int) period_processed);
        double[] x = intervals_list.stream().mapToDouble(i -> i).toArray();
        odometers_list.add(odometers);
        double[] y = odometers_list.stream().mapToDouble(i -> i).toArray();
        LinearRegression lr = new LinearRegression(x, y);
        slope = lr.slope();
        intercept = lr.intercept();
    }

    public int predict_by_date(Calendar date) {
        double odo_predict;
        try{
        long interval = (date.getTime().getTime() - dates_list.get(0).getTime().getTime()) / (1000 * 60 * 60 * 24);
        odo_predict = (slope * interval) + intercept;}
        catch (IndexOutOfBoundsException e)
        {
            odo_predict = 0;
        }
        return (int) odo_predict;
    }

    public Calendar predicted_by_odometer(int odometer) {
        double interval = (odometer - intercept)/slope;
        Calendar cal = dates_list.get(0);
        cal.add(Calendar.DATE, (int)interval);
        return cal;

    }

/**
    public static void main(String[] args) {
        DateTest d = new DateTest();


        d.add_data(Cal_Create(2020, 2, 5), 21425);


        d.add_data(Cal_Create(2020, 3, 21), 25580);
        d.add_data(Cal_Create(2020, 5, 15), 27680);
        d.add_data(Cal_Create(2020, 6, 14), 29780);
        d.add_data(Cal_Create(2020, 7, 29), 31580);
        d.add_data(Cal_Create(2020, 8, 29), 33633);
        d.add_data(Cal_Create(2020, 10, 14), 35741);
        d.add_data(Cal_Create(2020, 12, 18), 37800);
        d.add_data(Cal_Create(2021, 1, 16), 39703);
        d.add_data(Cal_Create(2021, 3, 8), 41912);

        int new_odo = d.predict_by_date(Cal_Create(2021, 3,27));
        Calendar new_due = d.predicted_by_odometer(43000);
        System.out.println(new_odo);
        System.out.println(new_due.getTime());

    }
 */

    public static Calendar Cal_Create(int year, int month, int day)
    {
        Calendar instance = Calendar.getInstance();
        instance.set(year,month,day);
        return instance;

    }

    public static Calendar Cal_Create2(Date date)
    {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance;

    }
}
