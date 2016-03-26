
import java.util.ArrayList;
import java.util.List;
public class Close {

	private List<ResultOfItem> rowsData;
	private double support;
	private List<String> generators;
	private List<List<String>> items;
	private List<List<ResultOfItem>> iterationsResults;
	double itemsSize;// necessaire pour le calcul du support
	
	public Close (List<ResultOfItem>rows,double support){
		
		iterationsResults=new ArrayList<List<ResultOfItem>>();
		this.support=support;
		this.rowsData=rows;
		generators=new ArrayList<String>();
		items=new ArrayList<List<String>>();
		for(ResultOfItem row:rows){
			items.add(row.getItems());
		}
		
		itemsSize=items.size();
	}
	
	public void launchAlgorithm(){
		/**
		 * 
		 *  
		 *  Partie initialisation de l'algorithme
		 *  Remplissage des GeneratorRow pour le calcul plus tard
		 *  
		 */
		createGenerators( generators);
		//printGenerators();
		generators.add("ab");
		generators.add("ae");
		generators.add("bc");
		generators.add("ce");
		int index=0;
		List<String>potentialElms=new ArrayList<String>();
		
		//create initial iteration items
		List<ResultOfItem>rows=new ArrayList<ResultOfItem>();
		
		for(String generator:generators){
		index=0;
		List<String>generatorElm=new ArrayList<String>();
		generatorElm.add(generator);
		ResultOfItem row=new ResultOfItem(generatorElm);
		//Ajout dans la liste des generatorRow (rows)
		rows.add(row);
		//find the first row where appears the generator
		for(List<String> setItem:items){
			if(isInString(setItem,generator)){
				potentialElms=setItem;
				break;
			}
			index++;
		}
		
		//trouver les fermetures et les support de la premiere iteration
		 trouverFermeture(row,potentialElms,index+1);
		 calculateSupport(row);
		 
	}
	int k=1;
	//enelver les iteration dont le support < minSupport 
	//et dont les fermetures sont deja calculées
	for(ResultOfItem row:rows){
		row.print();
	}
	List<ResultOfItem>newRows=filterRows(rows,-1);
	iterationsResults.add(newRows);
	List<ResultOfItem>rowsTemp;
	rowsTemp=iterationsResults.get(k-1);
	boolean continu=false;
	if(rowsTemp.size()>0){
		continu=true;
	 }
	// faire les iterations suivantes
	
	System.err.println(generators.size());
	
	 for(int w=0; w<9;w++){
		 	 //rowsTemp=iterationsResults.get(k-1);
		 	//List<GeneratorRow> iterationRows=calculateIterations(rowsTemp,k-1);
		 	//List<GeneratorRow> filtredRows=filterRows(iterationRows,k-1) ;
		 	List<ResultOfItem> iterationRows=calculateIterations(rowsTemp,k-1);
		 	List<ResultOfItem> filtredRows=filterRows(iterationRows,k-1) ;
		 	if(filtredRows.size()>0)
		 	iterationsResults.add(filtredRows );
		 	if(filtredRows.size()==0){
		 		continu=false;
		 	}
		 	k++;
		 }
	}
	/**
	 * calcule l'iteration numero k
	 * @param rows les lignes de l'iteration précédente
	 * @param k le numero de l'iteration courante
	 * @return
	 */
	public List<ResultOfItem> calculateIterations(List<ResultOfItem> rows,int k){
		List<ResultOfItem> rowResult=new ArrayList<ResultOfItem>();
	    //trouver la premiere fermeture potentielle
	    List<String>potentialElms;
	    int indexItem;
	    for(int indexRow=0;indexRow<rowResult.size();indexRow++ ){
	    	potentialElms=new ArrayList<String>();
	    	List<String>generators=	rowResult.get(indexRow).getGenerateurs();
	        for(indexItem=0;indexItem<items.size();indexItem++){
	        	 List<String> setItem=items.get(indexItem);	        	  
	        	 if(existe(setItem,generators)){
	        		  potentialElms=setItem;
	        		  break;
	        	  }
	     	}
	         trouverFermeture(rowResult.get(indexRow),potentialElms,indexItem+1);
			 calculateSupport(rowResult.get(indexRow));
	         rowResult.get(indexRow).print();
	    }
		return rowResult;
	}
	
