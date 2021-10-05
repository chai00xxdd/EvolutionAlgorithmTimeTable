package ServerDTO;



public class UserDTO {
	
	private String userName;
	private boolean isLoggedIn;
	
	public UserDTO(User user)
	{
		userName = user.getUsername();
		isLoggedIn = user.isLoggedIn();
	}

	public String getUserName() {
		return userName;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	
	

}
