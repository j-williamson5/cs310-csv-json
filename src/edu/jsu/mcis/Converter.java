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
            for(int i = 0; i < data.size(); i++){
                //System.out.println(data.get(i));
            }
            jsonObject.put("data",data);
            
            records.add(jsonObject);
            
            results += JSONValue.toJSONString(records);
            
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
            //ArrayList<String> csvData = new ArrayList<String>();
            String csvString = "";
            Object[] headings = jsonObject.keySet().toArray();//rowHeaders data colHeaders
            JSONArray csvDataArray = new JSONArray();
            
            String cleanedColHeaders = jsonObject.get("colHeaders").toString().replace("[","");
            String cleanColHeaders = cleanedColHeaders.replace("]","");
            String colHeadersNoQuotes = cleanColHeaders.replace("\"","");
            String[] colHeaders = colHeadersNoQuotes.split(",");
            
            String cleanedRowHeaders = jsonObject.get("rowHeaders").toString().replace("[","");
            String cleanRowHeaders = cleanedRowHeaders.replace("]","");
            String rowHeadersNoQuotes = cleanRowHeaders.replace("\"","");
            String[] rowHeaders = rowHeadersNoQuotes.split(",");
            
            String cleanedData = jsonObject.get("data").toString().replace("[","");
            String cleanData = cleanedData.replace("]","");
            String[] data = cleanData.split(",");
            /*
            String[] csvDataArray =  {titles,ids,rawData};
            for(int i = 0; i < csvDataArray.length; i++){
                System.out.println(csvDataArray[i]);
            }
           
            csvWriter.writeNext(csvDataArray);
          
            results += writer.toString();
            */
            //System.out.println(jsonObject.toJSONString());
            
            //Add the colHeaders
            for(int i = 0; i < colHeaders.length; i++){
                csvDataArray.add(colHeaders[i]);
                if(i != colHeaders.length - 1){
                    //csvDataArray.add("\"" + colHeaders[i] + "\","); //NO CSV WRITER
                }
                else{
                   //csvDataArray.add("\"" + colHeaders[i] + "\""); //NO CSV WRITER
                }
            }
            
            //Pair the rowHeaders with the data
            for(int i = 0; i < data.length; i++){
                
                //String[] temporaryDataArray = {rowHeaders[i],data[0 + (4* i)],data[1 + (4 * i)],data[2 + (4 * i)],data[3 + (4 * i)]};
                //csvDataArray.add(temporaryDataArray);
                if(i%4 == 0){
                   // csvDataArray.add("\"" + rowHeaders[i/4] + "\",");//add the ID //NO CSV WRITER
                    csvDataArray.add(rowHeaders[i/4]);
                    
                }
                csvDataArray.add(data[i]);
                if((i+1)%4 == 0){
                   //csvDataArray.add("\"" + data[i] + "\""); //NO CSV WRITER
                }
                else{
                    //csvDataArray.add("\"" + data[i] + "\","); //NO CSV WRITER
                }
                
            }
            

            
            String[] csvData = Arrays.copyOf(csvDataArray.toArray(), csvDataArray.toArray().length, String[].class);
            
            for(Object item: csvData){
                item = item.toString();
            }
            
            csvWriter.writeNext(csvData);
            /*
            for(Object item: csvDataArray){
                results += item.toString(); //NO CSV WRITER
            }
*/
            results = writer.toString();
                                                                                               
        }                                                                           
        
        catch(ParseException e) { return e.toString(); }
        
        return results.trim();
        
    }
	
}