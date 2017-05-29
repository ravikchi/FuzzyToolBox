package com.ravi.fuzzyToolBox.UserInterface;

import com.ravi.fuzzyToolBox.Examples.FuzzyPartitions;
import com.ravi.fuzzyToolBox.Examples.RulesInputs;
import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.Rules.Rules;
import com.ravi.fuzzyToolBox.Tnorm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Map;


/**
 * Created by 611445924 on 29/05/2017.
 */
public class FXCanvas extends Application {
    public void start(Stage primaryStage) throws Exception {
        int width = 1000;
        int height = 800;
        int margin = 100;
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setLineWidth(1.0);

        gc.strokeLine(margin, 0, margin, height);
        gc.strokeLine(0, height-margin, width, height-margin);

        FuzzyPartitions partitions = new FuzzyPartitions();
        Rules rules = partitions.getRules(new RulesInputs());
        FZOperation fzOperation = new Tnorm();
        int[][] counts = partitions.getCount(rules, fzOperation);

        this.printResults(counts);




        Pane root = new Pane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Test");
        primaryStage.show();

    }

    public void printResults(int[][] counts){


        for(int i=50; i>=0; i--) {
            String val = "";
            if(i!=0){
                val = String.format("%2d",i);
                System.out.print(val+" | ");
            }else{
                System.out.print("----");
                val = "  ";

            }
            for (int x = 1; x <= 50; x++) {
                //System.out.print(i+","+x+",");
                if(i!=0){
                    System.out.print(counts[i][x] + " ");
                }else {
                    System.out.print("--");
                }
            }

            System.out.println();
            if(i==0) {
                System.out.println("   |");
            }
        }


        /*for(int i=50; i>=0; i--) {
            for (int x = 1; x <= 50; x++) {
                System.out.println(i+","+x+","+counts[i][x]);
            }
        }*/
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
