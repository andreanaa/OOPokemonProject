package com.monstersaku.util;
import com.monstersaku.util.*;
import java.util.*;

public class SpecialMove extends Move {
    private int basepower;

    //konstruktor
    public SpecialMove(int id, String name, ElementType elementType, int accuracy, int priority, int ammunition, int basepower){
        super(id, name, elementType, accuracy, priority, ammunition);
        this.basepower = basepower;
    }
    
    //getter
    public int getBasePower() {
        return basepower;
    }

    public void execute (Monster own, Monster enemy) {
		double newHP;
        Random random = new Random();
        double rand = random.nextInt(16) + 85;
        double randomNumber = rand / 100;
        double effectivity = 1;
        for (ElementType e : enemy.getElementTypes()) {
            effectivity *= EffectivityPool.getEffectivity(this.getElementType(), e);
        }

        double burnEffect = 1;
        if (own.getStatusCondition() == StatusCondition.BURN) {
            burnEffect = 0.5;
        }

        int power;
        double attack;
        double defense;

        this.useMove();
        power = this.getBasePower();
        attack = own.getBaseStats().getCurrentSpecialAttack();
        defense = enemy.getBaseStats().getCurrentSpecialDefense();
        

        // if(this.isHit()){
            double damage = Math.floor((power * (attack/defense) + 2 ) * randomNumber * effectivity * burnEffect);
            newHP = enemy.getBaseStats().getCurrentHP() - damage;
            enemy.getBaseStats().setCurrentHP(newHP);
            if (enemy.isMonsterDead()) {
                System.out.printf("Monster %s mati", enemy.getName());
                System.out.println("");
                enemy.getBaseStats().setCurrentHP(0);
            }
            else {
                System.out.println("HP " + enemy.getName() + " bersisa " + newHP);
            }
        // } else{// miss
        //     System.out.printf("%s tidak berhasil :P!\n", own.getName());
        // }
    }

    /** 
    public void execute (Monster source, Monster target, EffectivityPool ep) {
        Random random = new Random();
        double randomNumber = (random.nextInt(85 + 1 - 100) + 85) / 100;
        double effectivity = 1;
        double burnEffect = 1;
        for (ElementType e : target.getElementTypes()) {
            effectivity *= ep.getEffectivity(this.elementType, e);
        }
        if (source.getStatusCondition() == StatusCondition.BURN) {
            burnEffect = 0.5;
        }
        double damage = Math.floor((getBasePower() * (source.getBaseStats().getCurrentSpecialAttack() / target.getBaseStats().getCurrentSpecialDefense() + 2) * randomNumber * effectivity * burnEffect));
        double newHP;
        newHP = target.getBaseStats().getCurrentHP() - damage;
        if (newHP <= 0) {

        }
        else {
            if (target.getStatusCondition() == StatusCondition.BURN) {
                newHP = Math.floor(newHP * 0.125);
            }
            else if (target.getStatusCondition() == StatusCondition.POISON) {
                newHP = Math.floor(newHP * 0.0625);
            }
            if (newHP > 0) {
                target.getBaseStats().setCurrentHP(newHP);
            }
            else {
                
            }
        }
    }
    */
}
