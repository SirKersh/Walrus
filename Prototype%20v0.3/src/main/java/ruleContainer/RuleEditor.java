package ruleContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;

import driver.Prototype;

public class RuleEditor 
{
	//Array lists that represent all activated and unactivated rules.
	ArrayList<String> activeRules = new ArrayList<String>();
	ArrayList<String> inActiveRules = new ArrayList<String>();
	KieFileSystem kfs;
	KieServices ks;
	
	public RuleEditor(KieFileSystem kfs,KieServices ks)
	{
		this.kfs = kfs;
		this.ks = ks;
	}

	/**
	 * Displays all the active rules
	 * @return all active rules
	 */
	public String displayAllActiveRules(){
		String temp = "There are all the active rules: \n";
		for(String s: activeRules){
			temp += s + "\n";
		}
		
		if(activeRules.isEmpty())
			return "There are no active rules";
		
		return temp;
	}
	
	/**
	 * Displays all the inactive rules
	 * @return all inactive rules
	 */
	public String displayAllInActiveRules(){
		String temp = "There are all the inactive rules: \n";
		for(String s: inActiveRules){
			temp += s + "\n";
		}
		
		if(inActiveRules.isEmpty())
			return "There are no inactive rules";
		
		return temp;
	}
	
	/**
	 * Adds a .drl file to the current working kie file system.
	 * One should call rebuildKFS() after using this method
	 * @param fileName the filename of the file we are adding
	 * @throws FileNotFoundException
	 * 
	 * http://stackoverflow.com/questions/23784652/drools-knowle dgebase-
	 * deprecated
	 */
	public void addRule(String fileName) throws FileNotFoundException {
		if(activeRules.contains(fileName)){
			System.out.println("There already exists a drl file with this name.");
			return;
		}
		FileInputStream fis;
		fis = new FileInputStream("src/main/resources/rules/" + fileName);
		kfs.write("src/main/resources/" + fileName, ks.getResources().newInputStreamResource(fis));
		activeRules.add(fileName);
		if(inActiveRules.contains(fileName))
		{
			inActiveRules.remove(fileName);
		}
		//call rebuildKFS() after using this method
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
				removeRule(temp + ".drl");
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
				addRule(temp + ".drl");
		}
	}
	
	/**
	 * Remove a .drl file from the current kie file system
	 * One should call rebuildKFS() after using this method
	 * @param fileName the filename of the file you wish to remove
	 */
	public void removeRule(String fileName) {
		if(!activeRules.contains(fileName)){
			System.out.println("There does not exist a drl file with this name.");
			return;
		}
		kfs.delete("src/main/resources/" + fileName);
		activeRules.remove(fileName);
		inActiveRules.add(fileName);
		// call rebuildKFS() after using this method
	}
	
	/**
	 * Load all rules at the start of the program.
	 * One should call rebuildKFS() after using this method
	 * @throws FileNotFoundException
	 */
	public void loadAllRules() throws FileNotFoundException{
		ArrayList<String> fileNames = listFilesForFolder(new File("src/main/resources/rules"));
		FileInputStream fis;

		for(String fileName : fileNames){
			fis = new FileInputStream("src/main/resources/rules/" + fileName);
			kfs.write("src/main/resources/" + fileName, ks.getResources().newInputStreamResource(fis));
			activeRules.add(fileName);
		}
		// call rebuildKFS() after using this method
	}
	
	/**
	 * Helper method for loadAllRules(). 
	 * @param folder the current directory of the .drl files
	 * @return the list of all .drl files in directory
	 */
	private ArrayList<String> listFilesForFolder(final File folder) {
		ArrayList<String> drls = new ArrayList<String>();
	    for (final File fileEntry : folder.listFiles()) {
	         drls.add(fileEntry.getName());
	    }
	    return drls;
	}
	
}
