package uwo.group2;

import java.util.ArrayList;

public class User{
  private int id;
  private String username;
  private String password;
  private UserType type;
  private ArrayList favorites;

  public User(){

  }

  public User(String name, String password, UserType type) {
    this.username = name;
    this.password = password;
    this.type = type;
  }
  public String getUserName(){
		return username;
	}
  public UserType getUserType(){
		return type;
	}

}
