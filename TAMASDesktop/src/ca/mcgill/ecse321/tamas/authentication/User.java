/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.tamas.authentication;

// line 8 "../../../../../Authentication.ump"
public abstract class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String username;
  private String password;
  private String name;

  //User Associations
  private Authentication authentication;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUsername, String aPassword, String aName)
  {
    username = aUsername;
    password = aPassword;
    name = aName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  /**
   * We may plan to store hashed passwords locally in a file for each username in the system.
   */
  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

  public Authentication getAuthentication()
  {
    return authentication;
  }

  public boolean hasAuthentication()
  {
    boolean has = authentication != null;
    return has;
  }

  public boolean setAuthentication(Authentication aAuthentication)
  {
    boolean wasSet = false;
    Authentication existingAuthentication = authentication;
    authentication = aAuthentication;
    if (existingAuthentication != null && !existingAuthentication.equals(aAuthentication))
    {
      existingAuthentication.removeUser(this);
    }
    if (aAuthentication != null)
    {
      aAuthentication.addUser(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (authentication != null)
    {
      Authentication placeholderAuthentication = authentication;
      this.authentication = null;
      placeholderAuthentication.removeUser(this);
    }
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "authentication = "+(getAuthentication()!=null?Integer.toHexString(System.identityHashCode(getAuthentication())):"null")
     + outputString;
  }
}