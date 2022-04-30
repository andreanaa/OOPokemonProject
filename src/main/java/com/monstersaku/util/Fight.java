package com.monstersaku.util;
import com.monstersaku.Main;
import java.util.*;

public class Fight {

    private static void readSixRandomMonsters (List<Monster> ListOfMonsters, CSVReader monsterReader, CSVReader moveReader) {
        ListOfMonsters.clear();

        try {
            monsterReader.setSkipHeader(true);
            List<String[]> lines = monsterReader.read();

            // cari random integer
            Random random = new Random();
            List<Integer> randomIntegers = new ArrayList<Integer>();
            for (int i = 0; i < 6; i++) {
                randomIntegers.add(random.nextInt(lines.size()));
            }

            for (int i : randomIntegers) {
                String[] line1 = lines.get(i);
                // masuk ke variabel
                int id = Integer.parseInt(line1[0]);
                String name = line1[1];
                String element = line1[2];
                String stat = line1[3];
                // String[] move = line1[4].split(",");

                // masuk ke objek
                ArrayList<ElementType> elementType = new ArrayList<ElementType>();
                String[] arrofeltype = element.split(",", 7);
                for (String a : arrofeltype) {
                    switch (a) {
                        case ("NORMAL") : 
                            elementType.add(ElementType.NORMAL);
                            break;
                        case ("FIRE") :
                            elementType.add(ElementType.FIRE);
                            break;
                        case ("WATER") : 
                            elementType.add(ElementType.WATER);
                            break;
                        case ("GRASS") : 
                            elementType.add(ElementType.GRASS);
                            break;
                    }
                }

                ArrayList<Double> stats = new ArrayList<Double>();
                String[] arrofstats = stat.split(",", 7);
                for(String a : arrofstats) {
                    Double d = Double.parseDouble(a);
                    stats.add(d);
                }
                Stats basestats = new Stats(stats.get(0), stats.get(1),stats.get(2),stats.get(3),stats.get(4),stats.get(5));
                ArrayList<Integer> moveIds = new ArrayList<Integer>();
                        
                for (String moveid : line1[4].split(",")) {
                    moveIds.add(Integer.parseInt(moveid));
                }

                Monster monsterbaru = new Monster(id, name, elementType, basestats, moveIds);

                ListOfMonsters.add(monsterbaru);
            }
        } catch (Exception e) {
            System.out.println("test " + e.getMessage());
        }

        for (Monster m : ListOfMonsters) {
            m.readMoves(moveReader);
        }
        // loop yg ini malah masukin semua move dari moveReader
    }

