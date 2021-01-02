package collage;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CollageApplication extends Application {
    
    
    private Color applyNegativeEffect(Color colorToChange) {
        double red = 1.0 - colorToChange.getRed();
        double green = 1.0 - colorToChange.getGreen();
        double blue = 1.0 - colorToChange.getBlue();
        double opacity = colorToChange.getOpacity();

        return new Color(red, green, blue, opacity);
    }

    @Override
    public void start(Stage stage) {

        // the example opens the image, creates a new image, and copies the opened image
        // into the new one, pixel by pixel
        Image sourceImage = new Image("file:monalisa.png");
        PixelReader imageReader = sourceImage.getPixelReader();

        int width = (int) sourceImage.getWidth();
        int height = (int) sourceImage.getHeight();
        int smolWidth = width/2;
        int smolHeight = height/2;
        
        WritableImage targetImage = new WritableImage(width, height);
        PixelWriter imageWriter = targetImage.getPixelWriter();
       
        for (int y = 0; y < smolHeight; y++){
            for (int x =0; x < smolWidth; x++){
                Color color = imageReader.getColor(x*2,y*2);
                
                Color negativeColor = applyNegativeEffect(color);
                
                imageWriter.setColor(x, y, negativeColor);
                imageWriter.setColor(x+smolWidth, y, negativeColor);
                imageWriter.setColor(x, (y+smolHeight), negativeColor);
                imageWriter.setColor((x+smolWidth), (y+smolHeight), negativeColor);
            }
        }
     
        
        ImageView image = new ImageView(targetImage);
        Pane pane = new Pane();
        pane.getChildren().add(image);
        
        stage.setScene(new Scene(pane));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
   

}
