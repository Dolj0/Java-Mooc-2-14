package smiley;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class SmileyApplication extends Application{

    @Override
        public void start(Stage window) {

            Canvas paintingCanvas = new Canvas(640, 480);
            GraphicsContext painter = paintingCanvas.getGraphicsContext2D();

            ColorPicker colorPalette = new ColorPicker();

            BorderPane paintingLayout = new BorderPane();
            paintingLayout.setCenter(paintingCanvas);
            paintingLayout.setRight(colorPalette);

            
            
            paintingCanvas.setOnMouseDragged((event) -> {
                double xLocation = event.getX();
                double yLocation = event.getY();
                painter.setFill(colorPalette.getValue());
                painter.fillOval(xLocation, yLocation, 4, 4);
            });

            drawShape(painter);
            Scene view = new Scene(paintingLayout);

            window.setScene(view);
            window.show();
        }
        
        private void drawShape(GraphicsContext gc){
            gc.setFill(Color.BLACK);
            gc.setLineWidth(5);
            
            gc.fillRect(110, 60, 30, 30);
            gc.fillRect(200, 60, 30, 30);
            gc.fillRect(110, 180, 30, 30);
            gc.fillRect(200, 180, 30, 30);
            gc.fillRect(140, 180, 30, 30);
            gc.fillRect(170, 180, 30, 30);
            gc.fillRect(80, 150, 30, 30);
            gc.fillRect(230, 150, 30, 30);
        } 
        

    
    public static void main(String[] args) {
        launch(SmileyApplication.class);
    }

}
