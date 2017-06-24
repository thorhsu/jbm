package com.painter.util;
//import com.singularsys.jep.Jep; 
import org.nfunk.jep.*;
//import org.nfunk.jep.e
//import com.singularsys.jep.JepException; 
public class SimpleExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JEP jep = new JEP(); //一个数学表达式 
		//JEP parser = new JEP();
		String expr  = "(($a+$b)*($c))"; //给变量赋值 
		jep.addVariable("$a", 100); 
		jep.addVariable("$b", 10); 
		jep.addVariable("$c", 10); 
		jep.addVariable("$d", 10); 		
		try { //执行 
			jep.addStandardFunctions();
			//((Object) jep).addStandardFunction();
			jep.parseExpression(expr);
			// handling errors while parsing
			double value = jep.getValue();
			
			
			//jep.parse(exp); 
			//Object result = jep.evaluate(); 
			System.out.println("计算结果： " + value); } 
		catch (Exception e) { 
			System.out.println("An error occured: " + e.getMessage()); 
		} 

		//String expr = "1 + 2 + 3"; 

		// handling errors during evaluation		
		
	}

}

