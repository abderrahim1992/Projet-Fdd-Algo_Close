
/**
 * 
 * Authors: Kheireddine Berkane et Amazigh Amrane
 */

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

public class ViewPanelLeft extends JTabbedPane {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jPanelFile;
	    private javax.swing.JScrollPane jScrollPane3;
	    private javax.swing.JTree jTreeFile ;
	    
	    private DefaultMutableTreeNode root;
	   
	    
	 public ViewPanelLeft(){
    jPanelFile = new javax.swing.JPanel();
    jPanelFile.setBackground(new java.awt.Color(255, 255, 255));
    jPanelFile.setPreferredSize(new java.awt.Dimension(100,1050));
    jPanelFile.setLayout(new GridLayout(1,1));
    
     
    //Les arbres sont généralement inseres dans des scrollPanel
  //La racine de l'arbre
    root =  new DefaultMutableTreeNode("Data") ;
	//Le modele pour insérer les element de l'arbre d'une maniere dynamique
	DefaultTreeModel treeModel = new DefaultTreeModel(root);
	
    jTreeFile = new javax.swing.JTree(treeModel);
    
    jTreeFile.setShowsRootHandles(true);
     
    jScrollPane3 = new javax.swing.JScrollPane();
   
    jScrollPane3.setViewportView(jTreeFile);   
    jPanelFile.add(jScrollPane3);    
    addTab("File", jPanelFile);
	
	 }



	public javax.swing.JTree getjTreeFile() {
		return jTreeFile;
	}
	 
	 public void fillTree(List<ItemRowData>rows){
		 for(ItemRowData row:rows){
			 DefaultMutableTreeNode node=new DefaultMutableTreeNode(row.getIdentifiant());
			  for(String elm:row.getItems()){
				  node.add(new DefaultMutableTreeNode(elm));
			  }
			 root.add(node);
		 }
		 
		 
	 }
	 
}
