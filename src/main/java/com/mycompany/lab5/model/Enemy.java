package com.mycompany.lab5.model;

/**
 *Класс {@code Enemy} является родительским для
 * всех конкретных типов врагов, отделяет общий родительский класс
 * для сущностей {@code Entity} от класса, описывающего игрока {@code Player}
 * @author nsoko
 */
public class Enemy extends Entity {
   
    public Enemy(int level, int health, int damage) {
        super(level, health, damage);
    }

}
