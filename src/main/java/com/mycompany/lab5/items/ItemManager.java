/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5.items;

/**
 *Класс {@code ItemManager} является обработчиком работы со
 * вспомогательными предметами. Здесь реализованы действия "получить предмет",
 * "использовать предмет", "проверить наличие предмета".
 * @author nsoko
 */
import com.mycompany.lab5.model.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemManager {

    private List<Item> itemsList = new ArrayList<>();
    private Random random = new Random();

    public ItemManager() {
        addItem(new Item("Крест возрождения", 0));
        addItem(new Item("Большое зелье лечения", 0));
        addItem(new Item("Малое зелье лечения", 0));

    }

    public void addRandomItem() {
        int chance = random.nextInt(100); // 0-99

        if (chance < 5) { // 5% - крест возрождения
            addItem(new Item("Крест возрождения", 1));
        } else if (chance < 20) { // 15% (от 5 до 20) - большое зелье лечения
            addItem(new Item("Большое зелье лечения", 1));
        } else if (chance < 45) { // 25% (от 20 до 45) - малое зелье лечения
            addItem(new Item("Малое зелье лечения", 1));
        }
    }

    public void addItem(Item item) {
        boolean found = false;
        for (Item i : itemsList) {
            if (i.getName().equals(item.getName())) {
                i.setCount(item.getCount());
                found = true;
                break;
            }
        }
        if (!found) {
            itemsList.add(item);
        }
    }

    public boolean useItem(Item item, Player player) {
        if (item.getCount() > 0 && itemsList.contains(item)) {
            switch (item.getName()) {
                case "Малое зелье лечения" -> {
                    player.healByPercentage(25);
                    item.setCount(-1); // Уменьшаем на 1
                    return true;
                }
                case "Большое зелье лечения" -> {
                    player.healByPercentage(50);
                    item.setCount(-1);
                    return true;
                }
                case "Крест возрождения" -> {
                    item.setCount(-1);
                    return true;
                }
                default -> {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean hasItems() {
        return !itemsList.isEmpty();
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void clearItems() {
        itemsList.clear();
    }
}
