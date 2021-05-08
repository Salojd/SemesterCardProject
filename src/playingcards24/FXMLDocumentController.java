/**
 * This is the entire Playing Cards 24 package including the game and its tests
 */

package playingcards24;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


/**
 * A class that contains everything being used to create the Playing Cards 24 game
 * @author peterschellhorn, Brett Silver
 * @verson 1.0
 * @since 2021-4-15
 * 
 */
public class FXMLDocumentController implements Initializable {

    // JavaScript Engine added to handle math equations
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");
    

    // Arrays created to hold the value and suits of the card
    static private String[] cardNumber = {
            "ace",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "jack",
            "queen",
            "king"
        },
        cardType = {
            "clubs",
            "diamonds",
            "hearts",
            "spades"
        },
        operators = {
            "+",
            "-",
            "*",
            "/"
        };

    Cards[] cards = new Cards[] {
        new Cards("3", "hearts", 3), new Cards("king", "clubs", 13), new Cards("4", "diamonds", 4),
            new Cards("2", "clubs", 2)
    };

    long time = System.nanoTime();

    boolean isCorrect;
    
    /**
     * These are the identifiers from this game from the FXML file including the buttons, labels, images, timer,
     * and images. This is the front end part of the program.
     */

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

    
    /**
     * Initialize is being used to initialize the controller after the root element has been processed. 
     * @param url a pointer to a resource on the world wide web or folder
     * @param rb provides FXMLLoader
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        writeToFile("START");
        /**
         * Creates a timer that starts when program opens, counting by seconds. 
         */
        long startTime = System.currentTimeMillis();
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    long elapsedMillis = System.currentTimeMillis() - startTime;
                    timer.setText("Time: " + Long.toString(elapsedMillis / 1000) + " seconds");
                }
            }.start();
    }

    /**
     * This method has all the code required to both load the cards with the values connected to them, as well
     * as gives the ability to refresh the cards in a random order.
     * @param event The actual event of pressing the button, and then loading random images.
     */
    
    @FXML
    private void loadCardImages(ActionEvent event) {
        
        try {
            /**
             * Below is a timer that refreshes to 0 each time the event is triggered
             */
            long startTime = System.currentTimeMillis();
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    long elapsedMillis = System.currentTimeMillis() - startTime;
                    timer.setText("Time: " + Long.toString(elapsedMillis / 1000) + " seconds");
                    //System.out.println(timer.getText());
                }
            }.start();
            
            /**
             * The log method is called to record that the button has been clicked
             * Multiple fields are refreshed after the event is used
             */
            writeToFile("Refresh button clicked");
            solutionField.setText("");
            ExpressionField.setText("");
            feedback.setText("Feedback:");

            // Random Generator created
            Random r = new Random();

            /**
             * Below is the loop to create both a random card and its suit
             */
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

        } catch (Exception e) {
            System.out.println("Error");
        }
    }
    /*
    This method sets the image based on which images were pulled from the loop
    */
    public void setImage() {
        card1.setImage(cards[0].getImage());
        card2.setImage(cards[1].getImage());
        card3.setImage(cards[2].getImage());
        card4.setImage(cards[3].getImage());
    }

    /**
     * This method is used to check if what the user inputs into the textbox, matches
     * the cards that have been outputted. If yes, a message will appear telling the user
     * if the input is right or wrong
     * @param event This is the event of checking wether the input is right or wrong
     */
    @FXML
    private void verifyUserExpression(ActionEvent event) {
        writeToFile("Verify button clicker");
        try {



            int[] n = new int[13];
            for (int i = 0; i < 4; i++) {
                n[cards[i].getValue() - 1]++;
            }
            String expressionInput = ExpressionField.getText();
            /**
             * If statement checks to see if the input matches which cards are displayed
             */
            if (expressionInput.contains(Integer.toString(cards[0].getValue())) &&
                expressionInput.contains(Integer.toString(cards[1].getValue())) &&
                expressionInput.contains(Integer.toString(cards[2].getValue())) &&
                expressionInput.contains(Integer.toString(cards[3].getValue()))) {

                if (count(expressionInput, Integer.toString(cards[0].getValue())) == n[cards[0].getValue() - 1] &&
                    count(expressionInput, Integer.toString(cards[1].getValue())) == n[cards[1].getValue() - 1] &&
                    count(expressionInput, Integer.toString(cards[2].getValue())) == n[cards[2].getValue() - 1] &&
                    count(expressionInput, Integer.toString(cards[3].getValue())) == n[cards[3].getValue() - 1] &&
                    count(expressionInput, "") == 4) {

                  //This if statement checks to see if the input equals to 24
                    if (engine.eval(expressionInput).equals(24)) {
                        isCorrect = true;
                        System.out.println(engine.eval(expressionInput));
                        feedback.setText("Correct! The total is 24.");
                        writeToFile("Correct user expression");
                        writeToFile("TIME");
                        //timer stop
                    } else {
                        //If the input is incorrect, the following code is the error
                        isCorrect = false;
                        System.out.println(engine.eval(expressionInput));
                        feedback.setText("Incorrect! The total is not 24, Please try again.");
                        writeToFile("Incorrect user expression ");
                    }
                } else {
                    isCorrect = false;
                    feedback.setText("Incorrect input. Please try again.");
                    writeToFile("Incorrect input in user expression ");
                }
            } else {
                isCorrect = false;
                feedback.setText("Please enter correct input");
                writeToFile("Incorrect input in user expression ");
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    /**
     * 
     * @param str
     * @param n
     * @return 
     */
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


    /**
     * This method will display the correct solution to Playing Cards 24. It will say
     * what the solution is, or that there is no solution
     * @param event The event used to find the solution
     */
    @FXML
    private void findSolution(ActionEvent event) {
        writeToFile("Find solution button clicked");
        writeToFile("TIME");
        
        try {


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

    /**
     * This method will decide the correct action based on the string s
     * The method will either add, minus , multiply, or divide the given numbers
     * @param x First number entered to be used
     * @param y second number entered to be used
     * @param s Will decide which action to take
     * @return will return nothing or 0
     */
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

    int count = 1;
    /**
     * This method records every button pressed into a log file, including time, and if the action is correct
     * @param str Initializes the method
     */
    public void writeToFile(String str) {
        //
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy 'at' HH:mm:ss ");
        Date date = new Date(System.currentTimeMillis());

        try {

            FileWriter myWriter = new FileWriter("log.txt", true);

            if (str.equals("START")) {
                myWriter.write("\n===========START==============\n");
            }
            if(str.equals("TIME")){
                myWriter.write("    TIMER: " + timer.getText()+"\n");
            }else{
            myWriter.write(count + ") " + str + " on " + formatter.format(date) + System.getProperty("line.separator"));
            }
            myWriter.close();
            count++;
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }




}