/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2.ideafactory.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import se2.ideafactory.model.TempDataWriteToXML;

/**
 *
 * @author Yohan
 */
public class CandidateSolution {
    
    private PreferenceTable preferenceTable;
    private Hashtable<String, CandidateAssignment> candidateAssignments;
    final int energyPenalty = 1000;

    //Default constructor.
    public CandidateSolution() {
                
    }

    //Constructor that gets PreferenceTable instance as an argument.
    public CandidateSolution(PreferenceTable pt) {
        this.preferenceTable = pt;
        
        this.candidateAssignments = new Hashtable<String, CandidateAssignment>();
		for(StudentEntry student : pt.getAllStudentEntries()) {
			candidateAssignments.put(student.getStudentName(), new CandidateAssignment(student));
		}
    }
  
    //Return a random assignment as an instance of CandidateAssignment.
    public CandidateAssignment getRandomAssignment(){
       Vector<String> keySet = new Vector<String>(candidateAssignments.keySet());
		return candidateAssignments.get(keySet.elementAt(PreferenceTable.RND.nextInt(keySet.size())));
    }
    
    //Return a CandidateAssignment instance for a given student name.
    public CandidateAssignment getAssignmentFor(String name){
        return candidateAssignments.get(name);
    }
    
    //Return energy of all candidate assignments plus energy penalties.
    public int getEnergy(){
    int energy = 0, counter = 0;
		Iterator<String> it = candidateAssignments.keySet().iterator();
		Hashtable<String, Integer> collisionCounter = new Hashtable<String, Integer>();
		
		while (it.hasNext()) {
			String sname = it.next();
			energy += candidateAssignments.get(sname).getEnergy();
			if (!collisionCounter.containsKey(candidateAssignments.get(sname).getAssignedProject()))  {
				collisionCounter.put(candidateAssignments.get(sname).getAssignedProject(), 1);
			} else {
				counter++;
			}
        }
		energy += (counter*energyPenalty);
		return energy;
    }
    
    public  Hashtable getSolution(){
        return candidateAssignments;
    }
    
    public void printAllAssignments(){
        Iterator<String> it = candidateAssignments.keySet().iterator();
        TempDataWriteToXML passWrite = new TempDataWriteToXML();
        int i = 1;
        while (it.hasNext()){
            String sname = it.next();
            String project = candidateAssignments.get(sname).getAssignedProject();
            StudentEntry student = preferenceTable.getEntryFor(sname);

            //remove the name from project and check output
            System.out.println(i+")"+sname+" : "+project);
            i++;
            
        }
         
    }
    
    //Return fitness of all candidate assignments. 
    public int getFitness(){
        return -getEnergy();
    }
    
}
