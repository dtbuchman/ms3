# ms3
Coding example
Derek Buchman
2/9/19

All DDLs and necesarry files are included to be able to run this program. 
Description: This application reads in a CSV file, parses the data, and if valid, 
inserts into an in-memory SQLite database.

Programming Strategy: I broke the task down into smaller tasks and worked my way through the problem.  
It was a pretty straight-forward project, so I went down the list and implemented each request one-by-one.
First, I read in all the data, verified each set was valid.  If it was a proper dataset, it was inserted
into the DB.  If not, it was written to the csv.  
I was unsure of how robust of a program was desired, so I wrote skeleton code (lines 157 - 209) for
adding in verification checks for each column.  For example, the client might request the system start ignoring 
payments from certain locations/countries or want to reject customers from a specific email domain (like 
"emails with @yandex.ru always need to be rejected").


[To run] 
(Once files are on local machine)

From comand-line:
1) Navigate to directory containing .java files
2) Compile file with "javac PaymentManager.java"
3) Execute program with "java PaymentManager"

In Eclipse or similar IDE:
1) From the main menu bar, select File > Import. The Import wizard opens.
2) Collapse or click + in General > Existing Project into Workspace and click Next.
3) Choose either Select root directory or Select archive file and click the associated Browse to locate the directory or file containing the projects.
4) Under Projects select the project or projects which you would like to import. 
5) Click Finish to start the import. 
6) Click Run project button.
