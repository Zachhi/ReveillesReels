Project 2 Dataset.

This project contains 5 CSV files. The data is contained in data.zip.

The python notebook contains information about how project 2 dataset was generated.


The 5 CSV files are:
1. crew.csv
2. customer_ratings.csv
3. names.csv
4. principals.csv
5. titles.csv

1. crew.csv
Contains information about directors and writers for a movie/tvshow.
Columns  are:
a. titleId - movie/tvshow Ids (alphanumeric).
b. directors - list of strings of ids for directors. 
c. writers - list of strings of ids for writers.

2. customer_ratings.csv
Contains information about ratings by a customer for a specific movie/tvshow.
a. customerId - Customer ID.
b. rating - The rating the user gave the title.
c. date - The date the user rated the title.
d. titleId - movie/tvshow ID.

3. names.csv
Contains more information about people who took part in creating the movie.  
Columns are:
a. nconst - alphanumeric unique identifier for the person.
b. primaryName - name by which the person is most often credited.
c. birthYear - in YYYY format.
d. deathYear - in YYYY format if applicable, else ''.
e. primaryProfession - the top-3 professions of the person.

4. principals 
Contains information about users and the roles the play in movies. 
Columns are:
a. titleId - movie/tvshow ID (alphanumeric).
b. nconst - alphanumeric unique identifier of the name/person.
c. category - the category of job that person was in.
d. job  - the specific job title if applicable, else ''.
e. characters - the name of the character played if applicable, else ''.

5. titles.
Contains information about tvshows and movies. 
Columns are:
a. titleId - movie/tvshow Ids (alphanumeric).
b. titleType - the type/format of the title (e.g. movie, short, tvseries, tvepisode, video, etc).
c. originalTitle - original title, in the original language.
d. startYear - represents the release year of a title. In the case of TV Series, it is the series start year.
e. endYear - TV Series end year. '' for all other title types.
f. runtimeMinutes - primary runtime of the title, in minutes.
g. genres - includes up to three genres associated with the title.
h. Year - represents the release year of a title.
i. averageRating - weighted average rating of the title. These ratings are from an external source with other set of users.
j. numVotes - number of votes used in calculating the averageRating for the title.