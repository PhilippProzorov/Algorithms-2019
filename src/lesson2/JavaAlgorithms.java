package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;
import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        //Трудоёмкость - O(n)
        //Ресурсоёмкость - O(n)
        //n - количество строк входного файла
        List<Integer> prices = new ArrayList<>();
        Pair<Integer, Integer> biggestProfit = new Pair<>(1, 2);
        int maxPriceValue = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName))) {
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                prices.add(Integer.parseInt(scanner.nextLine()));
            }
        } catch (IOException | NumberFormatException e) {
            throw new IllegalArgumentException("Incorrect input");
        }
        int amount = prices.size() - 2;
        int priceIndex = prices.size() - 1;
        while (amount > 0) {
            if ((prices.get(priceIndex) - prices.get(amount)) >= maxPriceValue) {
                biggestProfit = new Pair<>(amount + 1, priceIndex + 1);
                maxPriceValue = prices.get(priceIndex) - prices.get(amount);
            } else {
                if (prices.get(amount) > prices.get(priceIndex)) {
                    priceIndex = amount;
                }
            }
            amount--;
        }
        return biggestProfit;
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        //Трудоемкость O(n)
        //n- menNumber
        //Ресурсоемкость O(1)
        int number = 0;
        int menLeft = 0;
        if ((menNumber < 1) || (choiceInterval < 1)) {
            throw new AssertionError();
        }
        while (menLeft < menNumber) {
            number = (number + choiceInterval) % (menLeft + 1);
            menLeft++;
        }
        return number + 1;
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String first, String second) {
        //Трудоёмкость - O(f*s)
        //Ресурсоёмкость - O(f*s)
        //f - длина первой входной строки
        //s - длина второй входной строки
        int[][] strings = new int[first.length()][second.length()];
        int longestFirst = 0;
        int longestSecond = 0;
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if ((first.charAt(i)) == (second.charAt(j))) {
                    if ((i > 0) && (j > 0)) {
                        strings[i][j] = strings[i - 1][j - 1] + 1;
                    } else strings[i][j] = 1;
                    if (strings[i][j] > strings[longestFirst][longestSecond]) {
                        longestFirst = i;
                        longestSecond = j;
                    }
                } else strings[i][j] = 0;
            }
        }
        return first.substring(longestFirst - strings[longestFirst][longestSecond] + 1, longestFirst + 1);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        throw new NotImplementedError();
    }

    /**
     * Балда
     * Сложная
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
