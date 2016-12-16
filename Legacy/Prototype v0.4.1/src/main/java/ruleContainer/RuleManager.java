package ruleContainer;

import java.io.FileNotFoundException;
import java.util.Scanner;


public class RuleManager 
{
	//Array lists that represent all activated and unactivated rules.

	/**
	 * Displays all the active rules
	 * @return all active rules
	 */
	public String displayAllActiveRules(){
		String temp = "There are all the active rules: \n";
		for(String s: KieManager.activeRules){
			temp += s + "\n";
		}
		
		if(KieManager.activeRules.isEmpty())
			return "There are no active rules";
		return temp;
	}
	
	public void addRule(String fileName) throws FileNotFoundException{
		KieManager.addRule(fileName);
	}
	
	/**
	 * Displays all the inactive rules
	 * @return all inactive rules
	 */
	public String displayAllInActiveRules(){
		String temp = "There are all the inactive rules: \n";
		for(String s: KieManager.inActiveRules){
			temp += s + "\n";
		}
		
		if(KieManager.inActiveRules.isEmpty())
			return "There are no inactive rules";
		return temp;
	}

	/**
	 * Toggles rules on and off and then updates the kie file system.
	 * One should call rebuildKFS() after using this method
	 * @param scan scanner used for input
	 * @throws FileNotFoundException
	 */
	public void toggleRules(Scanner scan) throws FileNotFoundException{
		String temp;
		System.out.println("Below are all the rules that are toggled on:");
		System.out.println(displayAllActiveRules());
		System.out.println("Please enter the rule name(s) you wish to toggle off, 1 per line, and enter done when finished.");
		while(true)
		{
			temp = scan.nextLine();
			if(temp.equals("done"))
				break;
			else
				KieManager.removeRule(temp + ".drl");
		}
		
		System.out.println("Below are all the rules that are toggled off:");
		System.out.println(displayAllInActiveRules());
		System.out.println("Please enter the rule name(s) you wish to toggle on, 1 per line, and enter done when finished.");
		while(true)
		{
			temp = scan.nextLine();
			if(temp.equals("done"))
				break;
			else
				KieManager.addRule(temp + ".drl");
		}
	}
}
