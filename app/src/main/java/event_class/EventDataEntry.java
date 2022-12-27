package event_class;

import com.anychart.chart.common.dataentry.DataEntry;

public class EventDataEntry extends DataEntry {


    public EventDataEntry(String x, Number value, Number value2, Number value3, Number value4, String type) {
            setValue("x", x);
            setValue("value", value);
            setValue("value1", value);
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("Record_type",type);
            setValue("value4", value4);
        }


    public EventDataEntry(Number x, Number value) {
        setValue("x", x);
        setValue("value", value);
    }

}
