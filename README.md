# Reveilles-Reels

A movie recommendation software created in a team of 5 that recommends movies to clients based on different parameters. There is both a client and an analyst side. The client side uses a clients data to analyze their watch history and preferences. The analyst side is for people who want to analyze what movies are popular and what movies they should create more of. Implemented using Java, Java Swing, SQL, and PostgreSQL. Waterfall methodology was used in planning and executing this project. 

Data is pulled from 5 different csv files, each with 500,000 lines of data. Data is pulled, formatted, and stored into an online database using java and SQL. The data is then  fetched from the online database using java and SQL in order to give the correct analysis and results.

## Demo

https://www.youtube.com/watch?v=sT9SZdZqSJE


### Dependencies

* Some way to compile and execute Java code
* SQL and PostgreSQL
* Access to Tamu network, either directly or through a VPN in order to access the database

### Installing and Executing

* Download the source code from github, or clone the repository into Visual Studio
* Type `make` and then execute `./run`
* You will want to do this in a virtual enviroment if you edit the files, otherwise you may compromise your computer's health

## Authors

Zachary Chi - zachchi@tamu.edu
  
Emory Fields - emory.c.fields@tamu.edu
  
Morgan Roberts - morgan.roberts00@tamu.edu
  
Allison Edwards - allisone12@tamu.edu
  
Emma Haeussler - emmahaeussler@tamu.edu
  
## License

This project is licensed under the GNU General Public License v2.0 - see the LICENSE.md file for details
