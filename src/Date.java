import java.time.LocalDate; // LocalDate перевіряє календарні дати та враховує високосні роки.
import java.time.temporal.ChronoUnit; // DAYS.between обчислює різницю дат у днях.
import java.util.Objects; // Objects.hash узгоджує хеш-код із рівністю полів.
public final class Date implements Comparable<Date> { // Власний Date не слід плутати з java.util.Date.
    public static final int BASE_YEAR = 2000; // Рік кодуємо зміщенням від 2000, бо byte не вміщує чотирицифровий рік.
    private byte year; // Значення -128..127 відповідають повним рокам 1872..2127.
    private byte month; // Номер місяця зберігається в межах 1..12.
    private byte day; // День місяця зберігається в межах, дозволених календарем.
    public Date(int year, int month, int day) { // Конструктор із трьох чисел приймає повний рік.
        assign(LocalDate.of(year, month, day)); // Перевірка календаря відбувається до запису полів.
    }
    public Date(String text) { // Конструктор із рядка очікує формат рік.місяць.день.
        String[] parts = text.split("\\.", -1); // Екрануємо крапку; -1 зберігає також порожнє останнє поле.
        if (parts.length != 3) throw new IllegalArgumentException("Формат дати: 2026.09.14"); // Вимагаємо рівно три компоненти.
        assign(LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]))); // Перетворюємо компоненти та перевіряємо дату.
    }
    public Date(Date other) { // Конструктор копіювання створює незалежний об'єкт.
        assign(other.toLocalDate()); // Переносимо значення, а не посилання на змінний об'єкт.
    }
    private void assign(LocalDate date) { // Єдина точка зміни полів зберігає інваріант правильної дати.
        int offset = date.getYear() - BASE_YEAR; // Переводимо повний рік у компактне зміщення.
        if (offset < Byte.MIN_VALUE || offset > Byte.MAX_VALUE) throw new IllegalArgumentException("Рік має бути 1872..2127."); // Перевіряємо межі до приведення типу.
        year = (byte) offset; // Безпечне звуження перевіреного зміщення.
        month = (byte) date.getMonthValue(); // Календар гарантує допустимий місяць.
        day = (byte) date.getDayOfMonth(); // Календар гарантує допустимий день.
    }
    private LocalDate toLocalDate() { return LocalDate.of(getYear(), month, day); } // Відновлюємо стандартне представлення для календарної арифметики.
    public int getYear() { return BASE_YEAR + year; } // Повертаємо повний рік замість внутрішнього зміщення.
    public byte getMonth() { return month; } // Надаємо читання місяця без прямого доступу до поля.
    public byte getDay() { return day; } // Надаємо читання дня.
    public void setYear(int value) { assign(LocalDate.of(value, month, day)); } // Невалідний новий рік для 29 лютого відхиляється, стан не змінюється.
    public void setMonth(int value) { assign(LocalDate.of(getYear(), value, day)); } // Не перетворюємо 31 число автоматично на останній день короткого місяця.
    public void setDay(int value) { assign(LocalDate.of(getYear(), month, value)); } // Перевіряємо новий день до зміни об'єкта.
    public Date plusDays(long count) { // Повертаємо нову дату через count днів; початкова не змінюється.
        LocalDate result = toLocalDate().plusDays(count); // Стандартна бібліотека переносить дні через місяці та роки.
        return new Date(result.getYear(), result.getMonthValue(), result.getDayOfMonth()); // Повторно перевіряємо обмеження byte для року.
    }
    public Date minusDays(long count) { return plusDays(Math.negateExact(count)); } // Віднімання зводимо до додавання протилежного числа, перевіряючи переповнення long.
    public static boolean isLeapYear(int year) { return java.time.Year.isLeap(year); } // Статична операція застосовує григоріанське правило 4, 100 і 400.
    public boolean isLeapYear() { return isLeapYear(getYear()); } // Перевантажений метод перевіряє рік поточного об'єкта.
    public long daysUntil(Date other) { return ChronoUnit.DAYS.between(toLocalDate(), other.toLocalDate()); } // Знак додатний, якщо other лежить у майбутньому.
    @Override public int compareTo(Date other) { return toLocalDate().compareTo(other.toLocalDate()); } // Від'ємне значення означає «раніше», нуль означає ту саму дату.
    public boolean isBefore(Date other) { return compareTo(other) < 0; } // Зручна логічна перевірка порядку дат.
    public boolean isAfter(Date other) { return compareTo(other) > 0; } // Перевіряємо, чи поточна дата пізніша.
    @Override public boolean equals(Object other) { return other instanceof Date d && year == d.year && month == d.month && day == d.day; } // Порівнюємо значення, а не адреси об'єктів.
    @Override public int hashCode() { return Objects.hash(year, month, day); } // Рівні дати обов'язково мають однаковий хеш-код.
    @Override public String toString() { return String.format("%04d.%02d.%02d", getYear(), month, day); } // Форматуємо рік, місяць і день з початковими нулями.
}
