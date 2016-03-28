/**
 * 
 * Authors: Abderrahim Si zinai et Mohamed Ibrihen
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;


public class ViewResult extends JSplitPane   implements ChangeListener{
	private JFrame closeFrame;
    private JPanel dataPane;
    private JTabbedPane tablePane;
    private JScrollPane scorlFilePanel;
    private JTextArea contenufileSave;
    private JButton start;
    private JButton save;
    private JTextField nameFile ;
    private JPanel resultsPanel;
    private JTabbedPane resyltTabePane;
    private JScrollPane scorllPaneTable; 
    private static TextArea filecahine;
    private  JSpinner fixedSeuil;
    private double seuil=0.0;
	private JTable table;
    private DefaultTableModel modelTable ;
    private TitledBorder contenue;
    private TitledBorder resultats;
    private TitledBorder outils;
    private TitledBorder sauvegarde;
    private JButton loadFile;
    private PanelGauche PanelGauche;
    private JFileChooser fileChooser;
    private String inputFileLocation;
    private List<ResultOfItem> resultItem ;
    private List<List<ResultOfItem>>iterationsResults;
    int i;
//	JButton prcButton;
//	JButton svtButton;
	    
    public ViewResult(){
		createView();
		placeComponent();
		createControler();
		resultItem=new ArrayList<ResultOfItem>();
    }
    
    public void Display(){
    	closeFrame.setVisible(true);
    	closeFrame.setLocation(null);
    }
	
    
    /** 
     * creation de la vue
     * */
	public void createView(){
		loadFile =new JButton("loadFile");
		iterationsResults=new ArrayList<List<ResultOfItem>>();
		tablePane = new JTabbedPane();
		JPanel panel = new JPanel(); 
		scorlFilePanel = new JScrollPane();
		contenufileSave = new JTextArea();
	    
         start = new JButton("executer l'algorithme close");
         save = new JButton("Eregistrer les résultats ");
         nameFile= new JTextField(20);
	     resyltTabePane = new JTabbedPane();
	     scorllPaneTable = new JScrollPane();

	     filecahine = new TextArea();
	     contenufileSave.setColumns(20);
	     contenufileSave.setRows(5);
	     scorlFilePanel.setViewportView(contenufileSave);
	     scorllPaneTable.setViewportView(table);
	    
         double seuilSupport = 0.0;                                                                  
         SpinnerModel model = new SpinnerNumberModel(seuilSupport, seuilSupport- 0, seuilSupport + 1,0.100000000000000000);    
         model.addChangeListener(this);
         fixedSeuil = new JSpinner(model);
         Component mySpinnerEditor = fixedSeuil.getEditor();
         JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor) mySpinnerEditor).getTextField();
         jFormattedTextField.setColumns(3);
	         
         table = new JTable();
         table.setSize(WIDTH, 800);
         modelTable = new DefaultTableModel();
         modelTable.addColumn("Générateurs");
         modelTable.addColumn("Fermeture");
         modelTable.addColumn("Support");
         modelTable.addColumn("Régle");
         modelTable.addColumn("Lift");
         
         table.setModel(modelTable);
         table.getColumnModel().getColumn(0).setPreferredWidth(150);
         table.getColumnModel().getColumn(1).setPreferredWidth(150);
         table.getColumnModel().getColumn(3).setPreferredWidth(400);
         table.setBackground(Color.cyan);
         scorllPaneTable.setViewportView(table);
         contenue = BorderFactory.createTitledBorder("Contenue du fileSave de test");
         resultats = BorderFactory.createTitledBorder("Résultats aprés execution de Close");
         outils = BorderFactory.createTitledBorder("outils");
         sauvegarde = BorderFactory.createTitledBorder("sauvegarde");
	}
	
	
	/**
	 *disposition des panels 
	 */
	public void placeComponent(){
			JPanel p=new JPanel(new BorderLayout());{
				
				JPanel z=new JPanel();{
					z.add(loadFile );
				}
				p.add(z , BorderLayout.NORTH);
				p.add(scorlFilePanel , BorderLayout.CENTER);
			}
			p.setBorder(contenue);
			scorllPaneTable.setBorder(resultats);
			
			JPanel q=new JPanel();{
				q.add(new JLabel("Seuil : "));
				q.add(fixedSeuil);
				q.add(new JLabel(" "));
				q.add(new JLabel(" "));
				q.add(start);
			}
			
			q.setBorder(outils);
			JPanel r=new JPanel();{
				r.add(new JLabel("nom du fichier :"));
				r.add(nameFile);
				r.add(save);
			}
			
			r.setBorder(sauvegarde);
	        JPanel base     = new JPanel (new BorderLayout());
	        base.add(q , BorderLayout.NORTH);
	        base.add(r , BorderLayout.SOUTH);
	        base.add(scorllPaneTable , BorderLayout.CENTER);
			setDividerLocation(351);
			setDividerSize(2);
			setTopComponent(p);
			setRightComponent(base);
		
	}
	
	
	/** 
	 * controlleurs
	 * */
	public void createControler(){	
		
		fileChooser= new JFileChooser() ;
        loadFile.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 try {
                	 int dd=fileChooser.showOpenDialog(PanelGauche.getTree());
         	    	if(dd == JFileChooser.APPROVE_OPTION){
         			 inputFileLocation= fileChooser.getSelectedFile().toString();
                     int i;
                     String text="";
         	    	 List<String> lignes =  Files.readAllLines( 
         	    			   
         	    			FileSystems.getDefault().getPath(inputFileLocation), StandardCharsets.ISO_8859_1); 
         	    			 for (String ligne : lignes){
         	    				 text=text+ligne+"\n";
         	    				 
         	    			       i=0;
         	    			       ResultOfItem row=new ResultOfItem();
         	    			       ligne.replaceAll("\\s+","");
         	    			       String identifiant="";
         	    			         
         	    			        while(!(ligne.charAt(i)=='|')&& i<ligne.length()){
         	    			        	identifiant=identifiant+=ligne.charAt(i);
         	    			        	i++;
         	    			         
         	    			        }
         	    			        row.setId(identifiant);
         	    			        String item;
         	    			        i++;
         	    			      
         	    			       while(i<ligne.length()){
         	    			        	 item="";
         	    			        	while(i<ligne.length()&&!(ligne.charAt(i)=='|')){
         	    			        		
         	    			        		item=item+ligne.charAt(i);
         	    			        		 
         	    			        		i++;
         	    			        	}
         	    			        	 
         	    			        	row.getItems().add(item);
         	    			        	i++;
         	    			        }
         	    			       resultItem.add(row);
         	    			 
         	    			 }
         	    			   getTextArea().setText(text);
         	    			   PanelGauche.construireTree(resultItem)  ; 
         	    			
         		     }
				} catch (IOException e){
					e.printStackTrace();
				}
              }
         });
    	 
        PanelGauche = new PanelGauche();
		start.addActionListener(new java.awt.event.ActionListener() {
	         public void actionPerformed(java.awt.event.ActionEvent evt) {
	         
	        
	         }
	     });
		         
		         
        
         save.addActionListener(new java.awt.event.ActionListener() {
        	 public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	 try {
							saveInFile("Out2");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            
		             }
		         });
		
	}
	
	public void openFile() {
    	File file=new File("doc/i.html");
        Path pat=file.toPath();
		 System.out.println(pat);
			Desktop desktop = null;
			if (Desktop.isDesktopSupported()) {
				desktop = Desktop.getDesktop();
			}
			try {
				desktop.open(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	
	public JTextArea getTextArea() {
		return contenufileSave;
	}

	public double getSeuil() {
		return seuil;
	}
	
	public JButton getBouttonStart() {
		return start;
	}

	public static TextArea getfilecahine() {
		return filecahine;
	}
	
	public List<ResultOfItem> getResultItem(){
		return resultItem;
	}
	
	public List<List<ResultOfItem>> getIterationsResults() {
		return iterationsResults;
	}
	
	public void setTextArea(JTextArea contenufileSave) {
		this.contenufileSave = contenufileSave;
	}
	
	public void setBouttonStart(javax.swing.JButton start) {
		this.start= start;
	}

	

	public static void setfilecahine(TextArea filecahine) {
		ViewResult.filecahine = filecahine;
	} 
	
	public void stateChanged(ChangeEvent e) {
		seuil=  Double.valueOf(fixedSeuil.getModel().getValue().toString() );
		 
	}
	

	public void setIterationsResults(List<List<ResultOfItem>> iterationsResults) {
		this.iterationsResults = iterationsResults;
	}
	
	public void setSeuil(double seuil) {
		this.seuil = seuil;
	}
	
	public void insertDataInTable(int i){
		int j=modelTable.getRowCount();
		Vector vec=modelTable.getDataVector();
		vec.clear(); 
			if(i>=0 && i <iterationsResults.size()){
				for(ResultOfItem row:iterationsResults.get(i)){
					row.print();
					modelTable.addRow(new Object[]{row.getGenerateurs().toString(),row.getFermeture().toString(),String.valueOf(row.getSupport()),row.getRegle(),"1",row.getLift()});
					
				}
			
			}

	}
	
	/** 
	 * fonction pour la sauvegarde des resultats dans un fichier txt
	 * @param name: nom du fichier
	 * */
	
	public void saveInFile(String name) throws IOException{
		java.io.File fileSave = new java.io.File(nameFile.getText()+".txt");
		if (!fileSave.exists()) {
			fileSave.createNewFile();  
		}
		
		int k=0;
	   	String cahine="----------------- execution de l'algorithme Close -----------------";
		       cahine+="\n \n \n \n \n \n";
		       
		       for(List<ResultOfItem> rslt:iterationsResults){
		    	     cahine+="-----------------------------------------------------\n";
		    	   cahine+="Les resultat de l'iteration "+k+" : \n \n";
		    	     cahine+="------------------------------------------------------\n";
		    	     cahine+="\n";
		    	   for(ResultOfItem elm:rslt){
		    		   for(String generator :elm.getGenerateurs()){
		    			   cahine+=generator;
		    			}
		    		  cahine+="|   ";
		    		  cahine+="{  ";
		    		  for(String str:elm.getFermeture()){
		    			  cahine+=str;
		    			  cahine+=", ";
		    				}
		    		  cahine+="}"  ;
		    		  cahine+="|   ";
		    		   
		    		  cahine+=elm.getSupport();
		    		  cahine+="|   ";
		    		  cahine+=elm.getRegle();
		    		  cahine+="   |   ";
		    		  cahine+="Confiance = 1";
		    		  cahine+="|   ";
		    		  cahine+="Lift =  "+elm.getLift();
		    		  cahine+="\n";
		    	   }
		    	   k++ ;
		    	   cahine+="\n \n \n \n";
		       }
		java.io.FileOutputStream streameSaveFile = new java.io.FileOutputStream(fileSave); 
		byte[] byteChaine = cahine.getBytes();
		streameSaveFile.write(byteChaine);
		streameSaveFile.flush();
		streameSaveFile.close();
		
	}
}
