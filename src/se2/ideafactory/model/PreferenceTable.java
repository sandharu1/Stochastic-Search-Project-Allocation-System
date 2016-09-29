/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2.ideafactory.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

/**
 *
 * @author Rajitha
 */
public class PreferenceTable {
    //keep all StudentEntry instances.
    private ArrayList<StudentEntry> studentEntriesList;   
    
    //send student entrys to hash table
    private Hashtable studentEntriesTable;  

    private Vector studentTable;
    
    static Random RND = new Random();
    
    public PreferenceTable() {
        
    }

    //Constructor that takes file path as an argument.
    public PreferenceTable(String file_path) {
        studentTable = loadContentFromFile(file_path);      
    }
    
    //load the content from the given file and retun as a Vector of Vectors.
    private Vector loadContentFromFile(String file_path){
                
        File file = new File(file_path);
	FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        
        //keep single student details as vector
        Vector student = null; 
        //keep all students as vectors
        Vector students = null; 
        
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);         
            br = new BufferedReader(isr);    
            
            students = new Vector();
            
            String line;
                   
                //read the file line by line.
                while ((line = br.readLine()) != null) {
                    
                    //devide single row in to tokens by "tab".
                    StringTokenizer tokens = new StringTokenizer(line, "\t");
                    
                    student = new Vector(tokens.countTokens());      
                    
                    //fill student Vector with string values.
                    while(tokens.hasMoreTokens()){                     
                       student.addElement(tokens.nextToken()); 
                    }
                    
                    //add student Vectors in to students Vecotor.
                    students.addElement(student);
                    
                }
                
           
        } catch (FileNotFoundException ex) {
            System.out.println("File is not found!");

        }catch (IOException ex) {
            System.out.println("Error reading file!");
  
        }
         
        //builds a list of StudentEntry instances
        createStudentEntries(students);
       
        return students;
    }
    
    
    //printing out the resulting nested Vector representation.
    public void displayVectorRepresentation(){              
        System.out.println(studentTable);
    }
    
    //creates a list of StudentEntry instances
    void createStudentEntries(Vector students){
        
        String studentName = "";
        Boolean isPreAssignedproject = false;
        studentEntriesList = new ArrayList();
        studentEntriesTable = new Hashtable();
        
        //start from 1 to skip the vector of labels.
        for(int i=1;i<students.size();i++){     
            
            ArrayList preferedProjects = new ArrayList();
            
            studentName = ((Vector)students.get(i)).get(0).toString();
            isPreAssignedproject = false;
            
            if(((Vector)students.get(i)).get(1).toString().equals("Yes")){
                        isPreAssignedproject = true;
            }
            
            //start from second to get only prefered projects
            for(int j=2;j<((Vector)students.get(i)).size();j++){              
                preferedProjects.add(((Vector)students.get(i)).get(j));
            }
            
            StudentEntry student = new StudentEntry(studentName,isPreAssignedproject,preferedProjects);
            
            //populate the list and hash table.
            studentEntriesList.add(student); 
            studentEntriesTable.put(studentName, student);
            
        }
    }
    
    //return all StudentEntry instances.
    public ArrayList<StudentEntry> getAllStudentEntries(){
        return studentEntriesList;
    }
    
    //return StudentEntry instance for a given student name.
    public StudentEntry getEntryFor(String sname){
        return (StudentEntry)studentEntriesTable.get(sname);
    }
    
    //Return a random StudentEntry instance.
    public StudentEntry getRandomStudent(){
        //Generate random number between 0 to Maximum number of students.
        int random_number = RND.nextInt(studentEntriesList.size()); 
        return (StudentEntry)studentEntriesList.get(random_number);
    }
    
    //Return a random preference by getting a random student at first.
    public String getRandomPreference(){
        return getRandomStudent().getRandomPreference();
        
    }
    
    //Fill preference list of each student to the max number given as argument.
    public void fillPreferencesOfAll(int maxPrefs){
        
        String random_preference = null;
        StudentEntry student;
        int count;
        
        for(int i=0; i<studentEntriesList.size(); i++){           
            
            student = (StudentEntry)studentEntriesList.get(i);
          
            for(count = student.getNumberOfStatedPreferences(); count<maxPrefs ;count++){
                //Get random preference.
                random_preference = getRandomPreference();
                
                if(!student.hasPreference(random_preference)){
                    student.addProject(random_preference);
                }
            }
                        
        }
        
    }
    
}
