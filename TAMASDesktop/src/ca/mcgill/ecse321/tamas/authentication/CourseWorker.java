/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.tamas.authentication;

/**
 * Only need the bare minimum in the classes below to identify our user type.
 */
// line 16 "../../../../../Authentication.ump"
public class CourseWorker extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CourseWorker(String aUsername, String aPassword, String aName)
  {
    super(aUsername, aPassword, aName);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}