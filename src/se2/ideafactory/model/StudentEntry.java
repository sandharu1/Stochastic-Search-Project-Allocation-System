/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2.ideafactory.model;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Vimukthi
 */

public class StudentEntry {

    private String studentName;
    private boolean isPreAssignedProject;
    private ArrayList preferedProjects;
    private int numberOfStatedPreferences;
    private String assignedProjct;
       
    public StudentEntry() {
        
    }

    //Constructor that creates StudentEntry instances with given data.
    public StudentEntry(String studentName, boolean isPreAssignedproject, ArrayList preferedProjects) {
        this.studentName = studentName;
        this.isPreAssignedProject = isPreAssignedproject;
        this.preferedProjects = preferedProjects;
        
        numberOfStatedPreferences = preferedProjects.size();
    }

    //return student name.
    public String getStudentName(){
        return this.studentName;
    }
            
    //return list of ordered preferences.
    public ArrayList getOrderedPreferences(){
        return preferedProjects;
    }
    
    //return the state of whether a project is preassigned or not.
    public boolean hasPreassignedProject(){
        if(isPreAssignedProject && assignedProjct != null) {
		return true;
	}
	return false;
    }
    
    //return the nubmer of preferences states initially.
    public int getNumberOfStatedPreferences(){
        return numberOfStatedPreferences;
    }
    
    //ensures that a student’s list has only one preference, and records internally that thus preference has been pre-assigned.
    public void preassignProject(String pname){
        if(numberOfStatedPreferences==1 && isPreAssignedProject){
            assignedProjct = pname;
        }       
    }
    
    //assigns an extra project to the end of the student’s preferences list.
    public void addProject(String pname){
        this.preferedProjects.add(pname.intern());
    }
    
    //Return randomly choosen preference from preferedProjects list.
    public String getRandomPreference() {
        int random_number = PreferenceTable.RND.nextInt(numberOfStatedPreferences); //Generate random number between 0 … number of stated preferences.
        return preferedProjects.get(random_number).toString();
    }
    
    //Check availability of given preference with preferedProjects list.
    public boolean hasPreference(String preference){
        if(preferedProjects.indexOf(preference)==-1){
            return false;
        }
        return true;
    }
    
    public int hasProjectInInitialList(String preference){
        
        for(int i=0; i<numberOfStatedPreferences ; i++){
            if(preference.intern() == preferedProjects.get(i)){
                return i;
            }
        }
        
        return -1;
    }
    
    /*
    Return rank of given preference 
     0 - pre assigned or top ranked preference
    -1 - not a listed preference
     n - nth element
    */
    public int getRanking(String preference){
        
        if(hasPreassignedProject()){
            return 0;
        }   
        
        return preferedProjects.indexOf(preference); //if not find in the list, this will return -1
        
    }
    
    @Override
    public String toString() {
	return "Name: "+studentName+" Prefereces:"+getOrderedPreferences();
    }
    
}
