package DMMC;

public class Profile {
	
	private String name;
	private boolean viewedHelpPage;
	
	public Profile(String name)
	{
		this.name = name;
		this.viewedHelpPage = false;
	}
	
	public String getName(){ return name; }
	public boolean getHelpPageStatus(){ return viewedHelpPage; }
	
	public void setName(String name) { this.name = name; }
	public void setHelpPageStatus(boolean help) { this.viewedHelpPage = help; }

	
}
