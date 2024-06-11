package view;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JPanel;

import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

public class CustomPieChart{    

	
    public static JPanel createPieChart(HashMap<String, Integer> data) {
		org.knowm.xchart.PieChart chart = new PieChartBuilder().width(500).height(500).title("Pie Chart").build();
		Color[] sliceColors = new Color[data.keySet().size()];
		
		for (int i = 0; i < data.keySet().size(); i++) {
			sliceColors[i] = new Color((int) (Math.random() * 0x1000000));
		}
		chart.getStyler().setSeriesColors(sliceColors);
		
		for (String type : data.keySet()) {
			chart.addSeries(type, data.get(type));
		}   
      return new XChartPanel<>(chart);
    }
}
