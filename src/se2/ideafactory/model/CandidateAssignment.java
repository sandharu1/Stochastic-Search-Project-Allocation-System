/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2.ideafactory.model;

/**
 *
 * @author Rajitha
 */
public class CandidateAssignment {

    private StudentEntry studentEntry;
    private String previouslyAssignedProject;
    private String assignedProject;
    
    //Defauly constructor.
    public CandidateAssignment() {
    }

    //Constructor that gets a StudentEntry instance as an argument
    public CandidateAssignment(StudentEntry studentEntry) {
        this.studentEntry = studentEntry;
        randomizeAssignment();
    }
    
    //Asign random project to the student.
    public void randomizeAssignment(){
        previouslyAssignedProject = this.assignedProject;
        assignedProject = studentEntry.getRandomPreference().intern();       
    }

    //Return StudentEntry instance.
    public StudentEntry getStudentEntry() {
        return studentEntry;
    }
    
    //Return Assigned project.
    public String getAssignedProject(){
        return assignedProject;
    }
    
    //Undo assigned project and reassigned previous project.
    public void undoChange(){
        assignedProject = previouslyAssignedProject;
    }
    
    //Return energy of single candidate assignment.
    public int getEnergy(){
        return (int) Math.pow((studentEntry.getRanking(assignedProject)+1),2);
    }
    
    @Override
    public String toString() {
	//return studentEntry.getStudentName()+": "+assignedProject;
        return assignedProject;
    }
    
}
