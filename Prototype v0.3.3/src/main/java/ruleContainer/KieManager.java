package ruleContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;

public class KieManager {
	static KieServices ks = KieServices.Factory.get();
	static KieContainer kContainer = ks.getKieClasspathContainer();
	static KieFileSystem kfs = ks.newKieFileSystem();
	public static KieBase kBase;
	public static ArrayList<String> activeRules = new ArrayList<String>();
	public static ArrayList<String> inActiveRules = new ArrayList<String>();
	
	// Updates the KFS on request. Should be called after every addition/removal to/from the KFS. 
	// Sets kBase as a newly created KieBase.
	static public void rebuildKFS(){
		KieBuilder kieBuilder = ks.newKieBuilder(kfs).buildAll();
		Results results = kieBuilder.getResults();
		if (results.hasMessages(Message.Level.ERROR)) {
			System.out.println(results.getMessages());
			System.out.println("Check rule directory for indicated error(s).");
			throw new IllegalStateException("### errors ###");
		}
		KieContainer kContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());

		KieBaseConfiguration config = ks.newKieBaseConfiguration();
	    config.setOption(EventProcessingOption.STREAM);
	    kBase = kContainer.newKieBase( config );
	}
	
	/**
	 * Adds a .drl file to the current working kie file system.
	 * @param fileName the filename of the file we are adding
	 * @throws FileNotFoundException
	 * 
	 * http://stackoverflow.com/questions/23784652/drools-knowle dgebase-
	 * deprecated
	 */
	static public void addRule(String fileName) throws FileNotFoundException {
		/*
		if(activeRules.contains(fileName)){
			System.out.println("There already exists a drl file with this name.");
			return;
		}*/
		
		FileInputStream fis;
		fis = new FileInputStream("src/main/resources/rules/" + fileName);
		kfs.write("src/main/resources/" + fileName, ks.getResources().newInputStreamResource(fis));
		activeRules.add(fileName);
		if(inActiveRules.contains(fileName))
		{
			inActiveRules.remove(fileName);
		}
		rebuildKFS();
	}
	
	/**
	 * Remove a .drl file from the current kie file system
	 * @param fileName the filename of the file you wish to remove
	 */
	static public void removeRule(String fileName) {
		if(!activeRules.contains(fileName)){
			System.out.println("There does not exist a drl file with this name.");
			return;
		}
		kfs.delete("src/main/resources/" + fileName);
		activeRules.remove(fileName);
		inActiveRules.add(fileName);
		rebuildKFS();
	}
	
	/**
	 * For initialization. Loads all drl files into the KFS.
	 * @throws FileNotFoundException
	 */
	static public void loadAllRules() throws FileNotFoundException{
		ArrayList<String> fileNames = listFilesForFolder(new File("src/main/resources/rules"));
		FileInputStream fis;

		for(String fileName : fileNames){
			fis = new FileInputStream("src/main/resources/rules/" + fileName);
			kfs.write("src/main/resources/" + fileName, ks.getResources().newInputStreamResource(fis));
			activeRules.add(fileName);
		}
		rebuildKFS();
	}
	
	/**
	 * Helper method for loadAllRules(). 
	 * @param folder the current directory of the .drl files
	 * @return the list of all .drl files in directory
	 */
	static private ArrayList<String> listFilesForFolder(final File folder) {
		ArrayList<String> drls = new ArrayList<String>();
	    for (final File fileEntry : folder.listFiles()) {
	         drls.add(fileEntry.getName());
	    }
	    return drls;
	}
}
