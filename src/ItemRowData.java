


/**
 * 
 * Authors:  si ziani abderrahim
 */
import java.util.ArrayList;
import java.util.List;

public class ItemRowData {

	private String identifiant;
	private List<String> items;
	
	
	public ItemRowData(){
		items=new ArrayList<String>();
		
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
	
	
}
