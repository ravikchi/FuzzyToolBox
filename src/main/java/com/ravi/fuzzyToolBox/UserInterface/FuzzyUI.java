package com.ravi.fuzzyToolBox.UserInterface;

import com.ravi.fuzzyToolBox.Examples.FuzzyPartitions;
import com.ravi.fuzzyToolBox.Examples.NoveltyPartRules;
import com.ravi.fuzzyToolBox.Examples.RulesInputs;
import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;
import com.ravi.fuzzyToolBox.ProductTnorm;
import com.ravi.fuzzyToolBox.Rules.Rules;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.*;

/**
 * Created by 611445924 on 11/06/2017.
 */
public class FuzzyUI extends Application {

    private Pane root;
    FLC flc;
    Scene scene;
    Scale scale;
    Canvas canvas;
    int canvasWidth = 800;
    int canvasHeight = 700;
    int canvasMargin = 100;

    List<FuzzySet> inputs = new ArrayList<FuzzySet>();

    Canvas lowerNoveltyCanvas;
    Canvas upperNoveltyCanvas;

    double zeroValue=0.0;

    Map<Integer, List<String>> rectangles;

    public FuzzyUI() {
        root = new Pane();

        canvas = new Canvas(canvasWidth+canvasMargin, canvasHeight+canvasMargin);
        canvas.setId("canv");

        lowerNoveltyCanvas = new Canvas(canvasWidth+canvasMargin, canvasHeight+canvasMargin);
        lowerNoveltyCanvas.setId("novCanv");

        upperNoveltyCanvas = new Canvas(canvasWidth+canvasMargin, canvasHeight+canvasMargin);

        /*scale = new Scale();
        scale.setX(1);
        scale.setX(-1);

        canvas.getTransforms().add(scale);*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        double spread1 = 0.0;
        double spread2 = 0.0;

        String type = "1";

        getData(spread1, spread2, type);

        prepareCanvas(0, 0);

        drawNoveltyPartitions(0, 0, flc.getLowerNoveltyCounts(), lowerNoveltyCanvas);
        drawNoveltyPartitions(0, 0, flc.getUpperNoveltyCounts(), upperNoveltyCanvas);

        EventHandler event = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getSource() instanceof Canvas){
                    Canvas c = (Canvas) mouseEvent.getSource();
                    TextField tf = (TextField) c.getParent().getParent().getParent().lookup("#spread1T");
                    TextField tf1 = (TextField) c.getParent().getParent().getParent().lookup("#spread2T");
                    TextField flcTypeVal = (TextField) c.getParent().getParent().getParent().lookup("#flcTypeVal");
                    double tfint = 0;
                    double tf1int = 0;

                    try{
                        tfint = Double.parseDouble(tf.getText());
                        tf1int = Double.parseDouble(tf1.getText());

                        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        getData(tfint, tf1int, flcTypeVal.getText());
                        prepareCanvas(0, 0);

                        lowerNoveltyCanvas.getGraphicsContext2D().clearRect(0,0, lowerNoveltyCanvas.getWidth(), lowerNoveltyCanvas.getWidth());
                        upperNoveltyCanvas.getGraphicsContext2D().clearRect(0,0, upperNoveltyCanvas.getWidth(), upperNoveltyCanvas.getWidth());
                        drawNoveltyPartitions(0, 0, flc.getLowerNoveltyCounts(), lowerNoveltyCanvas);
                        drawNoveltyPartitions(0, 0, flc.getUpperNoveltyCounts(), upperNoveltyCanvas);

                    }catch(NumberFormatException nfe){
                        c.getGraphicsContext2D().setFill(Color.RED);
                        c.getGraphicsContext2D().fillText("Input should be a number", canvasMargin, canvasMargin-10);
                    }
                }
            }
        };

        canvas.setOnMouseClicked(event);
        lowerNoveltyCanvas.setOnMouseClicked(event);
        upperNoveltyCanvas.setOnMouseClicked(event);

        TabPane pane = new TabPane();
        Tab tab1 = new Tab();
        tab1.setText("RulePartitions");
        tab1.setContent(canvas);

        Tab tab2 = new Tab();
        tab2.setText("LowerNoveltyPartions");
        tab2.setContent(lowerNoveltyCanvas);

        Tab tab3 = new Tab();
        tab3.setText("Upper Novelty Partitions");
        tab3.setContent(upperNoveltyCanvas);

        pane.getTabs().add(tab1);
        pane.getTabs().add(tab2);
        pane.getTabs().add(tab3);

        root.getChildren().add(pane);

        //root.getChildren().add(canvas);
        root.getChildren().add(textBoxes(spread1, spread2, type));
/*
        root.getChildren().add(lowerNoveltyCanvas);
        root.getChildren().add(upperNoveltyCanvas);*/

        scene = new Scene(root, canvasWidth+canvasMargin+300, canvasHeight+canvasMargin);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Test");
        primaryStage.show();
    }

