/**
 * 
 * Authors: Mohamed Ibrihen & Abderrahim Si ziani
 */
import java.util.ArrayList;
import java.util.List;

public class ResultOfItem {

	
	private String id;
	private List<String> items;
	private List<String>generateurs;
	private double support;
	private List<String>fermeture;
	private String regle;
	private double lift;
	
	
	
	/**
	 * contructeur pour initaliser la liste des items
	 */
	
	public ResultOfItem() {
		items=new ArrayList<String>();
	}

	
	/**
	 * contructeur pour initaliser la liste des generateur  et des fermeteures 
	 */
	public ResultOfItem(List<String> generateurs) {
		support=0;
		this.generateurs = generateurs;
		fermeture=new ArrayList<String>();
		items=new ArrayList<String>();
	}


	public ResultOfItem(List<String>generateurs,List<String>fermeture,double support){
		
		this.generateurs=generateurs;
		this.fermeture=fermeture;
		this.support=support;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public List<String> getItems() {
		return items;
	}


	public void setItems(List<String> items) {
		this.items = items;
	}
	
	
	public double getLift() {
		return lift;
	}


	public void setLift(double lift) {
		this.lift = lift;
	}


	public List<String> getGenerateurs() {
		return generateurs;
	}


	public void setGenerateurs(List<String> generateurs) {
		this.generateurs = generateurs;
	}


	public List<String> getFermeture() {
		return fermeture;
	}


	public void setFermeture(List<String> fermeture) {
		this.fermeture = fermeture;
	}


	public double getSupport() {
		return support;
	}


	public void setSupport(double support) {
		this.support = support;
	}
	
	
	
	public String getRegle() {
		return regle;
	}


	public void setRegle(String regle) {
		this.regle = regle;
	}


	public void print(){
		System.out.println("*********nouveaux Generateurs ***************** " );
		for(String generateur :generateurs){
		System.out.println(generateur);
	}
		System.out.println("fermetures ");
		for(String str:fermeture){
		System.out.println(str);
		}
		System.out.println("support: "+support);
		System.out.println("regles: "+regle);
		System.out.println("lift: "+lift);
	}
	
}
