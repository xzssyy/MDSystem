package school.mdsystem.administor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class avltime {
	
	private StringProperty time_id;
	private StringProperty time;
	public avltime(){}
	
	public avltime(String a, String b)
	{
		super();
		this.time_id = new SimpleStringProperty(a);
		this.time = new SimpleStringProperty(b);
	}
			
	public StringProperty get_time_id() {
			return time_id;
	}
	
	public void set_time_id(String sd) {
			this.time_id = new SimpleStringProperty(sd);
	}
		
	public StringProperty get_time() {
			return time;
	}
		
	public void set_time(String se) {
			this.time = new SimpleStringProperty(se);
	}

}