    private Group textBoxes(double spr1, double spr2, String type){
        Group grp = new Group();
        grp.setTranslateX(canvasWidth+canvasMargin);


        int moveDown = 30;
        int moveRight = 140;
        Group row1 = new Group();
        Label title = new Label("Parameters");
        row1.getChildren().add(title);
        row1.setTranslateY(moveDown);
        moveDown = moveDown + 30;

        Group row2 = new Group();
        Label spread1L = new Label("Spread of Input FS 1");
        TextField spread1 = new TextField();
        spread1.setTranslateX(moveRight);
        spread1.setText(spr1+"");
        spread1.setId("spread1T");
        row2.getChildren().addAll(spread1, spread1L);
        row2.setTranslateY(moveDown);
        moveDown = moveDown + 30;

        Group row3 = new Group();
        Label spread2L = new Label("Spread of Input FS 2");
        TextField spread2 = new TextField();
        spread2.setTranslateX(moveRight);
        spread2.setId("spread2T");
        spread2.setText(spr2+"");
        row3.getChildren().addAll(spread2, spread2L);
        row3.setTranslateY(moveDown);
        moveDown = moveDown + 30;

        Group row4 = new Group();
        Label flcType = new Label("FLC Type");
        TextField flcTypeVal = new TextField();
        flcTypeVal.setTranslateX(moveRight);
        flcTypeVal.setId("flcTypeVal");
        flcTypeVal.setText(type);
        row4.getChildren().addAll(flcType, flcTypeVal);
        row4.setTranslateY(moveDown);

        grp.getChildren().add(row1);
        grp.getChildren().add(row2);
        grp.getChildren().add(row3);
        grp.getChildren().add(row4);

        return grp;
    }

    private void prepareCanvas(int startx, int starty){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setLineWidth(1.0);

        gc.strokeLine(startx + canvasMargin, starty, startx+canvasMargin, starty+canvasHeight);
        gc.strokeLine(startx, starty+canvasMargin, startx+canvasWidth, starty+canvasMargin);

        drawRectangles(startx, starty, gc, true);

        drawMemFuncLines(gc, startx, starty);

        drawMeasurementLines(startx, starty, gc);
        //drawRulePartitions(startx, starty, gc);

    }

    private void drawNoveltyPartitions(int startx, int starty, int[][] counts, Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setLineWidth(1.0);

        gc.strokeLine(startx + canvasMargin, starty, startx+canvasMargin, starty+canvasHeight);
        gc.strokeLine(startx, starty+canvasMargin, startx+canvasWidth, starty+canvasMargin);

        for (int i = 0; i < counts.length; i++) {
            for (int j = 0; j < counts[i].length; j++) {
                int value = counts[i][j];
                setGcFill(value, gc);
                gc.fillRect(canvasMargin+i*getScaleX(), canvasMargin+j*getScaleY(), getScaleX(), getScaleY());
            }
        }

        drawRectangles(startx, starty, gc, false);

        drawMemFuncLines(gc, startx, starty, false);

        drawMeasurementLines(startx, starty, gc);
    }

