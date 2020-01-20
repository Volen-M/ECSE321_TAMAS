/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.tamas.authentication;
import java.util.*;

// line 3 "../../../../../Authentication.ump"
public class Authentication
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Authentication Associations
  private List<User> users;
  private User currentUser;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Authentication()
  {
    users = new ArrayList<User>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  /**
   * We will want to ensure in our controller that we have atleast 1 user - if not make a default admin user.
   */
  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }

  public User getCurrentUser()
  {
    return currentUser;
  }

  public boolean hasCurrentUser()
  {
    boolean has = currentUser != null;
    return has;
  }

  public static int minimumNumberOfUsers()
  {
    return 0;
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    Authentication existingAuthentication = aUser.getAuthentication();
    if (existingAuthentication == null)
    {
      aUser.setAuthentication(this);
    }
    else if (!this.equals(existingAuthentication))
    {
      existingAuthentication.removeUser(aUser);
      addUser(aUser);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    if (users.contains(aUser))
    {
      users.remove(aUser);
      aUser.setAuthentication(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }

  public boolean setCurrentUser(User aNewCurrentUser)
  {
    boolean wasSet = false;
    currentUser = aNewCurrentUser;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    currentUser = null;
  }

}