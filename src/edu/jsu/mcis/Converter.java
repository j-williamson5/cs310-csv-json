package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and other whitespace
        have been added for clarity).  Note the curly braces, square brackets, and double-quotes!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160","111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            JSONObject jsonObject = new JSONObject();
            
            // INSERT YOUR CODE HERE
             String[] headings = iterator.next();//previously named headings for col headers
            
            JSONArray records = new JSONArray(); //Container for records
            String[] record;
            ArrayList<String> rowHeaders = new ArrayList<String>();//The array for row headers
            ArrayList<String> colHeaders = new ArrayList<String>();
            ArrayList<ArrayList> data = new ArrayList<ArrayList>();//The rest of the data
            
            //Get the data put in the proper Array List
            
            //Put the headings in the proper Array List
            for(int i = 0; i < headings.length; i ++){
                colHeaders.add(headings[i]);
            }
            
            while (iterator.hasNext()){
                record = iterator.next();
                ArrayList<Integer> groupOfGrades = new ArrayList<Integer>();
                for(int i = 0; i < headings.length; i++){
                    //System.out.println(headings[i] + " " + record[i]);
                    if(i == 0){
                       rowHeaders.add(record[i]);
                       //System.out.println(rowHeaders);
                    }
                    else{
                        //data.add(record[i]);
                        //System.out.println(data);
                        //System.out.println(record[i]);
                        groupOfGrades.add(Integer.parseInt(record[i]));
                        //jsonObject.put(headings[i],record[i]);
                    }
                    //System.out.println(Integer.toString(i));
                }
                data.add(groupOfGrades);
             
            }
               
              
            jsonObject.put("rowHeaders",rowHeaders);
            jsonObject.put("colHeaders",colHeaders);
            jsonObject.put("data",data);
            
            records.add(jsonObject);
            
            results += JSONValue.toJSONString(records);
            results = results.substring(1, results.length()-1);
            
            //PRINT STATEMENTS
            /*
            System.out.println(rowHeaders);
            for(int i = 0; i < headings.length;i++){
                System.out.print(headings[i] + " ");
            }
            System.out.println(data);
            */
        }
        
        catch(IOException e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            
            //Get Fromt he jsonObject and put the items into JSONArrays
            JSONArray jsonColHeaders = (JSONArray) jsonObject.get("colHeaders");
            JSONArray jsonRowHeaders = (JSONArray) jsonObject.get("rowHeaders");
            JSONArray jsonData = (JSONArray) jsonObject.get("data");
            Integer numberOfDataItems = 5;
            //Make String arrays for the items
            String[] csvColHeaders = new String[jsonColHeaders.size()];
            String[] csvRowHeaders = new String[jsonRowHeaders.size()];
            String[][] csvStringData = new String[jsonData.size()][4];
            String[][] csvRows = new String[csvRowHeaders.length][numberOfDataItems];
            
            //Put data in the String arrays
            for(int i = 0; i < jsonColHeaders.size(); i++){
                csvColHeaders[i] = jsonColHeaders.get(i).toString();
            }
            for(int i = 0; i < jsonData.size();i++){
                JSONArray currentData = (JSONArray)jsonData.get(i);
                for(int j = 0; j < currentData.size(); j++){
                     csvStringData[i][j] = currentData.get(j).toString();
                }
            }
            
            for(int i = 0; i < jsonRowHeaders.size();i++){
                csvRowHeaders[i] = jsonRowHeaders.get(i).toString();
            }
            for(int i = 0; i < csvRowHeaders.length;i++){
                csvRows[i][0] = csvRowHeaders[i];
                for(int j = 0; j < numberOfDataItems-1;j++){
                        csvRows[i][j+1] = csvStringData[i][j];
                }
            }
            csvWriter.writeNext(csvColHeaders);
            for(String[] item: csvRows){
                csvWriter.writeNext(item);
            }
            /*
            for(int i = 0; i < jsonRowHeaders.size();i++){
                //String[] currentData = jsonData[i];
                csvRows[i][0] =  jsonRowHeaders.get(i).toString();
                for(int j = 0; j < numberOfDataItems;j++){
                    if(j != 0){
                        csvRows[i][j] = jsonData.get(j).toString();
                    }
                }
                
            }


            csvWriter.writeNext(csvColHeaders);
            for(int i = 0; i < csvRows.length;i++){
                for(int j = 0; j <numberOfDataItems; j++){
                    String[] currentRow = {csvRows[i][j]};
                    csvWriter.writeNext(currentRow);
                }
*/
           
            
            results = writer.toString();
        }                                                                           
        
        catch(ParseException e) { return e.toString(); }
        
        return results.trim();
        
    }
	
}