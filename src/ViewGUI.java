
     
/**
 * 
 * Authors: Kheireddine Berkane et Amazigh Amrane
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

 
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;
import java.util.ArrayList;
 

import javax.swing.UIManager;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

public class ViewGUI extends javax.swing.JFrame   implements TreeSelectionListener{

	    
	    private static   ViewGUI viewGUI;
 	    private javax.swing.JSplitPane jSplitPaneContainer;
 	    private ViewResult mainAreaPane;
	    
	    
	    
	    /** Creation de la fenetre principale  GUI */
	    public ViewGUI() {
	    	
	        this.setTitle("Close Algorithm");
	        setSize(1000,700);
	        initComponents();

	    }
         
	    private void initComponents() {
	     
	    	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	    	mainAreaPane =new ViewResult();
	    	
	    	
	             
	             mainAreaPane.getjButton1().addActionListener(new java.awt.event.ActionListener() {
		             public void actionPerformed(java.awt.event.ActionEvent evt) {
			             System.out.println("je suis la !!!");
		 	            launchAlgorithm();
		             }
		         });
	             jSplitPaneContainer = new javax.swing.JSplitPane();
		         jSplitPaneContainer.setDividerLocation(201);
		         jSplitPaneContainer.setDividerSize(3);
		         
		         
	             //Add navigation area to the container
		         jSplitPaneContainer.setLeftComponent(null);
		         
		         //Add main area to the container
	             jSplitPaneContainer.setRightComponent(mainAreaPane);
	             
	             javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	             getContentPane().setLayout(layout);
	             layout.setHorizontalGroup(
	                 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                 .addComponent(jSplitPaneContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 1011, Short.MAX_VALUE)
	             );
	             layout.setVerticalGroup(
	                 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                 .addComponent(jSplitPaneContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
	             );
   
      
	       
	          
	         pack();
      
	    }
	    
	    public void launchAlgorithm(){
	    			
	    			List<ItemRowData> rows= mainAreaPane.getItemRows();
			    	Close CLoseAlgo=new Close(rows,this.mainAreaPane.getSeuil());
			    	CLoseAlgo.launchAlgorithm();
			    	mainAreaPane.setIterationsResults(CLoseAlgo.getIterationsResults());
			    	mainAreaPane.insertDataIntoTable(0);
	    }
			    
public static void main(String args[])  {
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
           viewGUI= new ViewGUI();
           viewGUI.setVisible(true);
           
            
            
          
        }
    });
}

@Override
public void valueChanged(TreeSelectionEvent arg0) {
	// TODO Auto-generated method stub
	
}
}
