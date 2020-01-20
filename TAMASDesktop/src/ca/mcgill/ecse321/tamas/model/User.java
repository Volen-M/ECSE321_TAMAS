/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.tamas.model;

// line 48 "../../../../../Model.ump"
public abstract class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String username;
  private String name;

  //User Associations
  private Tamas tamas;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUsername, String aName)
  {
    username = aUsername;
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

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }

  public String getName()
  {
    return name;
  }

  public Tamas getTamas()
  {
    return tamas;
  }

  public boolean hasTamas()
  {
    boolean has = tamas != null;
    return has;
  }

  public boolean setTamas(Tamas aTamas)
  {
    boolean wasSet = false;
    Tamas existingTamas = tamas;
    tamas = aTamas;
    if (existingTamas != null && !existingTamas.equals(aTamas))
    {
      existingTamas.removeUser(this);
    }
    if (aTamas != null)
    {
      aTamas.addUser(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (tamas != null)
    {
      Tamas placeholderTamas = tamas;
      this.tamas = null;
      placeholderTamas.removeUser(this);
    }
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tamas = "+(getTamas()!=null?Integer.toHexString(System.identityHashCode(getTamas())):"null")
     + outputString;
  }
}