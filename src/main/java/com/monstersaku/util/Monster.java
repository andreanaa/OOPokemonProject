package com.monstersaku.util;
import com.monstersaku.util.Stats;

import java.io.IOException;
import java.util.*;

public class Monster implements Cloneable {
	int id;
	private String name;
	private Stats baseStats;
	private ArrayList<ElementType> elementTypes;
	private ArrayList<Move> moves;
    private ArrayList<Integer> moveId;
    private StatusCondition statusCondition;
    private int countSleep;

	// private StatusCondition status = StatusCondition.NONE;
	// private List<ElementType> elementTypes = new ArrayList<ElementType>();
	// private List<Move> moves = new ArrayList<Move>();

	public Monster(int id, String name,  ArrayList<ElementType> elementTypes, Stats baseStats, ArrayList<Integer> moveIds) {
		setId(id);
		setName(name);
		setBaseStats(baseStats);
		setElementTypes(elementTypes);
		this.moveId = moveIds;
        this.moves = new ArrayList<Move>();
        this.statusCondition = StatusCondition.NONE;
        this.countSleep = 0;
		// setMoves(moves);
	}
	public Monster (Monster monster, Stats stats) {
        this.id = monster.getId();
        this.name = monster.getName();
        setBaseStats(stats);
        elementTypes = monster.getElementTypes();
        this.moveId = monster.getMoveId();
        this.moves = new ArrayList<Move>();
        this.statusCondition = StatusCondition.NONE;
        this.countSleep = 0;
    }

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Stats getBaseStats() {
		return this.baseStats;
	}

	public ArrayList<ElementType> getElementTypes() {
		return this.elementTypes;
	}

	public ArrayList<Move> getMoves() {
		return this.moves;
	}

	public ArrayList<Integer> getMoveId() {
        return this.moveId;
    }

	public StatusCondition getStatusCondition(){
		return this.statusCondition;
	}