	/**
	 * Fonction permmettant de verifier l'existance de tout les generateur dans 1 liste donnée
	 * 
	 * @param setItem
	 * @param generators
	 * @return
	 */
	private boolean existe(List<String> setItem, List<String> generators) {
		boolean found=true;
		for(String str:generators){
			if(!isInString(setItem,str)){
				found =false;
				break;
			}
		}
		return found;
	}

	
	
	public List<String>createGenerator(List<String>list1,List<String>list2,int k){
		List<String>gens=new ArrayList<String>();
		int i;
		for(i=0;i<=k;i++){
			gens.add(list1.get(i));
		}
		for(int j=i+1;j<list1.size();j++){
			gens.add(list1.get(j));
		}
		for(int j=i+1;j<list2.size();j++){
			gens.add(list2.get(j));
		}
		return gens;
	}
	
	public boolean egale(List<String>list1,List<String>list2){
 		boolean result=false;
		boolean diff=false;
		if(list1.size()==list2.size()){
			for(int i=0;i<list1.size();i++){
				diff=false;
				if(!list1.get(i).equalsIgnoreCase(list2.get(i))){
					diff=true;
					break;
				}
			}  
			if(!diff){
				result=true;
			}
		}else{
		}
		return result;
	}
	
	/**
	 * 
	 * Filtrer les row dont le support < MinSupport
	 * 
	 */
	
	public List<ResultOfItem> filterRows(List<ResultOfItem>  rows,int k){
		
		List<ResultOfItem>result=new ArrayList<ResultOfItem>();
		boolean add;
		
		boolean contenu =false;
		for(ResultOfItem row:rows){
			add=true;
			
			if(row.getSupport()<support){
				add=false;
			}
			
			if(row.getFermeture().size()==0 || row.getFermeture()==null){
				add=false;
			}
			if(k>=0){
			for(ResultOfItem rowP:iterationsResults.get(k)){
				contenu=false;
				 if(egale(row.getFermeture(),rowP.getFermeture())){
					contenu=true;
					break;
				 }
			 }
			if(contenu==true){
				add=false;
			}
			}
			
			
			
			
			if(add==true){
				result.add(row);
			}
		}
		
		if(result.size()>0){
			calculateRegles(result);
			calculateLift(result);
		}
		return result;
		
	}
	
	/**
	 * 
	 * Fonction permettant de calculer les régles 
	 * 
	 * @param rows
	 * @return
	 */
	public List<ResultOfItem> calculateRegles(List<ResultOfItem> rows){
		String regle="";
		for(ResultOfItem row:rows){
			regle="";
			for(String generateur:row.getGenerateurs()){
				regle+=generateur;
			}
			regle+="->";
			for(String ferm:row.getFermeture()){
				if(!isInString(row.getGenerateurs(),ferm)){
					regle+=ferm;
				}
			}
			row.setRegle(regle);
		}
		return rows;
		
		
	}
	
	
	/**
	 * 
	 * Fonction récursive permettant le calcul de l'ensemble des fermés pour un générateur donné
	 * (GeneratorRow)
	 * 
	 * @param row
	 * @param potentialElms
	 * @param index
	 * @return GeneratorRow
	 */
	private ResultOfItem trouverFermeture(ResultOfItem row,List<String> potentialElms, int index) {  
		if(index>=items.size()){
			row.setFermeture(potentialElms);
			return row;
		}
		else{
			boolean contenu=true;
			for(String generator:row.getGenerateurs()){
				if(!isInString(items.get(index),generator)){
					contenu=false;
					break;
				}
			}
 			if(contenu==true){
			List<String>fermeture=findClose(potentialElms,items.get(index),row);
			return trouverFermeture(row,fermeture,index+1);
			
			
			}
			else{
				return trouverFermeture(row,potentialElms,index+1);
			}
		}
		
	}
	
