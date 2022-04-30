package com.monstersaku.util;
import com.monstersaku.util.*;
import java.util.*;

public class StatusMove extends Move {
    private String target; // OWN / ENEMY
    private StatusCondition statusCondition;
    private Stats statusStats; // [healthPoint, attack, defense, specialAttack. specialDefense, speed]


    //kosntruktor
    public StatusMove(int id, String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, StatusCondition statusCondition,Double healthPoint, Double attack, Double defense, Double specialAttack, Double specialDefense, Double speed){
        super(id, name, elementType, accuracy, priority, ammunition);
        this.target = target;
        this.statusCondition = statusCondition;
        this.statusStats = new Stats(healthPoint, attack, defense, specialAttack, specialDefense, speed);
    }

    //getter
    public String getTarget(){
        return target;
    }

    public StatusCondition getStatusCondition(){
        return statusCondition;
    }

    public Stats getStatusStats(){
        return statusStats;
    }

    public void execute (Monster own, Monster enemy) {
        this.useMove();
            double newHP;
            if (this.getTarget().equals("OWN")){
                newHP = Math.floor(own.getBaseStats().getCurrentHP() + own.getBaseStats().getHealthPoint()*(this.getStatusStats().getHealthPoint()/100));
                if (newHP > own.getBaseStats().getHealthPoint()) {
                    own.getBaseStats().setCurrentHP(own.getBaseStats().getHealthPoint());
                }
                else {
                    own.getBaseStats().setCurrentHP(newHP);
                }
            }
            else {
                if (enemy.getStatusCondition() == StatusCondition.NONE) {
                    enemy.setStatusCondition(this.getStatusCondition());
                    if (enemy.getStatusCondition() == StatusCondition.BURN) {
                        System.out.println("Yahh otakmu terbakar karena tubes");
                    }
                    else if (enemy.getStatusCondition() == StatusCondition.POISON) {
                        System.out.println("Kamu tidak sengaja memakan keong racun");
                    }
                    else if (enemy.getStatusCondition() == StatusCondition.SLEEP) {
                        System.out.println("Mimpi yang indah yaa kawan");
                    }
                    else if (enemy.getStatusCondition() == StatusCondition.PARALYZE) {
                        System.out.println("Nah jangan mainan sama kabel, kesetrum kan :p");
                    }
                }
                else {
                    System.out.println("Eitsss cuman boleh satu yaa, gabole maruk >:(");
                }
            }		
    }
}