    private void drawSecondaryPartitions(MemFunc low, MemFunc mid, GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        if(low.getTop2() != mid.getStart()) {
            gc.strokeLine(low.getTop2() * getScaleX() + canvasMargin, canvasMargin, low.getTop2() * getScaleX() + canvasMargin, canvasHeight);
            gc.strokeLine(canvasMargin,low.getTop2()*getScaleY()+canvasMargin, canvasWidth, low.getTop2()*getScaleY()+canvasMargin);
            if(!mid.isUpper()) {
                gc.strokeLine(mid.getStart() * getScaleX() + canvasMargin, canvasMargin, mid.getStart() * getScaleX() + canvasMargin, canvasHeight);
                gc.strokeLine(canvasMargin, mid.getStart() * getScaleY() + canvasMargin, canvasWidth, mid.getStart() * getScaleY() + canvasMargin);
            }

        }

        if(mid.getTop1() != low.getEnd()){
            gc.strokeLine(mid.getTop1() * getScaleX() + canvasMargin, canvasMargin, mid.getTop1() * getScaleX() + canvasMargin, canvasHeight);
            gc.strokeLine(canvasMargin, mid.getTop1()*getScaleY()+canvasMargin, canvasWidth, mid.getTop1()*getScaleY()+canvasMargin);
            if(!low.isUpper()) {
                gc.strokeLine(low.getEnd() * getScaleX() + canvasMargin, canvasMargin, low.getEnd() * getScaleX() + canvasMargin, canvasHeight);
                gc.strokeLine(canvasMargin, low.getEnd()*getScaleY()+canvasMargin, canvasWidth, low.getEnd()*getScaleY()+canvasMargin);
            }
        }
    }

    private void drawXInputLines(GraphicsContext gc, int startx, int starty,FuzzySet fuzzySet, MemFunc memFunc){
        double sizex = canvasWidth - canvasMargin;

        double scalex = sizex/(flc.getEndi()-flc.getIncrementi()-flc.getStarti());


        double start = (memFunc.getStart()+zeroValue)*scalex;
        double top1 = (memFunc.getStart()-fuzzySet.getSpread()+zeroValue)*scalex;
        double top2 = (memFunc.getStart()-fuzzySet.getSpread()+zeroValue)*scalex;
        double end = (memFunc.getStart()-fuzzySet.getSpread()*2+zeroValue)*scalex;

        gc.strokeLine(startx+start+canvasMargin, starty+canvasMargin, startx+top1+canvasMargin, starty+canvasMargin/2);
        gc.strokeLine(startx+top1+canvasMargin, starty+canvasMargin/2, startx+top2+canvasMargin, starty+canvasMargin/2);
        gc.strokeLine(startx+top2+canvasMargin, starty+canvasMargin/2, startx+end+canvasMargin, starty+canvasMargin);

    }

    private void drawYInputLines(GraphicsContext gc, int startx, int starty,FuzzySet fuzzySet, MemFunc memFunc){
        double sizey = canvasHeight - canvasMargin;

        double scaley = sizey/(flc.getEndj()-flc.getIncrementj()-flc.getStartj());


        double start = (memFunc.getStart()+zeroValue)*scaley;
        double top1 = (memFunc.getStart()-fuzzySet.getSpread()+zeroValue)*scaley;
        double top2 = (memFunc.getStart()-fuzzySet.getSpread()+zeroValue)*scaley;
        double end = (memFunc.getStart()-fuzzySet.getSpread()*2+zeroValue)*scaley;

        gc.strokeLine(startx+canvasMargin, starty+start+canvasMargin, startx+canvasMargin/2, starty+top1+canvasMargin);
        gc.strokeLine(startx+canvasMargin/2, starty+top1+canvasMargin, startx+canvasMargin/2, starty+top2+canvasMargin);
        gc.strokeLine(startx+canvasMargin/2, starty+top2+canvasMargin, startx+canvasMargin, starty+end+canvasMargin);

    }

