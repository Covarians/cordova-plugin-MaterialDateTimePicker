package cordova.plugin.materialdatetime;

import android.util.Log;
import android.widget.DatePicker;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * This class echoes a string called from JavaScript.
 */
public class CordovaMaterialDateTime extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("datepicker")) {
			String title = args.getString(0);
			String color = args.getString(1);
			String oktext = args.getString(2);
			String canceltext = args.getString(3);
			String mindate = args.getString(4);
			String maxdate = args.getString(5);
			JSONArray highlightedDates = args.getJSONArray(6);
			Boolean showyearpickerbeforemonth = args.getBoolean(7);
			Boolean vibrateontouch = args.getBoolean(8);
			Boolean autodismiss = args.getBoolean(9);
			String selectedDate = args.getString(10);
			this.Datepicker(title, color, oktext, canceltext, mindate, maxdate, highlightedDates, showyearpickerbeforemonth, vibrateontouch, autodismiss, selectedDate, callbackContext);
			return true;
		}else{
			if (action.equals("timepicker")){
				String hours = args.getString(0);
				String minutes = args.getString(1);
				String seconds = args.getString(2);
				String title = args.getString(3);
				String color = args.getString(4);
				String oktext = args.getString(5);
				String canceltext = args.getString(6);
				JSONArray mintime = args.getJSONArray(7);
				JSONArray maxtime = args.getJSONArray(8);
				Boolean enableSeconds = args.getBoolean(9);
				Boolean enableMinutes = args.getBoolean(10);
				Boolean vibrateontouch = args.getBoolean(11);
				this.Timepicker(hours, minutes, seconds, title, color, oktext, canceltext, mintime, maxtime,enableSeconds, enableMinutes, vibrateontouch,  callbackContext);
				return true;
			}
		}
		return false;
	}

private void Datepicker(
        String title,
        String color,
        String oktext,
        String canceltext,
        String mindate,
        String maxdate,
        JSONArray highlightedDates,
        Boolean showyearpickerbeforemonth,
        Boolean vibrateontouch,
        Boolean autodismiss,
        String selectedDate,
        CallbackContext callbackContext) {

    SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    iso.setLenient(false); // stricter parsing

    try {
        Calendar initialDate = Calendar.getInstance(); // default today

        // Set initial date if provided
        if (selectedDate != null && !selectedDate.isEmpty()) {
            Date date = iso.parse(selectedDate);
            initialDate.setTime(date);
        }

        DatePickerDialog dpd = DatePickerDialog.newInstance(
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog view, int year, int month, int dayOfMonth) {
                    Calendar selected = Calendar.getInstance();
                    selected.set(year, month, dayOfMonth);
                    String dateStr = iso.format(selected.getTime()); // ISO format out
                    callbackContext.success(dateStr);
                    Log.d("CordovaDatePicker", "Clicked: " + dateStr);
                }
            },
            initialDate.get(Calendar.YEAR),
            initialDate.get(Calendar.MONTH),
            initialDate.get(Calendar.DAY_OF_MONTH)
        );

        if (title != null && !title.isEmpty()) dpd.setTitle(title);
        if (color != null && !color.isEmpty()) dpd.setAccentColor(color);
        if (oktext != null && !oktext.isEmpty()) dpd.setOkText(oktext);
        if (canceltext != null && !canceltext.isEmpty()) dpd.setCancelText(canceltext);

        // Min date
        if (mindate != null && !mindate.isEmpty()) {
            Date date = iso.parse(mindate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            dpd.setMinDate(cal);
        }

        // Max date
        if (maxdate != null && !maxdate.isEmpty()) {
            Date date = iso.parse(maxdate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            dpd.setMaxDate(cal);
        }

        // Highlighted dates
        if (highlightedDates.length() > 0) {
            Calendar[] finalDates = new Calendar[highlightedDates.length()];
            for (int i = 0; i < highlightedDates.length(); i++) {
                String dateStr = highlightedDates.getString(i);
                Date date = iso.parse(dateStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                finalDates[i] = cal;
            }
            dpd.setHighlightedDays(finalDates);
        }

        dpd.showYearPickerFirst(showyearpickerbeforemonth);
        dpd.vibrate(vibrateontouch);
        dpd.autoDismiss(autodismiss);

        dpd.show(cordova.getActivity().getFragmentManager(), "Datepickerdialog");

    } catch (Exception e) {
        callbackContext.error("Date parsing error: " + e.toString());
    }
}


	private void Timepicker(String hours, String minutes, String seconds, String title, String color, String oktext, String canceltext, JSONArray mintime, JSONArray maxtime, Boolean enableSeconds, Boolean enableMinutes, Boolean vibrateontouch, CallbackContext callbackContext) {
		if (1==1) {
			Calendar now = Calendar.getInstance();
			TimePickerDialog dpd =  TimePickerDialog.newInstance(
					new TimePickerDialog.OnTimeSetListener(){
						@Override
						public void onTimeSet(TimePickerDialog view, int hour, int minute,int seconds) {
							callbackContext.success(hour+":"+minute+":"+seconds);
							Log.d("Orignal", "Got clicked");
						}
					},
					Integer.parseInt(hours),
					Integer.parseInt(minutes),
					Integer.parseInt(seconds),
					true    // 24 hours format
			);
			if(title != null && title.length() > 0){
				dpd.setTitle(title);
			}
			if(color != null && color.length() > 0){
				dpd.setAccentColor(color);
			}
			if(oktext != null && oktext.length() > 0){
				dpd.setOkText(oktext);
			}
			if(canceltext != null && canceltext.length() > 0){
				dpd.setCancelText(canceltext);
			}
			if( mintime.length() > 0){

				try{
					dpd.setMinTime(mintime.getInt(0),mintime.getInt(1),mintime.getInt(2));
					//dpd.setmintime(cal);
				}catch (Exception e){
					callbackContext.error("Error in mintime : "+e.toString());
				}
			}
			if(maxtime.length() > 0){

				try{
					dpd.setMaxTime(maxtime.getInt(0),maxtime.getInt(1),maxtime.getInt(2));
				}catch (Exception e){
					callbackContext.error("Error in maxtime : "+e.toString());
				}
			}
			
			dpd.enableSeconds(enableSeconds);
			dpd.enableMinutes(enableMinutes);
			dpd.setTimeInterval(1,enableMinutes ? 1 : 60, enableSeconds ? 1 : 60);

			dpd.vibrate(vibrateontouch);

			// Specific Covarians 
			dpd.setLocale(Locale.UK);

			/*dpd.setThemeDark(true);*/
			/*dpd.setAccentColor(Color.parseColor("#9C27B0"));*/
			dpd.show(cordova.getActivity().getFragmentManager(),"Timepickerdialog");

		} else {
			callbackContext.error("Expected one non-empty string argument.");
		}
	}
}
