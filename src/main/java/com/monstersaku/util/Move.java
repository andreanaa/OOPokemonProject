package com.monstersaku.util;
import com.monstersaku.util.*;
import java.util.*;

public abstract class Move {
    protected int id;
    protected String name;
    protected ElementType elementType;
    protected int accuracy;
    protected int priority;
    protected int ammunition;
    //buat Movetype nanti pake instaceOf
    //id nanti buat baca file csv 
    protected int currentam;

    //konstruktor
    public Move(int id, String name, ElementType elementType, int accuracy, int priority, int ammunition){
        setID(id);
        setName(name);
        setElementType(elementType);
        setAccuracy(accuracy);
        setPriority(priority);
        this.ammunition = ammunition;

        setCurrentAm(ammunition);
    }

    // Getter
    public int getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public ElementType getElementType(){
        return elementType;
    }

    public int getAccuracy(){
        return accuracy;
    }

    public int getPriority(){
        return priority;
    }

    public int getAmmunition(){
        return ammunition;
    }

    public double getCurrentAm(){
        return currentam;
    }

    // setter
    public void setID (int id) {
        this.id = id;
    }

    public void setName (String name) {
        this.name = name;
    }
    
    public void setElementType (ElementType elementType) {
        this.elementType = elementType;
    }
    
    public void setAccuracy (int accuracy) {
        this.accuracy = accuracy;
    }
    
    public void setPriority (int priority) {
        this.priority = priority;
    }
    
    public void setCurrentAm (int ammunition) {
        this.currentam = ammunition;
    }

  // Method
    public void useMove(){ 
        this.currentam -= 1;
    }

    public boolean isHit(){
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;
        if (randomNumber <= getAccuracy()) {
            // hit = true;
            return true;
        }
        else {
            return false;
        }
    }
    
    public abstract void execute(Monster own, Monster enemy);

}
