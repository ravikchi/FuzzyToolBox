package com.ravi.fuzzyToolBox.UserInterface;

import com.ravi.fuzzyToolBox.Examples.FuzzyPartitions;
import com.ravi.fuzzyToolBox.Examples.NoveltyPartRules;
import com.ravi.fuzzyToolBox.Examples.RulesInputs;
import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.PWLMF;
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
    String type = "1";
    String inputTypeText = "1";

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

        double spread1 = 1;
        double spread2 = 1;

        getData(spread1, spread2, type, inputTypeText, true);

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
                    TextField inputTypeVal = (TextField) c.getParent().getParent().getParent().lookup("#inputTypeVal");
                    RadioButton button = (RadioButton) c.getParent().getParent().getParent().lookup("#TrapezoidalFN");
                    double tfint = 0;
                    double tf1int = 0;

                    try{
                        tfint = Double.parseDouble(tf.getText());
                        tf1int = Double.parseDouble(tf1.getText());

                        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        getData(tfint, tf1int, flcTypeVal.getText(), inputTypeVal.getText(), button.isSelected());
                        type = flcTypeVal.getText();
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
        root.getChildren().add(textBoxes(spread1, spread2, type, inputTypeText));
/*
        root.getChildren().add(lowerNoveltyCanvas);
        root.getChildren().add(upperNoveltyCanvas);*/

        scene = new Scene(root, canvasWidth+canvasMargin+300, canvasHeight+canvasMargin);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Test");
        primaryStage.show();
    }

    private Group textBoxes(double spr1, double spr2, String type, String inputTypeText){
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
        moveDown = moveDown + 30;

        Group row5 = new Group();
        Label inputType = new Label("Input Type");
        TextField inputTypeVal = new TextField();
        inputTypeVal.setTranslateX(moveRight);
        inputTypeVal.setId("inputTypeVal");
        inputTypeVal.setText(inputTypeText);
        row5.getChildren().addAll(inputType, inputTypeVal);
        row5.setTranslateY(moveDown);
        moveDown = moveDown + 30;

        Group row6 = new Group();
        RadioButton button1 = new RadioButton("Trapezoidal FN");
        button1.setSelected(true);
        button1.setId("TrapezoidalFN");
        row6.getChildren().addAll(button1);
        row6.setTranslateY(moveDown);

        grp.getChildren().add(row1);
        grp.getChildren().add(row2);
        grp.getChildren().add(row3);
        grp.getChildren().add(row4);
        grp.getChildren().add(row5);
        grp.getChildren().add(row6);

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
        double sizex = canvasWidth - canvasMargin;
        double scalex = sizex/(flc.getEndi()-flc.getStarti());

        double sizey = canvasHeight - canvasMargin;
        double scaley = sizey/(flc.getEndj()-flc.getStartj());

        gc.setStroke(Color.BLACK);
        if(low.getTop2() != mid.getStart()) {
            gc.strokeLine(low.getTop2() * scalex + canvasMargin, canvasMargin, low.getTop2() * scalex + canvasMargin, canvasHeight);
            gc.strokeLine(canvasMargin,low.getTop2()*scaley+canvasMargin, canvasWidth, low.getTop2()*scaley+canvasMargin);
            if(!mid.isUpper()) {
                gc.strokeLine(mid.getStart() * scalex + canvasMargin, canvasMargin, mid.getStart() * scalex + canvasMargin, canvasHeight);
                gc.strokeLine(canvasMargin, mid.getStart() * scaley + canvasMargin, canvasWidth, mid.getStart() * scaley + canvasMargin);
            }

        }

        if(mid.getTop1() != low.getEnd()){
            gc.strokeLine(mid.getTop1() * scalex + canvasMargin, canvasMargin, mid.getTop1() * scalex + canvasMargin, canvasHeight);
            gc.strokeLine(canvasMargin, mid.getTop1()*scaley+canvasMargin, canvasWidth, mid.getTop1()*scaley+canvasMargin);
            if(!low.isUpper()) {
                gc.strokeLine(low.getEnd() * scalex + canvasMargin, canvasMargin, low.getEnd() * scalex + canvasMargin, canvasHeight);
                gc.strokeLine(canvasMargin, low.getEnd()*scaley+canvasMargin, canvasWidth, low.getEnd()*scaley+canvasMargin);
            }
        }
    }

    private void drawXInputLines(GraphicsContext gc, int startx, int starty,MemFunc inputMem, MemFunc memFunc){
        double sizex = canvasWidth - canvasMargin;

        double scalex = sizex/(flc.getEndi()-flc.getIncrementi()-flc.getStarti());



        double start = (inputMem.getStart()+zeroValue)*scalex;
        double top1 = (inputMem.getTop1()+zeroValue)*scalex;
        double top2 = (inputMem.getTop2()+zeroValue)*scalex;
        double end = (inputMem.getEnd()+zeroValue)*scalex;

        gc.strokeLine(startx+start+canvasMargin, starty+canvasMargin, startx+top1+canvasMargin, starty+canvasMargin/2);
        gc.strokeLine(startx+top1+canvasMargin, starty+canvasMargin/2, startx+top2+canvasMargin, starty+canvasMargin/2);
        gc.strokeLine(startx+top2+canvasMargin, starty+canvasMargin/2, startx+end+canvasMargin, starty+canvasMargin);

    }

    private void drawYInputLines(GraphicsContext gc, int startx, int starty,MemFunc inputMem, MemFunc memFunc){
        double sizey = canvasHeight - canvasMargin;

        double scaley = sizey/(flc.getEndj()-flc.getIncrementj()-flc.getStartj());


        double start = (inputMem.getStart()+zeroValue)*scaley;
        double top1 = (inputMem.getTop1()+zeroValue)*scaley;
        double top2 = (inputMem.getTop2()+zeroValue)*scaley;
        double end = (inputMem.getEnd()+zeroValue)*scaley;

        double y1 = 0 + canvasMargin;
        double y2 = 0 + canvasMargin/2;

        gc.strokeLine(startx+y1, starty+start+y1, startx+y2, starty+top1+y1);
        gc.strokeLine(startx+y2, starty+top1+y1, startx+y2, starty+top2+y1);
        gc.strokeLine(startx+y2, starty+top2+y1, startx+y1, starty+end+y1);

    }

    private void drawXLines(GraphicsContext gc, int startx, int starty, MemFunc mc){
        double sizex = canvasWidth - canvasMargin;

        double scalex = sizex/(flc.getEndi()-flc.getIncrementi()-flc.getStarti());

        double start = (mc.getStart()+zeroValue)*scalex;
        double top1 = (mc.getTop1()+zeroValue)*scalex;
        double top2 = (mc.getTop2()+zeroValue)*scalex;
        double end = (mc.getEnd()+zeroValue)*scalex;

        double y1 = 0 + canvasMargin;
        double y2 = 0 + canvasMargin/2;

        double sizey = canvasMargin - canvasMargin/2;

        if(mc instanceof PWLMF){
            PWLMF pmc = (PWLMF) mc;

            y1 = y1-pmc.getY1() * sizey ;
            y2 = y1-pmc.getY2()* sizey ;

            System.out.println(pmc.getY1());
            System.out.println(pmc.getY2());
        }

        gc.strokeLine(startx+start+y1, starty+y1, startx+top1+y1, starty+y2);
        gc.strokeLine(startx+top1+y1, starty+y2, startx+top2+y1, starty+y2);
        gc.strokeLine(startx+top2+y1, starty+y2, startx+end+y1, starty+y1);
    }

    private void drawYLines(GraphicsContext gc, int startx, int starty, MemFunc mc){
        double sizey = canvasHeight - canvasMargin;

        double scaley = sizey/(flc.getEndj()-flc.getIncrementj()-flc.getStartj());

        double sizex = canvasMargin - canvasMargin/2;

        double start = (mc.getStart()+zeroValue)*scaley;
        double top1 = (mc.getTop1()+zeroValue)*scaley;
        double top2 = (mc.getTop2()+zeroValue)*scaley;
        double end = (mc.getEnd()+zeroValue)*scaley;

        double y1 = canvasMargin;
        double y2 = canvasMargin/2;

        if(mc instanceof PWLMF){
            PWLMF pmc = (PWLMF) mc;

            y1 = y1-pmc.getY1() * sizex ;
            y2 = y1-pmc.getY2()* sizex ;

            System.out.println(pmc.getY1());
            System.out.println(pmc.getY2());
        }

        gc.strokeLine(startx+y1, starty+start+y1, startx+y2, starty+top1+y1);
        gc.strokeLine(startx+y2, starty+top1+y1, startx+y2, starty+top2+y1);
        gc.strokeLine(startx+y2, starty+top2+y1, startx+y1, starty+end+y1);
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
        for (int i = 0; i < flc.getInputIUpperMemFunc().size(); i++) {
            setStokeColor(i, gc);
            if(flc.getInputILowerMemFunc().size() > 0)
                drawXLines(gc, startx, starty, flc.getInputILowerMemFunc().get(i));

            drawXLines(gc, startx, starty, flc.getInputIUpperMemFunc().get(i));

            if(i>0){
                if(flc.getInputILowerMemFunc().size() > 0)
                    drawSecondaryPartitions(flc.getInputILowerMemFunc().get(i-1), flc.getInputILowerMemFunc().get(i), gc);
                drawSecondaryPartitions(flc.getInputIUpperMemFunc().get(i-1), flc.getInputIUpperMemFunc().get(i), gc);
            }
        }


        for (int i = 0; i < flc.getInputJUpperMemFunc().size(); i++) {
            setStokeColor(i, gc);
            if(flc.getInputJLowerMemFunc().size() > 0)
                drawYLines(gc, startx, starty, flc.getInputJLowerMemFunc().get(i));

            drawYLines(gc, startx, starty, flc.getInputJUpperMemFunc().get(i));

            if(i>0){
                if(flc.getInputJLowerMemFunc().size() > 0)
                    drawSecondaryPartitions(flc.getInputJLowerMemFunc().get(i-1), flc.getInputJLowerMemFunc().get(i), gc);
                drawSecondaryPartitions(flc.getInputJUpperMemFunc().get(i-1), flc.getInputJUpperMemFunc().get(i), gc);
            }
        }

//        if(inputs.get(1).isType2()){
//
//        }else {
//            MemFunc inputMem = inputs.get(1).getMembershipFunction(flc.getInputIUpperMemFunc().get(2).getStart());
//            drawXInputLines(gc, startx, starty, inputMem, flc.getInputIUpperMemFunc().get(2));
//            inputMem = inputs.get(0).getMembershipFunction(flc.getInputIUpperMemFunc().get(2).getStart());
//            drawYInputLines(gc, startx, starty, inputMem, flc.getInputJUpperMemFunc().get(2));
//        }

        drawInputLines(gc, startx, starty, inputs.get(1), inputs.get(0), flc.getInputIUpperMemFunc().get(2), flc.getInputJUpperMemFunc().get(2));


        gc.setStroke(Color.BLACK);
    }

    private void drawMemFuncLines(GraphicsContext gc, int startx, int starty, boolean secPartitions){
        for (int i = 0; i < flc.getInputIUpperMemFunc().size(); i++) {
            setStokeColor(i, gc);
            if(flc.getInputILowerMemFunc().size() > 0)
                drawXLines(gc, startx, starty, flc.getInputILowerMemFunc().get(i));

            drawXLines(gc, startx, starty, flc.getInputIUpperMemFunc().get(i));

            if(i>0 && secPartitions){
                if(flc.getInputILowerMemFunc().size() > 0)
                    drawSecondaryPartitions(flc.getInputILowerMemFunc().get(i-1), flc.getInputILowerMemFunc().get(i), gc);
                drawSecondaryPartitions(flc.getInputIUpperMemFunc().get(i-1), flc.getInputIUpperMemFunc().get(i), gc);
            }
        }


        for (int i = 0; i < flc.getInputJUpperMemFunc().size(); i++) {
            setStokeColor(i, gc);
            if(flc.getInputJLowerMemFunc().size() > 0)
                drawYLines(gc, startx, starty, flc.getInputJLowerMemFunc().get(i));

            drawYLines(gc, startx, starty, flc.getInputJUpperMemFunc().get(i));

            if(i>0 && secPartitions){
                if(flc.getInputJLowerMemFunc().size() > 0)
                    drawSecondaryPartitions(flc.getInputJLowerMemFunc().get(i-1), flc.getInputJLowerMemFunc().get(i), gc);
                drawSecondaryPartitions(flc.getInputJUpperMemFunc().get(i-1), flc.getInputJUpperMemFunc().get(i), gc);
            }
        }

//        gc.setStroke(Color.BLACK);
//        if(inputs.get(1).isType2()){
//            List<MemFunc> memFuncs = inputs.get(1).getMemFuncs(flc.getInputIUpperMemFunc().get(2).getStart());
//            for (int i = 0; i < memFuncs.size(); i++) {
//                MemFunc inputMem = memFuncs.get(i);
//                drawXInputLines(gc, startx, starty, inputMem, flc.getInputIUpperMemFunc().get(2));
//            }
//        }else {
//            MemFunc inputMem = inputs.get(1).getMembershipFunction(flc.getInputIUpperMemFunc().get(2).getStart());
//            drawXInputLines(gc, startx, starty, inputMem, flc.getInputIUpperMemFunc().get(2));
//            inputMem = inputs.get(0).getMembershipFunction(flc.getInputIUpperMemFunc().get(2).getStart());
//            drawYInputLines(gc, startx, starty, inputMem, flc.getInputJUpperMemFunc().get(2));
//        }

        drawInputLines(gc, startx, starty, inputs.get(1), inputs.get(0), flc.getInputIUpperMemFunc().get(2), flc.getInputJUpperMemFunc().get(2));

        gc.setStroke(Color.BLACK);
    }

    private void drawInputLines(GraphicsContext gc, int startx, int starty, FuzzySet fuzzySetx, FuzzySet fuzzySety, MemFunc memFuncx, MemFunc memFuncy){
        if(fuzzySetx.isType2()){
            List<MemFunc> memFuncs = fuzzySetx.getMemFuncs(memFuncx.getStart());
            for (int i = 0; i < memFuncs.size(); i++) {
                MemFunc inputMem = memFuncs.get(i);
                drawXInputLines(gc, startx, starty, inputMem, memFuncx);
            }

            memFuncs = fuzzySety.getMemFuncs(memFuncy.getStart());
            for (int i = 0; i < memFuncs.size(); i++) {
                MemFunc inputMem = memFuncs.get(i);
                drawYInputLines(gc, startx, starty, inputMem, memFuncy);
            }

        }else {
            MemFunc inputMem = fuzzySetx.getMembershipFunction(memFuncx.getStart());
            drawXInputLines(gc, startx, starty, inputMem, memFuncx);
            inputMem = fuzzySety.getMembershipFunction(memFuncy.getStart());
            drawYInputLines(gc, startx, starty, inputMem, memFuncy);
        }
    }

    private void drawMeasurementLines(int startx, int starty,GraphicsContext gc){
        double sizex = canvasWidth - canvasMargin;

        double scalex = sizex/(flc.getEndi()-flc.getIncrementi()-flc.getStarti());

        for (double i=flc.getStarti(); i<flc.getEndi(); i=i+flc.getIncrementi()*5) {
            String val = String.format("%.2f",i);
            if(i>1){
                val = String.format("%.0f",i);
            }

            gc.strokeLine(((i+zeroValue)*scalex)+startx+canvasMargin, canvasMargin, ((i+zeroValue)*scalex)+canvasMargin, canvasMargin-10);
            gc.fillText(val,((i+zeroValue)*scalex)+canvasMargin-5, canvasMargin-20);
        }

        double sizey = canvasHeight - canvasMargin;

        double scaley = sizey/(flc.getEndj()-flc.getIncrementj()-flc.getStartj());

        for (double i=flc.getStartj(); i<flc.getEndj(); i=i+flc.getIncrementj()*5) {
            String val = String.format("%.2f",i);
            if(i>1){
                val = String.format("%.0f",i);
            }
            gc.strokeLine(canvasMargin-10, canvasMargin+(i+zeroValue)*scaley, canvasMargin, canvasMargin+(i+zeroValue)*scaley);
            gc.fillText(val,canvasMargin-25,canvasMargin+(i+zeroValue)*scaley+5);
        }

    }

    private double getScaleX(){
        double sizex = canvasWidth - canvasMargin;

        return sizex/(flc.getCounts()[0].length-1);
        //return sizex/(flc.getEndi()-flc.getStarti());
    }

    private double getScaleY(){
        double sizey = canvasHeight - canvasMargin;

        return sizey/(flc.getCounts().length-1);
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
            gc.setFill(Color.GREY);
        }else if(value == 1) {
            gc.setFill(Color.WHITE);
        }else if(value == 2){
            gc.setFill(Color.BLUE);
        }else if(value == 3){
            gc.setFill(Color.AQUA);
        }else if(value == 4){
            gc.setFill(Color.RED);
        }else if(value == 5) {
            gc.setFill(Color.YELLOW);
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

    private void getData(double spread1, double spread2, String type, String inputTypeText, boolean trapezoidal){
        NoveltyPartRules data = new NoveltyPartRules();
        Rules rules = data.getRules(type);

        inputs.clear();
        if(inputTypeText.equalsIgnoreCase("2") && spread1>0 && spread2>0) {
            inputs.add(new FuzzySetImpl(spread1-(2*spread1/10),spread1/10, trapezoidal));
            inputs.add(new FuzzySetImpl(spread2-(2*spread2/10), spread2/10, trapezoidal));
        }else {
            inputs.add(new FuzzySetImpl(spread1, trapezoidal));
            inputs.add(new FuzzySetImpl(spread2, trapezoidal));
        }


        RulesInputs rulesInputs = new RulesInputs(inputs.get(0), inputs.get(1), 11);

        if(type.equalsIgnoreCase("1")) {
            rulesInputs.defaultMemFunctions();
            rules = FuzzyPartitions.getRules(rulesInputs);
            setZeroValue(0);
        }else if(type.equalsIgnoreCase("0")){
            rulesInputs.type1MemFunctions();
            rules = FuzzyPartitions.getRules(rulesInputs);
            setZeroValue(0);
        }else if(type.equalsIgnoreCase("3")){
            setZeroValue(1);
        }


        FZOperation fzOperation = new ProductTnorm();

        this.flc = new FLC(rules, fzOperation);
        flc.initiate(149, 149);
        flc.runRules(inputs);
        if(type.equalsIgnoreCase("2")) {
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
