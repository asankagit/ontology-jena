package org.apache.jena.example.helloworld;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.system.FactoryRDF;
import org.apache.jena.riot.system.FactoryRDFStd;

public class XmlMain {
	static TextField txt_newNode = new TextField ();
	String inputNode = "";
	
	  public static void main(String[] args) {
	    JFrame f = new JFrame();

	    f.setSize(300, 500);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JPanel pan = new JPanel(new GridLayout(1, 1));
	    XmlJTree myTree = new XmlJTree(null);
	    f.add(new JScrollPane(myTree));
	    JButton btn_addchild = new JButton("Add child");
	    JButton btn_addsybling = new JButton("Add sybling");
	    JButton btn_delete = new JButton("Delete");
	    JButton button = new JButton("Choose file");
	    
	    button.addActionListener(e -> {
	      JFileChooser chooser = new JFileChooser();
	      FileNameExtensionFilter filter = new FileNameExtensionFilter("XML file",
	          "xml");
	      chooser.setFileFilter(filter);
	      int returnVal = chooser.showOpenDialog(null);
	      if (returnVal == JFileChooser.APPROVE_OPTION) {
	        myTree.setPath(chooser.getSelectedFile().getAbsolutePath());
	      }
	    });
	    
	    pan.add(button);
	    pan.add(btn_addchild);
	    pan.add(btn_addsybling);
	    pan.add(btn_delete);
	    pan.add(txt_newNode);
	    f.add(pan, BorderLayout.SOUTH);
	    f.setVisible(true);
	    
	    
	    
	    myTree.addTreeSelectionListener(new TreeSelectionListener() {
	    @Override
	  	  public void valueChanged(TreeSelectionEvent e) {
	  	     DefaultMutableTreeNode selectedNode =  (DefaultMutableTreeNode)myTree.getLastSelectedPathComponent(); 
	  	     System.out.println(selectedNode);  
	  	     //selectedNode.removeFromParent();
	  	     
	  	     
	  	   TreePath treepath = e.getPath();
           System.out.println("Java: " + treepath.getLastPathComponent());
           Object elements[] = treepath.getPath();
              for (int i = 0, n = elements.length; i < n; i++) {
                  System.out.print("->" + elements[i]);
              
              }
	  	     
	  	    }

		
	  	  });
	    
	    
	    btn_addchild.addActionListener( e->{
	    	//XmlDataWrite dw = new XmlDataWrite();
	    	
	    	System.out.println();
	    	
	    });
	  }
	  
	  
	}
