/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;

/**
 *
 * @author Nastya
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RecordManager {

    private static final String FILE_PATH = "src/main/resources/records.xlsx";
    private static final String SHEET_NAME = "Records";

    public RecordManager() {
        initialFillRecordsTable();
    }

    public void initialFillRecordsTable() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            // Создаём файл и заполняем массовкой
            List<PlayerRecord> sampleData = new ArrayList<>();
            Random random = new Random();

            String[] names = {"Scorpion", "SubZero", "LiuKang", "Sonya", "Raiden", "Baraka", "Kitana", "Jax", "Kano", "ShaoKahn"};
            for (String name : names) {
                sampleData.add(new PlayerRecord(name, 90 + random.nextInt(21)));
            }

            writeToFile(sampleData); // Это создаст файл и запишет данные
        }
    }

    public void readRecordsTable() {
        List<PlayerRecord> records = readFromFile();
        Collections.sort(records, Comparator.comparingInt(PlayerRecord::getScore).reversed());

        // Берём топ-10
        List<PlayerRecord> top10 = records.subList(0, Math.min(records.size(), 10));

        // Создаем модель таблицы
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Имя");
        model.addColumn("Очки");

        for (PlayerRecord record : top10) {
            model.addRow(new Object[]{record.getName(), record.getScore()});
        }

        // Создаем окно с таблицей
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Таблица рекордов");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    public void writeToTable(String playerName, int score) {
        List<PlayerRecord> records = readFromFile();
        records.add(new PlayerRecord(playerName, score));
        Collections.sort(records, Comparator.comparingInt(PlayerRecord::getScore).reversed());
        if (records.size() > 100) {
            records = records.subList(0, 100); // Ограничиваем до 100 записей
        }
        writeToFile(records);
    }

    // === Вспомогательные методы ===
    private List<PlayerRecord> readFromFile() {
        List<PlayerRecord> records = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new File(FILE_PATH))) {
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) {
                return records;
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Пропускаем заголовок
                }
                Cell nameCell = row.getCell(0);
                Cell scoreCell = row.getCell(1);

                if (nameCell != null && scoreCell != null) {
                    String name = nameCell.getStringCellValue();
                    int score = (int) scoreCell.getNumericCellValue();
                    records.add(new PlayerRecord(name, score));
                }
            }
        } catch (Exception e) {
            // Если файл повреждён или не существует — создаём новый
            createNewFile();
            return new ArrayList<>(); // Возвращаем пустой список
        }

        return records;
    }

    private void writeDefaultHeaders(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Имя");
        headerRow.createCell(1).setCellValue("Очки");
    }

    private void writeToFile(List<PlayerRecord> records) {
        Workbook workbook = null;
        try {
            if (new File(FILE_PATH).exists()) {
                // Открываем существующий файл
                try (FileInputStream fis = new FileInputStream(FILE_PATH)) {
                    workbook = WorkbookFactory.create(fis);
                }
            } else {
                // Создаём новый
                workbook = new XSSFWorkbook();
                workbook.createSheet(SHEET_NAME);
            }

            Sheet sheet = workbook.getSheet(SHEET_NAME);

            // Удаляем старые строки
            for (int i = sheet.getLastRowNum(); i >= 0; i--) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }
            }

            // Добавляем заголовки
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Имя");
            headerRow.createCell(1).setCellValue("Очки");

            // Добавляем данные
            int rowNum = 1;
            for (PlayerRecord record : records) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(record.getName());
                row.createCell(1).setCellValue(record.getScore());
            }

            // Сохраняем и закрываем
            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }

            // Закрываем workbook
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка сохранения таблицы рекордов.");
            try {
                if (workbook != null) {
                    workbook.close(); // Вторая попытка закрытия на случай, если не закрылся
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void createNewFile() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(SHEET_NAME);

        // Добавляем заголовки
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Имя");
        headerRow.createCell(1).setCellValue("Очки");

        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // === Вспомогательный класс ===
    private static class PlayerRecord {

        private final String name;
        private final int score;

        public PlayerRecord(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
}
