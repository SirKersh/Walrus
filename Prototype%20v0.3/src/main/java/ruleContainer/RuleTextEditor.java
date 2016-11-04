package ruleContainer;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;

import driver.Prototype;

/**
 * This simple java text editor was taken from a YouTube tutorial video. All rights go to them.
 * https://www.youtube.com/watch?v=UghqfpA2zy4
 *
 */
public class RuleTextEditor extends JFrame
{
	private JTextArea textArea = new JTextArea(20, 60);
	private JFileChooser fc = new JFileChooser("src//main//resources//rules");
	private Prototype p;
	
	public RuleTextEditor(Prototype P)
	{
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		//Filter for .drl files
		FileFilter txtFilter = new FileNameExtensionFilter("drools file (*.drl)", "drl");
		fc.setFileFilter(txtFilter);
		
		//menu and menu items
		add(scrollPane);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu file = new JMenu("File");
		menuBar.add(file);
		file.add(Open);
		file.add(Save);
		file.addSeparator();
		file.add(Exit);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);	
		p=P;
	}
	
	//actions
	Action Open = new AbstractAction("Open File"){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				openFile(fc.getSelectedFile().getAbsolutePath());
			}
			
		}
	};
	
	Action Save = new AbstractAction("Save File"){
		@Override
		public void actionPerformed(ActionEvent e){
			saveFile();
		}
	};
	
	Action Exit = new AbstractAction("Exit"){
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
		
	};
	
	//methods
	public void openFile(String filename)
	{
		FileReader fr = null;
		try{
			fr = new FileReader(filename);
			textArea.read(fr, null);
			fr.close();
			setTitle(filename);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void saveFile(){
		if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			FileWriter fw = null;
			try{
				fw = new FileWriter(fc.getSelectedFile().getAbsolutePath() + ".drl");
				textArea.write(fw);
				fw.close();
				
				RuleEditor rEditor = new RuleEditor(p.getKfs(),p.getKs());
				
				rEditor.addRule(fc.getSelectedFile().getName()+".drl");
				p.rebuildKFS();
				
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
