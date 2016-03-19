

/**
 * 
 * Authors: Kheireddine Berkane et Amazigh Amrane
 */
import java.util.ArrayList;
import java.util.List;

public class ItemSetRow {

	
	private List<String>generators;
	private List<String>fermeture;
	private double support;
	private String regle;
	private double lift;
	
	public double getLift() {
		return lift;
	}


	public void setLift(double lift) {
		this.lift = lift;
	}


	public ItemSetRow(List<String> generators) {
		support=0;
		this.generators = generators;
		fermeture=new ArrayList<String>();
	}


	public ItemSetRow(List<String>generators,List<String>fermeture,double support){
		
		this.generators=generators;
		this.fermeture=fermeture;
		this.support=support;
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
