package vttp2022.day3.workshop;

/**
 * Main Program
 *
 */
public class App 
{
    private static String defaultDB = "db";
    public static void main( String[] args )
    {
        // print out the first argument db name used to create directory
        if(args.length > 0)
            if(args[0] != null) {
                System.out.println(args[0]);
                App.defaultDB = args[0];
            }
        System.out.println( defaultDB );
        Repository repo = new Repository(defaultDB);
        Session session = new Session(repo);
        session.start();    
    }
}