	public List<String>findClose(List<String>str1, List<String>str2,ResultOfItem row){
		//double supportToAdd;
		List<String>result=new ArrayList<String>();
		for(String elmt:str1){
			if(isInString(str2,elmt)){
				 
				result.add(elmt);
				 
			}
		}
		return result;
	}

	/**
	 * Fonction permettant le remplissage des generateurs 
	 * � partir du fichier d'entr�e (apr�s phase du parsing)
	 * 
	 */
	public void createGenerators(List<String> generators){
		for(ResultOfItem row:rowsData){
			for(String item:row.getItems()){
				if(!isInString(generators,item)){
					generators.add(item);
				}
			}
		}
		
	}
	
	/**
	 * 
	 * Fonction permettant le calcul du Support pour chaque g�n�rateur
	 * 
	 * @param row
	 */
	
	public void calculateSupport(ResultOfItem row){
 		double supportToAdd=0;
		boolean contenu;
		if(row.getFermeture().size()>0)
		{
		for(List<String> ligne :items){
			contenu=true;
			for(String elmt:row.getFermeture()){
				 
				if(!isInString(ligne,elmt)){
					contenu=false;
 					break;
				}
			}
			if(contenu){
				supportToAdd+=1d/itemsSize;
 			}
			
		}
		}
		
		
		row.setSupport(supportToAdd);
		
	}
	
	
	/**
	 * Fonction permettant de verifier une liste <String > dans un String
	 * 
	 * @param rows
	 */
	public boolean ExistGeneratorRightProduction (String regleDroite,List <String> gen){
		
		boolean egal=true;	
		int k=0;
			while (k<gen.size()){
				if(!regleDroite.contains(gen.get(k))){
					
					return false;
				}
				k++;
			}
				return egal;
				
	}
	
	
	/**
	 * Fonction permettant le calcul du Lift d'un g�n�rateur donn�
	 *  
	 * @param rows
	 */
	public void calculateLift(List<ResultOfItem>  rows){
		for(ResultOfItem row:rows){
 			
		
		
		double liftToAdd=0;
 		double denominateur=1;
 		String []tabRegle=row.getRegle().split("->");
 		
 		//cas existance partie droite de la r�gle
 		if(tabRegle.length==2){
 			
	 		if(tabRegle[1]!=null && !tabRegle[1].isEmpty()){
	 			
	 			String regleDroite=tabRegle[1];
	 			ResultOfItem rowRightProduction;
	 			
	 			boolean egalite=false;
	 			for(int i=0;i<rows.size();i++){
	 					
	 					rowRightProduction =rows.get(i);
	 					if(rowRightProduction.getGenerateurs().size()==regleDroite.length()){
	 						List<String> gen=rowRightProduction.getGenerateurs();
	 						egalite=ExistGeneratorRightProduction(regleDroite,gen);
	 						 if(egalite){
	 							
	 							 denominateur=rowRightProduction.getSupport();
	 						 }
	 					
	 					}
	 				
	 			}
	 			
	 			liftToAdd = (row.getSupport())/(row.getSupport()*denominateur);
	 			row.setLift(liftToAdd);
	 			
	 		}
			}else{
				row.setLift(0);
				
			}
 		
		}
		
	}
	
	/**
	 * Fonction permettant de verifier l'existance d'un element dans une liste
	 * 
	 * @param list
	 * @param str
	 * @return
	 */
	public boolean isInString(List<String>list, String str){
		boolean result=false;
		for(String elm:list){
			if(elm.equalsIgnoreCase(str)){
				result=true;
				break;
			}
		}
		return result;
	}

	public List<List<ResultOfItem>> getIterationsResults() {
		return iterationsResults;
	}

	public void setIterationsResults(List<List<ResultOfItem>> iterationsResults) {
		this.iterationsResults = iterationsResults;
	}
}






