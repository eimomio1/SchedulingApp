package application;



	
	public class Appointment {
	    private int id;
	    private String description;
	    private String location;
	    private String type;
	    private String date;
	    private String starttime;
	    private String endtime;
		
	    
	    
	    
	    public Appointment(int id, String description, String location, String type, String date ,String starttime, 
	    		String endtime) {
		
			this.id = id;
			this.description = description;
			this.location = location;
			this.type = type;
			this.date = date;
			this.starttime = starttime;
			this.endtime = endtime;
		}




		public int getId() {
			return id;
		}




		public String getStarttime() {
			return starttime;
		}




		public String getEndtime() {
			return endtime;
		}




		public String getDescription() {
			return description;
		}




		public String getLocation() {
			return location;
		}




		public String getType() {
			return type;
		}




		public String getDate() {
			return date;
		}
	    
	    
	    
	    
	    
	    
	    
	    
		
	    
	   
	    

	  

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	}