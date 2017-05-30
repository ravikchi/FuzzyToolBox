package com.ravi.fuzzyToolBox.UserInterface;

import com.ravi.fuzzyToolBox.Examples.FuzzyPartitions;
import com.ravi.fuzzyToolBox.Examples.RulesInputs;
import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.Rules.Rules;
import com.ravi.fuzzyToolBox.Tnorm;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import javax.swing.text.html.HTMLDocument;
import java.util.*;


/**
 * Created by 611445924 on 29/05/2017.
 */
public class FXCanvas extends Application {
    Pane root;
    Scene scene;
    Canvas canvas;

    public void start(Stage primaryStage) throws Exception {
        final int width = 600;
        final int height = 600;
        final int margin = 100;

        root = new Pane();


        //root.getChildren().add(getCanvas(width, height, margin, 0, 0));
        Label spread1L = new Label("Spread for Input1");
        spread1L.setTranslateX(110);
        TextField spread1 = new TextField();
        spread1.setTranslateX(260);
        spread1.setText("0");
        spread1.setId("spread1T");

        Label spread2L = new Label("Spread for Input2");
        spread2L.setTranslateX(110);
        spread2L.setTranslateY(30);
        TextField spread2 = new TextField();
        spread2.setTranslateY(30);
        spread2.setTranslateX(260);
        spread2.setId("spread2T");
        spread2.setText("0");

        canvas = new Canvas(width, height);
        canvas.setId("canv");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        getCanvas(width, height, margin, Integer.parseInt(spread1.getText()), Integer.parseInt(spread2.getText()), gc);
        root.getChildren().add(canvas);


        root.getChildren().add(spread1L);
        root.getChildren().add(spread1);
        root.getChildren().add(spread2L);
        root.getChildren().add(spread2);

        //root.getChildren().add(new Line(0, margin,   width,   margin));
        //root.getChildren().add(new Line(margin, 0,   margin,   height));
        //addRectagles(width, height, margin,Integer.parseInt(spread1.getText()), Integer.parseInt(spread2.getText()), root);

        scene = new Scene(root);
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getSource() instanceof Canvas){
                    Canvas c = (Canvas) mouseEvent.getSource();
                    c.getGraphicsContext2D().clearRect(margin, margin, c.getWidth(), c.getHeight());
                    TextField tf = (TextField) c.getParent().lookup("#spread1T");
                    TextField tf1 = (TextField) c.getParent().lookup("#spread2T");
                    getCanvas(width, height, margin, Integer.parseInt(tf.getText()), Integer.parseInt(tf1.getText()), c.getGraphicsContext2D());
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Test");
        primaryStage.show();

    }

    private void addRectagles(int width, int height, int margin, int spread1, int spread2, Pane root){
        FuzzyPartitions partitions = new FuzzyPartitions();
        Rules rules = partitions.getRules(new RulesInputs(new FuzzySetImpl(spread1), new FuzzySetImpl(spread2)));
        FZOperation fzOperation = new Tnorm();
        int[][] counts = partitions.getCount(rules, fzOperation);

        this.printResults(counts);
        Map<Integer, List<String>> rectangles = this.findRectangles(counts);



        Iterator<Integer> it = rectangles.keySet().iterator();
        while(it.hasNext()){
            int value = it.next();
            List<String> coordinatesli = rectangles.get(value);
            for(String coordinates:coordinatesli) {
                String[] arr = coordinates.split(",");
                double x1 =Integer.parseInt(arr[0])*10+margin;
                double y1 = Integer.parseInt(arr[1])*10+margin;
                double x2 = Integer.parseInt(arr[2])*10+margin;
                double y2 = Integer.parseInt(arr[3])*10+margin;

                double w = Math.abs(x2 - x1);
                double h = Math.abs(y2 - y1);

                Rectangle rec = new Rectangle(x1, y1, w, h);
                Text text = new Text(value+"");
                text.setFontSmoothingType(FontSmoothingType.LCD);
                text.setX(x1+w/2);
                text.setY(y1+h/2);
                if(value == 1) {
                    rec.setFill(Color.WHITE);
                }else if(value == 2){
                    rec.setFill(Color.BLUE);
                }else if(value == 3){
                    rec.setFill(Color.AQUA);
                }else if(value == 4){
                    rec.setFill(Color.RED);
                }else if(value == 6){
                    rec.setFill(Color.BEIGE);
                }else if(value == 9){
                    rec.setFill(Color.MAGENTA);
                }

                root.getChildren().add(rec);
                root.getChildren().add(text);
            }
        }
    }

    private Canvas getCanvas(int width, int height, int margin, int spread1, int spread2, GraphicsContext gc){

        gc.setLineWidth(1.0);

        gc.strokeLine(margin, 0, margin, height);
        gc.strokeLine(0, margin, width, margin);

        FuzzyPartitions partitions = new FuzzyPartitions();
        Rules rules = partitions.getRules(new RulesInputs(new FuzzySetImpl(spread1), new FuzzySetImpl(spread2)));
        FZOperation fzOperation = new Tnorm();
        int[][] counts = partitions.getCount(rules, fzOperation);

        this.printResults(counts);
        Map<Integer, List<String>> rectangles = this.findRectangles(counts);



        Iterator<Integer> it = rectangles.keySet().iterator();
        while(it.hasNext()){
            int value = it.next();
            List<String> coordinatesli = rectangles.get(value);


            for(String coordinates:coordinatesli) {
                String[] arr = coordinates.split(",");
                double x1 =Integer.parseInt(arr[0])*10+100;
                double y1 = Integer.parseInt(arr[1])*10+100;
                double x2 = Integer.parseInt(arr[2])*10+100;
                double y2 = Integer.parseInt(arr[3])*10+100;

                double w = Math.abs(x2 - x1);
                double h = Math.abs(y2 - y1);

                setGcFill(value, gc);
                gc.fillRect(x1, y1, w, h);
                gc.setFill(Color.BLACK);
                gc.fillText(value+"", x1+w/2, y1+h/2);
            }
        }
        return canvas;
    }

    private void setGcFill(int value, GraphicsContext gc){
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
    }

    public Map<Integer, List<String>>  findRectangles(int[][] counts){
        int curNum = 1;
        Map<Integer, List<Triplet>> rowSE = new HashMap<Integer, List<Triplet>>();

        for (int i = 0; i <counts.length ; i++) {
            curNum = 0;
            int oldEnd = 0;
            for (int j = 0; j <counts[i].length ; j++) {
                int value = counts[i][j];
                if(counts[i][j] != curNum){
                    List<Triplet> endPoints = rowSE.get(curNum);
                    if(endPoints == null){
                        endPoints = new ArrayList<Triplet>();
                        rowSE.put(curNum, endPoints);
                    }
                    endPoints.add(new Triplet(i,oldEnd,j));

                    curNum = counts[i][j];
                    oldEnd = j;
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
                if(Integer.parseInt(arr[0])<0 || Integer.parseInt(arr[1])<0){
                    continue;
                }

                int startI = ll.get(0);
                if(ll.get(0) != 0){
                    startI = startI-1;
                }

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
                    prevI = ll.get(i);
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
