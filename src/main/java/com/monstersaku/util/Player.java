package com.monstersaku.util;
import com.monstersaku.util.*;
import java.util.*;

import javax.naming.NameAlreadyBoundException;

public class Player {
    private String nama;
    private ArrayList<Monster> listOfMonster; //monster yang dimiliki pemain
    private int numberOfMonster;
    
    public Player(String nama, ArrayList<Monster> listOfMonster){
        //Jumlah monster 6 per player
        this.nama = nama;
        this.listOfMonster = listOfMonster;
        this.numberOfMonster = 6;
    }

    // public Player(String nama, ArrayList<Monster> listOfMonster, int numberOfMonster){
    //     this.nama = nama;
    //     this.listOfMonster = listOfMonster;
    //     this.numberOfMonster = numberOfMonster;
    // }

    public String getName(){
        return this.nama;
    }

    public ArrayList<Monster> getListOfMonster(){
        return this.listOfMonster;
    }

    public int getNumberOfMonster(){
        return this.numberOfMonster;
    }


    //Setter
    public void setName(String nama){
        this.nama = nama;
    }

    public void setListOfMonster(ArrayList<Monster> listOfMonster){
        this.listOfMonster = listOfMonster;
    }

    public void setNumberOfMonster(int numberOfMonster){
        this.numberOfMonster = numberOfMonster;
    }

    
    public void getMonsterInfo(Player player1, Player player2) {
        System.out.println(player1.getName() + " Monster Info : ");
        for (int i = 0; i < listOfMonster.size(); i++) {
            player1.listOfMonster.get(i).info();
        }
        System.out.println("=====================================");
        System.out.println("=====================================");
        System.out.println("");
        System.out.println(player2.getName() + " Monster Info : ");
        for (int i = 0; i < listOfMonster.size(); i++) {
            player2.listOfMonster.get(i).info();
        }
    }

    public void getBattleInfo(Player player1, Player player2, int turn, int x, int y) {
        System.out.println("Battle Info : ");
        System.out.println("Turn " + turn);
        System.out.println("Monster di field " + player1.getName() + " : "+ player1.getListOfMonster().get(x).getName());
        System.out.println("Monster di bag " + player1.getName() + " :");
        for (int i = 0; i < listOfMonster.size(); i++) {
            if (i != x) {
                System.out.println(player1.getListOfMonster().get(i).getName());
            }
        }
        System.out.println("Monster di field " + player2.getName() + " : "+ player2.getListOfMonster().get(y).getName());
        System.out.println("Monster di bag " + player2.getName() + " :");
        for (int i = 0; i < listOfMonster.size(); i++) {
            if (i != x) {
                System.out.println(player2.getListOfMonster().get(i).getName());
            }
        }
        System.out.println("");
    }

    public void getInfoSleep() {
        for (int i = 0; i < listOfMonster.size(); i++) {
            if (listOfMonster.get(i).getStatusCondition() == StatusCondition.SLEEP) {
                listOfMonster.get(i).setCountSleep(listOfMonster.get(i).getCountSleep() - 1);
                if (listOfMonster.get(i).getCountSleep() == 0) {
                    listOfMonster.get(i).setStatusCondition(StatusCondition.NONE);
                }
            }

        }
    }

}