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
public class GeneticAlgorothm {
    PreferenceTable pt;
    int fitness;
    CandidateSolution bestSol;
    
    public GeneticAlgorothm(PreferenceTable pt) {
        this.pt = pt;
        this.pt.fillPreferencesOfAll(10);
        getBestSol();
    }

    private CandidateSolution getBestSol(){
        
        CandidateSolution initial_sol = new CandidateSolution(pt);
        this.fitness = initial_sol.getFitness();
        this.bestSol = initial_sol;
        
        for (int i=0; i<50; i++){
           CandidateSolution sol = new CandidateSolution(pt);
           compareFitness(sol.getFitness(),sol);
        }
        
        return bestSol;
    }
    
    private void compareFitness(int fitness, CandidateSolution sol){
        if(fitness>this.fitness){
            this.fitness = fitness;
            this.bestSol = sol;
        }
    }
    
    public void displayBestSol(){
        System.out.println("Fitness : "+fitness);
        bestSol.printAllAssignments();
    }
   
    public Hashtable getSolution(){
        return bestSol.getSolution();
    }
}
