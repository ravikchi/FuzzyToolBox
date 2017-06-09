package com.ravi.fuzzyToolBox.UserInterface;

import com.ravi.fuzzyToolBox.Examples.FuzzyPartitions;
import com.ravi.fuzzyToolBox.Examples.RulesInputs;
import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;
import com.ravi.fuzzyToolBox.Rules.Rules;
import com.ravi.fuzzyToolBox.Tnorm;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.xml.soap.Text;
import java.util.*;


/**
 * Created by 611445924 on 29/05/2017.
 */
public class FXCanvas extends Application {
    Pane root;
    Scene scene;
    Canvas canvas;
    int width = 600;
    int height = 700;
    int margin = 100;
    private TableView table = new TableView();

    public FXCanvas() {
        table.setId("Table");
    }

    public void start(Stage primaryStage) throws Exception {

        root = new Pane();


        Group grp = new Group();
        grp.setTranslateX(width+30);


        int moveDown = 0;
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
        spread1.setText("0");
        spread1.setId("spread1T");
        row2.getChildren().addAll(spread1, spread1L);
        row2.setTranslateY(moveDown);
        moveDown = moveDown + 30;

        Group row3 = new Group();
        Label spread2L = new Label("Spread of Input FS 2");
        TextField spread2 = new TextField();
        spread2.setTranslateX(moveRight);
        spread2.setId("spread2T");
        spread2.setText("0");
        row3.getChildren().addAll(spread2, spread2L);
        row3.setTranslateY(moveDown);
        moveDown = moveDown + 30;

        int colWidth = 30;

        Group row4 = new Group();
        row4.getChildren().add(table);
        row4.setTranslateY(moveDown);

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<MemFunc, String>("name"));
        TableColumn start = new TableColumn("Start");
        start.setCellValueFactory(new PropertyValueFactory<MemFunc, String>("start"));
        TableColumn top1 = new TableColumn("Top1");
        top1.setCellValueFactory(new PropertyValueFactory<MemFunc, String>("top1"));
        TableColumn top2 = new TableColumn("Top2");
        top2.setCellValueFactory(new PropertyValueFactory<MemFunc, String>("top2"));
        TableColumn end = new TableColumn("End");
        end.setCellValueFactory(new PropertyValueFactory<MemFunc, String>("end"));

        table.getColumns().addAll(name, start, top1, top2, end);

        RulesInputs rulesInputs = new RulesInputs(new FuzzySetImpl(0), new FuzzySetImpl(0), 11);
        List<MemFunc> low = new ArrayList<MemFunc>();
        MemFunc lowMem = new TrapezoidalMemFunc("lowLower",0, 0, 9, 19, true, false);
        MemFunc lowLower = new TrapezoidalMemFunc("lowUpper",0, 0, 11, 21, true, true);
        low.add(lowMem);
        low.add(lowLower);

        List<MemFunc> lowC = new ArrayList<MemFunc>();
        List<MemFunc> midC = new ArrayList<MemFunc>();
        List<MemFunc> highC = new ArrayList<MemFunc>();

        MemFunc lowLowerConsequent = new TrapezoidalMemFunc("lowConsequent", 0, 0, 9, 19, true, false);
        MemFunc lowUpperConsequent = new TrapezoidalMemFunc("lowUpperConsequent",0, 0, 11, 21, true, true);
        lowC.add(lowLowerConsequent);
        lowC.add(lowUpperConsequent);

        List<MemFunc> mid = new ArrayList<MemFunc>();
        MemFunc midMem = new TrapezoidalMemFunc("midUpper",10, 20, 31, 41, true, true);
        MemFunc midLower = new TrapezoidalMemFunc("midLower",12, 22, 29, 39, true, false);
        mid.add(midMem);
        mid.add(midLower);

        MemFunc midLowerConsequent = new TrapezoidalMemFunc("midConsequent", 12, 22, 29, 39, true, false);
        MemFunc midUpperConsequent = new TrapezoidalMemFunc("midUpperConsequent",10, 20, 31, 41, true, true);
        midC.add(midLowerConsequent);
        midC.add(midUpperConsequent);

        List<MemFunc> high = new ArrayList<MemFunc>();
        MemFunc highMem = new TrapezoidalMemFunc("highUpper",30, 40, 50, 50, true, true);
        MemFunc highLower = new TrapezoidalMemFunc("highLower",32, 42, 50, 50, true, false);
        high.add(highMem);
        high.add(highLower);

