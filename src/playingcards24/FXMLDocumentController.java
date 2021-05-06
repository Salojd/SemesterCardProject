package playingcards24;

import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;

/**
 *
 * @author peterschellhorn
 */
public class FXMLDocumentController implements Initializable {

    // JavaScript Engine added to handle math equations
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");

    // Arrays created to hold the value and suits of the card
    static private String[] cardNumber = {"ace","2","3","4","5","6","7","8","9","10","jack","queen","king"}, 
            cardType = {"clubs","diamonds","hearts","spades"}, 
            operators = {"+","-","*","/"};

    Cards[] cards = new Cards[] {
        new Cards("3", "hearts", 3), new Cards("king", "clubs", 13), new Cards("4", "diamonds", 4),
            new Cards("2", "clubs", 2)
    };

    long time = System.nanoTime();

    boolean isCorrect;

    private Label label;
    @FXML
    private Button findSolution;
    @FXML
    private TextField solutionField;
    @FXML
    private Button refreshText;
    @FXML
    private Button verifyCheck;
    @FXML
    private TextField ExpressionField;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;
    @FXML
    private ImageView card4;
    @FXML
    private Label feedback;
    @FXML
    private Label timer;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void loadCardImages(ActionEvent event) {
        
        System.out.println("Testing");
        try {
            long startTime = System.currentTimeMillis();
            new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedMillis = System.currentTimeMillis() - startTime ;
                timer.setText("Time: " + Long.toString(elapsedMillis / 1000) + " seconds");
            }
        }.start();
            time = System.nanoTime() - time;
            //timer.setText("Time: "+ Long.toString(TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS)) + "seconds");
            time = System.nanoTime();

            solutionField.setText("");
            ExpressionField.setText("");
            feedback.setText("Feedback:");

            // Random Generator created
            Random r = new Random();

            for (int i = 0; i < 4; i++) {
                int n1 = r.nextInt(13), n2 = r.nextInt(4);
                String number = cardNumber[n1], type = cardType[n2];
                boolean abundant = false;
                do {
                    abundant = false;
                    for (Cards c: cards) {
                        if (number.equals(c.getNumber()) && type.equals(c.getType())) {
                            abundant = true;
                            n1 = r.nextInt(13);
                            n2 = r.nextInt(4);
                            number = cardNumber[n1];
                            type = cardType[n2];
                        }
                    }
                } while (abundant == true);
                cards[i] = new Cards(number, type, n1 + 1);
            }
            setImage();
            // Images are set in the GUI
            //card1.setImage(cards[0].getImage());
            //card2.setImage(cards[1].getImage());
            //card3.setImage(cards[2].getImage());
            //card4.setImage(cards[3].getImage());
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
            //added to clean up code with methods, taken from above
            public void setImage(){
            card1.setImage(cards[0].getImage());
            card2.setImage(cards[1].getImage());
            card3.setImage(cards[2].getImage());
            card4.setImage(cards[3].getImage());
           }

