//Bryant Le
//Mrs.Kalathy
//Period 5

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ShapeSmasherApplication extends Application {

    private GraphicsContext gc;
    private int skulls, score;
    private long startTimeS, startTimeC, startTimeT;
    double x,y;
    double ranSqrX, ranSqrY, ranCirX, ranCirY, ranTriX, ranTriY;
    Image sk;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shape Smasher");
        Group root = new Group();
        Canvas canvas = new Canvas(500, 500);
        gc = canvas.getGraphicsContext2D();
        sk = new Image("skul.png");
        reset();

        new AnimationTimer(){
            @Override
            public void handle(long now)
            {
                long currentTime = System.nanoTime();

                long tS = ((currentTime - startTimeS)/1000000000);
                double tC = ((currentTime - startTimeC)/1000000000.0);
                long tT = ((currentTime - startTimeT)/1000000000);

                if(skulls < 3){
                    if(tT == 2.0){
                        resetTri();
                        skulls++;
                    }
                    if(tC > 2.4 && tC < 2.6){
                        resetCir();
                        skulls++;
                    }
                    if(tS == 3.0){
                        resetSqr();
                        skulls++;
                    }
                }
                draw(gc);
            }
        }.start();


        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = event.getX();
                y = event.getY();

                if(event.getButton() == MouseButton.PRIMARY && skulls != 3){
                    if(x >= ranSqrX && x <= ranSqrX+40 && y >= ranSqrY && y <= ranSqrY+40){
                    resetSqr();
                    score++;
                    }
                    else if(x >= ranCirX && x <= ranCirX+40 && y >= ranCirY && y <= ranCirY+40) {
                        resetCir();
                        score += 3;
                    }
                    else if(x >= ranTriX-20 && x <= ranTriX+20 && y >= ranTriY && y <= ranTriY+40){
                        resetTri();
                        score += 5;
                    }
                }
                else if(event.getButton() == MouseButton.SECONDARY && skulls == 3)
                    reset();

                draw(gc);
            }
        });

        draw(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    boolean running = true;

    private void reset() {
        skulls = 0;
        score = 0;
        resetSqr();
        resetCir();
        resetTri();
    }

    private void resetSqr(){
        ranSqrX = (int)(Math.random()*400+50);
        ranSqrY = (int)(Math.random()*400+50);
        startTimeS = System.nanoTime();
    }

    private void resetCir(){
        ranCirX = (int)(Math.random()*400+50);
        ranCirY = (int)(Math.random()*400+50);
        startTimeC = System.nanoTime();
    }

    private void resetTri(){
        ranTriX = (int)(Math.random()*400+50);
        ranTriY = (int)(Math.random()*400+50);
        startTimeT = System.nanoTime();
    }

    private void draw(GraphicsContext gc) {
        //background
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,500,500);

        gc.setStroke(Color.WHITE);
        gc.strokeText("Skulls",50,25);
        for(int i = 0; i < skulls; i++)
            gc.drawImage(sk,i*30+70,0);

        gc.strokeText("Score:",400,25);
        gc.strokeText(""+score, 435,25);

        //drawSqr
        gc.setFill(Color.GREEN);
        gc.fillRect(ranSqrX,ranSqrY,40,40);

        //drawCir
        gc.setFill(Color.BLUE);
        gc.fillOval(ranCirX,ranCirY,40,40);

        //drawTri
        gc.setFill(Color.RED);
        double[] xPoints = {ranTriX,ranTriX-20,ranTriX+20};
        double[] yPoints = {ranTriY,ranTriY+40,ranTriY+40};
        gc.fillPolygon(xPoints, yPoints,3);

        if(skulls == 3){
            gc.setStroke(Color.RED);
            gc.strokeText("Game Over :P",220,25);
        }
    }

    @Override
    public void stop() throws Exception{
        running = false;
        super.stop();
    }
}