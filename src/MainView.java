
     
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

public class MainView  implements TreeSelectionListener{

	    
	    private static   MainView viewGUI;
 	    private javax.swing.JSplitPane jSplitPaneContainer;
 	    private ViewResult mainAreaPane;
 	    private JFrame frame;
	    
	    
	    
	    /** Creation de la fenetre principale  GUI */
	    public MainView() {
	    	frame=new JFrame();
	    	frame.setTitle("Close Algorithm");
	    	frame.setSize(1000,700);
	        initComponents();

	    }
         
	    private void initComponents() {
	     
	    	frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
	             
	             javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
	             frame.getContentPane().setLayout(layout);
	             layout.setHorizontalGroup(
	                 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                 .addComponent(jSplitPaneContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 1011, Short.MAX_VALUE)
	             );
	             layout.setVerticalGroup(
	                 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                 .addComponent(jSplitPaneContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
	             );
   
      
	       
	          
	             frame.pack();
      
	    }
	    
	    public void launchAlgorithm(){
	    			
	    			List<ResultOfItem> rows= mainAreaPane.getItemRows();
			    	Close CLoseAlgo=new Close(rows,this.mainAreaPane.getSeuil());
			    	CLoseAlgo.launchAlgorithm();
			    	mainAreaPane.setIterationsResults(CLoseAlgo.getIterationsResults());
			    	mainAreaPane.insertDataIntoTable(0);
	    }
	    @Override
	    public void valueChanged(TreeSelectionEvent arg0) {
	    	// TODO Auto-generated method stub
	    	
	    }			    
	    public static void main(String args[])  {
	    	java.awt.EventQueue.invokeLater(new Runnable() {
	    		public void run() {
	    			
	    			try {
						UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					} catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			viewGUI= new MainView();
	    			viewGUI.frame.setVisible(true);
          
	    		}
	    	});
	    }


}
