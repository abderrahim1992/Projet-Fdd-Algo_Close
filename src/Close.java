
import java.util.ArrayList;
import java.util.List;
public class Close {

	private List<ItemRowData> rowsData;
	private double support;
	private List<String> generators;
	private List<List<String>> items;
	private List<List<ItemSetRow>> iterationsResults;
	double itemsSize;// necessaire pour le calcul du support
	
	public Close (List<ItemRowData>rows,double support){
		
		iterationsResults=new ArrayList<List<ItemSetRow>>();
		this.support=support;
		this.rowsData=rows;
		generators=new ArrayList<String>();
		items=new ArrayList<List<String>>();
		for(ItemRowData row:rows){
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
		int index=0;
		List<String>potentialElms=new ArrayList<String>();
		
		//create initial iteration items
		List<ItemSetRow>rows=new ArrayList<ItemSetRow>();
		
		for(String generator:generators){
		index=0;
		List<String>generatorElm=new ArrayList<String>();
		generatorElm.add(generator);
		ItemSetRow row=new ItemSetRow(generatorElm);
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
	for(ItemSetRow row:rows){
		row.print();
	}
	List<ItemSetRow>newRows=filterRows(rows,-1);
	iterationsResults.add(newRows);
	List<ItemSetRow>rowsTemp;
	rowsTemp=iterationsResults.get(k-1);
	boolean continu=false;
	if(rowsTemp.size()>0){
		continu=true;
	 }
	// faire les iterations suivantes  
	 while(k<generators.size() && continu) {
		 	 rowsTemp=iterationsResults.get(k-1);
		 	//List<GeneratorRow> iterationRows=calculateIterations(rowsTemp,k-1);
		 	//List<GeneratorRow> filtredRows=filterRows(iterationRows,k-1) ;
		 	List<ItemSetRow> iterationRows=calculateIterations(rowsTemp,k-1);
		 	List<ItemSetRow> filtredRows=filterRows(iterationRows,k-1) ;
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
	public List<ItemSetRow> calculateIterations(List<ItemSetRow> rows,int k){
		List<ItemSetRow> rowResult=new ArrayList<ItemSetRow>();
	    joinGenerators(rowResult,rows,k);
	    //trouver la premiere fermeture potentielle
	    List<String>potentialElms;
	    int indexItem;
	    for(int indexRow=0;indexRow<rowResult.size();indexRow++ ){
	    	potentialElms=new ArrayList<String>();
	    	List<String>generators=	rowResult.get(indexRow).getGenerators();
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

	public void joinGenerators(List<ItemSetRow> rowResult,List<ItemSetRow> rows,int k){
		List<String> initialGenerators=new ArrayList<String>();
 		if(k==1){
 			//on est a la deuxieme iteration
			for(ItemSetRow row:rows)	{
				initialGenerators.add(row.getGenerators().get(0));
			}
			List<List<String>>newGen=combineGenerators(initialGenerators);
			// Enlever les g�n�rateurs existants dans les femr�s d�ja calcul�s
			boolean contenu =true;
			for(List<String> liste: newGen ){
				contenu=false;
				 for(ItemSetRow row:rows){
					 if(egale(liste,row.getFermeture())){
						contenu=true;
						break;
					 }
				 }
				 if(contenu==false)
					 rowResult.add(new ItemSetRow(liste));
			}
 		}else if(k>1){
 			//il faut que les k-2 premiers elements se ressemblent
			int index1=0;
			for(index1=0;index1<rows.size()-1;index1++){
				
				for(int index2=index1+1;index2<rows.size();index2++){
					if(egaleWithIndex(rows.get(index1).getGenerators(),rows.get(index2).getGenerators(),k-2)){
						List<String>gens=createGenerator(rows.get(index1).getGenerators(),rows.get(index2).getGenerators(),k-2);
						rowResult.add(new ItemSetRow(gens));
					}
				}
				
			}
			
		}
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
	
	public boolean egaleWithIndex(List<String>list1,List<String>list2, int endIndex){
 		boolean result=false;
		boolean diff=false;
		for(int i=0;i<=endIndex;i++){
			diff=false;
			if(!list1.get(i).equalsIgnoreCase(list2.get(i))){
				diff=true;
				break;
			}
		}  
			if(!diff){
				result=true;
			}
		 
		 
		return result;
		
	}
	
	
public List<List<String>>	combineGenerators(List<String> gens){
	List<List<String>>result=new ArrayList<List<String>>();
	
	for(int i=0;i<gens.size()-1;i++){
		for(int j=i+1;j<gens.size();j++){
			List<String>ligneGens=new ArrayList<String>();
			ligneGens.add(gens.get(i));
			ligneGens.add(gens.get(j));
			result.add(ligneGens);
		}
	}
	return  result;
	
	}
	/**
	 * 
	 * Filtrer les row dont le support < MinSupport
	 * 
	 */
	
	public List<ItemSetRow> filterRows(List<ItemSetRow>  rows,int k){
		
		List<ItemSetRow>result=new ArrayList<ItemSetRow>();
		boolean add;
		
		boolean contenu =false;
		for(ItemSetRow row:rows){
			add=true;
			
			if(row.getSupport()<support){
				add=false;
			}
			
			if(row.getFermeture().size()==0 || row.getFermeture()==null){
				add=false;
			}
			if(k>=0){
			for(ItemSetRow rowP:iterationsResults.get(k)){
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
	public List<ItemSetRow> calculateRegles(List<ItemSetRow> rows){
		String regle="";
		for(ItemSetRow row:rows){
			regle="";
			for(String generateur:row.getGenerators()){
				regle+=generateur;
			}
			regle+="->";
			for(String ferm:row.getFermeture()){
				if(!isInString(row.getGenerators(),ferm)){
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
	private ItemSetRow trouverFermeture(ItemSetRow row,List<String> potentialElms, int index) {  
		if(index>=items.size()){
			row.setFermeture(potentialElms);
			return row;
		}
		else{
			boolean contenu=true;
			for(String generator:row.getGenerators()){
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
	
	public List<String>findClose(List<String>str1, List<String>str2,ItemSetRow row){
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
		for(ItemRowData row:rowsData){
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
	
	public void calculateSupport(ItemSetRow row){
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
	public void calculateLift(List<ItemSetRow>  rows){
		for(ItemSetRow row:rows){
 			
		
		
		double liftToAdd=0;
 		double denominateur=1;
 		String []tabRegle=row.getRegle().split("->");
 		
 		//cas existance partie droite de la r�gle
 		if(tabRegle.length==2){
 			
	 		if(tabRegle[1]!=null && !tabRegle[1].isEmpty()){
	 			
	 			String regleDroite=tabRegle[1];
	 			ItemSetRow rowRightProduction;
	 			
	 			boolean egalite=false;
	 			for(int i=0;i<rows.size();i++){
	 					
	 					rowRightProduction =rows.get(i);
	 					if(rowRightProduction.getGenerators().size()==regleDroite.length()){
	 						List<String> gen=rowRightProduction.getGenerators();
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
	public void printGenerators() {
		System.out.println("les generateurs sont:");
		for(String generator:generators) {
			System.out.println("generateur: "+ generator);
		}
	}

	public List<List<ItemSetRow>> getIterationsResults() {
		return iterationsResults;
	}

	public void setIterationsResults(List<List<ItemSetRow>> iterationsResults) {
		this.iterationsResults = iterationsResults;
	}
}






