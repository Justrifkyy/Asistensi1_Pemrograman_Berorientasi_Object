import manager.ProductManager;
import manager.TransactionManager;
import manager.UserManager;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        ProductManager productManager = new ProductManager();
        TransactionManager transactionManager = new TransactionManager();
        ConsoleUI ui = new ConsoleUI(userManager, productManager, transactionManager);
        ui.start();
    }
}