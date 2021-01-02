package application;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SavingsCalculatorApplication extends Application {
    
    public double savingsReset = 25;
    public double rRateReset = 0.0;
    public ArrayList<Double> withInterest = new ArrayList<>();
    

    private String roundDouble(Double valueToRound) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(valueToRound.doubleValue());
    }
    
    private double yearlyWithRates (double interestRate, double savingsRate, int year){
      
        double previous = savingsRate*12;
        withInterest.add(0.0);
        for(int i = 1; i<=year; i++){
            withInterest.add(previous*(1+(interestRate/100)));
            previous = withInterest.get(i) + (savingsRate*12);
        }
        double d = withInterest.get(year);
        withInterest.clear();
        return d;
    }
    
//    private LineChart<Number, Number> updateGraphic(XYChart.Series savin, XYChart.Series savinWI){
//        NumberAxis xAxis = new NumberAxis(0, 30, 1);
//        NumberAxis yAxis = new NumberAxis(0, 125000, 25000);
//        
//        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
//        lineChart.setTitle("Pollution Counter");
//        
//        lineChart.getData().add(savin);
//        lineChart.getData().add(savinWI);
//        
//        return lineChart;
//    }
    
    private void updateChart(double monthlySavings, double interestRate, XYChart.Series savin, XYChart.Series savinWI){
            savin.getData().clear();
            savinWI.getData().clear();
            
            savin.getData().add(new XYChart.Data(0,0));
            savinWI.getData().add(new XYChart.Data(0,0));
            
            double previous = 0;
            
            for (int i=1;i<=30;i++){
                savin.getData().add(new XYChart.Data(i, i*monthlySavings*12));
                
                previous += monthlySavings*12;
                previous *= (1+interestRate/100.0);
                
                savinWI.getData().add(new XYChart.Data(i, previous));
                
            }
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        launch(SavingsCalculatorApplication.class);
    }
    
    @Override
    public void start(Stage stage){
        
         //Create linechart
        NumberAxis xAxis = new NumberAxis(0, 30, 1);
        NumberAxis yAxis = new NumberAxis(0, 125000, 25000);
        
        
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Savings Calculator");
        XYChart.Series savin = new XYChart.Series();
        XYChart.Series savinWI = new XYChart.Series();
        lineChart.getData().add(savin);
        lineChart.getData().add(savinWI);
        
        //create vbox top borderpane
        Label topbpText = new Label("Monthly savings");
        Slider sliderOne = new Slider();
        sliderOne.setMin(25);
        sliderOne.setMax(250);
        sliderOne.setShowTickLabels(true); 
        sliderOne.setShowTickMarks(true); 
        Label sRateValue = new Label("");
        
        //create vbox bottom borderpane
        Label bottombpText = new Label("Yearly interest rate");
        Slider sliderTwo = new Slider();
        sliderTwo.setMin(0);
        sliderTwo.setMax(10);
        sliderTwo.setBlockIncrement(2.5);
        sliderTwo.setShowTickLabels(true); 
        sliderTwo.setShowTickMarks(true); 
        Label rRateValue = new Label("");
        BorderPane top = new BorderPane();
        top.setLeft(topbpText);
        top.setRight(sRateValue);
        top.setCenter(sliderOne);
        BorderPane bottom = new BorderPane();
        bottom.setLeft(bottombpText);
        bottom.setRight(rRateValue);
        bottom.setCenter(sliderTwo);
        
        
        sliderOne.valueProperty().addListener((changed, oldValue, newValue) ->
                sRateValue.setText(roundDouble(newValue.doubleValue())));
        
        sliderTwo.valueProperty().addListener((changed, oldValue, newValue) ->
                rRateValue.setText(roundDouble(newValue.doubleValue())));
        
        
        sliderOne.setOnMouseReleased(dragOver -> {
            savingsReset = sliderOne.getValue();
            rRateReset = sliderTwo.getValue();
            updateChart(savingsReset, rRateReset, savin, savinWI);
//            savin.getData().clear();
//            savinWI.getData().clear();
            
//            for (int i = 0; i <= 30; i++){
//                savin.getData().add(new XYChart.Data(i, i*12*savingsReset));
//            }
//            lineChart.getData().add(savin); 
//            rRateReset = sliderTwo.getValue();
//            for (int i = 0; i <= 30; i++){
//                savinWI.getData().add(new XYChart.Data(i, yearlyWithRates(rRateReset, savingsReset, i)));
//            }
//            
//            lineChart.getData().add(savin);
//            lineChart.getData().add(savinWI);
        });
        
        
        sliderTwo.setOnMouseReleased(dragOver -> {
            savingsReset = sliderOne.getValue();
            rRateReset = sliderTwo.getValue();
            updateChart(savingsReset, rRateReset, savin, savinWI);
//            savin.getData().clear();
//            savinWI.getData().clear();
//            savingsReset = sliderOne.getValue();
//            for (int i = 0; i <= 30; i++){
//                savin.getData().add(new XYChart.Data(i, i*12*savingsReset));
//            }
//            lineChart.getData().add(savin); 
//            rRateReset = sliderTwo.getValue();
//            for (int i = 0; i <= 30; i++){
//                savinWI.getData().add(new XYChart.Data(i, yearlyWithRates(rRateReset, savingsReset, i)));
//            }
//            
//            lineChart.getData().add(savin);
//            lineChart.getData().add(savinWI);
        });
        
       
        
        //create vbox
        VBox vbox = new VBox(top, bottom);
        

        // Set the alignment of the Top Text to Center
        BorderPane.setAlignment(vbox,Pos.TOP_CENTER);
        
        // Create a BorderPane with node in each of the five regions
        BorderPane root = new BorderPane(lineChart, vbox, null, null, null);
        // Set the Size of the VBox
        root.setPrefSize(640, 480);		
        // Set the Style-properties of the BorderPane

       // Create the Scene
        Scene scene = new Scene(root);
        // Add the scene to the Stage
        stage.setScene(scene);
        // Display the Stage
        stage.show();		 
        
    }

}
