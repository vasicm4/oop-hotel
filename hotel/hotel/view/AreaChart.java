package view;

import java.awt.Color;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

public class AreaChart {
	public static JPanel createAreaChart(HashMap<String, ArrayList<Double>> data) {
	    XYChart chart = new XYChartBuilder().width(800).height(600).title("EarningChart").xAxisTitle("Month").yAxisTitle("Amount").build();
	    Color[] sliceColors = new Color[data.keySet().size()];
		for (int i = 0; i < data.keySet().size(); i++) {
			sliceColors[i] = new Color((int) (Math.random() * 0x1000000));
		}
	    
		DateFormat sdf = new SimpleDateFormat("MM.yyyy");
	    List<Date> months = new ArrayList<Date>(12); 	
	    Date date = null;
	    for (int i = 0; i < 12; i++) {
	        try {
               date = sdf.parse(LocalDate.now().minusMonths(i).format(DateTimeFormatter.ofPattern("MM.yyyy")));
	        } catch (ParseException e) {
	          e.printStackTrace();
	        }
	        months.add(date);
        }
	 
		for (String type : data.keySet()) {
			List<Double> yData = new ArrayList<Double>(12);
			for (int i = 0; i < 12; i++) {
				yData.add(data.get(type).get(i));
			}
			chart.addSeries(type, months, yData);
		}
		return new XChartPanel<>(chart);
	}
}