    public static void startGame(CSVReader monsterReader, CSVReader moveReader) {
        Scanner scan = Main.scanner;
        System.out.println("Masukkan Nama Pemain");
        System.out.println("Masukkan Nama Player1:");
        String nPlayer1 = scan.next();
        System.out.println("Masukkan Nama Player2:");
        String nPlayer2 = scan.next();
        ArrayList<Monster> listOfMonster1 = new ArrayList<Monster>();
        ArrayList<Monster> listOfMonster2 = new ArrayList<Monster>();
        Player player1 = new Player(nPlayer1,listOfMonster1);
        Player player2 = new Player(nPlayer2,listOfMonster2);

        Fight.readSixRandomMonsters(listOfMonster1, monsterReader, moveReader);
        Fight.readSixRandomMonsters(listOfMonster2, monsterReader, moveReader);

        // System.out.println("banyak monster player1 : " + player1.getListOfMonster().size());
        // System.out.println("banyak monster player2 : " + player2.getListOfMonster().size());

        boolean isEndGame = false;
        int x = 0;
        int y = 0;
        int turn = 0;
        int move1 = 0;
        int move2 = 0;
        Monster currentMonsterPlayer1, currentMonsterPlayer2;
        boolean turn1 = true;
        boolean turn2 = true;

        while (!isEndGame) {
            while (player1.getNumberOfMonster() != 0 || player2.getNumberOfMonster() != 0) {
                turn += 1;
                currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                // System.out.println(turn1);
                // System.out.println(turn2);
                System.out.println("Round : " + turn);
                System.out.println("");
                System.out.println(player1.getName());
                System.out.println("==============================");
                System.out.println(currentMonsterPlayer1.getName() + "  " + currentMonsterPlayer1.getBaseStats().getCurrentHP() + "/" + currentMonsterPlayer1.getBaseStats().getHealthPoint());
                System.out.println("");
                System.out.println("          VS");
                System.out.println("");
                System.out.println(currentMonsterPlayer2.getName() + "  " + currentMonsterPlayer2.getBaseStats().getCurrentHP() + "/" + currentMonsterPlayer2.getBaseStats().getHealthPoint());
                System.out.println("==============================");
                System.out.println(player2.getName());
                System.out.println("");
                System.out.println("[1] SWITCH [2] MOVE [3] MONSTER INFO [4] GAME INFO");
                System.out.println("Masukkan pilihanmu " + player1.getName() + "!");
                
                int command1 = scan.nextInt();
                if (command1 == 1) {
                    if (player1.getNumberOfMonster() == 1) {
                        System.out.println("Yahh monster kamu sisa satu, gabisa switch deh :(");
                        command1 = -1;
                    }
                }
                while (command1 != 1 && command1 != 2) {
                    if (command1 == 3) {
                        player1.getMonsterInfo(player1, player2);
                    }
                    else if (command1 == 4) {
                        player1.getBattleInfo(player1, player2, turn, x, y);
                    }
                    System.out.println("[1] SWITCH [2] MOVE [3] MONSTER INFO [4] GAME INFO");
                    System.out.println("Masukkan pilihanmu " + player1.getName() + "!");
                    command1 = scan.nextInt();
                    if (command1 == 2) {
                        if (player1.getNumberOfMonster() == 1) {
                            System.out.println("Yahh monster kamu sisa satu, gabisa switch deh :(");
                            command1 = -1;
                        }
                    }
                }

                if (command1 == 1) {
                    System.out.println("Mau switch dengan monster yang mana?");
                    for (int i = 0; i < player1.getListOfMonster().size(); i++) {
                        System.out.println((i+1) + (". ") + player1.getListOfMonster().get(i).getName() + (" ") + player1.getListOfMonster().get(i).getBaseStats().getCurrentHP() + ("/") + player1.getListOfMonster().get(i).getBaseStats().getHealthPoint());
                    }
                    int pilihSwitch1 = scan.nextInt();
                    move1 = pilihSwitch1 - 1;

                } else if (command1 == 2) {
                    for (int i = 0; i < currentMonsterPlayer1.getMoves().size(); i++) {
                        if (currentMonsterPlayer1.getMoves().get(i).getName().equals("Default Move")) {
                            System.out.println(("[") + (i+1) + ("] ") + (currentMonsterPlayer1.getMoves().get(i).getName()) + (" ") + currentMonsterPlayer1.getMoves().get(i).getElementType());
                        } else {
                            System.out.println(("[") + (i+1) + ("] ") + currentMonsterPlayer1.getMoves().get(i).getName() + (" ") + currentMonsterPlayer1.getMoves().get(i).getElementType() + (" ") + currentMonsterPlayer1.getMoves().get(i).getCurrentAm() + ("/") + currentMonsterPlayer1.getMoves().get(i).getAmmunition());
                        }
                    }

                    System.out.println("Pilih move yang ingin digunakan!");
                    int pilihmove1 = scan.nextInt();
                    if (currentMonsterPlayer1.getMoves().get(pilihmove1 - 1).getCurrentAm()==0){
                        System.out.println("Move sudah habis terpakai :(");
                        pilihmove1 = 0;
                    }

                    while ((pilihmove1 - 1) < 0 || (pilihmove1 -1) > currentMonsterPlayer1.getMoves().size()) {
                        System.out.println("Pilih move yang ingin digunakan!");
                        pilihmove1 = scan.nextInt();
                        if (currentMonsterPlayer1.getMoves().get(pilihmove1 - 1).getCurrentAm()==0){
                            System.out.println("Move sudah habis terpakai :(");
                            pilihmove1 = 0;
                        }
                    }
                    move1 = pilihmove1 - 1;
                    
                } 
                
                System.out.println("[1] SWITCH [2] MOVE [3] MONSTER INFO [4] GAME INFO");
                System.out.println("Masukkan pilihanmu " + player2.getName() + "!");
                
                // disini letak bug nya
                int command2 = scan.nextInt();
                // int command2 = scan.nextInt();
                if (command2 == 1) {
                    if (player2.getNumberOfMonster() == 1) {
                        System.out.println("Yahh monster kamu sisa satu, gabisa switch deh :(");
                        command2 = -1;
                    }
                }
                while (command2 != 1 && command2 != 2) {
                    if (command2 == 3) {
                        player2.getMonsterInfo(player1, player2);
                    }
                    else if (command2 == 4) {
                        player2.getBattleInfo(player1, player2, turn, x, y);
                    }
                    System.out.println("[1] SWITCH [2] MOVE [3] MONSTER INFO [4] GAME INFO");
                    System.out.println("Masukkan pilihanmu " + player2.getName() + "!");
                    command2 = scan.nextInt();
                    if (command2 == 2) {
                        if (player2.getNumberOfMonster() == 1) {
                            System.out.println("Yahh monster kamu sisa satu, gabisa switch deh :(");
                            command2 = -1;
                        }
                    }
                }
                
                if (command2 == 1) {
                    System.out.println("Mau switch dengan monster yang mana?");
                    for (int i = 0; i < player2.getListOfMonster().size(); i++) {
                        System.out.println((i+1) + (". ") + player2.getListOfMonster().get(i).getName() + (" ") + player2.getListOfMonster().get(i).getBaseStats().getCurrentHP() + ("/") + player2.getListOfMonster().get(i).getBaseStats().getHealthPoint());
                    }
                    int pilihSwitch2 = scan.nextInt();
                    move2 = pilihSwitch2 - 1;

                } else if (command2 == 2) {
                    for (int i = 0; i < currentMonsterPlayer2.getMoves().size(); i++) {
                        if (currentMonsterPlayer2.getMoves().get(i).getName().equals("Default Move")) {
                            System.out.println(("[") + (i+1) + ("] ") + (currentMonsterPlayer2.getMoves().get(i).getName() + (" ") + currentMonsterPlayer2.getMoves().get(i).getElementType()));
                        } else {
                            System.out.println(("[") + (i+1) + ("] ") + currentMonsterPlayer2.getMoves().get(i).getName() + (" ") + currentMonsterPlayer2.getMoves().get(i).getElementType() + (" ") + player2.getListOfMonster().get(y).getMoves().get(i).getCurrentAm() + ("/") + currentMonsterPlayer2.getMoves().get(i).getAmmunition());
                        }
                    }

                    System.out.println("Pilih move yang ingin digunakan!");
                    int pilihmove2 = scan.nextInt();
                    if (currentMonsterPlayer2.getMoves().get(pilihmove2 - 1).getCurrentAm()==0){
                        System.out.println("Move sudah habis terpakai :(");
                        pilihmove2 = 0;
                    }

                    while ((pilihmove2 - 1) < 0 || (pilihmove2 -1) > currentMonsterPlayer2.getMoves().size()) {
                        System.out.println("Pilih move yang ingin digunakan!");
                        pilihmove2 = scan.nextInt();
                        if (currentMonsterPlayer2.getMoves().get(pilihmove2 - 1).getCurrentAm()==0){
                            System.out.println("Move sudah habis terpakai :(");
                            pilihmove2 = 0;
                        }
                    }
                    move2 = pilihmove2 - 1;
                }
            
                if (command1 == 1 && command2 == 1) {
                    int temp = Switch.switchMonster(player1, x, move1);
                    x = temp;
                    currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                    temp = Switch.switchMonster(player2, y, move2);
                    y = temp;
                    currentMonsterPlayer2 = player2.getListOfMonster().get(y);

                } else if (command1 == 1 && command2 == 2) {
                    int temp = Switch.switchMonster(player1, x, move1);
                    x = temp;
                    currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                    if (turn2) {
                        System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                        if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                            currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                            if (currentMonsterPlayer1.isMonsterDead()) {
                                currentMonsterPlayer1.setNone();
                                System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                if (player1.getNumberOfMonster() > 0) {
                                    temp = Switch.switchMonster(player1, x, -1);
                                    x = temp;
                                    currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                }
                            }
                        }
                        else {
                            System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                        }
                    }
                    
                    else if (!turn2) {
                        if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                            System.out.println(player2.getListOfMonster().get(x).getName() + " kesetrum");
                        }
                        else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                            System.out.println(player2.getListOfMonster().get(x).getName() + "rebahan");
                        }
                    }

                } else if (command1 == 2 && command2 == 1) {
                    int temp = Switch.switchMonster(player2, y, move2);
                    y = temp;
                    currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                    if (turn1) {
                        System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                        if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                            currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                            if (currentMonsterPlayer2.isMonsterDead()) {
                                currentMonsterPlayer2.setNone();
                                System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                if (player2.getNumberOfMonster() > 0) {
                                    temp = Switch.switchMonster(player2, y, -1);
                                    y = temp;
                                    currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                }
                            }
                        }
                        else {
                            System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                        }
                    }
    