    private void drawXLines(GraphicsContext gc, int startx, int starty, MemFunc mc){
        double sizex = canvasWidth - canvasMargin;

        double scalex = sizex/(flc.getEndi()-flc.getIncrementi()-flc.getStarti());

        double start = (mc.getStart()+zeroValue)*scalex;
        double top1 = (mc.getTop1()+zeroValue)*scalex;
        double top2 = (mc.getTop2()+zeroValue)*scalex;
        double end = (mc.getEnd()+zeroValue)*scalex;

        gc.strokeLine(startx+start+canvasMargin, starty+canvasMargin, startx+top1+canvasMargin, starty+canvasMargin/2);
        gc.strokeLine(startx+top1+canvasMargin, starty+canvasMargin/2, startx+top2+canvasMargin, starty+canvasMargin/2);
        gc.strokeLine(startx+top2+canvasMargin, starty+canvasMargin/2, startx+end+canvasMargin, starty+canvasMargin);
    }

    private void drawYLines(GraphicsContext gc, int startx, int starty, MemFunc mc){
        double sizey = canvasHeight - canvasMargin;

        double scaley = sizey/(flc.getEndj()-flc.getIncrementj()-flc.getStartj());

        double start = (mc.getStart()+zeroValue)*scaley;
        double top1 = (mc.getTop1()+zeroValue)*scaley;
        double top2 = (mc.getTop2()+zeroValue)*scaley;
        double end = (mc.getEnd()+zeroValue)*scaley;

        gc.strokeLine(startx+canvasMargin, starty+start+canvasMargin, startx+canvasMargin/2, starty+top1+canvasMargin);
        gc.strokeLine(startx+canvasMargin/2, starty+top1+canvasMargin, startx+canvasMargin/2, starty+top2+canvasMargin);
        gc.strokeLine(startx+canvasMargin/2, starty+top2+canvasMargin, startx+canvasMargin, starty+end+canvasMargin);
    }

    private void setStokeColor(int i, GraphicsContext gc){
        switch (i){
            case 0: gc.setStroke(Color.RED); break;
            case 1: gc.setStroke(Color.BLUE); break;
            case 2: gc.setStroke(Color.GREEN); break;
            default: gc.setStroke(Color.BLACK); break;
         }
    }

    private void drawMemFuncLines(GraphicsContext gc, int startx, int starty){
        for (int i = 0; i < flc.getInputILowerMemFunc().size(); i++) {
            setStokeColor(i, gc);
            drawXLines(gc, startx, starty, flc.getInputILowerMemFunc().get(i));

            drawXLines(gc, startx, starty, flc.getInputIUpperMemFunc().get(i));

            if(i>0){
                drawSecondaryPartitions(flc.getInputILowerMemFunc().get(i-1), flc.getInputILowerMemFunc().get(i), gc);
                drawSecondaryPartitions(flc.getInputIUpperMemFunc().get(i-1), flc.getInputIUpperMemFunc().get(i), gc);
            }
        }


        for (int i = 0; i < flc.getInputJLowerMemFunc().size(); i++) {
            setStokeColor(i, gc);
            drawYLines(gc, startx, starty, flc.getInputJLowerMemFunc().get(i));

            drawYLines(gc, startx, starty, flc.getInputJUpperMemFunc().get(i));

            if(i>0){
                drawSecondaryPartitions(flc.getInputJLowerMemFunc().get(i-1), flc.getInputJLowerMemFunc().get(i), gc);
                drawSecondaryPartitions(flc.getInputJUpperMemFunc().get(i-1), flc.getInputJUpperMemFunc().get(i), gc);
            }
        }

        drawXInputLines(gc, startx, starty, inputs.get(1), flc.getInputIUpperMemFunc().get(2));
        drawYInputLines(gc, startx, starty, inputs.get(0), flc.getInputJUpperMemFunc().get(2));


        gc.setStroke(Color.BLACK);
    }

