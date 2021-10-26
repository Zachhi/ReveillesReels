### Skeleton for GUI utility
* MainFile.java is the starting point
* Asks user to enter V or A, for client or analyst
* Once input is given, we create a clientManager or analystManager
* Client Manager
  * Tracks and holds everything for client GUI
  * Makes use of a CardLayout (usually how people flip Jpanels)
  * ClientPanel.java (Client home screen)
  * AllContent.java
  * ReccomendedContent.java
  * WatchHistory.java
  * These are all classes for different panels
  * Client Manager uses a cardLayout for all of these so we can easily switch
* Analyst Manager
  * Tracks and holds everything for anayst GUI
  * makes use of CardLayout as well
  * AnalystPanel.java (Analyst Home Screen)
  * PopularData.java
  * WatchHistoryData.java
  * All of these are differnet JPanels
 
TODO: Organize file structure..."Analyst" folder, "Client" folder 
