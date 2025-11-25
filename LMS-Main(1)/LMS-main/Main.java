import controller.LibrarianController;
import controller.MemberController;

public class Main {

    public static void main(String[] args) {
        // Run the program, show dashboard, and perform operations
        // Example for Librarian:
        LibrarianController librarianController = new LibrarianController();
        librarianController.showDashboard();
        
        // Example for Member:
        MemberController memberController = new MemberController();
        memberController.showDashboard();
    }
}
