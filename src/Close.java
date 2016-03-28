/** 
 * Author :  Abderrahim Si ziani & Mohamed Ibrihen
 * */
import java.util.ArrayList;
import java.util.List;
public class Close {

	
	private List<String> generateurs;
	private List<List<String>> items;
	int sizeOfItem;
	private List<List<ResultOfItem>> Resultats;
	private double support;
	private List<ResultOfItem> resultOfItem ;
	
	public Close (List<ResultOfItem>rslt,double support){
		
		items=new ArrayList<List<String>>();
		for(ResultOfItem row:rslt){
			items.add(row.getItems());
		}
		sizeOfItem=items.size();
		generateurs=new ArrayList<String>();
		Resultats=new ArrayList<List<ResultOfItem>>();
		this.support=support;
		this.resultOfItem=rslt;
	}
	
	public void executeClose(){
		/** 
		 * initialisation de l'algorithme
		 * **/
		initGenerateurs( generateurs);
		int i=0;
		List<String> elements=new ArrayList<String>();
		
		List<ResultOfItem>resultats=new ArrayList<ResultOfItem>();
		
		for(String generateur:generateurs){
		i=0;
		List<String>generateurElm=new ArrayList<String>();
		generateurElm.add(generateur);
		ResultOfItem resultat=new ResultOfItem(generateurElm);
		//Ajout dans la liste des generateurRow (resultats)
		resultats.add(resultat);
		//find the first row where appears the generateur
		for(List<String> itms:items){
			if(existInList(itms,generateur)){
				elements=itms;
				break;
			}
			i++;
		}
		
		/** 
		 * calculer les femeteurs et le support le l'itération 1
		 * */
		 calculFermeture(resultat,elements,i+1);
		 calculSupport(resultat);
		 
	}
	int k=1;
	/**supprimer les generateurs dont le support < seuil
	 * et dont les fermetures sont deja calculées
	 * */
	for(ResultOfItem rresultat:resultats){
		rresultat.print();
	}
	List<ResultOfItem>nouveauReslutats=removeOldResult(resultats,-1);
	Resultats.add(nouveauReslutats);
	List<ResultOfItem>resultatsTemp;
	resultatsTemp=Resultats.get(k-1);
	boolean next=false;
	if(resultatsTemp.size()>0){
		next=true;
	 }
	
	/** 
	 * the next itération
	 * */
	
	 for(int h=0; h<9; h++){ 
		 	List<ResultOfItem> iterationresultats=calculateIterations(resultatsTemp,k-1);
		 	List<ResultOfItem> newResultFiltered=removeOldResult(iterationresultats,k-1) ;
		 	if(newResultFiltered.size()>0)
		 	Resultats.add(newResultFiltered );
		 	if(newResultFiltered.size()==0){
		 		next=false;
		 	}
		 	k++;
		 }
	}
	/**
	 * calcule l'iteration i
	 * @param resultats de l'iteration précédente
	 * @param i le numero de l'iteration courante
	 * @return les resultats de l'itération
	 */
	public List<ResultOfItem> calculateIterations(List<ResultOfItem> resultats,int i){
		List<ResultOfItem> results=new ArrayList<ResultOfItem>();
	    List<String>elements;
	    i=0;
	    for(int k=0;k<results.size();k++ ){
	    	elements=new ArrayList<String>();
	    	List<String>generateurs=	results.get(k).getGenerateurs();
	        for(i=0;i<items.size();i++){
	        	 List<String> itm=items.get(i);	        	  
	        	 if(existe(itm,generateurs)){
	        		  elements=itm;
	        		  break;
	        	  }
	     	}
	         calculFermeture(results.get(k),elements,i+1);
			 calculSupport(results.get(k));
	         results.get(k).print();
	    }
		return results;
	}
	
	/**
	 * verifier si l'ensemble des générateurs existe dans une liste
	 * 
	 * @param itms
	 * @param generateurs
	 * @return boolean
	 */
	private boolean existe(List<String> itms, List<String> generateurs) {
		boolean exist=true;
		for(String str:generateurs){
			if(!existInList(itms,str)){
				exist =false;
				break;
			}
		}
		return exist;
	}

	
	
	/**
	 * initialisation des generateur pour la premiére itération
	 * @param list1
	 * @param list2
	 * @param j
	 * @return liste de generateur
	 */
	public List<String> initGenerateurs(List<String>lst1,List<String>lst2,int i){
		List<String> generateurs=new ArrayList<String>();
		int j;
		for(j=0;j<=i;i++){
			generateurs.add(lst1.get(j));
		}
		for(int k=i+1;i<lst1.size();i++){
			generateurs.add(lst1.get(j));
		}
		for(int k=i+1;j<lst2.size();j++){
			generateurs.add(lst2.get(j));
		}
		return generateurs;
	}
	
	/**
	 * Fonction permettant de remplir la list des generateurs 
	 * à partir d'un fichier d'entrée 
	 * @param liste de generateur
	 */
	public void initGenerateurs(List<String> generateurs){
		for(ResultOfItem rs:resultOfItem){
			for(String item:rs.getItems()){
				if(!existInList(generateurs,item)){
					generateurs.add(item);
				}
			}
		}
		
	}
	
	public boolean egale(List<String>lst1,List<String>lst2){
 		boolean result=false;
		boolean differ=false;
		if(lst1.size()==lst2.size()){
			for(int i=0;i<lst1.size();i++){
				differ=false;
				if(!lst1.get(i).equalsIgnoreCase(lst2.get(i))){
					differ=true;
					break;
				}
			}  
			if(!differ){
				result=true;
			}
		}else{
		}
		return result;
	}
	