                    else if (!turn1) {
                        if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                            System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                        }
                        else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                            System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                        }
                    }

                } else if (command1 == 2 && command2 == 2) {
                    if (currentMonsterPlayer1.getMoves().get(move1).getPriority() > currentMonsterPlayer2.getMoves().get(move2).getPriority()) {
        
                        if (turn1 && turn2) {
                            System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                            if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.println(currentMonsterPlayer2.getName() + " rebahan dulu");
                                }
                                else if (currentMonsterPlayer2.isMonsterDead()) {
                                    currentMonsterPlayer2.setNone();
                                    System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                    player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                    if (player2.getNumberOfMonster() > 0) {
                                        int temp = Switch.switchMonster(player2, y, -1);
                                        y = temp;
                                        currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                    }
                                }
                                else {
                                    System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                    if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                        currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                        // executeMove(currentMonsterPlayer2, currentMonsterPlayer1, currentMonsterPlayer2.getMoves().get(move2));
                                        if (currentMonsterPlayer1.isMonsterDead()) {
                                            currentMonsterPlayer1.setNone();
                                            System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                            player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                            if (player1.getNumberOfMonster() > 0) {
                                                int temp = Switch.switchMonster(player1, x, -1);
                                                x = temp;
                                                currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                            }
                                        }
                                    }
                                    else {
                                        System.out.println("gagal");
                                    }
                                }
                            }
                            else {
                                System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                    currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                    if (currentMonsterPlayer1.isMonsterDead()) {
                                        currentMonsterPlayer1.setNone();
                                        System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                        player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                        if (player1.getNumberOfMonster() > 0) {
                                            int temp = Switch.switchMonster(player1, x, -1);
                                            x = temp;
                                            currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                        }
                                    }
                                }
                                else {
                                    System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                }
                            }
                        }
                        
                        else if (!turn1 && !turn2) {
                            if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                            }
                            else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                            }
                
                            if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                            }
                            else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                            }
                        }
                
                        else if (turn1 && !turn2) {
                            System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                            if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                if (currentMonsterPlayer2.isMonsterDead()) {
                                    currentMonsterPlayer2.setNone();
                                    System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                    player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                    if (player2.getNumberOfMonster() > 0) {
                                        int temp = Switch.switchMonster(player2, y, -1);
                                        y = temp;
                                        currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                    }
                                }
                                else { 
                                    if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                        System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                    }
                                    else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                    }
                                }
                            }
                            else {
                                System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                    System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                }
                                else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                }
                            }
                        }
                
                        else if (!turn1 && turn2) {
                            if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                            }
                            else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                            }
                            System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                            if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                if (currentMonsterPlayer1.isMonsterDead()) {
                                    currentMonsterPlayer1.setNone();
                                    System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                    player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                    if (player1.getNumberOfMonster() > 0) {
                                        int temp = Switch.switchMonster(player1, x, -1);
                                        x = temp;
                                        currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                    }
                                }
                            }
                            else {
                                System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                            }
                        }
                
                    }
                
                    else if (currentMonsterPlayer1.getMoves().get(move1).getPriority() < currentMonsterPlayer2.getMoves().get(move2).getPriority()) {
                
                        if (turn1 && turn2) {
                            System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                            if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.println(currentMonsterPlayer1.getName() + " rebahan dulu");
                                }
                                else if (currentMonsterPlayer1.isMonsterDead()) {
                                    currentMonsterPlayer1.setNone();
                                    System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                    player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                    if (player1.getNumberOfMonster() > 0) {
                                        int temp = Switch.switchMonster(player1, x, -1);
                                        x = temp;
                                        currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                    }
                                }
                                else {
                                    System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                    if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                        currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                        if (currentMonsterPlayer2.isMonsterDead()) {
                                            currentMonsterPlayer2.setNone();
                                            System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                            player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                            if (player2.getNumberOfMonster() > 0) {
                                                int temp = Switch.switchMonster(player2, y, -1);
                                                y = temp;
                                                currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                            }
                                        }
                                    }
                                    else {
                                        System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                    }
                                }
                            }
                            else {
                                System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                    if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                        currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                        if (currentMonsterPlayer2.isMonsterDead()) {
                                            currentMonsterPlayer2.setNone();
                                            System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                            player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                            if (player2.getNumberOfMonster() > 0) {
                                                int temp = Switch.switchMonster(player2, y, -1);
                                                y = temp;
                                                currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                            }
                                        }
                                    }
                                    else {
                                        System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                    }
                            }
                        }
                
                        else if (!turn1 && !turn2) {
                            if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                            }
                            else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                            }
                
                            if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                            }
                            else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                            }
                        }
                
                        else if (turn1 && !turn2) {
                            if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                            }
                            else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                            }
                            System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                            if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                if (currentMonsterPlayer2.isMonsterDead()) {
                                    currentMonsterPlayer2.setNone();
                                    System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                    player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                    if (player2.getNumberOfMonster() > 0) {
                                        int temp = Switch.switchMonster(player2, y, -1);
                                        y = temp;
                                        currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                    }
                                }
                            }
                            else {
                                System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                            }
                        }
                
                        else if (!turn1 && turn2) {
                            System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                            if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                if (currentMonsterPlayer1.isMonsterDead()) {
                                    currentMonsterPlayer1.setNone();
                                    System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                    player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                    if (player1.getNumberOfMonster() > 0) {
                                        int temp = Switch.switchMonster(player1, x, -1);
                                        x = temp;
                                        currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                    }
                                }
                                else {
                                    if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                        System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                    }
                                    else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                    }
                                }
                            }
                            else {
                                System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                    System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                }
                                else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                }
                            }
                        }
                
                    }
                
                    else if (currentMonsterPlayer1.getMoves().get(move1).getPriority() == currentMonsterPlayer2.getMoves().get(move2).getPriority()) {
                        if (currentMonsterPlayer1.getBaseStats().getCurrentSpeed() > currentMonsterPlayer2.getBaseStats().getCurrentSpeed()) {
                
                            if (turn1 && turn2) {
                                System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                    currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                    if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer2.getName() + " rebahan dulu");
                                    }
                                    else if (currentMonsterPlayer2.isMonsterDead()) {
                                        currentMonsterPlayer2.setNone();
                                        System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                        player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                        if (player2.getNumberOfMonster() > 0) {
                                            int temp = Switch.switchMonster(player2, y, -1);
                                            y = temp;
                                            currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                        }
                                    }
                                    else {
                                        System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                        if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                            currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                            if (currentMonsterPlayer1.isMonsterDead()) {
                                                currentMonsterPlayer1.setNone();
                                                System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                                player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                                if (player1.getNumberOfMonster() > 0) {
                                                    int temp = Switch.switchMonster(player1, x, -1);
                                                    x = temp;
                                                    currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                                }
                                            }
                                        }
                                        else {
                                            System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                        }
                                    }
                                }
                                else {
                                    System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                    System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                    if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                        currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                        if (currentMonsterPlayer1.isMonsterDead()) {
                                            currentMonsterPlayer1.setNone();
                                            System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                            player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                            if (player1.getNumberOfMonster() > 0) {
                                                int temp = Switch.switchMonster(player1, x, -1);
                                                x = temp;
                                                currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                            }
                                        }
                                    }
                                    else {
                                        System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                    }
                                }
                            }
                            
                            else if (!turn1 && !turn2) {
                                if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                    System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                }
                                else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                }
                
                                if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                    System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                }
                                else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                }
                            }
                
                            else if (turn1 && !turn2) {
                                System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                    currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                    if (currentMonsterPlayer2.isMonsterDead()) {
                                        currentMonsterPlayer2.setNone();
                                        System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                        player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                        if (player2.getNumberOfMonster() > 0) {
                                            int temp = Switch.switchMonster(player2, y, -1);
                                            y = temp;
                                            currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                        }
                                    }
                                    else { 
                                        if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                            System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                        }
                                        else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                            System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                        }
                                    }
                                }
                                else {
                                    System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                    if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                        System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                    }
                                    else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                    }
                                }
                            }
                
                            else if (!turn1 && turn2) {
                                if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                    System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                }
                                else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                }
                                System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                    currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                    if (currentMonsterPlayer1.isMonsterDead()) {
                                        currentMonsterPlayer1.setNone();
                                        System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                        player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                        if (player1.getNumberOfMonster() > 0) {
                                            int temp = Switch.switchMonster(player1, x, -1);
                                            x = temp;
                                            currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                        }
                                    }
                                }
                                else {
                                    System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                }
                            }
                        }
                
                        else if (currentMonsterPlayer1.getBaseStats().getCurrentSpeed() < currentMonsterPlayer2.getBaseStats().getCurrentSpeed()) {
                
                            if (turn1 && turn2) {
                                System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                    currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                    if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer1.getName() + " rebahan dulu");
                                    }
                                    else if (currentMonsterPlayer1.isMonsterDead()) {
                                        currentMonsterPlayer1.setNone();
                                        System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                        player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                        if (player1.getNumberOfMonster() > 0) {
                                            int temp = Switch.switchMonster(player1, x, -1);
                                            x = temp;
                                            currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                        }
                                    }
                                    else {
                                        System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                        if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                            currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                            if (currentMonsterPlayer2.isMonsterDead()) {
                                                currentMonsterPlayer2.setNone();
                                                System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                                player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                                if (player2.getNumberOfMonster() > 0) {
                                                    int temp = Switch.switchMonster(player2, y, -1);
                                                    y = temp;
                                                    currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                                }
                                            }
                                        }
                                        else {
                                            System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                        }
                                    }
                                }
                                else {
                                    System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                    System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                        if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                            currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                            if (currentMonsterPlayer2.isMonsterDead()) {
                                                currentMonsterPlayer2.setNone();
                                                System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                                player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                                if (player2.getNumberOfMonster() > 0) {
                                                    int temp = Switch.switchMonster(player2, y, -1);
                                                    y = temp;
                                                    currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                                }
                                            }
                                        }
                                        else {
                                            System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                        }
                                }
                            }
                
                            else if (!turn1 && !turn2) {
                                if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                    System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                }
                                else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                }
                
                                if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                    System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                }
                                else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                }
                            }
                
                            else if (turn1 && !turn2) {
                                if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                    System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                }
                                else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                    System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                }
                                System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                    currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                    if (currentMonsterPlayer2.isMonsterDead()) {
                                        currentMonsterPlayer2.setNone();
                                        System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                        player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                        if (player2.getNumberOfMonster() > 0) {
                                            int temp = Switch.switchMonster(player2, y, -1);
                                            y = temp;
                                            currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                        }
                                    }
                                }
                                else {
                                    System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                }
                            }
                
                            else if (!turn1 && turn2) {
                                System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                    currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                    if (currentMonsterPlayer1.isMonsterDead()) {
                                        currentMonsterPlayer1.setNone();
                                        System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                        player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                        if (player1.getNumberOfMonster() > 0) {
                                            int temp = Switch.switchMonster(player1, x, -1);
                                            x = temp;
                                            currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                        }
                                    }
                                    else {
                                        if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                            System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                        }
                                        else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                            System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                        }
                                    }
                                }
                                else {
                                    System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                    if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                        System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                    }
                                    else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                    }
                                }
                            }
                        }
                
                        else {
                            Random random = new Random();
                            int randomNumber = random.nextInt(2) + 1;
                            if (randomNumber == 1) {
                
                                if (turn1 && turn2) {
                                    System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                    if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                        currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                        if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                            System.out.println(currentMonsterPlayer2.getName() + " rebahan dulu");
                                        }
                                        else if (currentMonsterPlayer2.isMonsterDead()) {
                                            currentMonsterPlayer2.setNone();
                                            System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                            player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                            if (player2.getNumberOfMonster() > 0) {
                                                int temp = Switch.switchMonster(player2, y, -1);
                                                y = temp;
                                                currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                            }
                                        }
                                        else {
                                            System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                            if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                                currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                                if (currentMonsterPlayer1.isMonsterDead()) {
                                                    currentMonsterPlayer1.setNone();
                                                    System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                                    player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                                    if (player1.getNumberOfMonster() > 0) {
                                                        int temp = Switch.switchMonster(player1, x, -1);
                                                        x = temp;
                                                        currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                                    }
                                                }
                                            }
                                            else {
                                                System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                            }
                                        }
                                    }
                                    else {
                                        System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                        System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                        if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                            currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                            if (currentMonsterPlayer1.isMonsterDead()) {
                                                currentMonsterPlayer1.setNone();
                                                System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                                player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                                if (player1.getNumberOfMonster() > 0) {
                                                    int temp = Switch.switchMonster(player1, x, -1);
                                                    x = temp;
                                                    currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                                }
                                            }
                                        }
                                        else {
                                            System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                        }
                                    }
                                }
                                
                                else if (!turn1 && !turn2) {
                                    if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                        System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                    }
                                    else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                    }
                
                                    if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                        System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                    }
                                    else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                    }
                                }
                
                                else if (turn1 && !turn2) {
                                    System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                    if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                        currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                        if (currentMonsterPlayer2.isMonsterDead()) {
                                            currentMonsterPlayer2.setNone();
                                            System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                            player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                            if (player2.getNumberOfMonster() > 0) {
                                                int temp = Switch.switchMonster(player2, y, -1);
                                                y = temp;
                                                currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                            }
                                        }
                                        else { 
                                            if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                                System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                            }
                                            else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                                System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                            }
                                        }
                                    }
                                    else {
                                        System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                        if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                            System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                        }
                                        else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                            System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                        }
                                    }
                                }
                
                                else if (!turn1 && turn2) {
                                    if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                        System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                    }
                                    else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                    }
                                    System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                    if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                        currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                        if (currentMonsterPlayer1.isMonsterDead()) {
                                            currentMonsterPlayer1.setNone();
                                            System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                            player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                            if (player1.getNumberOfMonster() > 0) {
                                                int temp = Switch.switchMonster(player1, x, -1);
                                                x = temp;
                                                currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                            }
                                        }
                                    }
                                    else {
                                        System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                    }
                                }
                            }
                            else if (randomNumber == 2) {
                
                                if (turn1 && turn2) {
                                    System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                    if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                        currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                        if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                            System.out.println(currentMonsterPlayer1.getName() + " rebahan dulu");
                                        }
                                        else if (currentMonsterPlayer1.isMonsterDead()) {
                                            currentMonsterPlayer1.setNone();
                                            System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                            player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                            if (player1.getNumberOfMonster() > 0) {
                                                int temp = Switch.switchMonster(player1, x, -1);
                                                x = temp;
                                                currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                            }
                                        }
                                        else {
                                            System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                            if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                                currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                                if (currentMonsterPlayer2.isMonsterDead()) {
                                                    currentMonsterPlayer2.setNone();
                                                    System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                                    player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                                    if (player2.getNumberOfMonster() > 0) {
                                                        int temp = Switch.switchMonster(player2, y, -1);
                                                        y = temp;
                                                        currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                                    }
                                                }
                                            }
                                            else {
                                                System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                            }
                                        }
                                    }
                                    else {
                                        System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                        System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                            if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                                currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                                if (currentMonsterPlayer2.isMonsterDead()) {
                                                    currentMonsterPlayer2.setNone();
                                                    System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                                    player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                                    if (player2.getNumberOfMonster() > 0) {
                                                        int temp = Switch.switchMonster(player2, y, -1);
                                                        y = temp;
                                                        currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                                    }
                                                }
                                            }
                                            else {
                                                System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                            }
                                    }
                                }
                
                                else if (!turn1 && !turn2) {
                                    if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                        System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                    }
                                    else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                    }
                
                                    if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                        System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                    }
                                    else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                    }
                                }
                
                                else if (turn1 && !turn2) {
                                    if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
                                        System.out.println(currentMonsterPlayer2.getName() + " kesetrum");
                                    }
                                    else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.SLEEP) {
                                        System.out.println(currentMonsterPlayer2.getName() + "rebahan");
                                    }
                                    System.out.println(currentMonsterPlayer1.getName() + " use " + currentMonsterPlayer1.getMoves().get(move1).getName());
                                    if (currentMonsterPlayer1.getMoves().get(move1).isHit()) {
                                        currentMonsterPlayer1.getMoves().get(move1).execute(currentMonsterPlayer1, currentMonsterPlayer2);
                                        if (currentMonsterPlayer2.isMonsterDead()) {
                                            currentMonsterPlayer2.setNone();
                                            System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                                            player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                                            if (player2.getNumberOfMonster() > 0) {
                                                int temp = Switch.switchMonster(player2, y, -1);
                                                y = temp;
                                                currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                                            }
                                        }
                                    }
                                    else {
                                        System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer1.getName());
                                    }
                                }
                
                                else if (!turn1 && turn2) {
                                    System.out.println(currentMonsterPlayer2.getName() + " use " + currentMonsterPlayer2.getMoves().get(move2).getName());
                                    if (currentMonsterPlayer2.getMoves().get(move2).isHit()) {
                                        currentMonsterPlayer2.getMoves().get(move2).execute(currentMonsterPlayer2, currentMonsterPlayer1);
                                        if (currentMonsterPlayer1.isMonsterDead()) {
                                            currentMonsterPlayer1.setNone();
                                            System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                                            player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                                            if (player1.getNumberOfMonster() > 0) {
                                                int temp = Switch.switchMonster(player1, x, -1);
                                                x = temp;
                                                currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                                            }
                                        }
                                        else {
                                            if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                                System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                            }
                                            else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                                System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                            }
                                        }
                                    }
                                    else {
                                        System.out.printf("%s tidak kena :P!\n", currentMonsterPlayer2.getName());
                                        if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
                                            System.out.println(currentMonsterPlayer1.getName() + " kesetrum");
                                        }
                                        else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.SLEEP) {
                                            System.out.println(currentMonsterPlayer1.getName() + "rebahan");
                                        }
                                    }
                                }
                            }
                        }

                }
            // ketika monster mati
            if (currentMonsterPlayer1.isMonsterDead() && player1.getNumberOfMonster() > 0) {
                System.out.println(currentMonsterPlayer1.getName() + " pingsan terkena tubes, pilih monster lain");
                player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                if (player1.getNumberOfMonster() > 0) {
                    int temp = Switch.switchMonster(player1, x, -1);
                    x = temp;
                    currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                }
            }

            if (currentMonsterPlayer2.isMonsterDead() && player2.getNumberOfMonster() > 0) {
                System.out.println(currentMonsterPlayer2.getName() + " pingsan terkena tubes, pilih monster lain");
                player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                if (player2.getNumberOfMonster() > 0) {
                    int temp = Switch.switchMonster(player2, y, -1);
                    y = temp;
                    currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                }
            }
        // end game
        if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.BURN) {
            currentMonsterPlayer1.setEffectBurn();
            if (currentMonsterPlayer1.isMonsterDead()) {
                System.out.println(currentMonsterPlayer1.getName() + "pingsan terkena tubes, pilih monster lain");
                player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                if (player1.getNumberOfMonster() > 0) {
                    int temp = Switch.switchMonster(player1, x, -1);
                    x = temp;
                    currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                }
            }
        } 
        else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.POISON) {
            currentMonsterPlayer1.setEffectPoison();
            if (currentMonsterPlayer1.isMonsterDead()) {
                System.out.println(currentMonsterPlayer1.getName() + "pingsan terkena tubes, pilih monster lain");
                player1.setNumberOfMonster(player1.getNumberOfMonster() - 1);
                if (player1.getNumberOfMonster() > 0) {
                    int temp = Switch.switchMonster(player1, x, -1);
                    x = temp;
                    currentMonsterPlayer1 = player1.getListOfMonster().get(x);
                }
            }
        } 
        else if (currentMonsterPlayer1.getStatusCondition() == StatusCondition.PARALYZE) {
            turn1 = currentMonsterPlayer1.setEffectParalyze();
        }
        for (int i = 0; i < player1.getListOfMonster().size(); i++) {
            if (player1.getListOfMonster().get(i).getStatusCondition() == StatusCondition.SLEEP) {
                player1.getListOfMonster().get(i).setEffectSleep();
            }
        }
        
        // System.out.println(currentMonsterPlayer1.getCountSleep());
        if (currentMonsterPlayer1.getCountSleep() == 0) {
            turn1 = true;
        }
        else if (currentMonsterPlayer1.getCountSleep() > 0) {
            turn1 = false;
        }
        

        if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.BURN) {
            currentMonsterPlayer2.setEffectBurn();
            if (currentMonsterPlayer2.isMonsterDead()) {
                System.out.println(currentMonsterPlayer2.getName() + "pingsan terkena tubes, pilih monster lain");
                player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                if (player2.getNumberOfMonster() > 0) {
                    int temp = Switch.switchMonster(player2, y, -1);
                    y = temp;
                    currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                }
            }
        } 
        else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.POISON) {
            currentMonsterPlayer2.setEffectPoison();
            if (currentMonsterPlayer2.isMonsterDead()) {
                System.out.println(currentMonsterPlayer2.getName() + "pingsan terkena tubes, pilih monster lain");
                player2.setNumberOfMonster(player2.getNumberOfMonster() - 1);
                if (player2.getNumberOfMonster() > 0) {
                    int temp = Switch.switchMonster(player2, y, -1);
                    y = temp;
                    currentMonsterPlayer2 = player2.getListOfMonster().get(y);
                }
            }
        } 
        else if (currentMonsterPlayer2.getStatusCondition() == StatusCondition.PARALYZE) {
            turn2 = currentMonsterPlayer2.setEffectParalyze();
        }

        for (int i = 0; i < player2.getListOfMonster().size(); i++) {
            if (player2.getListOfMonster().get(i).getStatusCondition() == StatusCondition.SLEEP) {
                player2.getListOfMonster().get(i).setEffectSleep();
            }
        }
        
        // System.out.println(currentMonsterPlayer2.getCountSleep());
        if (currentMonsterPlayer2.getCountSleep() == 0) {
            turn2 = true;
        }
        else if (currentMonsterPlayer2.getCountSleep() > 0) {
            turn2 = false;
        }

        if (player1.getNumberOfMonster() == 0) {
            isEndGame = true;
            System.out.println(player1.getName() + " sudah tidak memiliki monster lagi T___T");
            System.out.println("Selamat " + player2.getName() + " telah menang !!! _/(^O^)/");
            System.out.println("Kembali ke menu");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        if (player2.getNumberOfMonster() == 0) {
            isEndGame = true;
            System.out.println(player2.getName() + " sudah tidak memiliki monster lagi T___T");
            System.out.println("Selamat " + player1.getName() + " telah menang !!! _/(^O^)/");
            System.out.println("Kembali ke menu");
            System.out.println("");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
            }
        }
        }    
    }
}