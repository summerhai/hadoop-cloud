/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containerselection;

import java.io.Serializable;

/**
 *
 * @author A0092669M
 */
 public class ShipContainer implements Serializable{
    protected int containerID;
    protected double weight;
    
    ShipContainer(int containerID,double weight){
    	this.containerID = containerID;
    	this.weight = weight;
    }
    public int getContainerID() {
        return containerID;
    }

    public double getWeight() {
        return weight;
    }
    
    public void setContainerID(int containerID) {
        this.containerID = containerID;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
}
