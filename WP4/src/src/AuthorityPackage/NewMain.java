package src.AuthorityPackage;

/**
 *
 * @author Ernesto
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       AuthorityManagement a = AuthorityManagement.getInstance();
       a.votesDecryption();
    }
    
}