	/**
	 * 
	 * supprimer les generateurs oû leurs support < seuil
	 * 
	 */
	
	public List<ResultOfItem> removeOldResult(List<ResultOfItem>  resultats,int j){
		
		List<ResultOfItem>result=new ArrayList<ResultOfItem>();
		boolean add;
		
		boolean next =false;
		for(ResultOfItem resl:resultats){
			add=true;
			
			if(resl.getSupport()<support){
				add=false;
			}
			
			if(resl.getFermeture().size()==0 || resl.getFermeture()==null){
				add=false;
			}
			if(j>=0){
			for(ResultOfItem rsl:Resultats.get(j)){
				next=false;
				 if(egale(resl.getFermeture(),rsl.getFermeture())){
					next=true;
					break;
				 }
			 }
			if(next==true){
				add=false;
			}
			}
			if(add==true){
				result.add(resl);
			}
		}
		
		if(result.size()>0){
			calculerRegles(result);
			calculerLift(result);
		}
		return result;
		
	}
	
	/**
	 * 
	 * Fonction permettant de calculer les régles 
	 * 
	 * @param resultats
	 * @return
	 */
	public List<ResultOfItem> calculerRegles(List<ResultOfItem> resultats){
		String regle="";
		for(ResultOfItem rs:resultats){
			regle="";
			for(String generateur:rs.getGenerateurs()){
				regle+=generateur;
			}
			regle+="->";
			for(String fermetur:rs.getFermeture()){
				if(!existInList(rs.getGenerateurs(),fermetur)){
					regle+=fermetur;
				}
			}
			rs.setRegle(regle);
		}
		return resultats;
		
		
	}

	/**
	 * 
	 * le calcul  des fermés pour un générateur donné
	 * @param resul
	 * @param elements
	 * @param i
	 * @return generateurRow
	 */
	private ResultOfItem calculFermeture(ResultOfItem resul,List<String> elements, int i) {  
		if(i>=items.size()){
			resul.setFermeture(elements);
			return resul;
		}
		else{
			boolean next=true;
			for(String generateur:resul.getGenerateurs()){
				if(!existInList(items.get(i),generateur)){
					next=false;
					break;
				}
			}
 			if(next==true){
			List<String>fermeture=trouverClose(elements,items.get(i),resul);
			return calculFermeture(resul,fermeture,i+1);
			
			
			}
			else{
				return calculFermeture(resul,elements,i+1);
			}
		}
		
	}
	
	public List<String>trouverClose(List<String> lst1, List<String>lst2,ResultOfItem row){
		List<String>result=new ArrayList<String>();
		for(String elmt:lst1){
			if(existInList(lst2,elmt)){
				 
				result.add(elmt);
				 
			}
		}
		return result;
	}

	
	
	/**
	 * 
	 * Fonction permettant le calcul du Support
	 * 
	 * @param rsul
	 */
	
	public void calculSupport(ResultOfItem rsul){
 		double support=0;
		boolean next;
		if(rsul.getFermeture().size()>0)
		{
		for(List<String> item :items){
			next=true;
			for(String elmt:rsul.getFermeture()){
				 
				if(!existInList(item,elmt)){
					next=false;
 					break;
				}
			}
			if(next){
				support+=1d/sizeOfItem;
 			}
			
		}
		}
		
		
		rsul.setSupport(support);
		
	}
	
	/**
	 *  le calcul de Lift d'un generateur
	 *  
	 * @param resultats
	 */
	public void calculerLift(List<ResultOfItem>  resultats){
		for(ResultOfItem rlt:resultats){
		double lift=0;
 		double denominateur=1;
 		String []regles=rlt.getRegle().split("->");
 		
 		if(regles.length==2){
 			
	 		if(regles[1]!=null && !regles[1].isEmpty()){
	 			
	 			String regleRght=regles[1];
	 			ResultOfItem resultInRegle;
	 			
	 			boolean equals=false;
	 			for(int i=0;i<resultats.size();i++){
	 					
	 					resultInRegle =resultats.get(i);
	 					if(resultInRegle.getGenerateurs().size()==regleRght.length()){
	 						List<String> gen=resultInRegle.getGenerateurs();
	 						equals=generateurInRegle (regleRght,gen);
	 						 if(equals){
	 							
	 							 denominateur=resultInRegle.getSupport();
	 						 }
	 					
	 					}
	 				
	 			}
	 			
	 			lift = (rlt.getSupport())/(rlt.getSupport()*denominateur);
	 			rlt.setLift(lift);
	 			
	 		}
			}else{
				rlt.setLift(0);
				
			}
 		
		}
		
	}
	
	/**
	 * Fonction permettant de verifier une liste <String > dans un String
	 * 
	 * @param resultats
	 */
	public boolean generateurInRegle  (String regle,List <String> genera){
		boolean exist=true;	
		int i=0;
			while (i<genera.size()){
				if(!regle.contains(genera.get(i))){
					
					return false;
				}
				i++;
			}
				return exist;
				
	} 
	
	public void setResultats(List<List<ResultOfItem>> Resultats) {
		this.Resultats = Resultats;
	}

	public List<List<ResultOfItem>> getResultats() {
		return Resultats;
	}
	
	public boolean existInList(List<String>list, String elment){
		boolean exist=false;
		for(String elm:list){
			if(elm.equalsIgnoreCase(elment)){
				exist=true;
				break;
			}
		}
		return exist;
	}

	
}






