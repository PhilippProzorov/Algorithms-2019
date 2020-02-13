package lesson1;

import kotlin.NotImplementedError;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import static java.util.Collections.sort;
import static java.lang.Float.*;


@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws IOException {
        //Трудоемкость O(n*log(n))
        //Ресурсоёмкость O(n)
        //n - количество строк
        ArrayList<String> timeAM = new ArrayList<>();
        ArrayList<String> timePM = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(inputName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        String format = "^((0[1-9])|(1[0-2])):[0-5][0-9]:[0-5][0-9] (AM|PM)$";
        String currentLine = reader.readLine();
        while (currentLine != null) {
            if (Pattern.matches(format, currentLine)) {
                if (currentLine.startsWith("12")) {
                    currentLine = "00" + currentLine.substring(2);
                }
                if (currentLine.endsWith("AM")) {
                    timeAM.add(currentLine);
                } else {
                    timePM.add(currentLine);
                }

            }
            currentLine = reader.readLine();
        }
        reader.close();
        sort(timeAM);
        sort(timePM);
        timeAM.addAll(timePM);
        for (String time : timeAM) {
            if (time.startsWith("00")) {
                time = "12" + time.substring(2);
            }
            writer.write(time);
            writer.newLine();
        }
        writer.close();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        throw new NotImplementedError();
    }


    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */

    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        //Трудоемкость O(n*log(n))
        // n - количество строк в файле
        //Ресурсоёмкость O(t)
        // t - количество температур
        BufferedReader reader = new BufferedReader(new FileReader(inputName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        String line = reader.readLine();
        float noPoint = parseFloat(line)*10;
        short min = -2730;
        short max = 5000;
        Map<Short, Integer> output = new TreeMap<>();
        while (line != null) {
            short shortNoPoint = (short) noPoint;
            if ((noPoint >= min) && (noPoint <= max)) {
                output.merge(shortNoPoint, 1, Integer::sum);
            } else throw new IllegalArgumentException("Invalid input");
            line = reader.readLine();
        }
        reader.close();
        for (Map.Entry<Short, Integer> entry : output.entrySet()) {
            int i = 0;
            float temperatures = entry.getValue();
            while (i < temperatures) {
                float withPoint = (float) entry.getKey() / 10;
                writer.write(String.valueOf(withPoint));
                writer.newLine();
                i++;
            }
        }
        writer.close();
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        //Трудоёмкость - O(n)
        //Ресурсоёмкость - O(n)
        //n - количество ячеек
        int checkedLength = 0;
        int mergedLength = first.length;
        int mergingLength = 0;
        while (mergingLength < second.length) {
            if ((checkedLength < first.length) && ((mergedLength == second.length) ||
                    (first[checkedLength].compareTo(second[mergedLength])) <= 0)) {
                second[mergingLength] = first[checkedLength++];
            }
            else {
                second[mergingLength] = second[mergedLength++];
            }
            mergingLength++;
        }
    }
}