	public int getCountSleep() {
        return this.countSleep;
    }

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public void setBaseStats(double healthPoint, double attack, double defense, double specialAttack, double specialDefense, double speed) {
	// 	this.baseStats = new Stats(healthPoint, attack, defense, specialAttack, specialDefense, speed);
	// }

	public void setBaseStats (Stats baseStats) {
        this.baseStats = baseStats;
    }

	public void setElementTypes(ArrayList<ElementType> elementTypes) {
		this.elementTypes = elementTypes;
	}

	public void setMoves(ArrayList<Move> moves) {
		this.moves = moves;
	}

	public void setStatusCondition(StatusCondition statusCondition){
		this.statusCondition = statusCondition;
	}

	public void setCountSleep (int countSleep) {
        this.countSleep = countSleep;
    }

	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	public boolean isMonsterDead() {
		return (this.getBaseStats().getCurrentHP() <= 0);
	}

	public void info() {
        if (elementTypes.size() == 1) {
            System.out.println(getName() + " " + elementTypes.get(0));
        }
        else if (elementTypes.size() == 2) {
            System.out.println(getName() + " " + elementTypes.get(0) + " " + elementTypes.get(1));
        }
        System.out.println("StatusCondition "  + getStatusCondition());
        System.out.println("HP : " + getBaseStats().getCurrentHP() + ("/") + getBaseStats().getHealthPoint());
        System.out.println("Attack : " + getBaseStats().getCurrentAttack());
        System.out.println("Defense " + getBaseStats().getCurrentDefense());
        System.out.println("Special Attack : " + getBaseStats().getCurrentSpecialAttack());
        System.out.println("Special Defense " + getBaseStats().getCurrentSpecialDefense());
        System.out.println("Speed " + getBaseStats().getCurrentSpeed());
        System.out.println("");
    }

    public void setNone() {
        this.getBaseStats().setCurrentSpeed(this.getBaseStats().getSpeed());
        this.setStatusCondition(StatusCondition.NONE);
    }

    public void setEffectBurn() {
        double temp = this.getBaseStats().getCurrentHP() - Math.floor(this.getBaseStats().getHealthPoint() * 0.125);
        this.getBaseStats().setCurrentHP(temp);
        if (this.getBaseStats().getCurrentHP() <= 0) {
            this.getBaseStats().setCurrentHP(0);
        }
        System.out.println(this.getName() + " terkena burn! HP menjadi " + this.getBaseStats().getCurrentHP());
    }

    public void setEffectPoison() {
        double temp = this.getBaseStats().getCurrentHP() - Math.floor(this.getBaseStats().getHealthPoint() * 0.0625);
        this.getBaseStats().setCurrentHP(temp);
        if (this.getBaseStats().getCurrentHP() <= 0) {
            this.getBaseStats().setCurrentHP(0);
        }
        System.out.println(this.getName() + " terkena poison! HP menjadi " + this.getBaseStats().getCurrentHP());
    }

    public boolean setEffectParalyze() {
        if (this.getBaseStats().getSpeed() == this.getBaseStats().getCurrentSpeed()) {
            this.getBaseStats().setCurrentSpeed(Math.floor(this.getBaseStats().getSpeed() * 0.5));
        }
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;
        if (randomNumber <= 25) {
            // hit = true;
            return false;
        }
        else {
            return true;
        }
    }

    public void setEffectSleep() {
        if (this.countSleep == 0) {
            Random random = new Random();
            int randomNumber = random.nextInt(7) + 1;
            setCountSleep(randomNumber);
        }
        else {
            setCountSleep(this.countSleep - 1);
            if (this.countSleep == 0) {
                this.setNone();
            }
        }
    }

    public void readMoves (CSVReader reader) {
        reader.setSkipHeader(true);
        try {
            List<String[]> lines2 = reader.read();
            for (String[] line : lines2) {
                int id = Integer.parseInt(line[0]);
                for (int i = 0; i < moveId.size(); i++) {
                    if (id == moveId.get(i)) {
                        String moveType = line[1];
                        String name = line[2];
                        ElementType elementType = ElementType.valueOf(line[3]);
                        int accuracy = Integer.parseInt(line[4]);
                        int priority = Integer.parseInt(line[5]);
                        int ammunition = Integer.parseInt(line[6]);
                        
                        if (moveType.equals("NORMAL")) {
                            int effect = Integer.parseInt(line[8]);
                            NormalMove normal = new NormalMove(id, name, elementType, accuracy, priority, ammunition, effect);
                            moves.add(normal);
                        }

                        else if (moveType.equals("SPECIAL")) {
                            int effect = Integer.parseInt(line[8]);
                            SpecialMove special = new SpecialMove(id, name, elementType, accuracy, priority, ammunition, effect);
                            moves.add(special);
                        }

                        else if (moveType.equals("STATUS")) {
                            String target = line[7];
                            String status = line[8];
                            StatusCondition statusCondition;
                            if (status.equals("-")) {
                                statusCondition = StatusCondition.NONE;
                            }
                            else {
                                statusCondition = StatusCondition.valueOf(line[8]);
                            }
                            String statusStats = line[9];
                            String[] arrStatusStats = statusStats.split(",", 6);
                            Double hpEffect = Double.parseDouble(arrStatusStats[0]);
                            Double attackEffect = Double.parseDouble(arrStatusStats[1]);
                            Double defenseEffect = Double.parseDouble(arrStatusStats[2]);
                            Double specialAttackEffect = Double.parseDouble(arrStatusStats[3]);
                            Double specialDefenseEffect = Double.parseDouble(arrStatusStats[4]);
                            Double speedEffect = Double.parseDouble(arrStatusStats[5]);
                            // Stats statusStat = new Stats(hpEffect, attackEffect, defenseEffect, specialAttackEffect, specialDefenseEffect, speedEffect);
                            StatusMove statusMove = new StatusMove(
                                id, 
                                name, 
                                elementType, 
                                accuracy, 
                                priority, 
                                ammunition, 
                                target, 
                                statusCondition,
                                hpEffect,
                                attackEffect,
                                defenseEffect,
                                specialAttackEffect,
                                specialDefenseEffect,
                                speedEffect
                            );

                            moves.add(statusMove);
                        }
                    }
                }
            }
            DefaultMove defaultMove = new DefaultMove();
            moves.add(defaultMove);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}