import java.awt.event.*;



//Defines actions for number buttons.
public class NumberListener implements ActionListener {

	//Calculator to listen.
	private Calculator calc;
	
	//Constructor for listener.
	//Listens the calculator, where the number buttons are.
	public NumberListener(Calculator calc){
		this.calc = calc;
	}
	
	//On pressing the number button:
	public void actionPerformed(ActionEvent numPress){
		//If error mode is on, it must be cleared before program can continue.
		if(calc.getErrorMode()){
			//Resets the calculator.
			calc.clearErrorMode();
		}
		//Checks whether the last key pressed was a operator key.
		if(calc.getLastKeyOperator()){
			//User wants to enter a new number after operator key.
			//Text field must be emptied before the new number can be entered.
			calc.settfResult("");
		}
		
		//After pressing number key, Calculator must know that the last key was number.
		calc.setLastKeyOperator(false);
		
		//Read current text being displayed in the text field.
		String number = calc.gettfResult();
		//If the text field consists only of the number zero, it is replaced.
		if(number.equals("0")){
			calc.settfResult(numPress.getActionCommand());
		//In any other case, when the user presses a number button
		//it is added at the end of the number currently being displayed in the text field.
		}else{
			calc.settfResult(number + numPress.getActionCommand());
		}
	}

}
