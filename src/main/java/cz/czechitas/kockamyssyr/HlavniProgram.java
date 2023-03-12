package cz.czechitas.kockamyssyr;

import cz.czechitas.kockamyssyr.api.*;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.Random;

/**
 * Hlaví třída pro hru Kočka–myš–sýr.
 */
public class HlavniProgram {
    private final Random random = new Random();

    private final int VELIKOST_PRVKU = 50;
    private final int SIRKA_OKNA = 1000 - VELIKOST_PRVKU;
    private final int VYSKA_OKNA = 600 - VELIKOST_PRVKU;

    private Cat tom;
    private Mouse jerry;

    /**
     * Spouštěcí metoda celé aplikace.
     *
     * @param args
     */
    public static void main(String[] args) {
        new HlavniProgram().run();
    }

    /**
     * Hlavní metoda obsahující výkonný kód.
     */
    public void run() {
        tom = vytvorKocku();
        // tom.setBrain(new KeyboardBrain(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D));

        jerry = vytvorMys();
        jerry.setBrain(new KeyboardBrain(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D));

        vytvorVeci(8);
        chytMys();
    }

    public void chytMys() {
        // TODO: Sem vepište svůj program


        do {
            ulovOsuY();
            if (!tom.isPossibleToMoveForward()) {
                obejdiPrekazku();
                ulovOsuY();
            }
            ulovOsuX();
                if (!tom.isPossibleToMoveForward()){
                    obejdiPrekazku();
                    ulovOsuX();
            }
        }
        while (tom.getX() != jerry.getX() || tom.getY() != jerry.getY());
    }

    private void obejdiPrekazku() {
        if (!tom.isPossibleToMoveForward()) {
            tom.turnLeft();
            tom.moveForward(30);
          ulovOsuX();
        }
    }


    private void ulovOsuY() {
        // zjistíme osu Y
        var YMys = jerry.getY();
        var YCat = tom.getY();
        var rozdilY = YMys - YCat;         // - znamená běž na sever?, + znamená běž na jih

        var kamHlediTom = tom.getOrientation();
//        System.out.println("osaY" + kamHlediTom);
        // Tom uloví osu y
        if (rozdilY > 0) {
            if (kamHlediTom == PlayerOrientation.DOWN) {
                tom.moveForward(Math.abs(rozdilY));
            }
            if (kamHlediTom == PlayerOrientation.UP) {
                tom.turnLeft();
                tom.turnLeft();
                tom.moveForward(Math.abs(rozdilY));
            }
            if (kamHlediTom == PlayerOrientation.RIGHT) {
                tom.turnRight();
                tom.moveForward(Math.abs(rozdilY));
            }
            if (kamHlediTom == PlayerOrientation.LEFT) {
                tom.turnLeft();
                tom.moveForward(Math.abs(rozdilY));
            }
        }

            if (rozdilY < 0) {
                if (kamHlediTom == PlayerOrientation.UP) {
                    tom.moveForward(Math.abs(rozdilY));
                }
                if (kamHlediTom == PlayerOrientation.DOWN) {
                    tom.turnLeft();
                    tom.turnLeft();
                    tom.moveForward(Math.abs(rozdilY));
                }
                if (kamHlediTom == PlayerOrientation.LEFT) {
                    tom.turnRight();
                    tom.moveForward(Math.abs(rozdilY));
                }
                if (kamHlediTom == PlayerOrientation.RIGHT) {
                    tom.turnLeft();
                    tom.moveForward(Math.abs(rozdilY));
                }
            }
        }


    private void ulovOsuX() {
        // zjistíme osu X
        var XMys = jerry.getX();
        var XCat = tom.getX();
        var rozdilX = XMys - XCat;         // - znamená běž na západ, + znamená běž na východ
//        System.out.println("rozdilX " + rozdilX);



        var kamHlediTom = tom.getOrientation();
        System.out.println(kamHlediTom);

        // Tom uloví osu x
            if (rozdilX > 0) {
                if (kamHlediTom == PlayerOrientation.DOWN) {
                    tom.turnLeft();
                    tom.moveForward(Math.abs(rozdilX));
                }
                if (kamHlediTom == PlayerOrientation.UP) {
                    tom.turnRight();
                    tom.moveForward(Math.abs(rozdilX));
                }
                if (kamHlediTom == PlayerOrientation.RIGHT) {
                    tom.moveForward(Math.abs(rozdilX));
                }
                if (kamHlediTom == PlayerOrientation.LEFT) {
                    tom.turnLeft();
                    tom.turnLeft();
                    tom.moveForward(Math.abs(rozdilX));
                }
            }

            if (rozdilX < 0) {
                if (kamHlediTom == PlayerOrientation.UP){
                    tom.turnLeft();
                    tom.moveForward(Math.abs(rozdilX));
                }
                if (kamHlediTom == PlayerOrientation.DOWN){
                    tom.turnRight();
                    tom.moveForward(Math.abs(rozdilX));
                }
                if (kamHlediTom == PlayerOrientation.LEFT){
                    tom.moveForward(Math.abs(rozdilX));
                }
                if (kamHlediTom == PlayerOrientation.RIGHT){
                    tom.turnRight();
                    tom.turnRight();
                    tom.moveForward(Math.abs(rozdilX));
                }

            }
        }



    public void vytvorVeci(int pocetStromu) {
        for (int i = 0; i < pocetStromu; i++) {
            vytvorStrom();
        }
        vytvorSyr();
        vytvorJitrnici();
    }

    public Tree vytvorStrom() {
        return new Tree(vytvorNahodnyBod());
    }

    public Cat vytvorKocku() {
        return new Cat(vytvorNahodnyBod());
    }

    public Mouse vytvorMys() {
        return new Mouse(vytvorNahodnyBod());
    }

    public Cheese vytvorSyr() {
        return new Cheese(vytvorNahodnyBod());
    }

    public Meat vytvorJitrnici() {
        return new Meat(vytvorNahodnyBod());
    }

    private Point vytvorNahodnyBod() {
        return new Point(random.nextInt(SIRKA_OKNA), random.nextInt(VYSKA_OKNA));
    }

}
