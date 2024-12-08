# Verification Project Setup Instructions

These instructions exist to try and preemptively solve any problems with the setup of this project.

Please note that this system was designed and is intended to run on **Java 8**.

## Database Information

The project expects to run on an [embedded apache derby database](https://db.apache.org/derby/) (the connection string is `jdbc:derby:DB_NAME`). The driver for derby needs to be added as a dependency if running with IntelliJ, the driver install location typically is `%JAVA_HOME%/db`.

The SQL script for creating and populating tables for the project's database is located in the root directory as `verification.sql`. The script will delete the tables if they exist, then re-create the tables, and finally load the data into the database.

## Setting up the Database

To run the database on my machine in IntelliJ IDEA, I had to manually include the database libraries as dependencies for my project.

The database itself needs to be setup before it can be used. Specifically, setup is done by running the `"%JAVA_HOME%\db\bin\ij"` command **in the location you want the database to be stored**. For example, if you create a directory `verif-db` on your Desktop, you would be using that command in the `C:/Users/YOUR_USERNAME/Desktop/verif-db` directory.

After launching `ij`, you can connect to the database with the `connect` command. This command has the following format:

> `connect 'jbdc:derby:DB_NAME;user=USERNAME;password=PASSWORD';`

Where `DB_NAME` is the database name, `USERNAME` is the username to connect with, and `PASSWORD` is the password. Additionally, if the database has not been created, add the `create=true;` argument. For example:

> `connect 'jbdc:derby:DB_NAME;create=true;user=USERNAME;password=PASSWORD';`

The project expects the following parameters about the database to be true:
- Database Name: `verification`
- Login Username: `veriftool`
- Login Password: `csis643`

The system will fail to connect if the DB is not setup with these values. These values can be changed within the system if desired by editing the constructor for class `Database`. Originally, these were environment variables, but for the sake of simplicity for this project they have been hardcoded.

Once connected (the command line will show `ij(CONNECTION)>`), the database setup and insertion script can be run with `RUN 'path/to/file';` or just by copy/pasting the statements into the terminal.

Once finished, make sure to close the connection with `exit;` or the program will not be able to start!

## Ensuring the System can Connect to the Database

It is very important you tell Java where to find the database when starting the system. Failure to do so will result in the system not starting at all.

This can be done by executing the `java` command with the `-Dderby.system.home="Absolute/Path/to/Database"` argument. For example:

> `java -Dderby.system.home="C:/Program Files/Java/jdk-1.8/db/bin" edu.liberty.andrewwerner.iplverification.presenter.Driver`

## Running the Program

The `Driver` class in the `edu.liberty.andrewwerner.iplverification.presenter` package is the program's driver class. All required supporting packages (including those from intellij's form editor for GUIs) are included in the `out/production/CSIS 653 Verification System` (yes, there was a typo creating the project name) directory. The java files themselves can be found in the `src` directory.

## Notes

IntelliJ IDE's form editor was used to help create and layout the GUIs in this program. As a result, the various view classes contain auto-generated code from the form editor, and dependent classes are included in the `out/` directory.

If you have any problems, questions, or concerns feel free to contact me.
