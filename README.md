MBank3.0
========

MBank project for John Bryce College - 2014 Java Server Course

Included files:
---------------
1. MBank project
2. MBankTest project
3. MBankAdminConsole project
4. CreateDB project
5. CreateMBankDB project
6. MBankDB folder (database dump)
7. db-derby-10.10.2.0-bin (Apache Derby Database server application)


Before being able to run the projects you must perform the following steps:
---------------------------------------------------------------------------
1. From the db-derby-10.10.2.0-bin folder, run the batch file ‘\db-derby-10.10.2.0-bin\bin\StartNetworkServer.bat’.
This will start the DB server and assign it the appropriate port.
2. At this point you can choose one of two options:
  a. From the CreateMBankDB project, run the test ‘suites.CreateCleanDBAndPopulateBasicTableData.java’ as a JUnit.
     This will create a new database on the Derby server, called Mbank, in addition to creating all Mbank tables, populating the properties table and inserting an administrator user into the clients table (this test deletes any previous versions of Mbank database).
  b. Use the provided dump file and load it into the Mbank DB.
  
How to run the projects:
------------------------
Mbank core (Mbank project)
**************************
Run the project as a java application.
The project contains a Main class with a main method that provides a command line interface (CLI).
The CLI provides access to AdminAction and ClientAction methods so that they can be tested.

Swing UI (MBankAdminConsole project)
************************************
Run the project as a java application.
The project contains a MainFrame class with a main that launches the stand-alone UI application.
Use the following credentials in order to login:
Username: system
Password: admin

Detailed specification of projects:
-----------------------------------
Phase 1: Building System Core
###MBank server###
  a. MBank project – phase 1 implementation, using Java SE.
  b. MBankTest project – unit test code for MBank classes, using JUnit 4 framework (you can run all tests by running mbank.AllTests as a JUnit).

###MBank database – helper project###
  a. CreateDB – project for general creation/deletion/population of database tables.
  b. CreateMBankDB – used to create/delete/populate specific tables needed for the MBank project.

Phase 2: Building Administration Desktop Application
###Mbank Admin Management Application###
  MBankAdminConsole project – phase 2 implementation, using Java Swing framework
  
