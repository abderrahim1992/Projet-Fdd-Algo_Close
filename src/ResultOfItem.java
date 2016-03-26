

/**
 * 
 * Authors: Kheireddine Berkane et Amazigh Amrane
 */
import java.util.ArrayList;
import java.util.List;

public class ResultOfItem {

	
	private List<String>generators;
	private List<String>fermeture;
	private double support;
	private String regle;
	private double lift;
	private String identifiant;
	private List<String> items;
	

	public ResultOfItem() {
		items=new ArrayList<String>();
	}

	
	public ResultOfItem(List<String> generators) {
		support=0;
		this.generators = generators;
		fermeture=new ArrayList<String>();
		items=new ArrayList<String>();
	}


	public ResultOfItem(List<String>generators,List<String>fermeture,double support){
		
		this.generators=generators;
		this.fermeture=fermeture;
		this.support=support;
	}

	public String getIdentifiant() {
		return identifiant;
	}


	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
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


	public List<String> getGenerators() {
		return generators;
	}


	public void setGenerators(List<String> generators) {
		this.generators = generators;
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
		System.out.println("*********new Generator row***************** " );
		for(String generator :generators){
		System.out.println(generator);
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
