package src.BlockChainPackage;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Speranza
 */
public class BlockChain implements Serializable {

    private List<Block> chain;
    private String filename;
    private String filename_serial;

    /**
     *
     */
    public BlockChain() {
        chain = new ArrayList<>();
    }

    // costruttore da utilizzare quando bisogna richiamare la blockchain
    // da file di testo, dopo la prima inizializzazione.

    /**
     *
     * @param fileName
     * @param filename_serial
     */
    public BlockChain(String fileName, String filename_serial) {
        this.filename_serial = filename_serial;
        this.filename = fileName;
        chain = readFromFile(filename_serial);
    }

    /**
     *
     * @param block
     */
    public void addBlock(Block block) {
        chain.add(block);
        writeOnFile(this.filename, block);
        writeSerialized(this.filename_serial);
    }

    private static void writeOnFile(String fileName, Block block) {

        try ( PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
            pw.print(block.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeSerialized(String fileName) {
        try ( ObjectOutputStream dout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(this.filename_serial)))) {
            dout.writeObject(this.chain);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private List<Block> readFromFile(String fileName) {

        BufferedWriter writer;
        Path path = FileSystems.getDefault().getPath("/" + this.filename);
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            try {
                writer = new BufferedWriter(new FileWriter(this.filename));
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        List<Block> temporaryChain = new ArrayList<>();

        try ( ObjectInputStream din = new ObjectInputStream(new BufferedInputStream(new FileInputStream(this.filename_serial)))) {

            temporaryChain = (List) din.readObject();

        } catch (FileNotFoundException ex) {
            try {
                writer = new BufferedWriter(new FileWriter(this.filename_serial));
                writer.close();
            } catch (IOException ex1) {
                Logger.getLogger(BlockChain.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println("File not found. Created empty blockchain");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
        return temporaryChain;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return chain.toString();
    }

}
