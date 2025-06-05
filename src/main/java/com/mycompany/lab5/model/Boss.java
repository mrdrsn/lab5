/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.lab5.model;

/**
 *Интерфейс {@code Boss} описывает дополнительный функционал
 * врага-босса
 * @author nsoko
 */
public interface Boss {
    void regenerateHealth();
    void cumulateDamage(int damage);
}
