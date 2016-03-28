
/**
 * 
 * Authors: Abderrahim Si ziani et Mohamed Ibrihen
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

 
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
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

	 	private JFrame frame;
	    private static   MainView viewSource;
 	    private ViewResult resultView;
 	    private JSplitPane split;	
	    
	    public MainView() {
	    	frame=new JFrame();
	    	frame.setTitle("Close Algorithm");
	    	frame.setSize(1000,700);
	        initView();
	    }
         
	    private void initView() {
	     
	    	frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	    	resultView =new ViewResult();
	             resultView.getBouttonStart().addActionListener(new java.awt.event.ActionListener() {
		             public void actionPerformed(java.awt.event.ActionEvent evt) {
			             
		 	            execAlgorithm();
		             }
		         });
	             split = new JSplitPane();
		         split.setLeftComponent(null);
	             split.setRightComponent(resultView);
	             GroupLayout layout = new GroupLayout(frame.getContentPane());
	             frame.getContentPane().setLayout(layout);
	             layout.setHorizontalGroup(
	                 layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                 .addComponent(split, GroupLayout.DEFAULT_SIZE, 1011, Short.MAX_VALUE)
	             );
	             layout.setVerticalGroup(
	                 layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                 .addComponent(split, GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
	             );
   
     
	             frame.pack();
      
	    }
	    
	    public void execAlgorithm(){
	    			
	    			List<ResultOfItem> results= resultView.getResultItem();
			    	Close CLoseAlgo=new Close(results,this.resultView.getSeuil());
			    	CLoseAlgo.executeClose();
			    	resultView.setIterationsResults(CLoseAlgo.getResultats());
			    	resultView.insertDataInTable(0);
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
	    			viewSource= new MainView();
	    			viewSource.frame.setVisible(true);
	    		}
	    	});
	    }

		@Override
		public void valueChanged(TreeSelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}


}