    @FXML
    private void verifyUserExpression(ActionEvent event) throws ScriptException {
        try {
            //maybe move timer so if expression is wrong, timer is still running
            
            timer.setText("Time: "+ Long.toString(TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS)) + "seconds");
            int[] n = new int[13];
            for (int i = 0; i < 4; i++) {
                n[cards[i].getValue() - 1]++;
            }
            String expressionInput = ExpressionField.getText();
            if (expressionInput.contains(Integer.toString(cards[0].getValue())) &&
                expressionInput.contains(Integer.toString(cards[1].getValue())) &&
                expressionInput.contains(Integer.toString(cards[2].getValue())) &&
                expressionInput.contains(Integer.toString(cards[3].getValue()))) {

                if (count(expressionInput, Integer.toString(cards[0].getValue())) == n[cards[0].getValue() - 1] &&
                    count(expressionInput, Integer.toString(cards[1].getValue())) == n[cards[1].getValue() - 1] &&
                    count(expressionInput, Integer.toString(cards[2].getValue())) == n[cards[2].getValue() - 1] &&
                    count(expressionInput, Integer.toString(cards[3].getValue())) == n[cards[3].getValue() - 1] &&
                    count(expressionInput, "") == 4) {
                    if (engine.eval(expressionInput).equals(24)) {
                        isCorrect = true;
                        System.out.println(engine.eval(expressionInput));
                        feedback.setText("Correct! The total is 24.");
                    } else {
                        isCorrect = false;
                        System.out.println(engine.eval(expressionInput));
                        feedback.setText("Incorrect! The total is not 24, Please try again.");
                    }
                }else {
                    isCorrect = false;// cant get this to output***********
                    feedback.setText("Incorrect input. Please try again.");
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public int count(String str, String n) {
        int count = 0;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(str);
        while (m.find()) {
            if (n.equals("")) {
                count++;
            } else {
                if (m.group().equals(n)) {
                    count++;
                }
            }
        }
        return count;
    }


    @FXML
    private void findSolution(ActionEvent event) {
        try {
            time = System.nanoTime() - time;
            time = System.nanoTime();

            int[] n = {
                cards[0].getValue(),
                cards[1].getValue(),
                cards[2].getValue(),
                cards[3].getValue()
            };
            boolean[] deleted = new boolean[n.length];
            ArrayList < String > list = getInstructions(-1, -1, -1, n, deleted);

            if (!list.isEmpty()) {
                String str = getEquation(n, list);
                if (engine.eval(str).equals(24)) {
                    solutionField.setText(str);
                    
                } else {
                    solutionField.setText("No Solution for: [" + Integer.toString(n[0]) + ", " + Integer.toString(n[1]) + ", " + Integer.toString(n[2]) + ", " + Integer.toString(n[3]) + "]");
                }
            } else {
                solutionField.setText("No Solution for: [" + Integer.toString(n[0]) + ", " + Integer.toString(n[1]) + ", " + Integer.toString(n[2]) + ", " + Integer.toString(n[3]) + "]");
                
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public ArrayList < String > getInstructions(int i1, int i2, int i3, int[] n, boolean[] deleted) {
        int remain = 0, tmp = 0;
        for (int i = 0; i < deleted.length; i++) {
            if (!deleted[i]) {
                tmp = n[i];
                remain++;
            }
        }
        if (remain == 1) {
            if (Math.abs(tmp - 24) < 1E-10) {
                ArrayList < String > list = new ArrayList < > ();
                list.add("parenthesis," + 1);
                list.add("number," + i1);
                list.add("operator," + i2);
                list.add("number," + i3);
                list.add("parenthesis," + 2);
                return list;
            } else {
                return new ArrayList < > ();
            }
        }
        for (int f = 0; f < 4; f++) {
            for (int i = 0; i < 4; i++) {
                if (!deleted[i]) {
                    for (int j = 0; j < 4; j++) {
                        if (!deleted[j]) {
                            if (i != j) {
                                int[] tmpn = n.clone();
                                boolean[] tmpDeleted = deleted.clone();
                                tmpDeleted[j] = true;
                                try {
                                    tmpn[i] = evaluate(n[i], n[j], operators[f]);
                                } catch (Exception e) {
                                    break;
                                }
                                ArrayList < String > list = getInstructions(i, f, j, tmpn, tmpDeleted), tmpList = new ArrayList < > ();
                                if (list.size() > 0) {
                                    for (int o = 0; o < list.size(); o++) {
                                        if (list.get(o).substring(0, list.get(o).indexOf(",")).equals("number") && list.get(o).substring(list.get(o).indexOf(",") + 1, list.get(o).length()).equals(Integer.toString(i1))) {
                                            tmpList.add("parenthesis," + 1);
                                            tmpList.add("number," + i1);
                                            tmpList.add("operator," + i2);
                                            tmpList.add("number," + i3);
                                            tmpList.add("parenthesis," + 2);
                                        } else {
                                            tmpList.add(list.get(o));
                                        }
                                    }
                                    return tmpList;
                                }
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList < > ();
    }

    public String getEquation(int[] n, ArrayList < String > list) {
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).substring(0, list.get(i).indexOf(",")).equals("number")) {
                str += n[Integer.parseInt(list.get(i).substring(list.get(i).indexOf(",") + 1, list.get(i).length()))];
            } else if (list.get(i).substring(0, list.get(i).indexOf(",")).equals("operator")) {
                str += operators[Integer.parseInt(list.get(i).substring(list.get(i).indexOf(",") + 1, list.get(i).length()))];
            } else if (list.get(i).substring(0, list.get(i).indexOf(",")).equals("parenthesis")) {
                if (list.get(i).substring(list.get(i).indexOf(",") + 1, list.get(i).length()).equals("1")) {
                    str += "(";
                } else {
                    str += ")";
                }
            }
        }
        return str;
    }

    public int evaluate(int x, int y, String s) {
        switch (s) {
            case "+":
                return x + y;
            case "-":
                return x - y;
            case "*":
                return x * y;
            case "/":
                return x / y;
        }
        return 0;
    }




}