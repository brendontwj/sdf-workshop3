package vttp2022.day3.workshop;

import java.io.Console;
import java.util.List;

public class Session {
    public static final String LIST = "list";
    public static final String CARTS = "carts";
    public static final String ADD = "add";
    public static final String DELETE = "del";
    public static final String USERS = "users";
    public static final String LOAD = "load";
    public static final String SAVE = "save";
    public static final String END = "end";
    public static final String LOGIN = "login";

    private Repository repository;
    private Cart currCart;

    public Session(Repository repo) {
        this.repository = repo;
    }

    public void start() {
        Console cons = System.console();
        boolean stop = false;
        currCart = new Cart("Anon");

        while(!stop) {
            String input = cons.readLine("> ");
            String[] terms = input.split(" ");
            switch(terms[0]){

                case LIST:
                    // list items in cart
                    System.out.printf("Items in %s's shopping cart \n", currCart.getUsername());
                    printList(currCart.getContents());
                    break;

                case ADD:
                    int before = currCart.getContents().size();
                    for(int i=1; i < terms.length; i++) {
                        currCart.add(terms[i]);
                    }
                    int addedCount = currCart.getContents().size() - before;
                    System.out.printf("Added %d item(s) to %s's cart\n", addedCount, currCart.getUsername());
                    break;

                case DELETE:
                    int idx = Integer.parseInt(terms[1]);
                    String item = currCart.delete(idx-1);
                    System.out.printf("Removed %s from %s's cart \n", item, currCart.getUsername());
                    break;

                case LOAD:
                    currCart = repository.load(currCart.getUsername());
                    System.out.printf("Loaded %s shopping cart. There are %s items(s)\n", currCart.getUsername() , currCart.getContents().size());
                    break;

                case SAVE:
                    repository.save(currCart);
                    System.out.println("Done!");
                    break;

                case LOGIN:
                    currCart = new Cart(terms[1]);
                    System.out.printf("%s login OK\n", terms[1]);
                    currCart = repository.load(currCart.getUsername());
                    System.out.printf("Loaded %s shopping cart. There are %s items(s)\n", currCart.getUsername() , currCart.getContents().size());
                    break;
                
                case USERS:
                    List<String> allCarts = repository.getShoppingCarts();
                    System.out.println(allCarts);
                    this.printList(allCarts);
                    break;

                case END:
                    stop = true;
                    break;

                default:
                    System.err.printf("Unknown input: %s\n", terms[0]);
            }
            System.out.println("");
        }
        System.out.println("Thanks for shopping with us!");
    }

    public void printList(List<String> list) {
        if(list.size() <= 0) {
            System.out.println("No record found");
            return;
        }

        for(int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s\n", (i+1), list.get(i));
        }
    }
}