        MemFunc highMemConsequent = new TrapezoidalMemFunc("highUpperConsequent",30, 40, 50, 50, true, true);
        MemFunc highLowerConsequent = new TrapezoidalMemFunc("highLowerConsequent",32, 42, 50, 50, true, false);
        highC.add(highMemConsequent);
        highC.add(highLowerConsequent);

        rulesInputs.setLow(low);
        rulesInputs.setMid(mid);
        rulesInputs.setHigh(high);

        rulesInputs.setLowConseqent(lowC);
        rulesInputs.setMidConseqent(midC);
        rulesInputs.setHighConseqent(highC);

        ObservableList<MemFunc> memFuncs = FXCollections.observableArrayList();
        memFuncs.addAll(low);
        memFuncs.addAll(mid);
        memFuncs.addAll(high);
        memFuncs.addAll(lowC);
        memFuncs.addAll(midC);
        memFuncs.addAll(highC);

        table.setItems(memFuncs);

        grp.getChildren().add(row1);
        grp.getChildren().add(row2);
        grp.getChildren().add(row3);
        grp.getChildren().add(row4);

        canvas = new Canvas(width, height);
        prepareCanvas(rulesInputs);

        Line line = new Line(width,0, width, height);

        root.getChildren().add(canvas);
        root.getChildren().add(grp);
        root.getChildren().add(line);

        scene = new Scene(root, width+500, height);


