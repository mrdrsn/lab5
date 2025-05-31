/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5.items;

/**
 *
 * @author nsoko
 */
import com.mycompany.lab5.model.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemManager {
    private List<Items> itemsList = new ArrayList<>();
    private Random random = new Random();

    // Добавление случайного предмета по вероятности после боя
    public void addRandomItem() {
        int chance = random.nextInt(100); // 0-99

        if (chance < 100) { // 5% - крест возрождения
            addItem(new Items("Крест возрождения", 1));
        } else if (chance < 20) { // 15% (от 5 до 20) - большое зелье лечения
            addItem(new Items("Большое зелье лечения", 1));
        } else if (chance < 100) { // 25% (от 20 до 45) - малое зелье лечения
            addItem(new Items("Малое зелье лечения", 1));
        }
        // Остальное — ничего не выпадает
    }

    // Добавить предмет
    public void addItem(Items item) {
        boolean found = false;
        for (Items i : itemsList) {
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

    // Удалить предмет (использовать)
    public boolean useItem(Items item, Player player) {
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

    // Проверка наличия предмета
    public boolean hasItems() {
        return !itemsList.isEmpty();
    }

    // Получить список предметов
    public List<Items> getItemsList() {
        return itemsList;
    }

    // Сброс предметов (для новых игр)
    public void clearItems() {
        itemsList.clear();
    }
}