    private void drawMemFuncLines(GraphicsContext gc, int startx, int starty, boolean secPartitions){
        for (int i = 0; i < flc.getInputILowerMemFunc().size(); i++) {
            setStokeColor(i, gc);
            drawXLines(gc, startx, starty, flc.getInputILowerMemFunc().get(i));

            drawXLines(gc, startx, starty, flc.getInputIUpperMemFunc().get(i));

            if(i>0 && secPartitions){
                drawSecondaryPartitions(flc.getInputILowerMemFunc().get(i-1), flc.getInputILowerMemFunc().get(i), gc);
                drawSecondaryPartitions(flc.getInputIUpperMemFunc().get(i-1), flc.getInputIUpperMemFunc().get(i), gc);
            }
        }


        for (int i = 0; i < flc.getInputJLowerMemFunc().size(); i++) {
            setStokeColor(i, gc);
            drawYLines(gc, startx, starty, flc.getInputJLowerMemFunc().get(i));

            drawYLines(gc, startx, starty, flc.getInputJUpperMemFunc().get(i));

            if(i>0 && secPartitions){
                drawSecondaryPartitions(flc.getInputJLowerMemFunc().get(i-1), flc.getInputJLowerMemFunc().get(i), gc);
                drawSecondaryPartitions(flc.getInputJUpperMemFunc().get(i-1), flc.getInputJUpperMemFunc().get(i), gc);
            }
        }

        gc.setStroke(Color.BLACK);
    }

    private void drawMeasurementLines(int startx, int starty,GraphicsContext gc){
        double sizex = canvasWidth - canvasMargin;

        double scalex = sizex/(flc.getEndi()-flc.getIncrementi()-flc.getStarti());

        for (double i=flc.getStarti(); i<flc.getEndi(); i=i+flc.getIncrementi()*5) {
            String val = String.format("%.0f",i);
            gc.strokeLine((i*getScaleX())+startx+canvasMargin, canvasMargin, (i*getScaleX())+canvasMargin, canvasMargin-10);
            gc.fillText(val,(i*getScaleX())+canvasMargin-5, canvasMargin-20);
        }

        for (double i=flc.getStartj(); i<flc.getEndj(); i=i+flc.getIncrementj()*5) {
            String val = String.format("%.0f",i);
            gc.strokeLine(canvasMargin-10, canvasMargin+i*getScaleY(), canvasMargin, canvasMargin+i*getScaleY());
            gc.fillText(val,canvasMargin-25,canvasMargin+i*getScaleY()+5);
        }

    }

    private double getScaleX(){
        double sizex = canvasWidth - canvasMargin;

        return sizex/flc.getCounts()[0].length;
        //return sizex/(flc.getEndi()-flc.getStarti());
    }

    private double getScaleY(){
        double sizey = canvasHeight - canvasMargin;

        return sizey/flc.getCounts().length;
        //return sizey/(flc.getEndi()-flc.getStarti());
    }

    private void drawRectangles(int startx, int starty, GraphicsContext gc, boolean fill){
        rectangles = findRectangles(flc.getCounts());

        Iterator<Integer> it = rectangles.keySet().iterator();
        while(it.hasNext()){
            int value = it.next();
            List<String> coordinatesli = rectangles.get(value);

            for(String coordinates:coordinatesli) {
                String[] arr = coordinates.split(",");
                double x1 =Integer.parseInt(arr[0])*getScaleX()+startx+canvasMargin;
                double y1 = Integer.parseInt(arr[1])*getScaleY()+starty+canvasMargin;
                double x2 = Integer.parseInt(arr[2])*getScaleX()+startx+canvasMargin;
                double y2 = Integer.parseInt(arr[3])*getScaleY()+starty+canvasMargin;

                double w = Math.abs(x2 - x1);
                double h = Math.abs(y2 - y1);

                if(fill) {
                    setGcFill(value, gc);
                    gc.fillRect(x1, y1, w, h);
                    gc.setFill(Color.BLACK);
                    gc.fillText(value + "", x1 + w / 2, y1 + h / 2);
                }else{
                    gc.setStroke(Color.BLACK);
                    gc.strokeRect(x1, y1, w, h);
                    gc.setFill(Color.BLACK);
                    gc.fillText(value + "", x1 + w / 2, y1 + h / 2);
                }
            }
        }
    }

