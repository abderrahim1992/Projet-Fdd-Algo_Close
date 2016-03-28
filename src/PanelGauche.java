
/**
 * 
 * Authors: Abderrahim Si ziani & Mohamed Ibrihen
 */

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

public class PanelGauche extends JTabbedPane {
	
	private JScrollPane jScrollPaneT;
	private JTree tree ;
	private DefaultMutableTreeNode racineTree;
	  
	public PanelGauche(){
	    JPanel p= new JPanel();
	    p.setPreferredSize(new Dimension(100,1050));
	    racineTree =  new DefaultMutableTreeNode("Data") ;
		DefaultTreeModel treeModel = new DefaultTreeModel(racineTree);
	    tree = new javax.swing.JTree(treeModel);
	    tree.setShowsRootHandles(true);
	    jScrollPaneT = new JScrollPane();
	    jScrollPaneT.setViewportView(tree);   
	    p.add(jScrollPaneT);    
	    addTab("File", p);
	
	 }
	 
	public JTree getTree() {
		return tree;
	}  
	
	 public void construireTree(List<ResultOfItem> results){
		 for(ResultOfItem result:results){
			 DefaultMutableTreeNode noeud=new DefaultMutableTreeNode(result.getId());
			  for(String elm:result.getItems()){
				  noeud.add(new DefaultMutableTreeNode(elm));
			  }
			 racineTree.add(noeud);
		 }
	 }
	 
}
