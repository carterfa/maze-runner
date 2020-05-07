package Maze;

import java.util.Scanner;

public class MazeRunner {

    public static Scanner input = new Scanner(System.in);
    public static Maze maze = new Maze();
    public static int limit = 60;
    public static int moveCount = 0;

    public static void intro(){
        System.out.println("Welcome to Maze Runner!");
        System.out.println("Directions:\nR = Move Right\nL = Move Left\nD = Move Down\nU = Move Up");
        maze.printMap();
        String dir = userMove();
        navigate(dir);
    }

    public static String userMove() {
        System.out.println("Where would you like to move?");
        String dir = input.nextLine().trim().toUpperCase();

        if (!dir.equals("D") && !dir.equals("L") && !dir.equals("R") && !dir.equals("U")){
            System.out.println("Not a valid move.");
            dir = userMove();
        }

        moveCount++;
        //System.out.println(dir);
        return dir;
    }

    public static void pitChecker(String dir){
            System.out.println("There's a pit in the way! Would you like to jump? Y/N");
            String choice = input.nextLine().trim().toUpperCase();
            if (choice.equals("Y")){
                maze.jumpOverPit(dir);
                winCheck();
                maze.printMap();
                navigate(userMove());;
            } else if (choice.equals("N")){
                navigate(userMove());
            } else{
                System.out.println("Not an option");
                pitChecker(dir);
            }
    }

    public static void gameOver(){
        System.out.println("Play again? Y/N");
        String choice = input.nextLine().trim().toUpperCase();
        if (choice.equals("Y")){
            moveCount = 0;
            maze = new Maze();
            intro();
        } else if (choice.equals("N")){
            System.out.println("Thanks for playing!");
            System.exit(0);
        } else{
            System.out.println("Not an option");
            gameOver();
        }
    }

    public static void winCheck(){

        if (maze.didIWin()){
            System.out.println("You escaped the maze! You did it in " + moveCount + " moves.");
            gameOver();
        }else {
            //System.out.println((100-moveCount) + " moves left.");
            switch (limit - moveCount){
                case 0:{
                    System.out.println("Oh no! You took too long to escape, and now the maze exit is closed FOREVER >:[");
                    gameOver();
                    break;
                }
                case 10:{
                    System.out.println("DANGER! You only have 10 moves left to escape!!");
                    break;
                }
                case 25:{
                    System.out.println("Alert! You only have 25 moves left to escape.");
                    break;
                }
                case 50:{
                    System.out.println("Warning: You have 50 moves remaining before the maze exit closes.");
                    break;
                }
            }

        }
    }

    public static void navigate(String dir){

        if (maze.isThereAPit(dir)){
            pitChecker(dir);
        }

        boolean wall = false;

        switch(dir) {
            case "U": {
                if (maze.canIMoveUp()) {
                    maze.moveUp();
                }else{
                    wall = true;
                }
                break;
            }
            case "D": {
                if (maze.canIMoveDown()) {
                    maze.moveDown();
                }else{
                    wall = true;
                }
                break;
            }
            case "L": {
                if (maze.canIMoveLeft()) {
                    maze.moveLeft();
                }else{
                    wall = true;
                }
                break;
            }
            case "R": {
                if (maze.canIMoveRight()) {
                    maze.moveRight();
                }else{
                    wall = true;
                }
                break;
            }
        }
        maze.printMap();
        if(wall){System.out.println("Sorry there's a wall.");}
        winCheck();
        navigate(userMove());
    }
}
