package com.example.asus.battleship;

import java.util.Random;
import java.util.Scanner;
//----------->>>>>COmputer Attack only remaining
public class BattleShip {
    public static void main(String[] args){
        int[][] ocean=new int[5][5];
        System.out.println("******** Welcome to BattleShips Game ********");
        System.out.println("\nRight now the sea is empty\n");
        System.out.println();
        deploy(ocean);
        deployComputer(ocean);
        startBattle(ocean);
    }

    public static void deploy(int[][] map){

    }
    public static void deployComputer(int[][] map){
        Random random=new Random();
        System.out.println();
        System.out.println("---------------------------------------------------------------------");
        System.out.println("\nComputer is deploying ships\n");
        for (int i=0;i<5;i++){
            int x,y;
            x=random.nextInt(5);
            y=random.nextInt(5);
            if (map[x][y]==1 || map[x][y]==2){
                i=i-1;
                continue;
            }
            else {
                map[x][y]=2;
                System.out.println("Ship"+(i+1)+ " Deployed");
            }
        }
        System.out.println();
        System.out.println("---------------------------------------------------------------------");
        System.out.println();
    }

    public static void startBattle(int[][] map){
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Battle Starting\n");
        int score_player=5,score_computer=5;
        while (score_computer!=0 && score_player!=0){
            System.out.println("\nBattleShips Remaining\nPlayer Ships : "+score_player+"\nComputer Ships : "+score_computer);
            int attack_result;
            attack_result=PlayerAttack(map);
            if (attack_result==1){
                score_computer--;
            }
            else if (attack_result==-1){
                score_player--;
            }
            attack_result=computerAttack(map);
            if (attack_result==1){
                score_player--;
            }
            else if (attack_result==-1){
                score_computer--;
            }
        }
        if (score_computer==0){
            System.out.println("*******YOU WON*******");
        }
        else if (score_player==0){
            System.out.println("*******COMPUTER WON********\nBetter luck next time");
        }

    }
    public static int PlayerAttack(int [][] map){
        int x,y;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Players Turn");
        Scanner input=new Scanner(System.in);
        System.out.println("Where do you want to launch the Attack");
        System.out.print("Enter X coordinate");
        x=input.nextInt();
        System.out.print("Enter Y coordinate");
        y=input.nextInt();
        if (map[x][y]==2){
            System.out.println("Enemy Ship Destroyed");
            map[x][y]=9;
            System.out.println("---------------------------------------------------------------------");
            return 1;
        }
        else if (map[x][y]==1){
            System.out.println("You attacked your own ship\n-1 Ship");
            map[x][y]=9;
            System.out.println("---------------------------------------------------------------------");
            return -1;
        }
        else{
            System.out.println("Missed the attack");
            map[x][y]=9;
            System.out.println("---------------------------------------------------------------------");
            return 0;
        }
    }
    public static int computerAttack(int[][] map){
        int x,y;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Computers Turn");
        Random random=new Random();
        x=random.nextInt(5);
        y=random.nextInt(5);
        if (map[x][y]==1){
            System.out.println("Enemy Ship Destroyed");
            System.out.println("---------------------------------------------------------------------");
            map[x][y]=9;
            return 1;
        }
        else if (map[x][y]==2){
            System.out.println("Computer Destroyed Its Ship\n-1 Ship");
            map[x][y]=9;
            System.out.println("---------------------------------------------------------------------");
            return -1;
        }
        else{
            System.out.println("Computer Missed");
            map[x][y]=9;
            System.out.println("---------------------------------------------------------------------");
            return 0;
        }
    }
}
