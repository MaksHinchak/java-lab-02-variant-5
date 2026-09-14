public class Main { // Запуск демонстрацій усіх трьох класів другої лабораторної.
    public static void main(String[] args) { // Шість аргументів запускають безпосередньо задачу про кулі.
        try { // Помилки введення й календаря обробляємо на межі програми.
            if (args.length > 0) { // Аргументи командного рядка мають пріоритет над меню.
                if (args.length != 6) throw new IllegalArgumentException("Потрібно 6 цілих: x1 y1 r1 x2 y2 r2."); // Перевіряємо повноту параметрів за умовою.
                Balloon a = new Balloon(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])); // Створюємо першу кулю.
                Balloon b = new Balloon(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])); // Створюємо другу кулю.
                printBalloons(a, b); // Демонструємо операції над кулями.
                return; // Після командного запуску меню не потрібне.
            }
            int task = Input.integer("Завдання (1 - дата, 2 - квадрат, 3 - кулі): ", 1, 3); // Вибір демонстрації.
            if (task == 1) { // Демонстрація всіх календарних операцій.
                Date a = new Date(Input.text("Дата рік.місяць.день: ")); // Викликаємо конструктор із рядка.
                Date b = new Date(a); // Демонструємо конструктор копіювання.
                long days = Input.integer("Кількість днів: ", -10000, 10000); // Зчитуємо зміщення в днях.
                System.out.println("Дата: " + a + "; копія: " + b + "; рівні: " + a.equals(b)); // toString викликається під час конкатенації автоматично.
                System.out.println("Рік=" + a.getYear() + ", місяць=" + a.getMonth() + ", день=" + a.getDay()); // Демонструємо всі три getters.
                System.out.println("Плюс: " + a.plusDays(days) + "; мінус: " + a.minusDays(days)); // Додавання й віднімання не змінюють a.
                System.out.println("Високосний: " + a.isLeapYear() + "; 2000: " + Date.isLeapYear(2000)); // Використовуємо об'єктний і статичний методи.
                Date c = new Date(2024, 1, 15); // Конструктор із чисел створює дату для демонстрації setters.
                c.setYear(2025); // Змінюємо рік із перевіркою календаря.
                c.setMonth(2); // Змінюємо місяць.
                c.setDay(20); // Змінюємо день.
                System.out.println("Після setters: " + c + "; днів до неї: " + a.daysUntil(c)); // Різниця може бути від'ємною.
                System.out.println("compareTo=" + a.compareTo(c) + "; до=" + a.isBefore(c) + "; після=" + a.isAfter(c)); // Демонструємо три способи порівняння порядку.
                System.out.println("Хеші рівних дат: " + a.hashCode() + " / " + b.hashCode()); // Перевіряємо контракт equals/hashCode на копії.
            } else if (task == 2) { // Геометрія квадрата.
                Square a = new Square(Input.real("Сторона квадрата: ")); // Створюємо квадрат із введеною стороною.
                Square b = new Square(a); // Створюємо незалежну копію.
                System.out.println(a + "; сторона=" + a.getSide() + "; периметр=" + a.perimeter()); // Показуємо стан і периметр.
                System.out.println("Площа=" + a.area() + "; обидві діагоналі=" + a.diagonal()); // Обидві діагоналі квадрата рівні.
                System.out.println("Копія рівна=" + a.equals(b) + "; хеші=" + a.hashCode() + "/" + b.hashCode()); // Демонструємо рівність за значенням.
                System.out.println("Одиничний: " + Square.unit() + "; сторін=" + Square.SIDE_COUNT); // Викликаємо статичну фабрику й читаємо константу.
            } else { // Інтерактивна альтернатива шести аргументам.
                String[] values = Input.text("x1 y1 r1 x2 y2 r2: ").trim().split("\\s+"); // Розділяємо рядок за одним або кількома пробілами.
                if (values.length != 6) throw new IllegalArgumentException("Потрібно рівно 6 чисел."); // Не допускаємо рекурсивного повернення в меню на порожньому вводі.
                main(values); // Повторно використовуємо гілку розбору аргументів.
            }
        } catch (RuntimeException e) { // У цій короткій демонстрації показуємо і помилки календаря, і формату, і діапазону.
            System.out.println("Помилка: " + e.getMessage()); // Причина стає видимою користувачу.
        }
    }
    private static void printBalloons(Balloon a, Balloon b) { // Єдиний формат демонстрації геометричних операцій.
        System.out.println(a + "\n" + b); // Друкуємо обидві кулі на окремих рядках.
        System.out.println("Об'єми: " + a.volume() + "; " + b.volume()); // Показуємо власний об'єм кожної кулі.
        System.out.println("Перетин=" + a.intersection(b) + "; об'єднання=" + a.union(b)); // Виводимо шукані результати.
        Balloon copy = new Balloon(a); // Демонструємо другий конструктор.
        System.out.println("Рівні між собою=" + a.equals(b) + "; копія рівна=" + a.equals(copy)); // Відрізняємо рівність геометрії від самого факту перетину.
        System.out.println("Хеші копії=" + a.hashCode() + "/" + copy.hashCode() + "; одинична=" + Balloon.unit()); // Демонструємо hashCode та статичний метод.
    }
}
