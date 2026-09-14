public class LabChecks { // Автономні перевірки без зовнішніх бібліотек тестування.
    private static int count = 0; // Лічильник успішно перевірених умов.
    private static void check(boolean value) { // Допоміжний метод перетворює хибну умову на провал тесту.
        if (!value) throw new AssertionError("Перевірка " + (count + 1) + " не пройшла."); // Зупиняємо тест із ненульовим кодом процесу.
        count++; // Рахуємо лише успішні перевірки.
    }
    private static void near(double actual, double expected) { // Порівнюємо double з допуском на округлення.
        check(Math.abs(actual - expected) <= 1e-9 * Math.max(1, Math.abs(expected))); // Допуск враховує масштаб очікуваного числа.
    }
    private interface Action { void run() throws Exception; } // Лямбда тесту може породжувати і перевірювані винятки.
    private static void expect(Class<? extends Throwable> type, Action action) { // Перевіряємо, що помилкові дані дають саме потрібний вид помилки.
        try { action.run(); } // Виконуємо потенційно помилкову операцію.
        catch (Throwable error) { check(type.isInstance(error)); return; } // Неправильний тип винятку теж провалює тест.
        throw new AssertionError("Очікували " + type.getSimpleName()); // Відсутність потрібного винятку є помилкою реалізації.
    }
    public static void main(String[] args) throws Exception { // Метод запускає усі перевірки цієї лабораторної.
        Date leap = new Date(2024, 2, 28); // Дата перед високосним днем.
        check(leap.plusDays(1).toString().equals("2024.02.29")); // Додавання повинно враховувати 29 лютого.
        check(leap.plusDays(2).toString().equals("2024.03.01")); // Перевіряємо перехід у наступний місяць.
        check(new Date("2025.01.01").minusDays(1).toString().equals("2024.12.31")); // Перевіряємо межу року.
        check(Date.isLeapYear(2000) && !Date.isLeapYear(1900)); // Століття високосне лише за кратності 400.
        expect(java.time.DateTimeException.class, () -> new Date(2025, 2, 29)); // Неможлива календарна дата повинна бути відхилена.
        expect(IllegalArgumentException.class, () -> new Date(2128, 1, 1)); // Перевіряємо межу представлення року у byte.
        Date copy = new Date(leap); // Копія має незалежний змінний стан.
        copy.setDay(27); check(leap.getDay() == 28); // Зміна копії не повинна змінити початкову дату.
        check(leap.daysUntil(new Date(2024,3,1)) == 2); // Кількість днів не дорівнює простій різниці номерів дня.
        Date invalidSetter = new Date(2024,2,29); // Перевіряємо атомарність зміни року.
        expect(java.time.DateTimeException.class, () -> invalidSetter.setYear(2025)); // Невдалий setter не повинен частково змінити об'єкт.
        check(invalidSetter.getYear() == 2024); // Рік залишився початковим.
        Square square = new Square(3); // Контрольний квадрат зі стороною три.
        near(square.area(), 9); near(square.perimeter(), 12); near(square.diagonal(), Math.sqrt(18)); // Незалежні очікувані геометричні результати.
        expect(IllegalArgumentException.class, () -> new Square(-1)); // Від'ємна сторона не створює об'єкт.
        Balloon a = new Balloon(0,0,2); // Куля радіуса два.
        near(a.intersection(new Balloon(4,0,2)), 0); // Зовнішній дотик має нульовий спільний об'єм.
        near(a.intersection(new Balloon(0,0,1)), 4*Math.PI/3); // Повністю вкладена куля дає власний об'єм.
        near(a.intersection(new Balloon(a)), a.volume()); // Однакові центри не повинні призвести до ділення на нуль.
        near(a.intersection(new Balloon(2,0,2)), 10*Math.PI/3); // Частковий перетин дорівнює сумі двох сферичних сегментів висоти один.
        near(a.union(new Balloon(2,0,2)), 18*Math.PI); // Перевіряємо включення-виключення на тому самому випадку.
        near(new Balloon(0,0,0).intersection(a), 0); // Вироджена куля має нульовий об'єм.
        System.out.println("OK: " + count + " перевірок"); // Видимий підсумок після успішного виконання.
    }
}
