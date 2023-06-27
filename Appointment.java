package application;



	
	public class Appointment {
	    private int id;
	    private String description;
	    private String location;
	    private String title;
	    private String date;
	    private String starttime;
	    private String endtime;
		private String name;
	    
	    
	    
	    public Appointment(int id, String description, String location, String title, String date ,String starttime, 
	    		String endtime, String name) {
		
			this.id = id;
			this.description = description;
			this.location = location;
			this.title = title;
			this.date = date;
			this.starttime = starttime;
			this.endtime = endtime;
			
			this.name = name;
		}




		public String getName() {
			return name;
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




		public String getTitle() {
			return title;
		}




		public String getDate() {
			return date;
		}
	    
	    
	    
	    
	    
	    
	    
	    
		
	    
	   
	    

	  

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	}