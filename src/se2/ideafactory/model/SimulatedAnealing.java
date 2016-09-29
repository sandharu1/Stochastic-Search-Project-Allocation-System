/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2.ideafactory.model;


import java.util.Hashtable;

/**
 *
 * @author Sandharu1
 */

public class SimulatedAnealing {

    PreferenceTable pt;
    int energy;
    CandidateSolution initial_sol;
    
    public SimulatedAnealing(PreferenceTable pt,int max) {
        this.pt = pt;
        this.pt.fillPreferencesOfAll(10);
        getBestSol(max);
    }

    private void getBestSol(int max){
        
        initial_sol = new CandidateSolution(pt);
        this.energy = initial_sol.getEnergy();
        
        for (int i=0; i<max; i++){ 
            CandidateAssignment tempCa = initial_sol.getRandomAssignment();
            tempCa.randomizeAssignment();
            compareEnergy(tempCa);
        }
        
    }
    
    private void compareEnergy(CandidateAssignment ca){
        
        int energy = initial_sol.getEnergy();
        
        if(energy<this.energy){
            this.energy = energy;
        }else{
            ca.undoChange();
        }
        
    }
    
    public void displayBestSol(){
        System.out.println("Energy : "+energy);
        initial_sol.printAllAssignments();
    }
   
    public Hashtable getSolution(){
        return initial_sol.getSolution();
    }
    
}
