public final class Square { // Клас моделює квадрат однією довжиною сторони.
    public static final int SIDE_COUNT = 4; // Спільна для всіх квадратів незмінна кількість сторін.
    private final double side; // Після конструктора сторона не змінюється.
    public Square(double side) { // Основний конструктор приймає довжину сторони.
        if (!Double.isFinite(side) || side <= 0 || side > 1e100) throw new IllegalArgumentException("Сторона має бути в (0; 1e100]."); // Відхиляємо вироджені квадрати й небезпечні для обчислень числа.
        this.side = side; // this.side позначає поле, а side - параметр конструктора.
    }
    public Square(Square other) { this(other.side); } // Конструктор копіювання повторно використовує основний.
    public double getSide() { return side; } // Доступ до сторони лише для читання.
    public double perimeter() { return SIDE_COUNT * side; } // Периметр є сумою чотирьох рівних сторін.
    public double area() { return side * side; } // Площа квадрата дорівнює квадрату сторони.
    public double diagonal() { return side * Math.sqrt(2); } // Обидві діагоналі мають цю довжину за теоремою Піфагора.
    public static Square unit() { return new Square(1); } // Статична фабрика створює одиничний квадрат.
    @Override public boolean equals(Object other) { return other instanceof Square s && Double.compare(side, s.side) == 0; } // Рівність визначаємо за довжиною сторони.
    @Override public int hashCode() { return Double.hashCode(side); } // Хеш відповідає полю, яке використано в equals.
    @Override public String toString() { return "Square{side=" + side + "}"; } // Людинозрозумілий опис об'єкта.
}
