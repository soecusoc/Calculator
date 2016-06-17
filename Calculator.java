import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal; //Subresult is a BigDecimal.
import java.util.LinkedList; //Container for BigDecimal.



public class Calculator extends Frame implements WindowListener {
	
	private static final long serialVersionUID = -8670453260797478824L;
	
	//Length of text field to display the result.
	public static final int LENGTH = 17;

	/********************************************
	 *Private variables of the Calculator-class:*
	 ********************************************/
	
	//Container for one BigDecimal.
	//Needed, because BigDecimal is immutable.
	//For every operation, new BigDecimal must be created.
	private LinkedList<BigDecimal> results = new LinkedList<BigDecimal>();
	//Text field to show result.
	private TextField tfResult;
	//Buttons
	private Button b0;
	private Button b1;
	private Button b2;
	private Button b3;
	private Button b4;
	private Button b5;
	private Button b6;
	private Button b7;
	private Button b8;
	private Button b9;
	private Button bDot;
	private Button bAdd;
	private Button bSub;
	private Button bMul;
	private Button bDiv;
	private Button bEq;
	private Button bCE;
	//Boolean value to indicate whether the last key pressed was an operator.
	//A button can be either an operator button or a number button.
	private boolean lastKeyOperator = false;
	//Boolean value to indicate whether the Calculator is in error state.
	//E.g. Division by zero, Too large number.
	private boolean errorMode = false;
	
	/*****************************
	 *Constructor for Calculator.*
	 *****************************/
	
	Calculator(){
		//Settings fonts and layout.
		Font buttonFont = new Font("Arial", Font.PLAIN, 17);
		Font tfFont = new Font("Arial", Font.BOLD, 12);
		setLayout(new FlowLayout());
		
		//Setting up text field for showing the result.
		tfResult = new TextField("", LENGTH);
		tfResult.setFont(tfFont);
		add(tfResult);
		//User cannot change text field with keyboard.
		tfResult.setEditable(false);
		
		//Setting layout of buttons and adding commands to them.
		b7 = new Button(" 7 ");
		b7.setFont(buttonFont);
		b7.setActionCommand("7");
		add(b7);
		
		b8 = new Button(" 8 ");
		b8.setFont(buttonFont);
		b8.setActionCommand("8");
		add(b8);
		
		b9 = new Button(" 9 ");
		b9.setFont(buttonFont);
		b9.setActionCommand("9");
		add(b9);
		
		bDiv = new Button(" / ");
		bDiv.setFont(buttonFont);
		bDiv.setActionCommand("DIV");
		add(bDiv);
		
		b4 = new Button(" 4 ");
		b4.setFont(buttonFont);
		b4.setActionCommand("4");
		add(b4);
		
		b5 = new Button(" 5 ");
		b5.setFont(buttonFont);
		b5.setActionCommand("5");
		add(b5);
		
		b6 = new Button(" 6 ");
		b6.setFont(buttonFont);
		b6.setActionCommand("6");
		add(b6);

		bMul = new Button(" * ");
		bMul.setFont(buttonFont);
		bMul.setActionCommand("MUL");
		add(bMul);
		
		b1 = new Button(" 1 ");
		b1.setFont(buttonFont);
		b1.setActionCommand("1");
		add(b1);
		
		b2 = new Button(" 2 ");
		b2.setFont(buttonFont);
		b2.setActionCommand("2");
		add(b2);
		
		b3 = new Button(" 3 ");
		b3.setFont(buttonFont);
		b3.setActionCommand("3");
		add(b3);
		
		bSub = new Button(" - ");
		bSub.setFont(buttonFont);
		bSub.setActionCommand("SUB");
		add(bSub);
		
		b0 = new Button(" 0 ");
		b0.setFont(buttonFont);
		b0.setActionCommand("0");
		add(b0);
		
		bDot = new Button(" . ");
		bDot.setFont(buttonFont);
		bDot.setActionCommand("DOT");
		add(bDot);

		bEq = new Button(" = ");
		bEq.setFont(buttonFont);
		bEq.setActionCommand("EQ");
		add(bEq);
		
		bAdd = new Button(" +");
		bAdd.setFont(buttonFont);
		bAdd.setActionCommand("ADD");
		add(bAdd);
		
		bCE = new Button(" CE ");
		bCE.setFont(buttonFont);
		bCE.setActionCommand("CE");
		add(bCE);
		//End of setting up the buttons.
		
		
		//Setting up the frame for the window.
		setTitle("Calculator");
		setSize(160, 240);
		setVisible(true);
		
		
		//ActionListener for closing the window.
		addWindowListener(this);
	}//End of constructor.
	
