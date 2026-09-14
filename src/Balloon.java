import java.util.Objects; // Хеш-код обчислюватимемо за всіма геометричними параметрами.
public final class Balloon { // Куля у просторі; за трьома числами умови центр приймаємо (x,y,0).
    public static final double VOLUME_FACTOR = 4.0 * Math.PI / 3.0; // Спільний множник формули об'єму 4πr^3/3.
    private final int x; // Перша координата центра.
    private final int y; // Друга координата центра; координата z дорівнює нулю для обох куль.
    private final int radius; // Невід'ємний цілий радіус; нуль описує вироджену кулю.
    public Balloon(int x, int y, int radius) { // Три числа однозначно задають кулю за прийнятим трактуванням.
        if (radius < 0) throw new IllegalArgumentException("Радіус не може бути від'ємним."); // Від'ємний радіус не має геометричного змісту.
        this.x = x; // Зберігаємо координату x.
        this.y = y; // Зберігаємо координату y.
        this.radius = radius; // Зберігаємо перевірений радіус.
    }
    public Balloon(Balloon other) { this(other.x, other.y, other.radius); } // Копіюємо значення геометричних параметрів.
    public double volume() { return VOLUME_FACTOR * radius * radius * radius; } // Обчислення починається з double, тому int не переповнюється.
    public double intersection(Balloon other) { // Об'єм спільної частини двох куль.
        double r = radius; // Переводимо перший радіус у double до арифметичних операцій.
        double s = other.radius; // Другий радіус також використовуємо як double.
        double d = Math.hypot((double) x - other.x, (double) y - other.y); // Відстань між центрами; cast перед відніманням захищає int від переповнення.
        if (d >= r + s) return 0; // Окремі або зовнішньо дотичні кулі не мають спільного об'єму.
        if (d <= Math.abs(r - s)) return Math.min(volume(), other.volume()); // При вкладенні, зокрема однакових центрах, перетин є меншою кулею.
        double cap = r + s - d; // Величина перекриття вздовж лінії центрів.
        double value = Math.PI * cap * cap * (d * d + 2 * d * (r + s) - 3 * (r - s) * (r - s)) / (12 * d); // Формула сферичної лінзи для часткового перетину.
        return Math.max(0, Math.min(value, Math.min(volume(), other.volume()))); // Усунення лише похибок округлення поза геометричними межами.
    }
    public double union(Balloon other) { return volume() + other.volume() - intersection(other); } // Принцип включення-виключення не дозволяє двічі рахувати перетин.
    public static Balloon unit() { return new Balloon(0, 0, 1); } // Статичний метод створює одиничну кулю в початку координат.
    @Override public boolean equals(Object other) { return other instanceof Balloon b && x == b.x && y == b.y && radius == b.radius; } // Порівнюємо центр і радіус.
    @Override public int hashCode() { return Objects.hash(x, y, radius); } // Однакові кулі матимуть однаковий хеш-код.
    @Override public String toString() { return "Balloon{center=(" + x + "," + y + ",0), r=" + radius + "}"; } // У виводі явно показуємо припущення z=0.
}
