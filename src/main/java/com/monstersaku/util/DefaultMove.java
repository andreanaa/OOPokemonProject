package com.monstersaku.util;
import com.monstersaku.util.*;
import java.util.*;

public class DefaultMove extends Move{
    private int basepower = 50;

    //konstruktor
    public DefaultMove(){
        super(0, "Default Move", ElementType.NORMAL, 100, 0, 1);
    }

    //Getter
    public int getBasePower(){
        return basepower;
    }

    public void useMove(){
        //do nothing
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
        double defense;;
        this.useMove();
        power = this.getBasePower();
        attack = own.getBaseStats().getCurrentAttack();
        defense = enemy.getBaseStats().getCurrentDefense();
        
        // if(this.isHit()){
            double damage = Math.floor((power * (attack/defense) + 2 ) * randomNumber * effectivity * burnEffect);
            newHP = enemy.getBaseStats().getCurrentHP() - damage;
            enemy.getBaseStats().setCurrentHP(newHP);
            if (enemy.isMonsterDead()) {
                System.out.printf("Monster %s mati\n", enemy.getName());
                enemy.getBaseStats().setCurrentHP(0);
            }
            else {
                System.out.println("HP " + enemy.getName() + " bersisa " + newHP);
            }
            double newHPSource;
            newHPSource = Math.floor(own.getBaseStats().getCurrentHP() - (0.25 * own.getBaseStats().getHealthPoint()));
            own.getBaseStats().setCurrentHP(newHPSource);
            if (own.isMonsterDead()) {
                System.out.printf("Monster %s mati", own.getName());
                System.out.println("");
                own.getBaseStats().setCurrentHP(0);
            }
            else {
                System.out.println("HP " + own.getName() + " bersisa " + newHPSource + " karena memakai move default");
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
        double damage = Math.floor((getBasePower() * (source.getBaseStats().getCurrentAttack() / target.getBaseStats().getCurrentDefense() + 2) * randomNumber * effectivity * burnEffect));
        double newHPTarget;
        double newHPSource;
        newHPTarget = target.getBaseStats().getCurrentHP() - damage;
        if (newHPTarget <= 0) {

        }
        else {
            if (target.getStatusCondition() == StatusCondition.POISON) {
                newHPTarget = Math.floor(newHPTarget * 0.0625);
            }

            else if (target.getStatusCondition() == StatusCondition.BURN) {
                newHPTarget = Math.floor(newHPTarget * 0.125);
            } 

            if (newHPTarget > 0) {
                target.getBaseStats().setCurrentHP(newHPTarget);
            }
        }
        
        newHPSource = Math.floor(source.getBaseStats().getCurrentHP() - (1/4 * source.getBaseStats().getCurrentHP()));
        if (newHPSource <= 0) {

        }
        else {
           source.getBaseStats().setCurrentHP(newHPSource);
        }
    }
    */


    
}
