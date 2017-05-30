package com.ravi.fuzzyToolBox.UserInterface;

import com.ravi.fuzzyToolBox.Examples.FuzzyPartitions;
import com.ravi.fuzzyToolBox.Examples.RulesInputs;
import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.Rules.Rules;
import com.ravi.fuzzyToolBox.Tnorm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.text.html.HTMLDocument;
import java.util.*;


/**
 * Created by 611445924 on 29/05/2017.
 */
public class FXCanvas extends Application {
    public void start(Stage primaryStage) throws Exception {
        int width = 600;
        int height = 600;
        int margin = 100;
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setLineWidth(1.0);

        gc.strokeLine(margin, 0, margin, height);
        gc.strokeLine(0, height-margin, width, height-margin);

        FuzzyPartitions partitions = new FuzzyPartitions();
        Rules rules = partitions.getRules(new RulesInputs(new FuzzySetImpl(0), new FuzzySetImpl(0)));
        FZOperation fzOperation = new Tnorm();
        int[][] counts = partitions.getCount(rules, fzOperation);

        this.printResults(counts);
        Map<Integer, List<String>> rectangles = this.findRectangles(counts);



        Iterator<Integer> it = rectangles.keySet().iterator();
        while(it.hasNext()){
            int value = it.next();
            List<String> coordinatesli = rectangles.get(value);
            if(value == 1) {
                gc.setFill(Color.WHITE);
            }else if(value == 2){
                gc.setFill(Color.BLUE);
            }else if(value == 3){
                gc.setFill(Color.AQUA);
            }else if(value == 4){
                gc.setFill(Color.RED);
            }else if(value == 6){
                gc.setFill(Color.BEIGE);
            }else if(value == 9){
                gc.setFill(Color.MAGENTA);
            }

            for(String coordinates:coordinatesli) {
                String[] arr = coordinates.split(",");
                double x1 =Integer.parseInt(arr[0])*10+100;
                double y1 = Integer.parseInt(arr[1])*10;
                double x2 = Integer.parseInt(arr[2])*10+100;
                double y2 = Integer.parseInt(arr[3])*10;

                double w = Math.abs(x2 - x1);
                double h = Math.abs(y2 - y1);

                gc.fillRect(x1, y1, w, h);
            }
        }

        Pane root = new Pane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Test");
        primaryStage.show();

    }

    public Map<Integer, List<String>>  findRectangles(int[][] counts){
        int curNum = 0;
        Map<Integer, List<Triplet>> rowSE = new HashMap<Integer, List<Triplet>>();

        for (int i = 0; i <counts.length ; i++) {
            curNum = 0;
            int oldEnd = 0;
            for (int j = 0; j <counts[i].length ; j++) {
                if(counts[i][j] != curNum){
                    List<Triplet> endPoints = rowSE.get(curNum);
                    if(endPoints == null){
                        endPoints = new ArrayList<Triplet>();
                        rowSE.put(curNum, endPoints);
                    }
                    endPoints.add(new Triplet(i,oldEnd,(j-1) ));

                    curNum = counts[i][j];
                    oldEnd = j-1;
                }
            }
            List<Triplet> endPoints = rowSE.get(curNum);
            if(endPoints == null){
                endPoints = new ArrayList<Triplet>();
                rowSE.put(curNum, endPoints);
            }
            endPoints.add(new Triplet(i,oldEnd,counts[i].length-1));

        }

        Map<Integer, List<String>> output = new HashMap<Integer, List<String>>();

        Iterator<Integer> it = rowSE.keySet().iterator();
        while(it.hasNext()){
            int k = it.next();
            if(k==0){
                continue;
            }
            Map<String, List<Integer>> listMap = new HashMap<String, List<Integer>>();

            List<Triplet> points = rowSE.get(k);
            for(Triplet t : points){
                List<Integer> ll = listMap.get(t.getStart()+"~"+t.getEnd());
                if(ll == null){
                    ll = new ArrayList<Integer>();
                    listMap.put(t.getStart()+"~"+t.getEnd(), ll);
                }

                ll.add(t.getRow());
            }

            Iterator<String> itr  = listMap.keySet().iterator();
            while (itr.hasNext()){
                String coordinates = itr.next();
                List<Integer> ll = listMap.get(coordinates);
                Collections.sort(ll);
                String[] arr = coordinates.split("~");
                if(Integer.parseInt(arr[0])<0 || Integer.parseInt(arr[1])<1){
                    continue;
                }

                int startI = ll.get(0)-1;
                int prevI = ll.get(0);
                for (int i = 1; i < ll.size(); i++) {
                    if(prevI+1!=ll.get(i)){
                        System.out.println("coordinates for "+k+" : ("+arr[0]+", "+startI+") and ("+arr[1]+", "+prevI+")");
                        List<String> coords = output.get(k);
                        if(coords == null){
                            coords = new ArrayList<String>();
                            output.put(k, coords);
                        }

                        coords.add(arr[0]+","+startI+","+arr[1]+","+prevI);
                        startI = ll.get(i)-1;


                    }
                    prevI = ll.get(i)+1;
                }
                System.out.println("coordinates for "+k+" : ("+arr[0]+", "+startI+") and ("+arr[1]+", "+prevI+")");
                List<String> coords = output.get(k);
                if(coords == null){
                    coords = new ArrayList<String>();
                    output.put(k, coords);
                }

                coords.add(arr[0]+","+startI+","+arr[1]+","+prevI);
            }
        }
        return output;
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
                    String v = String.format("%2d",counts[i][x]);
                    System.out.print(v + " ");
                }else {
                    System.out.print("--");
                }
            }

            System.out.println();
            if(i==0) {
                System.out.print("   | ");
                for (int x = 1; x <= 50; x++) {
                    String v = String.format("%2d",x);
                    System.out.print(v + " ");
                }
                System.out.println();
            }
        }


        for(int i=50; i>=0; i--) {
            for (int x = 1; x <= 50; x++) {
                System.out.println(i+","+x+","+counts[i][x]);
            }
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
