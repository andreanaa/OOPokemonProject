package com.monstersaku;

import com.monstersaku.util.CSVReader;
import com.monstersaku.util.EffectivityPool;
import com.monstersaku.util.ElementType;
import com.monstersaku.util.Fight;
import com.monstersaku.util.Help;
import com.monstersaku.util.Monster;
import com.monstersaku.util.Move;
import com.monstersaku.util.NormalMove;
import com.monstersaku.util.SpecialMove;
import com.monstersaku.util.Stats;
import com.monstersaku.util.StatusCondition;
import com.monstersaku.util.StatusMove;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/monsterpool.csv",
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv"));

    public static void main(String[] args) {
        ArrayList<CSVReader> readers = new ArrayList<CSVReader>();
        ArrayList<Move> movePool = new ArrayList<Move>();
        ArrayList<Monster> monsterPool = new ArrayList<Monster>();

        for (String fileName : CSV_FILE_PATHS) {
            try {
                readers.add(new CSVReader(new File(Main.class.getResource(fileName).toURI()), ";"));
            } catch (Exception e) { }
        }
        try {
        // Membaca Effectivity Pool
            readers.get(2).setSkipHeader(true);
            List<String[]> lines = readers.get(2).read();
            for (String[] line : lines) {
                ElementType source, target;
                try {
                    // System.out.println(line[0] + line[1] + line[2]);
                    source = ElementType.valueOf(line[0]); //dr string ke enum
                    target = ElementType.valueOf(line[1]);
                    double effectivity = Double.parseDouble(line[2]);
                    // System.out.println("b");
                    EffectivityPool.addEffectivity(source, target, effectivity);
                } catch (IllegalArgumentException e) {
                    System.out.println("[IllegalArgumentException] " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("[Exception] " + e.getMessage());
                }
            }
            //baca movepool
            // readers.get(1).setSkipHeader(true);
            // List<String[]> lines2 = readers.get(1).read();
            // for (String[] line : lines2) {
            //     int id = Integer.parseInt(line[0]);
            //     String moveType = line[1];
            //     String name = line[2];
            //     ElementType elementType = ElementType.valueOf(line[3]);
            //     int accuracy = Integer.parseInt(line[4]);
            //     int priority = Integer.parseInt(line[5]);
            //     int ammunition = Integer.parseInt(line[6]);
                
            //     if (moveType.equals("NORMAL")) {
            //         int effect = Integer.parseInt(line[8]);
            //         NormalMove normal = new NormalMove(id, name, elementType, accuracy, priority, ammunition, effect);
            //         movePool.add(normal);
            //     }

            //     else if (moveType.equals("SPECIAL")) {
            //         int effect = Integer.parseInt(line[8]);
            //         SpecialMove special = new SpecialMove(id, name, elementType, accuracy, priority, ammunition, effect);
            //         movePool.add(special);
            //     }

            //     else if (moveType.equals("STATUS")) {
            //         String target = line[7];
            //         String status = line[8];
            //         StatusCondition statusCondition;
            //         if (status.equals("-")) {
            //             statusCondition = StatusCondition.NONE;
            //         }
            //         else {
            //             statusCondition = StatusCondition.valueOf(line[8]);
            //         }
            //         String statusStats = line[9];
            //         String[] arrStatusStats = statusStats.split(",", 6);
            //         Double hpEffect = Double.parseDouble(arrStatusStats[0]);
            //         Double attackEffect = Double.parseDouble(arrStatusStats[1]);
            //         Double defenseEffect = Double.parseDouble(arrStatusStats[2]);
            //         Double specialAttackEffect = Double.parseDouble(arrStatusStats[3]);
            //         Double specialDefenseEffect = Double.parseDouble(arrStatusStats[4]);
            //         Double speedEffect = Double.parseDouble(arrStatusStats[5]);
            //         // Stats statusStat = new Stats(hpEffect, attackEffect, defenseEffect, specialAttackEffect, specialDefenseEffect, speedEffect);
            //         StatusMove statusMove = new StatusMove(
            //             id, 
            //             name, 
            //             elementType, 
            //             accuracy, 
            //             priority, 
            //             ammunition, 
            //             target, 
            //             statusCondition,
            //             hpEffect,
            //             attackEffect,
            //             defenseEffect,
            //             specialAttackEffect,
            //             specialDefenseEffect,
            //             speedEffect
            //         );

            //         movePool.add(statusMove);
            //     }
            // }

            // readers.get(0).setSkipHeader(true);
            // List<String[]> lines1 = readers.get(0).read();
            // for (String[] line1 : lines1) {
            //     // masuk ke variabel
            //     int id = Integer.parseInt(line1[0]);
            //     String name = line1[1];
            //     String element = line1[2];
            //     String stat = line1[3];
            //     String[] move = line1[4].split(",");


            //     // masuk ke objek
            //     ArrayList<ElementType> elementType = new ArrayList<ElementType>();
            //     String[] arrofeltype = element.split(",", 7);
            //     for (String a : arrofeltype) {
            //         switch (a) {
            //             case ("NORMAL") : 
            //                 elementType.add(ElementType.NORMAL);
            //                 break;
            //             case ("FIRE") :
            //                 elementType.add(ElementType.FIRE);
            //                 break;
            //             case ("WATER") : 
            //                 elementType.add(ElementType.WATER);
            //                 break;
            //             case ("GRASS") : 
            //                 elementType.add(ElementType.GRASS);
            //                 break;
            //         }
            //     }

            //     ArrayList<Double> stats = new ArrayList<Double>();
            //     String[] arrofstats = stat.split(",", 7);
            //     for(String a : arrofstats) {
            //         Double d = Double.parseDouble(a);
            //         stats.add(d);
            //     }
            //     Stats basestats = new Stats(stats.get(0), stats.get(1),stats.get(2),stats.get(3),stats.get(4),stats.get(5));

            //     ArrayList<Integer> moveIds = new ArrayList<Integer>();
                        
            //     for (String moveid : line1[4].split(",")) {
            //         moveIds.add(Integer.parseInt(moveid));
            //     }

            //     Monster monsterbaru = new Monster(id, name, elementType, basestats, moveIds);

            //     for(String moves : move) {
            //         monsterbaru.getMoveId().add(Integer.parseInt(moves));
            //     }

            //     for (int i=0; i < monsterbaru.getMoveId().size(); i++){
            //         monsterbaru.getMoves().add(movePool.get(i));
            //     }

            //     monsterPool.add(monsterbaru);
            // }
        } 
        catch (Exception e) { }

        boolean endProgram = false;

        while (!endProgram) {
            System.out.println("----------------------------------");
            System.out.println("|                                |");
            System.out.println("| SELAMAT DATANG DI MONSTERSAKU! |");
            System.out.println("|                                |");
            System.out.println("|      Masukkan Pilihanmu!       |");
            System.out.println("|      [1] START GAME            |");
            System.out.println("|      [2] HELP                  |");
            System.out.println("|      [3] EXIT                  |");
            System.out.println("|                                |");
            System.out.println("----------------------------------");
            
            int pilihan = Main.scanner.nextInt();
            while (pilihan < 1 || pilihan > 3) {
                System.out.println("Input salah!");
                pilihan = Main.scanner.nextInt();
            }
            if (pilihan == 1) {
                Fight.startGame(readers.get(0), readers.get(1));
            } else if (pilihan == 2) {
                Help.getHelp();
            } else {
                System.out.println("Terima kasih sudah memainkan game kami, sampai jumpa lagi :D");
                System.exit(-1);
            }
        }
        
        // buat itung di move ntar
        // double effectivity = EffectivityPool.getEffectivity(ElementType.WATER, ElementType.GRASS);
        // System.out.println(effectivity);
        // contoh dapet effectivity
        // System.out.println(EffectivityPool.getEffectivity(ElementType.FIRE, ElementType.GRASS));
        // System.out.println(ElementType.FIRE.toString()); //dari enum ke string
/*
        for (String fileName : CSV_FILE_PATHS) {
            try {
                System.out.printf("Filename: %s\n", fileName);
                CSVReader reader = new CSVReader(new File(Main.class.getResource(fileName).toURI()), ";");
                reader.setSkipHeader(true);
                List<String[]> lines = reader.read();
                System.out.println("=========== CONTENT START ===========");
                for (String[] line : lines) {
                    for (String word : line) {
                        System.out.printf("%s ", word);
                    }
                    System.out.println();
                }
                System.out.println("=========== CONTENT END ===========");
                System.out.println();
            } catch (Exception e) {
                // do nothing
            }
        }
*/ 
    }

    // ini apa cok
    // private static void startGame() {
    //     Scanner input = Main.scanner;
    //     System.out.println("Masukkan Nama Pemain");
    //     System.out.println("Masukkan Nama Player1:");
    //     String nPlayer1 = input.next();
    //     System.out.println("Masukkan Nama Player2:");
    //     String nPlayer2 = input.next();
    //     ArrayList<Monster> listOfMonster1 = new ArrayList<Monster>();
    //     ArrayList<Monster> listOfMonster2 = new ArrayList<Monster>();
    //     Player player1 = new Player(nPlayer1,listOfMonster1);
    //     Player player2 = new Player(nPlayer2,listOfMonster2);

    //     while (player1.getNumberOfMonster() != 0 || player2.getNumberOfMonster() != 0) {
    //         System.out.println("[1] SWITCH [2] MOVE [3] MONSTER INFO [4] GAME INFO");
    //         System.out.println("Masukkan pilhanmu!");
    //         int command1 = input.nextInt(); // input player1
    //         int command2 = input.nextInt(); // input player2
    //         while (command1 < 1 || command2 > 4 || command2 < 1 || command2 > 4) {
    //             System.out.println("Input salah!");
    //             command1 = input.nextInt();
    //             command2 = input.nextInt();
    //         }
    //         while (command1 == 3 || command2 == 3 || command1 == 4 || command2 == 4) {
    //             if (command1 == 3) {

    //             } else if (command2 == 3) {

    //             } else if (command1 == 4) {

    //             } else {
                    
    //             }
    //         }
    //     }
    // }

    
}
