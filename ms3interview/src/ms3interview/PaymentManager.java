/*
 * Author: Derek Buchman
 * Description: This application reads in a CSV file, parses the data, and if valid, 
 * inserts into an in-mem SQLite db
 */

package ms3interview;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PaymentManager 
{
	public static void main(String[] args)
	{
		BufferedReader buffRead = null;
		String csvFilepath ="src\\ms3interview.csv";
		String badDataStr = "";
		String currLine = "";
		int retCounter = 0;
		int successCounter = 0; 
		int failCounter = 0;
	
		try 
		{
			//init logger
			FileHandler handler = new FileHandler("stats.log"); 
			Logger logger = Logger.getLogger("com.javacodegeeks.snippets.core");
			logger.addHandler(handler);
			
			//load driver
			Class.forName("org.sqlite.JDBC"); 
	        Connection con = DriverManager.getConnection("jdbc:sqlite::memory:");
	        Statement stmt = con.createStatement();  //get connection and create statement
	        
	        //drop the ms3interview table if it already exists
	        stmt.executeUpdate("drop table if exists ms3interview");
			
	        //create the ms3interview table
	        stmt.executeUpdate("create table "
	        + "ms3interview(id integer,"
	        + "firstName varchar(30)," 
	        + "lastName varchar(30)," 
	        + "emailAddr varchar(30),"
	        + "sex varchar(15),"
	        + "imgPath varchar(300),"
	        + "payMethod varchar(30),"
	        + "payAmount REAL," 
	        + "firstBool INT,"
	        + "secondBool INT,"
	        + "location varchar(30),"
	        + "primary key (id));");
			
			//load the file into buffer
			buffRead = new BufferedReader(new FileReader(csvFilepath));
			
			//read file in, line by line
			currLine = buffRead.readLine();
			int recordCount = 0;
			while( currLine != null )
			{
				retCounter++; //successful record (line) retrieval
				recordCount++; //used for insert id
				
				//verify current dataset
				if(isValid(currLine))
				{
					// parse out values from current line
					String[] inputVals = createInputVals(currLine);
					
					//insert elem in sqlite in-mem db
					stmt.executeUpdate("insert into ms3interview VALUES("+ recordCount +","
					+ "\'" + inputVals[0] +"\',"
					+ "\'" + inputVals[1] +"\',"
					+ "\'" + inputVals[2] +"\',"
					+ "\'" + inputVals[3] +"\',"
					+ "\'" + inputVals[4] +"\',"
					+ "\'" + inputVals[5] +"\',"
					+ "\'" + inputVals[6] +"\',"
					+ "\'" + inputVals[7] +"\',"
					+ "\'" + inputVals[8] +"\',"
					+ "\'" + inputVals[9] +"\' "
					+ ")  ");

					successCounter++; //valid element counter++	
				}
				else
				{
					failCounter++; //invalid element counter++
					badDataStr += currLine +"\n";  //append bad data to var
				}
				currLine = buffRead.readLine();  //read in next line
			}
			
			//write bad data to file
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String filePath = "src\\bad-data-" + sdf.format(timestamp) +".csv";
			PrintWriter writer = new PrintWriter(new File(filePath));
	        writer.write(badDataStr);
			
			//write stats to a log file	
			logger.info("Records retrieved: " + retCounter);
			logger.info("Successful records: " + successCounter);
			logger.info("Failed records: " + failCounter);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
            if (buffRead != null) {
                try {
                    buffRead.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
	}
	
	//check if current line is a valid entry
	//string must meet ALL requirements
	private static boolean isValid(String line)
	{
		String[] rawData = line.split(",");
		//check for null vals
		for(int i = 0; i < rawData.length; i++)
		{
			//return invalid if empty 
			if(rawData[i].equals(""))
			{
				return false;
			}
			
			//invalid data checklist
			switch(i) 
			{
				case 1:
					if(rawData[i].length() < 2)
					{
						return false;
					}
					break;
				case 2:
					if(rawData[i].equals(""))
					{
						return false;
					}
					break;
				case 3:
					if(rawData[i].equals(""))
					{
						return false;
					}
					break;
				case 4:
					if(rawData[i].equals(""))
					{
						return false;
					}
					break;
				case 5:
					if(rawData[i].equals(""))
					{
						return false;
					}
					break;
				case 6:
					if(rawData[i].equals(""))
					{
						return false;
					}
					break;
				case 7:
					if(rawData[i].equals(""))
					{
						return false;
					}
					break;
				case 8:
					if(rawData[i].equals(""))
					{
						return false;
					}
					break;
				case 9:
					if(rawData[i].equals(""))
					{
						return false;
					}
					break;
				case 10:
					if(rawData[i].equals(""))
					{
						return false;
					}
					break;
				default:
					break;
			}
		}
		return true;
	}
	
	//create element to insert into db
	private static String[] createInputVals(String inputLine)
	{
		String[] rawData = inputLine.split(",");
		String[] retVal = new String[10];
		
		//add escape char for comma
		rawData[4].replace(",", "\\,");
		
		retVal[0] = rawData[0];
		retVal[1] = rawData[1];
		retVal[2] = rawData[2];
		retVal[3] = rawData[3];
		retVal[4] = rawData[4]+rawData[5];
		retVal[5] = rawData[6];
		retVal[6] = rawData[7];
		retVal[7] = rawData[8];
		retVal[8] = rawData[9];
		retVal[9] = rawData[10];
		
		//sanitize retVal for escape chars
		retVal = sanitInput( retVal );
		
		return retVal;
	}
	
	//replace escape chars for input into db
	private static String[] sanitInput( String[] inputStr )
	{
		String sanitStr = "";
		String[] retStrArr = new String[10];
		for(int i =0; i<inputStr.length; i++)
		{
			retStrArr[i] = inputStr[i].replaceAll("'","''");
		}
		return retStrArr;
	}
}
