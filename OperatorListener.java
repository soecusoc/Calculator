import java.awt.event.*;
import java.math.BigDecimal;



//Defines actions for operator buttons.
public class OperatorListener implements ActionListener {
	
	/************************************
	 *Private variables and constructor:*
	 ************************************/
	
	//Supported operators.
		private enum Operator {
			DIV, MUL, SUB, DOT, EQ, ADD, CE
		}
		
		//Calculator for OperatorListener to listen.
		private Calculator calc;
		//Last operator must be remembered for the calculations.
		private Operator lastOperator;
		
		//Constructor.
		//Sets given calculator for OperatorListener to listen.
		public OperatorListener(Calculator calc){
			this.calc = calc;
		}
		
	/**********************************
	 *Private functions for operators:*
	 **********************************/
	
	//User wants to add dot in other words wants to create decimal number.
	private void addDot(){
		//If text field is initially empty, adds '0.' to the display.
		if(calc.gettfResult().equals("")){
			calc.settfResult("0.");
		//Decimal dot can be added only, if there is no other decimal dot.
		}else if(calc.gettfResult().indexOf('.') < 0){
			//Adds the dot.
			calc.settfResult(calc.gettfResult() + ".");
		}
	}
	
	//CE: resets the calculator.
	private void clearEverything(){
		calc.settfResult("");
		calc.clearResult();
	}
	
	//Actions for +, -, * and / operators.
	private void useOperator(Operator operator){
		//Last key is now operator key.
		calc.setLastKeyOperator(true);
		
		//If the text field is empty when an operator button is pressed
		//no calculation is done.
		//Only the last operator is updated/noted.
		if(calc.gettfResult().equals("")){
			this.lastOperator = operator;
			
		//BigDecimal container is empty.
		//This means there is no former input.
		//This is the number the user wants to add, subtract, multiply or divide.
		}else if(calc.getResultListSize() == 0){
			//Last operator is updated/noted.
			this.lastOperator = operator;
			//Number from the display is saved in the container.
			calc.setResult(new BigDecimal(calc.gettfResult()));
			//Display erased.
			calc.settfResult("");
		//BigDecimal container is full.
		//There is a number the user wants to add, subtract, multiply or divide.
		//This is the number the user wants to add, subtract, multiply or divide with.
		}else{
			//calculate throws an error, if divisor is zero.
			try{
				//Calculate the result from given numbers with given operator.
				BigDecimal result = calculate(calc.getResult(), new BigDecimal(calc.gettfResult()), this.lastOperator);
				//Last operator is updated/noted.
				//After the calculation is done.
				//User may want to calculate with the previous result.
				this.lastOperator = operator;
				//Display the result.
				calc.settfResult(result.toPlainString());
				//The result is the new subresult.
				calc.setResult(result);
			}catch(ArithmeticException e){
				//User tries to divide by zero.
				//Error mode is set.
				calc.setErrorMode("DivisionByZero");
			}
		}
	}
	
	//Private function to operate the given numbers with the given operator.
	//May throw an exception.
	private BigDecimal calculate(BigDecimal val1, BigDecimal val2, Operator operator) throws ArithmeticException {
		//Switch/case for the given operator.
		switch (operator){
			//Addition.
			case ADD:
				return val1.add(val2);
			//Subtraction.
			case SUB:
				return val1.subtract(val2);
			//Multiplication.
			case MUL:
				return val1.multiply(val2).stripTrailingZeros();
			//Division.
			case DIV:
				//Cannot divide by zero.
				if(val2.compareTo(BigDecimal.ZERO) == 0){
					//Throws an exception.
					throw new ArithmeticException("DivisionByZero");
				//Division with a non-zero divisor.
				}else{
					//The scale is larger than the largest displayable number by one.
					//This scale is guaranteed to be precise enough for rounding for the display.
					return val1.divide(val2, Calculator.LENGTH + 1, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
				}
			default:
				//User cannot give unknown operator.
				//default is never reached.
				return new BigDecimal(0.0);
		}
	}
	
	//The user presses equals-button.
	private void calculateResult(){
		//Nothing happens if both numbers are not given.
		if(calc.gettfResult().equals("") || calc.getResultListSize() == 0){}
		//The user has given two numbers.
		else{
			//calculate may throw an exception.
			try{
				//Calculate with the given numbers and the given operator.
				calc.settfResult(calculate(calc.getResult(), new BigDecimal(calc.gettfResult()), this.lastOperator).toPlainString());
				//Container is emptied.
				//If the user wants to use the result as a subresult, it can be found from the dispay.
				calc.clearResult();
				//Last key is now operator, next input must reset the display.
				calc.setLastKeyOperator(true);
			}catch(ArithmeticException e){
				//Division by zero. Error mode turns on.
				calc.setErrorMode("DivisionByZero");
			}
		}
	}

	/***************************
	 *actionPerformed-function:*
	 ***************************/
	
	//Defines the actions, when operator button is pressed.
	public void actionPerformed(ActionEvent buttonPress){
		//Clears the error mode if necessary.
		if(calc.getErrorMode()){
			calc.clearErrorMode();
		}
		
		//Gets the operator the user pressed.
		//The command is a String.
		String command = buttonPress.getActionCommand();
		//String to Operator (enum-class) -conversion.
		Operator operator = Operator.valueOf(command);
		
		//Switch/case for operator given by the user.
		switch (operator) {
		//The user wants to input a decimal number.
		case DOT:
			addDot();
			break;
		//The user wants to clear everything in other words reset the calculator.
		case CE:
			clearEverything();
			break;
		//The user presses the equals-button. Wants to calculate the result.
		case EQ:
			calculateResult();
			break;
		//None of the above.
		//The user wants to add, subtract, multiply or divide.
		default:
			useOperator(operator);
			break;
		}	
	}
}
