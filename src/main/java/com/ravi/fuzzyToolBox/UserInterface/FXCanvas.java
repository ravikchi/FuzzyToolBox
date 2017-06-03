package com.ravi.fuzzyToolBox.UserInterface;

import com.ravi.fuzzyToolBox.Examples.FuzzyPartitions;
import com.ravi.fuzzyToolBox.Examples.RulesInputs;
import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
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
        final int height = 700;
        final int margin = 100;

        root = new Pane();


        //root.getChildren().add(getCanvas(width, height, margin, 0, 0));
        Label spread1L = new Label("Spread of Input FS 1");
        spread1L.setTranslateX(110);
        TextField spread1 = new TextField();
        spread1.setTranslateX(260);
        spread1.setText("0");
        spread1.setId("spread1T");

        Label spread2L = new Label("Spread of Input FS 2");
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
                    TextField tf = (TextField) c.getParent().lookup("#spread1T");
                    TextField tf1 = (TextField) c.getParent().lookup("#spread2T");
                    int tfint = 0;
                    int tf1int = 0;
                    try{

                        tfint = Integer.parseInt(tf.getText());
                        tf1int = Integer.parseInt(tf1.getText());
                        c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
                        getCanvas(width, height, margin, tfint, tf1int, c.getGraphicsContext2D());

                    }catch(NumberFormatException nfe){
                        c.getGraphicsContext2D().setFill(Color.RED);
                        c.getGraphicsContext2D().fillText("Input should be a number", margin, margin-10);
                    }
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Test");
        primaryStage.show();

    }

    private RulesInputs getCanvas(int width, int height, int margin, int spread1, int spread2, GraphicsContext gc){

        gc.setLineWidth(1.0);

        gc.strokeLine(margin, 0, margin, height);
        gc.strokeLine(0, height-margin, width, height-margin);

        FuzzyPartitions partitions = new FuzzyPartitions();
        RulesInputs rulesInputs = new RulesInputs(new FuzzySetImpl(spread1), new FuzzySetImpl(spread2));
        Rules rules = partitions.getRules(rulesInputs);
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

        gc.setFill(Color.BLUE);
        drawMemFuncX(gc, rulesInputs.getLow(), margin, height);
        gc.setFill(Color.GREEN);
        drawMemFuncX(gc, rulesInputs.getMid(), margin, height);
        gc.setFill(Color.YELLOW);
        drawMemFuncX(gc, rulesInputs.getHigh(), margin, height);

        gc.setFill(Color.BLUE);
        drawMemFuncY(gc, rulesInputs.getLow(), margin, width);
        gc.setFill(Color.GREEN);
        drawMemFuncY(gc, rulesInputs.getMid(), margin, width);
        gc.setFill(Color.YELLOW);
        drawMemFuncY(gc, rulesInputs.getHigh(), margin, width);

        if(rulesInputs.getLow().getTop2() != rulesInputs.getMid().getStart()) {
            gc.strokeLine(rulesInputs.getLow().getTop2() * 10 + margin, margin, rulesInputs.getLow().getTop2() * 10 + margin, height - margin);
            gc.strokeLine(margin, rulesInputs.getLow().getTop2()*10+margin, width, rulesInputs.getLow().getTop2()*10+margin);
        }

        if(rulesInputs.getMid().getTop1() != rulesInputs.getLow().getEnd()){
            gc.strokeLine(rulesInputs.getMid().getTop1() * 10 + margin, margin, rulesInputs.getMid().getTop1() * 10 + margin, height - margin);
            gc.strokeLine(margin, rulesInputs.getMid().getTop1()*10+margin, width, rulesInputs.getMid().getTop1()*10+margin);
        }

        if(rulesInputs.getMid().getTop2() != rulesInputs.getHigh().getStart()){
            gc.strokeLine(rulesInputs.getMid().getTop2() * 10 + margin, margin, rulesInputs.getMid().getTop2() * 10 + margin, height - margin);
            gc.strokeLine(margin, rulesInputs.getMid().getTop2()*10+margin, width, rulesInputs.getMid().getTop2()*10+margin);
        }

        if(rulesInputs.getHigh().getTop1() != rulesInputs.getMid().getEnd()){
            gc.strokeLine(rulesInputs.getHigh().getTop1() * 10 + margin, margin, rulesInputs.getHigh().getTop1() * 10 + margin, height - margin);
            gc.strokeLine(margin, rulesInputs.getHigh().getTop1()*10+margin, width, rulesInputs.getHigh().getTop1()*10+margin);
        }

        return rulesInputs;
    }

    private void drawMemFuncX(GraphicsContext gc, MemFunc memFunc, int margin, int height){
        gc.strokeLine(memFunc.getStart()*10+margin, height-margin, memFunc.getTop1()*10+margin, height-margin/2);
        //gc.strokeLine(memFunc.getTop1()*10+margin, margin, memFunc.getTop1()*10+margin, height-margin);
        gc.strokeLine(memFunc.getTop1()*10+margin, height-margin/2, memFunc.getTop2()*10+margin,height-margin/2);
        //gc.strokeLine(memFunc.getTop2()*10+margin, margin, memFunc.getTop2()*10+margin, height-margin);
        gc.strokeLine(memFunc.getTop2()*10+margin, height-margin/2, memFunc.getEnd()*10+margin,height-margin);
    }

    private void drawMemFuncY(GraphicsContext gc, MemFunc memFunc, int margin, int width){
        gc.strokeLine(margin, memFunc.getStart()*10+margin, margin/2, memFunc.getTop1()*10+margin);
        //gc.strokeLine(margin, memFunc.getTop1()*10+margin, width, memFunc.getTop1()*10+margin);
        gc.strokeLine(margin/2, memFunc.getTop1()*10+margin, margin/2, memFunc.getTop2()*10+margin);
        //gc.strokeLine(margin, memFunc.getTop2()*10+margin, width, memFunc.getTop2()*10+margin);
        gc.strokeLine(margin/2, memFunc.getTop2()*10+margin, margin, memFunc.getEnd()*10+margin);
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
                    oldEnd = j-1;
                    if(oldEnd == -1){
                        oldEnd = 0;
                    }
                }
            }
            List<Triplet> endPoints = rowSE.get(curNum);
            if(endPoints == null){
                endPoints = new ArrayList<Triplet>();
                rowSE.put(curNum, endPoints);
            }
            endPoints.add(new Triplet(i,oldEnd,counts[i].length));

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

                int startI = ll.get(0)-1;
                if(startI < 0){
                    startI = 0;
                }

                int prevI = ll.get(0);
                for (int i = 1; i < ll.size(); i++) {
                    int rowNum = ll.get(i);
                    if(prevI+1!= rowNum){
                        System.out.println("coordinates for "+k+" : ("+arr[0]+", "+startI+") and ("+arr[1]+", "+prevI+")");
                        List<String> coords = output.get(k);
                        if(coords == null){
                            coords = new ArrayList<String>();
                            output.put(k, coords);
                        }

                        coords.add(arr[0]+","+startI+","+arr[1]+","+(prevI+1));
                        startI = rowNum-1;
                        if(startI < 0){
                            startI = 0;
                        }
                    }
                    prevI = rowNum;
                }
                System.out.println("coordinates for "+k+" : ("+arr[0]+", "+startI+") and ("+arr[1]+", "+(prevI+1)+")");
                List<String> coords = output.get(k);
                if(coords == null){
                    coords = new ArrayList<String>();
                    output.put(k, coords);
                }

                coords.add(arr[0]+","+startI+","+arr[1]+","+(prevI+1));
            }
        }
        return output;
    }

    public void printResults(int[][] counts){


        for(int i=49; i>=0; i--) {
            String val = "";
            if(i!=0){
                val = String.format("%2d",i);
                System.out.print(val+" | ");
            }else{
                System.out.print("----");
                val = "  ";

            }
            for (int x = 1; x < 50; x++) {
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


        /*for(int i=49; i>=0; i--) {
            for (int x = 1; x < 50; x++) {
                System.out.println(i+","+x+","+counts[i][x]);
            }
        }*/
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
