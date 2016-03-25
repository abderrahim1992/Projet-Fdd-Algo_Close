/**
 * 
 * Authors: Kheireddine Berkane et Amazigh Amrane
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
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
	private javax.swing.JMenu FileMenu;
    private javax.swing.JMenuItem openFItem;
    private javax.swing.JMenuItem  Quitter;
	private   JPanel  panD ;
    private javax.swing.JPanel dataPane;
    private javax.swing.JTabbedPane jTabbedDataPane;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private JTextArea contenuFichier;
    private javax.swing.JButton start;
    private javax.swing.JButton save;
    private javax.swing.JTextField nameFile ;
    private javax.swing.JPanel resultsPanel;
    private javax.swing.JTabbedPane jTabbedPaneResult;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jPanelRer ; 
    private static TextArea textArea1Rer;
    private  JSpinner fixedSeuil;
    private double seuil=0.0;
	private JTable jTable1;
    private DefaultTableModel modelTable ;
    private TitledBorder title;
    private JButton loadFile;
    private ViewPanelLeft viewPanelLeft;
    private JFileChooser jFileChooser;
    private String inputFileLocation;
    private List<ResultOfItem> rows ;
	    
    JButton svtButton;
    JButton prcButton;
    
    private List<List<ResultOfItem>>iterationsResults;
    int index;
	    
	    
    public ViewResult(){
		createView();
		placeComponent();
		createControler();
		rows=new ArrayList<ResultOfItem>();
    }
    
    public void Display(){
    	closeFrame.setVisible(true);
    	closeFrame.setLocation(null);
    }
	
	public void createView(){
		loadFile =new JButton("loadFile");
		iterationsResults=new ArrayList<List<ResultOfItem>>();
		jTabbedDataPane = new JTabbedPane();//for data editor
		jPanel5 = new JPanel(); // data editor panel
		jScrollPane1 = new JScrollPane();
		contenuFichier = new JTextArea();
	    
         start = new JButton("start Close");//submit button
         //jButton2 = new javax.swing.JButton();//clear button
         save = new JButton("save Result");
         nameFile= new JTextField(20);
	     jTabbedPaneResult = new JTabbedPane();
	     jScrollPane2 = new JScrollPane();

	     textArea1Rer = new TextArea();
	     contenuFichier.setColumns(20);
	     contenuFichier.setRows(5);
	     jScrollPane1.setViewportView(contenuFichier);
	     jScrollPane2.setViewportView(jTable1);
	      //add spinner for "le min support"
         double seuilSupport = 0.0;                                                                  
         SpinnerModel model = new SpinnerNumberModel(seuilSupport, seuilSupport- 0, seuilSupport + 1,0.100000000000000000);    
         model.addChangeListener(this);
         fixedSeuil = new JSpinner(model);
	         
	         
         jTable1 = new JTable();
         jTable1.setSize(WIDTH, 600);
         modelTable = new DefaultTableModel();
         modelTable.addColumn("Générateurs");
         modelTable.addColumn("Fermeture");
         modelTable.addColumn("Support");
         modelTable.addColumn("Régle");
         modelTable.addColumn("Confiance");
         modelTable.addColumn("Lift");
          //La table dans laquelle seront affich�es les ligens r�sultats
         jTable1.setModel(modelTable);

         jTable1.setBackground(Color.LIGHT_GRAY);
         //jTable1.add(dataPane);
         jScrollPane2.setViewportView(jTable1);

         prcButton = new JButton("<<");
         prcButton.setEnabled(false);
         title = BorderFactory.createTitledBorder("content File");

	}
	
	public void placeComponent(){
			JPanel p=new JPanel();{
				
				p.add(jScrollPane1);
				p.add(loadFile);
			}
			p.setBorder(title);
			JPanel q=new JPanel(new GridLayout(1,6));{
				q.add(start);
				q.add(save);
				q.add(new JLabel("nom du fichier"));
				q.add(nameFile);
				q.add(save);
				q.add(new JLabel("support"));
				q.add(fixedSeuil);
				
				
			}

			JPanel w=new JPanel();{
				w.add(jScrollPane2);
			}
			
	        panD     = new JPanel ();
	        panD.setLayout((new BoxLayout(panD, BoxLayout.Y_AXIS) ));   
	        panD.add(q);
	        panD.add(w);
			setDividerLocation(351);
			setDividerSize(4);
			setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			setTopComponent(p);
			setRightComponent(panD);
		
	}
	
	public void createControler(){	
		
		jFileChooser= new JFileChooser() ;
        loadFile.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 try {
                	 int dd=jFileChooser.showOpenDialog(viewPanelLeft.getjTreeFile());
         	    	if(dd == JFileChooser.APPROVE_OPTION){
         			 inputFileLocation= jFileChooser.getSelectedFile().toString();
                     int index;
                     String text="";
         	    	 List<String> lignes =  Files.readAllLines( 
         	    			   
         	    			FileSystems.getDefault().getPath(inputFileLocation), StandardCharsets.ISO_8859_1); 
         	    			 for (String ligne : lignes){
         	    				 text=text+ligne+"\n";
         	    				 
         	    			       index=0;
         	    			       ResultOfItem row=new ResultOfItem();
         	    			        //supprimer les espaces
         	    			        ligne.replaceAll("\\s+","");
         	    			        //r�cup�rer l'identifiant
         	    			        String identifiant="";
         	    			         
         	    			        while(!(ligne.charAt(index)=='|')&& index<ligne.length()){
         	    			        	identifiant=identifiant+=ligne.charAt(index);
         	    			        	index++;
         	    			         
         	    			        }
         	    			        row.setIdentifiant(identifiant);
         	    			        
         	    			        
         	    			         //inss�rer les items
         	    			        String item;
         	    			        //depasser le "|"
         	    			        index++;
         	    			      
         	    			       while(index<ligne.length()){
         	    			        	 item="";
         	    			        	while(index<ligne.length()&&!(ligne.charAt(index)=='|')){
         	    			        		
         	    			        		item=item+ligne.charAt(index);
         	    			        		 
         	    			        		index++;
         	    			        	}
         	    			        	 
         	    			        	row.getItems().add(item);
         	    			        	index++;
         	    			        }
         	    			       rows.add(row);
         	    			 
         	    			 }
         	    			   getjTextArea1().setText(text);
         	    			   viewPanelLeft.fillTree(rows)  ; 
         	    			
         		     }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              }
         });
    	 
        viewPanelLeft = new ViewPanelLeft();
		start.addActionListener(new java.awt.event.ActionListener() {
	         public void actionPerformed(java.awt.event.ActionEvent evt) {
	         
	        
	         }
	     });
		         
		         
        
         save.addActionListener(new java.awt.event.ActionListener() {
        	 public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	 try {
							generateFileOut("Out2");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            
		             }
		         });
         prcButton.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
            	 index--;
            	 insertDataIntoTable(index);
            	 
             }
         });
	     
		
	}
	public void openFile() {
    	File file=new File("doc/index.html");
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

	 
    

	public javax.swing.JTextArea getjTextArea1() {
		return contenuFichier;
	}

	public void setjTextArea1(JTextArea contenuFichier) {
		this.contenuFichier = contenuFichier;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		seuil=  Double.valueOf(fixedSeuil.getModel().getValue().toString() );
		 
	}
	

	public double getSeuil() {
		return seuil;
	}

	public void setSeuil(double seuil) {
		this.seuil = seuil;
	}

	public javax.swing.JButton getjButton1() {
		return start;
	}

	public void setjButton1(javax.swing.JButton start) {
		this.start= start;
	}

	public static TextArea getTextArea1Rer() {
		return textArea1Rer;
	}

	public static void setTextArea1Rer(TextArea textArea1Rer) {
		ViewResult.textArea1Rer = textArea1Rer;
	} 
	
	public List<List<ResultOfItem>> getIterationsResults() {
		return iterationsResults;
	}

	public void setIterationsResults(List<List<ResultOfItem>> iterationsResults) {
		this.iterationsResults = iterationsResults;
	}

	public void insertDataIntoTable(int index){
	
	int j=modelTable.getRowCount();
	Vector vec=modelTable.getDataVector();
	vec.clear(); 
	
	
	
		if(index>=0 && index <iterationsResults.size()){
		for(ResultOfItem row:iterationsResults.get(index)){
			row.print();
			modelTable.addRow(new Object[]{row.getGenerators().toString(),row.getFermeture().toString(),String.valueOf(row.getSupport()),row.getRegle(),"1",row.getLift()});
			
		}
		
		}
		
		if(index>0){
			prcButton.setEnabled(true);
		}
		else{
			prcButton.setEnabled(false);
		}
		if(index<iterationsResults.size()-1){
			//svtButton.setEnabled(true);
		}
		else{
			//svtButton.setEnabled(false);
		}
	}
	
   public List<ResultOfItem> getItemRows(){
	   return rows;
   }
	
	public void generateFileOut(String name) throws IOException{
		java.io.File fichier = new java.io.File(nameFile.getText()+".txt");
		if (!fichier.exists()) {
			fichier.createNewFile();  
		}
		
		int k=0;
	   	String content="******************** Project of FDD ****************\n";
		       content+="************* The result of Close Algorithm ************";
		       content+="\n \n \n \n";
		       
		       for(List<ResultOfItem> row:iterationsResults){
		    	     content+="***************************************************\n";
		    	   content+="Les resultat de l'iteration "+k+"\n";
		    	     content+="***************************************************\n";
		    	     content+="\n";
		    	   for(ResultOfItem elm:row){
		    		   for(String generator :elm.getGenerators()){
		    			   content+=generator;
		    			}
		    		  content+="|   ";
		    		  content+="[  ";
		    		  for(String str:elm.getFermeture()){
		    			  content+=str;
		    			   content+=", ";
		    				}
		    		  content+="]"  ;
		    		  content+="|   ";
		    		   
		    		  content+=elm.getSupport();
		    		  content+="|   ";
		    		  content+=elm.getRegle();
		    		  content+="   |   ";
		    		  content+="Confiance : 1";
		    		  content+="|   ";
		    		  content+="Lift :  "+elm.getLift();
		    		  content+="\n";
		    	   }
		    	   k++ ;
		    	   content+="\n \n \n \n";
		       }
		java.io.FileOutputStream monFluxFichier = new java.io.FileOutputStream(fichier); 
		byte[] contentInBytes = content.getBytes();
		 
		monFluxFichier.write(contentInBytes);
		monFluxFichier.flush();
		monFluxFichier.close();
		
	}
}
