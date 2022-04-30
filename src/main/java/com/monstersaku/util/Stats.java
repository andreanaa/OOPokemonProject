package com.monstersaku.util; 

public class Stats {
    private double healthPoint;
    private double attack;
    private double defense;
    private double specialAttack;
    private double specialDefense;
    private double speed;
    private double currentHP;
    private double currentAttack;
    private double currentDefense;
    private double currentSpecialAttack;
    private double currentSpecialDefense;
    private double currentSpeed;

    public Stats (Double healthPoint, Double attack, Double defense, Double specialAttack, Double specialDefense, Double speed) {
        this.healthPoint = healthPoint;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        setCurrentHP(healthPoint);
        setCurrentAttack(attack);
        setCurrentDefense(defense);
        setCurrentSpecialAttack(specialAttack);
        setCurrentSpecialDefense(specialDefense);
        setCurrentSpeed(speed);
    }

    public void setCurrentHP(double healthPoint){
        this.currentHP = healthPoint;
    }

    public void setCurrentAttack(double attack){
        this.currentAttack = attack;
    }

    public void setCurrentDefense(double defense){
        this.currentDefense = defense;
    }

    public void setCurrentSpecialAttack(double specialAttack){
        this.currentSpecialAttack = specialAttack;
    }

    public void setCurrentSpecialDefense(double specialDefense){
        this.currentSpecialDefense = specialDefense;
    }

    public void setCurrentSpeed(double speed){
        this.currentSpeed = speed;
    }

    public Double getHealthPoint() {
        return this.healthPoint;
    }

    public Double getAttack() {
        return this.attack;
    }

    public void setAttack (Double attack) {
        this.attack = attack; 
    }

    public Double getDefense() {
        return this.defense;
    }

    public void setDefense (Double defense) {
        this.defense = defense;
    }

    public Double getSpecialAttack() {
        return this.specialAttack;
    }

    public void setSpecialAttack (Double specialAttack) {
        this.specialAttack = specialAttack; 
    }

    public Double getSpecialDefense() {
        return this.specialDefense;
    }

    public void setSpecialDefense (Double specialDefense) {
        this.specialDefense = specialDefense;
    }

    public Double getSpeed() {
        return this.speed;
    }
    public double getCurrentHP() {
        return this.currentHP;
    }

    public double getCurrentAttack() {
        return this.currentAttack;
    }

    public double getCurrentDefense() {
        return this.currentDefense;
    }

    public double getCurrentSpecialAttack() {
        return this.currentSpecialAttack;
    }

    public double getCurrentSpecialDefense() {
        return this.currentSpecialDefense;
    }

    public double getCurrentSpeed() {
        return this.currentSpeed;
    }

    public void setSpeed (Double speed) {
        this.speed = speed;
    }

    public void getStats() {
        System.out.println("HP : " + getCurrentHP());
        System.out.println("Attack : " + getCurrentAttack());
        System.out.println("Defense : " + getCurrentDefense());
        System.out.println("SpecialAttack : " + getCurrentSpecialAttack());
        System.out.println("SpecialDefense : " + getCurrentSpecialDefense());
        System.out.println("Speed : " + getCurrentSpeed());
    }
}
