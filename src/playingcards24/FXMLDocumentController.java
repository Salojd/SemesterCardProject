package playingcards24;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class FXMLDocumentController  implements Initializable {
    
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
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ImageView fourCardStack[] = {card1,card2,card3,card4};
	//randomCardSelector(fourCardStack);
        //initialImgsSelector(fourCardStack);
    }    

    @FXML
    private void loadCardImages(ActionEvent event) throws Exception{
        ImageView fourCardStack[] = {card1,card2,card3,card4};
		randomCardSelector(fourCardStack);
		ExpressionField.clear();
    }
		
     @FXML
    private void verifyUserExpression(ActionEvent event){
        isExpression(ExpressionField.getText());
    }
    
    
        public void isExpression(String input){
		try {
			Pattern p = Pattern.compile("[a-zA-Z]+");
			Matcher m = p.matcher(input);
			if(m.find()){
					JOptionPane.showMessageDialog(null, "Invalid: " + input + " is not an expression in numeric form");
					ExpressionField.clear();
					m.reset();
				}
			p = Pattern.compile("[^0-9\\+\\(\\)\\+\\-\\/\\*]");
			m = p.matcher(input);
			if(m.find()){
					JOptionPane.showMessageDialog(null, "Please enter a valid numeric expression!");
					ExpressionField.clear();
					m.reset();
				}
			p = Pattern.compile("^[\\*\\+\\-\\)\\/]");
			m = p.matcher(input);
			if(m.find()){
				JOptionPane.showMessageDialog(null, "Invalid: the first character cannot be + - / x ) ");
				ExpressionField.clear();
				m.reset();
				}
			else{
				String validInput = input;
				logic(validInput);
			}
					
		} catch (NumberFormatException e) 
			{
				e.printStackTrace();
			}
	
	}
        
        public void logic(String s)
	{
		ScriptEngineManager checker = new ScriptEngineManager();
		        ScriptEngine engine = checker.getEngineByName("JavaScript");
		        try{ 
		            Object textboxText = engine.eval(s);  //this is where excution is taking place
		            String userTotal = textboxText.toString();
		            String correctAns = "24";
		            System.out.println(userTotal);
		            if(userTotal.equals(correctAns))
		            {
		            	JOptionPane.showMessageDialog(null, "Good Job! Correct Answer");
		            }
		           
		        }catch (ScriptException e)
		        { }
	}
    
// determine if operators entered are valid
	public static boolean isOperator(char operator) {
		return (operator == '(' ||
				operator == ')' ||
				isArithmeticOperator(operator));
	}
	public static boolean isArithmeticOperator(char operator) {
		return (operator == '/' ||
				operator == '+' ||
				operator == '-' ||
				operator == '*');
	}



	public void clear (ActionEvent clr) {

		refreshText.setText(null);
	}

    @FXML
    private void findSolution(ActionEvent event) {
    }

    
String rand = " "; 
	public String randomImgTypeSelector(){
		String[] cardTypes = {"_of_clubs.png", "_of_diamonds.png", "_of_hearts.png", "_of_spades.png"};
		Random r = new Random();
		rand = cardTypes[r.nextInt(cardTypes.length)];
		return rand;
	}
	
	public void randomCardSelector(ImageView[] cardStack){
		
		int[] cardRand = new int[4];
			for(int counter = 0; counter < 4; counter ++)
				{
				cardRand[counter] = (int) (Math.floor(Math.random() * 13) + 1);
				int somenum = cardRand[counter];
				//checkNums(somenum);
				cardStack[counter].setImage(new ImageView("CardImages/"+cardRand[counter]+randomImgTypeSelector()).getImage());
				
				}
	}


}