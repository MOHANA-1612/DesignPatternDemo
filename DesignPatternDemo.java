import java.util.*;

// =================== BEHAVIORAL PATTERNS ===================

// 1. STRATEGY PATTERN
interface PaymentStrategy {
    void pay(int amount);
}
class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Credit Card.");
    }
}
class UPIPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using UPI.");
    }
}
class PaymentContext {
    private PaymentStrategy strategy;
    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    public void payAmount(int amount) {
        if(strategy == null) {
            System.out.println("No payment method selected.");
        } else {
            strategy.pay(amount);
        }
    }
}

// 2. OBSERVER PATTERN
interface Observer {
    void update(String news);
}
class Student implements Observer {
    private String name;
    public Student(String name) { this.name = name; }
    public void update(String news) {
        System.out.println(name + " received news: " + news);
    }
}
class NewsAgency {
    private List<Observer> observers = new ArrayList<>();
    public void addObserver(Observer o) { observers.add(o); }
    public void removeObserver(Observer o) { observers.remove(o); }
    public void notifyObservers(String news) {
        for(Observer o: observers) {
            o.update(news);
        }
    }
}

// =================== CREATIONAL PATTERNS ===================

// 3. SINGLETON PATTERN
class Logger {
    private static Logger instance;
    private Logger() {}
    public static synchronized Logger getInstance() {
        if(instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    public void log(String msg) {
        System.out.println("[LOG]: " + msg);
    }
}

// 4. FACTORY METHOD PATTERN
interface Shape {
    void draw();
}
class Circle implements Shape {
    public void draw() { System.out.println("Drawing Circle"); }
}
class Square implements Shape {
    public void draw() { System.out.println("Drawing Square"); }
}
class ShapeFactory {
    public static Shape getShape(String type) {
        if(type.equalsIgnoreCase("circle")) return new Circle();
        else if(type.equalsIgnoreCase("square")) return new Square();
        throw new IllegalArgumentException("Unknown shape type");
    }
}

// =================== STRUCTURAL PATTERNS ===================

// 5. ADAPTER PATTERN
class LegacyPrinter {
    public void printText(String text) {
        System.out.println("LegacyPrinter: " + text);
    }
}
interface ModernPrinter {
    void print(String message);
}
class PrinterAdapter implements ModernPrinter {
    private LegacyPrinter legacyPrinter;
    public PrinterAdapter(LegacyPrinter legacyPrinter) {
        this.legacyPrinter = legacyPrinter;
    }
    public void print(String message) {
        legacyPrinter.printText(message);
    }
}

// 6. DECORATOR PATTERN
interface Coffee {
    String getDescription();
    double getCost();
}
class SimpleCoffee implements Coffee {
    public String getDescription() { return "Simple Coffee"; }
    public double getCost() { return 5.0; }
}
abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) { super(coffee); }
    public String getDescription() { return coffee.getDescription() + ", Milk"; }
    public double getCost() { return coffee.getCost() + 1.5; }
}
class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) { super(coffee); }
    public String getDescription() { return coffee.getDescription() + ", Sugar"; }
    public double getCost() { return coffee.getCost() + 0.5; }
}

// =================== MAIN APP ===================
public class DesignPatternDemo {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.log("Starting Design Pattern Demonstrations");

        // Strategy Pattern Demo
        logger.log("Demonstrating Strategy Pattern");
        PaymentContext payment = new PaymentContext();
        payment.setStrategy(new CreditCardPayment());
        payment.payAmount(100);
        payment.setStrategy(new UPIPayment());
        payment.payAmount(200);

        // Observer Pattern Demo
        logger.log("Demonstrating Observer Pattern");
        NewsAgency agency = new NewsAgency();
        Student s1 = new Student("Alice");
        Student s2 = new Student("Bob");
        agency.addObserver(s1);
        agency.addObserver(s2);
        agency.notifyObservers("Exam postponed!");

        // Singleton Pattern Demo
        logger.log("Demonstrating Singleton Pattern");
        Logger logger2 = Logger.getInstance();
        logger2.log("Same instance reused for logging.");

        // Factory Method Pattern Demo
        logger.log("Demonstrating Factory Method Pattern");
        Shape shape1 = ShapeFactory.getShape("circle");
        Shape shape2 = ShapeFactory.getShape("square");
        shape1.draw();
        shape2.draw();

        // Adapter Pattern Demo
        logger.log("Demonstrating Adapter Pattern");
        LegacyPrinter legacyPrinter = new LegacyPrinter();
        ModernPrinter printer = new PrinterAdapter(legacyPrinter);
        printer.print("Hello via Adapter!");

        // Decorator Pattern Demo
        logger.log("Demonstrating Decorator Pattern");
        Coffee coffee = new SimpleCoffee();
        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " -> $" + coffee.getCost());

        logger.log("All Design Patterns Demonstrated Successfully!");
    }
}
