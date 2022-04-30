package com.monstersaku.util;
import com.monstersaku.util.*;
import java.util.*;

public class NormalMove extends Move {
    private int basepower;

    //konstruktor
    public NormalMove(int id, String name, ElementType elementType, int accuracy, int priority, int ammunition, int basepower){
        super(id, name, elementType, accuracy, priority, ammunition);
        this.basepower = basepower;
    }
    
    //getter
    public int getBasePower() {
       return this.basepower;
    }

    public void execute (Monster source, Monster target) {
		double newHP;
        Random random = new Random();
        // harus positif si nextInt(angka)
        double rand = random.nextInt(16) + 85;
        double randomNumber = rand / 100;
        double effectivity = 1.0;
        for (ElementType e : target.getElementTypes()) {
            effectivity *= EffectivityPool.getEffectivity(this.getElementType(), e);
        }

        double burnEffect = 1.0;
        if (source.getStatusCondition() == StatusCondition.BURN) {
            burnEffect = 0.5;
        }

        int power;
        double attack;
        double defense;
        this.useMove();
        power = getBasePower();
        attack = source.getBaseStats().getCurrentAttack();
        defense = target.getBaseStats().getCurrentDefense();

        // if(this.isHit()){
            double damage = Math.floor((power * (attack/defense) + 2 ) * randomNumber * effectivity * burnEffect);
            newHP = target.getBaseStats().getCurrentHP() - damage;
            target.getBaseStats().setCurrentHP(newHP);
            if (target.isMonsterDead()) {
                System.out.printf("Monster %s mati", target.getName());
                System.out.println("");
                target.getBaseStats().setCurrentHP(0);
            }
            else {
                System.out.println("HP " + target.getName() + " bersisa " + target.getBaseStats().getCurrentHP());
            }
        // } else{// miss
        //     System.out.printf("%s tidak berhasil :P!\n", source.getName());
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
        double newHP;
        newHP = target.getBaseStats().getCurrentHP() - damage;
        if (newHP <= 0) {
            
        }
        else {
            if (target.getStatusCondition() == StatusCondition.POISON) {
                newHP = Math.floor(newHP * 0.0625);
            }
            
            else if (target.getStatusCondition() == StatusCondition.BURN) {
                newHP = Math.floor(newHP * 0.125);
            }

            if (newHP > 0) {
                target.getBaseStats().setCurrentHP(newHP);
            }
        }
    }
    */
}
