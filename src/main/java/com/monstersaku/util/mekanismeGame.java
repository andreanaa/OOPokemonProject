// package com.monstersaku;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;
// import java.util.Scanner;

// import com.monstersaku.move.*;

// public class mekanismeGame {
// 	public void mekanismeGame() throws InterruptedException {
// 		Scanner input = new Scanner(System.in);
// 		boolean isStartGame = false;
// 		System.out.println("Masukkan Angka Pilihan!");
// 		System.out.println("[1] START [2] HELP [3] EXIT");

// 		int command = input.nextInt();

// 		while (command != 3) {
// 			if (command == 2) {
// 				// help();
// 				System.out.println("[1] START [2] HELP [3] EXIT");
// 				command = input.nextInt();
// 			} else if (command == 1) {
// 				//Player player1 = new Player();
// 				//Player player2 = new Player();
// 				// while (player1.getNumberOfMonster != 0 || player2.getNumberOfMonster != 0)
// 					// System.out.println("[1] SWITCH [2] MOVE");
// 					// player1.turn();
// 					// player2.turn();

// 				// if (player1.getNumberOfMonster == 0)
// 					// System.out.println("Selamat Player2!");
// 				// else
// 					// System.out.println("Selamat Player1!");
				
// 			} else {
// 				System.exit(0);
// 			}
// 		}
// 	}

// 	// public void turn (int command2, Player player) {
// 	// 		if (command2 == 1) {

// 	// 		} else {executeMove() }
// 	// }

// 	public void executeMove (Monster source, Monster target, Move move, EffectivityPool ep) {
// 		double newHP;
// 		if (move instanceof NormalMove || move instanceof DefaultMove || move instanceof SpecialMove){
//         	Random random = new Random();
//         	double randomNumber = (random.nextInt(85 + 1 - 100) + 85) / 100;
//         	double effectivity = 1;
//         	for (ElementType e : target.getElementTypes()) {
//         	    effectivity *= ep.getEffectivity(move.getElementType(), e);
//         	}

// 			double burnEffect = 1;
//         	if (source.getStatusCondition() == StatusCondition.BURN) {
//         	    burnEffect = 0.5;
//         	}

// 			double power;
// 			double attack;
// 			double defense;
// 			if (move instanceof DefaultMove){
// 				DefaultMove defaultMove = (DefaultMove)move;
// 				defaultMove.useMove();
// 				power = defaultMove.getBasePower();
// 				attack = source.getBaseStats().getCurrentAttack();
// 				defense = target.getBaseStats().getCurrentDefense();
// 			} 
// 			else if(move instanceof NormalMove){
// 				NormalMove normalMove = (NormalMove)move;
// 				normalMove.useMove();
// 				power = normalMove.getBasePower();
// 				attack = source.getBaseStats().getCurrentAttack();
// 				defense = target.getBaseStats().getCurrentDefense();
// 			}
// 			else{
// 				SpecialMove specialMove = (SpecialMove)move;
// 				specialMove.useMove();
// 				power = specialMove.getBasePower();
// 				attack = source.getBaseStats().getCurrentSpecialAttack();
// 				defense = target.getBaseStats().getCurrentSpecialDefense();
// 			}

// 			if(move.isHit()){
//         		double damage = Math.floor((power * (attack/defense) + 2 ) * randomNumber * effectivity * burnEffect);
//         		newHP = target.getBaseStats().getCurrentHP() - damage;
// 				target.getBaseStats().setCurrentHP(newHP);
//         		if (target.isMonsterDead()) {
// 					System.out.printf("Monster %s mati/n", target.getName());
				
//         		}
//         		else {
//         		    if (target.getStatusCondition() == StatusCondition.POISON) {
//         		        newHP = Math.floor(newHP * 0.0625);
//         		    }
				
//         		    else if (target.getStatusCondition() == StatusCondition.BURN) {
//         		        newHP = Math.floor(newHP * 0.125);
//         		    }

//         		    if (newHP > 0) {
//         		        target.getBaseStats().setCurrentHP(newHP);
//         		    }
//         		}
// 			} else{// miss
// 				System.out.printf("%s tidak berhasil :P!\n", source.getName());
// 			}
// 		} else{ 
// 			StatusMove statusMove = (StatusMove)move;
// 			statusMove.useMove();
			
// 			if (move.isHit()){
// 				double newAttack;
// 				double newDefense;
// 				double newSpecialAttack;
// 				double newSpecialDefense;
// 				double newSpeed;
// 				if (statusMove.getTarget()=="OWN"){
// 					source.setStatusCondition(statusMove.getStatusCondition());
// 					newHP = source.getBaseStats().getCurrentHP() + source.getBaseStats().getHealthPoint()*(statusMove.getStatusStats().getHealthPoint()/100);
// 					newAttack = source.getBaseStats().getCurrentAttack() + statusMove.getStatusStats().getCurrentAttack();
// 					newDefense = source.getBaseStats().getCurrentDefense() + statusMove.getStatusStats().getCurrentDefense();
// 					newSpecialAttack = source.getBaseStats().getCurrentSpecialAttack() + statusMove.getStatusStats().getCurrentSpecialAttack();
// 					newSpecialDefense = source.getBaseStats().getCurrentSpecialDefense() + statusMove.getStatusStats().getCurrentSpecialDefense();
// 					newSpeed = source.getBaseStats().getCurrentSpeed() + statusMove.getStatusStats().getCurrentSpeed();
// 					source.getBaseStats().setCurrentHP(newHP);
// 					source.getBaseStats().setAttack(newAttack);
// 					source.getBaseStats().setDefense(newDefense);
// 					source.getBaseStats().setSpecialAttack(newSpecialAttack);
// 					source.getBaseStats().setSpecialDefense(newSpecialDefense);
// 					source.getBaseStats().setSpeed(newSpeed);
// 				}
// 				else {
// 					target.setStatusCondition(statusMove.getStatusCondition());
// 					newHP = target.getBaseStats().getCurrentHP() + target.getBaseStats().getHealthPoint()*(statusMove.getStatusStats().getHealthPoint()/100);
// 					newAttack = target.getBaseStats().getCurrentAttack() + statusMove.getStatusStats().getCurrentAttack();
// 					newDefense = target.getBaseStats().getCurrentDefense() + statusMove.getStatusStats().getCurrentDefense();
// 					newSpecialAttack = target.getBaseStats().getCurrentSpecialAttack() + statusMove.getStatusStats().getCurrentSpecialAttack();
// 					newSpecialDefense = target.getBaseStats().getCurrentSpecialDefense() + statusMove.getStatusStats().getCurrentSpecialDefense();
// 					newSpeed = target.getBaseStats().getCurrentSpeed() + statusMove.getStatusStats().getCurrentSpeed();
// 					target.getBaseStats().setCurrentHP(newHP);
// 					target.getBaseStats().setAttack(newAttack);
// 					target.getBaseStats().setDefense(newDefense);
// 					target.getBaseStats().setSpecialAttack(newSpecialAttack);
// 					target.getBaseStats().setSpecialDefense(newSpecialDefense);
// 					target.getBaseStats().setSpeed(newSpeed);
// 				}
// 			} else{ //miss
// 				System.out.printf("%s tidak berhasil :P!\n", source.getName());
// 			}
// 		}
//     }

	
// }