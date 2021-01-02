package asteroids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.scene.text.Text;
import java.lang.Math; 

public class AsteroidsApplication extends Application {
    
    
    public static int WIDTH = 300;
    public static int HEIGHT = 200;

    @Override
    public void start(Stage stage) throws Exception {
        //Create pane
        Pane pane = new Pane();
        Text text = new Text(10, 20, "Points: 0");
        pane.getChildren().add(text);
        pane.setPrefSize(WIDTH, HEIGHT);
        
        AtomicInteger points = new AtomicInteger();
        
        //creating ship
        Ship ship = new Ship(WIDTH/2,HEIGHT/2);
        List<Projectile> projectiles = new ArrayList<>();
        
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH/3), rnd.nextInt(HEIGHT/3));
            asteroids.add(asteroid);
        }
        
        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
      
        
        //set scene
        Scene scene = new Scene(pane);
        stage.setTitle("Asteroids!");
        
        //ship controlls
        
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        Point2D movement = new Point2D(1, 0);
        
        
        
        new AnimationTimer() {

            
            @Override
            public void handle(long now) {
                
                
                
                if(pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }
                
                if(pressedKeys.getOrDefault(KeyCode.UP, false)){
                    ship.accelerate();
                }

                ship.move();
                asteroids.forEach(asteroid -> asteroid.move());
                projectiles.forEach(projectile -> projectile.move());
                
                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid)){
                        stop();
                    }
                });
                 
                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 3) {
                    // we shoot
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);

                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));

                    pane.getChildren().add(projectile.getCharacter());
                }
                
                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if(projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                    
                    if(!projectile.isAlive()){
                        text.setText("Points: "+points.addAndGet(1000));
                    }
                });

                projectiles.stream()
                    .filter(projectile -> !projectile.isAlive())
                    .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
                projectiles.removeAll(projectiles.stream()
                                        .filter(projectile -> !projectile.isAlive())
                                        .collect(Collectors.toList()));

                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                asteroids.removeAll(asteroids.stream()
                                            .filter(asteroid -> !asteroid.isAlive())
                                            .collect(Collectors.toList()));
                
                
            if(Math.random() < 0.005) {
                Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
                if(!asteroid.collide(ship)) {
                    asteroids.add(asteroid);
                    pane.getChildren().add(asteroid.getCharacter());
                }
            }    
            }

        }.start();

        // show scene
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static int partsCompleted() {
        // State how many parts you have completed using the return value of this method
        return 4;
    }
}