        primaryStage.setScene(scene);
        primaryStage.setTitle("Test");
        primaryStage.show();

    }

    private void prepareCanvas(RulesInputs rulesInputs){
        canvas.setId("canv");
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Rules rules = FuzzyPartitions.getRules(rulesInputs);
        FZOperation fzOperation = new Tnorm();
        FuzzyPartitions partitions = new FuzzyPartitions(rules, fzOperation);
        this.printResults(partitions.getCounts());
        this.printResults(partitions.getLowerNoveltyCounts());
        this.printResults(partitions.getUpperNoveltyCounts());

        getCanvas(width, height, margin, rulesInputs, partitions, gc);
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

                        RulesInputs rulesInputs = new RulesInputs(new FuzzySetImpl(tfint), new FuzzySetImpl(tf1int), 11);
                        List<MemFunc> low = new ArrayList<MemFunc>();
                        List<MemFunc> mid = new ArrayList<MemFunc>();
                        List<MemFunc> high = new ArrayList<MemFunc>();
                        List<MemFunc> lowC = new ArrayList<MemFunc>();
                        List<MemFunc> midC = new ArrayList<MemFunc>();
                        List<MemFunc> highC = new ArrayList<MemFunc>();

                        for(Object obj : table.getItems()){
                            if(obj instanceof MemFunc) {
                                MemFunc memFunc = (MemFunc) obj;
                                if(memFunc.getName().contains("Consequent")){
                                    if (memFunc.getName().contains("low")) {
                                        lowC.add(memFunc);
                                    } else if (memFunc.getName().contains("mid")) {
                                        midC.add(memFunc);
                                    } else if (memFunc.getName().contains("high")) {
                                        highC.add(memFunc);
                                    }
                                }else {
                                    if (memFunc.getName().contains("low")) {
                                        low.add(memFunc);
                                    } else if (memFunc.getName().contains("mid")) {
                                        mid.add(memFunc);
                                    } else if (memFunc.getName().contains("high")) {
                                        high.add(memFunc);
                                    }
                                }
                            }
                        }

                        rulesInputs.setLow(low);
                        rulesInputs.setMid(mid);
                        rulesInputs.setHigh(high);
                        rulesInputs.setLowConseqent(lowC);
                        rulesInputs.setMidConseqent(midC);
                        rulesInputs.setHighConseqent(highC);

                        c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());

                        Rules rules = FuzzyPartitions.getRules(rulesInputs);
                        FZOperation fzOperation = new Tnorm();
                        FuzzyPartitions partitions = new FuzzyPartitions(rules, fzOperation);

                        getCanvas(width, height, margin, rulesInputs, partitions, c.getGraphicsContext2D());

                    }catch(NumberFormatException nfe){
                        c.getGraphicsContext2D().setFill(Color.RED);
                        c.getGraphicsContext2D().fillText("Input should be a number", margin, margin-10);
                    }
                }
            }
        });
    }

    private RulesInputs getCanvas(int width, int height, int margin, RulesInputs rulesInputs, FuzzyPartitions partitions, GraphicsContext gc){

        int[][] counts = partitions.getCounts();

        gc.setLineWidth(1.0);

        gc.strokeLine(margin, 0, margin, height);
        gc.strokeLine(0, height-margin, width, height-margin);


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

        drawLines(gc, rulesInputs, 0);
        drawLines(gc, rulesInputs, 1);

        drawMeasurementLines(width, height, margin, gc, counts.length, counts[0].length);

        return rulesInputs;
    }

    private void drawMeasurementLines(int width, int height, int margin, GraphicsContext gc, int xCoords, int yCoords){
        for (int i = 0; i <= xCoords; i=i+5) {
            gc.strokeLine((i*10)+margin, height-margin, (i*10)+margin, height-margin+10);
            gc.fillText(i+"",(i*10)+margin-5,height-margin+25);
        }

        for (int i = 0; i <= xCoords; i=i+5) {
            gc.strokeLine(margin-10, margin+i*10, margin, margin+i*10);
            gc.fillText(i+"",margin-25,margin+i*10+5);
        }

    }

    private void drawLines(GraphicsContext gc, RulesInputs rulesInputs, int i){
        if(rulesInputs.getLow().size() <= i){
            return;
        }
        gc.setStroke(Color.BLUE);
        drawMemFuncX(gc, rulesInputs.getLow().get(i), margin, height);
        gc.setStroke(Color.GREEN);
        drawMemFuncX(gc, rulesInputs.getMid().get(i), margin, height);
        gc.setStroke(Color.RED);
        drawMemFuncX(gc, rulesInputs.getHigh().get(i), margin, height);
        gc.setStroke(Color.BROWN);
        drawMemFuncX(gc, rulesInputs.getInput(), margin, height);

        gc.setStroke(Color.BLUE);
        drawMemFuncY(gc, rulesInputs.getLow().get(i), margin, width);
        gc.setStroke(Color.GREEN);
        drawMemFuncY(gc, rulesInputs.getMid().get(i), margin, width);
        gc.setStroke(Color.RED);
        drawMemFuncY(gc, rulesInputs.getHigh().get(i), margin, width);

        gc.setStroke(Color.BROWN);
        drawMemFuncY(gc, rulesInputs.getInput(), margin, width);

        drawSecondaryPartitions(rulesInputs.getLow().get(i), rulesInputs.getMid().get(i), gc);
        drawSecondaryPartitions(rulesInputs.getMid().get(i), rulesInputs.getHigh().get(i), gc);
    }

    private void drawSecondaryPartitions(MemFunc low, MemFunc mid, GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        if(low.getTop2() != mid.getStart()) {
            gc.strokeLine(low.getTop2() * 10 + margin, margin, low.getTop2() * 10 + margin, height - margin);
            gc.strokeLine(margin,low.getTop2()*10+margin, width, low.getTop2()*10+margin);
        }

        if(mid.getTop1() != low.getEnd()){
            gc.strokeLine(mid.getTop1() * 10 + margin, margin, mid.getTop1() * 10 + margin, height - margin);
            gc.strokeLine(margin, mid.getTop1()*10+margin, width, mid.getTop1()*10+margin);
        }
    }

    private void drawMemFuncX(GraphicsContext gc, MemFunc memFunc, int margin, int height){
        gc.strokeLine(memFunc.getStart()*10+margin, height-margin, memFunc.getTop1()*10+margin, height-margin/2);
        gc.strokeLine(memFunc.getTop1()*10+margin, height-margin/2, memFunc.getTop2()*10+margin,height-margin/2);
        gc.strokeLine(memFunc.getTop2()*10+margin, height-margin/2, memFunc.getEnd()*10+margin,height-margin);
    }

    private void drawMemFuncY(GraphicsContext gc, MemFunc memFunc, int margin, int width){
        gc.strokeLine(margin, memFunc.getStart()*10+margin, margin/2, memFunc.getTop1()*10+margin);
        gc.strokeLine(margin/2, memFunc.getTop1()*10+margin, margin/2, memFunc.getTop2()*10+margin);
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