	/**********************
	 *Setters and getters:*
	 **********************/
	
	//Public method to set a text in the text field.
	public void settfResult(String value){
		//Logic for finding numbers too large to display in the text field.
		if(value.indexOf('.') > LENGTH || (value.indexOf('.') < 0 && value.length() > LENGTH)){
			setErrorMode("TooLargeNumber");
		}else{
			//Cutting off the decimals that doesn't fit in the text field.
			while(value.length() > LENGTH){
				value = value.substring(0, value.length() - 1);
			}
			//Displaying the text.
			tfResult.setText(value);
		}
	}
	
	//Reading the current String from the text field.
	public String gettfResult(){
		return tfResult.getText();
	}
	
	//Setter and getter for lastKeyOperator.
	public void setLastKeyOperator(boolean b){
		lastKeyOperator = b;
	}
	
	public boolean getLastKeyOperator(){
		return lastKeyOperator;
	}
	
	//Setter and getter for the subresult in BigDecimal container.
	public void setResult(BigDecimal value){
		//Setter for empty container.
		if(this.results.size() == 0){
			this.results.add(value);
		}else{
			//The size of the container cannot be larger than 1.
			this.results.set(0, value);
		}
	}
	
	public BigDecimal getResult(){
		return this.results.get(0);
	}
	
	//Empties the container. Public method.
	public void clearResult(){
		this.results.clear();
	}
	
	//Check the size of the container.
	//Checks actually whether there's a BigDecimal in container or not.
	public int getResultListSize(){
		return this.results.size();
	}
	
	
	//Setter and getter for error mode.
	public void setErrorMode(String error){
		this.errorMode = true;
		settfResult(error);
	}
	
	public boolean getErrorMode(){
		return this.errorMode;
	}
	
	//Public method to clear the error mode.
	public void clearErrorMode(){
		//Clear the flag.
		this.errorMode = false;
		//Clear the text field.
		settfResult("");
		//Empty the BigDecimal container.
		clearResult();
	}
	
	
	/****************
	 *Start of main.*
	 ****************/
	
	public static void main(String[] args) {
		//Create calculator.
		//Calculator must have a name.
		//It's referred when creating listeners.
		//Listeners must know what they are listening.
		Calculator calc = new Calculator();
		//Constructor for listener for number buttons.
		NumberListener bNumL = new NumberListener(calc);
		//Adding NumberListener to number buttons.
		calc.b0.addActionListener(bNumL);
		calc.b1.addActionListener(bNumL);
		calc.b2.addActionListener(bNumL);
		calc.b3.addActionListener(bNumL);
		calc.b4.addActionListener(bNumL);
		calc.b5.addActionListener(bNumL);
		calc.b6.addActionListener(bNumL);
		calc.b7.addActionListener(bNumL);
		calc.b8.addActionListener(bNumL);
		calc.b9.addActionListener(bNumL);
		//Constructor for OperatorListener.
		OperatorListener bOpeL = new OperatorListener(calc);
		//Adding OperatorListener for operator buttons.
		calc.bDot.addActionListener(bOpeL);
		calc.bAdd.addActionListener(bOpeL);
		calc.bSub.addActionListener(bOpeL);
		calc.bMul.addActionListener(bOpeL);
		calc.bDiv.addActionListener(bOpeL);
		calc.bEq.addActionListener(bOpeL);
		calc.bCE.addActionListener(bOpeL);
	}//End of main.
	
	/***************
	 *Window Closer*
	 ***************/
	
	//Exit program when exit button is pressed.
	public void windowClosing(WindowEvent onClose){
		System.exit(0);
	}
	//No action needed for these.
	//Implementation must be present though, thus empty implementations.
	public void windowClosed(WindowEvent evt){}
	public void windowActivated(WindowEvent evt){}
	public void windowDeactivated(WindowEvent evt){}
	public void windowIconified(WindowEvent evt){}
	public void windowDeiconified(WindowEvent evt){}
	public void windowOpened(WindowEvent evt){}
}
