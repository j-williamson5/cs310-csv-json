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
            ArrayList<String> csvData = new ArrayList<String>();
            String csvString = "";
            Object[] headings = jsonObject.keySet().toArray();
            String titles = jsonObject.get(headings[2]).toString();
            String rawData = jsonObject.get(headings[1]).toString();//.split(",");
            String ids = jsonObject.get(headings[0]).toString();
            
            titles = titles.substring(1, titles.length()-1);
            titles = titles.replace("\"", "");
  
            ids = ids.substring(1,ids.length()-1);
            ids = ids.replace("\"","");
            
            rawData = rawData.substring(1,rawData.length()-1);
            rawData = rawData.replace("[","");
            rawData = rawData.replace("]","");
            //Add the headings, then the data while inserting the ids into the data
            csvString += titles;
            csvString += ids;
            csvString += rawData;
            
            String[] csvDataArray =  {titles,ids,rawData};
            for(int i = 0; i < csvDataArray.length; i++){
                System.out.println(csvDataArray[i]);
            }
            /*
            System.out.println(titles);
            System.out.println(ids);
            System.out.println(rawData);
            */
            //csvData.add(csvString);
            //csvData.add(titles);
            //csvData.add(rawData);
            /*
            for(int i = 0; i < rawData.length; i++){
            System.out.print(rawData[i]+ " ");
            }
             */
            /*
            ArrayList<String> combedData = new ArrayList<String>();
            ArrayList<String> data = new ArrayList<String>();
            String[] id = jsonObject.get(headings[0]).toString().split(",");
            ArrayList<String> combinedData = new ArrayList<String>();
            for(int i = 0; i < rawData.length; i++){
            System.out.println(rawData[i]);
            combedData.add(Arrays.toString(rawData[i].split("\\[")));
            }
            for(int i = 0; i < combedData.size();i++){
            data.add(Arrays.toString(combedData.get(i).split("\\]")));
            }
            System.out.println(combedData.toString());
            System.out.println(data.toString());
             */
            
            //for(int i = 0; i < rawData.length; i++){
                //combinedData.add(id[i]);
                //combinedData.add(data[i]);
                
            //}
            //System.out.println(combinedData.toString());
            //for (char c : id.toCharArray()){
               // System.out.println(c);
             //}
            //System.out.println(jsonObject.keySet());//This gets us rowHeaders, data, and colHeaders
            
            //Add the colHeaders first
            //csvData.add(jsonObject.get(headings[2]).toString());
            //csvData.add(jsonObject.get(headings[1]).toString());
            //for(int i = 0; i < headings.length - 1; i++){
                
                //csvData.add(jsonObject.get(headings[i]).toString());
                //System.out.println(jsonObject.get(jsonObject.keySet().toArray()[i]));
            //}
            //csvData.add()
            csvWriter.writeNext(csvDataArray);
            //System.out.println(writer.toString());
            //System.out.println(csvData.toString());
            results += writer.toString();
        }
        
        catch(ParseException e) { return e.toString(); }
        
        return results.trim();
        
    }
	
}