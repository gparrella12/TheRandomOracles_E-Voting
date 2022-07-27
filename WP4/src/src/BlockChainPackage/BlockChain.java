package src.BlockChainPackage;

import java.io.*;
import java.util.*;

/**
 *
 * @author Speranza
 */
public class BlockChain {

    private List<Block> chain;

    public BlockChain() {
        chain = new ArrayList<>();
    }

    // costruttore da utilizzare quando bisogna richiamare la blockchain
    // da file di testo, dopo la prima inizializzazione.
    public BlockChain(String fileName) {
        chain = readFromFile(fileName);
    }

    void addBlock(String fileName, Block block) {
        chain.add(block);
        writeOnFile(fileName, block);
    }

    private static void writeOnFile(String fileName, Block block) {

        try ( PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            pw.print(block.toString());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private static List<Block> readFromFile(String fileName) {

        List<Block> temporaryChain = new ArrayList<>();
        
        try ( ObjectInputStream din = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {

            Block b = (Block) din.readObject();
            temporaryChain.add(b);

        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error during opening file.");
            return null;
        }
        return temporaryChain;
    }

}