    private void setGcFill(int value, GraphicsContext gc){
        if(value == 0) {
            gc.setFill(Color.WHITE);
        }else if(value == 1) {
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

    /*public Map<Integer, List<String>> findRectangles(int[][] counts){
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
            endPoints.add(new Triplet(i,oldEnd,counts[i].length+1));

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
                        startI = rowNum;
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
*/
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

                int startI = ll.get(0);
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
                        startI = rowNum;
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
    private void drawRulePartitions(int startx, int starty, GraphicsContext gc){
        Set<Double> switches = flc.calculateRulePartitionsi();

        double scaley = getScaleY();
        double scalex = getScaleX();

        double zeroValues = 0;

        Iterator<Double> it = switches.iterator();
        while (it.hasNext()){
            double val = it.next();
            val = val + zeroValues;
            gc.strokeLine(startx+canvasMargin, val*scalex+canvasMargin, startx+canvasWidth, val*scalex+canvasMargin);
        }

        switches = flc.calculateRulePartitionsj();



        it = switches.iterator();
        while (it.hasNext()){
            double val = it.next();
            val = val + zeroValues;
            gc.strokeLine(val*scaley+canvasMargin, starty+canvasMargin, val*scaley+canvasMargin, starty+canvasHeight);
        }
    }

    public double getZeroValue() {
        return zeroValue;
    }

    public void setZeroValue(double zeroValue) {
        this.zeroValue = zeroValue;
    }

    private void getData(double spread1, double spread2, String type){
        NoveltyPartRules data = new NoveltyPartRules();
        Rules rules = data.getRules();

        inputs.clear();
        inputs.add(new FuzzySetImpl(spread1));
        inputs.add(new FuzzySetImpl(spread2));

        RulesInputs rulesInputs = new RulesInputs(inputs.get(0), inputs.get(1), 11);
        rulesInputs.defaultMemFunctions();
        if(type.equalsIgnoreCase("1")) {
            rules = FuzzyPartitions.getRules(rulesInputs);
            setZeroValue(0);
        }


        FZOperation fzOperation = new ProductTnorm();

        this.flc = new FLC(rules, fzOperation);
        flc.initiate(49, 49);
        flc.runRules(inputs);
        if(!type.equalsIgnoreCase("1")) {
            setZeroValue(1);
        }

        System.out.println("########################################################################################################################################################################################");

        for (int i = 0; i < flc.getCounts().length-1; i++) {
            for (int j = 0; j < flc.getCounts()[i].length-1; j++) {
                System.out.print(flc.getCounts()[i][j]+"  ");
            }
            System.out.println();
        }

        System.out.println("########################################################################################################################################################################################");

        for (int i = 0; i < flc.getLowerNoveltyCounts().length-1; i++) {
            for (int j = 0; j < flc.getLowerNoveltyCounts()[i].length-1; j++) {
                System.out.print(flc.getLowerNoveltyCounts()[i][j]+"  ");
            }
            System.out.println();
        }

        System.out.println("########################################################################################################################################################################################");

        for (int i = 0; i < flc.getUpperNoveltyCounts().length-1; i++) {
            for (int j = 0; j < flc.getUpperNoveltyCounts()[i].length-1; j++) {
                System.out.print(flc.getUpperNoveltyCounts()[i][j]+"  ");
            }
            System.out.println();
        }
    }
}
