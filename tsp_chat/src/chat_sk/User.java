package chat_sk;

import java.util.ArrayList;

public class User {
	String login; 
	String password;
	String IP_adres;
	int number_of_port;
	ArrayList <User> list_of_friends;
	boolean log_in ;
	
	public User(String login, String password) {
		this.login = login;
		this.password = password;
		this.list_of_friends = new ArrayList<User>();
		this.log_in=false;
	}
	
	void addFriend(User friend) {
		this.list_of_friends.add(friend);
	}
	
}
