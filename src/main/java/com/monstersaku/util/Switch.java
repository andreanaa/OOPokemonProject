package com.monstersaku.util;
import com.monstersaku.Main;
import java.util.*;

public class Switch {
	public final static int switchMonster (Player player, int x, int move) {
		Scanner input = Main.scanner;
		int command = move;
		if (command >= 0 && command < player.getListOfMonster().size() - 1) {
			if (player.getListOfMonster().get(command).isMonsterDead()) {
				command = -1;
			}
		}

		while (command == x || command < 0 || command > player.getListOfMonster().size() - 1) {
			if (command == x) {
				System.out.println("Monster ada di field!");
				System.out.println("Pilih yang lain dongs");
				for (int i = 0; i < player.getListOfMonster().size(); i++) {
					System.out.println(("[") + (i+1) + ("] ") + player.getListOfMonster().get(i).getName() + (" ") + player.getListOfMonster().get(i).getBaseStats().getCurrentHP() + ("/") + player.getListOfMonster().get(i).getBaseStats().getHealthPoint());
				}
				command = input.nextInt();
				command -= 1;
			} else {
				System.out.println("Pilih yang lain dongs");
				for (int i = 0; i < player.getListOfMonster().size(); i++) {
					System.out.println(("[") + (i+1) + ("] ") + player.getListOfMonster().get(i).getName() + (" ") + player.getListOfMonster().get(i).getBaseStats().getCurrentHP() + ("/") + player.getListOfMonster().get(i).getBaseStats().getHealthPoint());
				}
				command = input.nextInt();
				command -= 1;
			}

			if (command >= 0 && command < player.getListOfMonster().size() - 1) {
				while (player.getListOfMonster().get(command).isMonsterDead()) {
					command = input.nextInt();
					command -= 1;					
				} if (command < 0 || command > player.getListOfMonster().size() - 1) {
					break;
				} 
			}
		}

		System.out.println(("Monstermu sekarang adalah ") + player.getListOfMonster().get(command).getName());
		return command;
	}
}