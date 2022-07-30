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
 * This class represents the BlockChain.
 *
 * @author Speranza
 */
public class BlockChain implements Serializable {

    private List<Block> chain;
    private String filename;
    private String filename_serial;

    /**
     * Constructor of <code>BlockChain</code> class. It creates an empty
     * <code>ArrayList</code> of Blocks.
     */
    public BlockChain() {
        chain = new ArrayList<>();
    }

    /**
     * Constructor of <code>BlockChain</code> class, used to retrieve
     * information from the BlockChain.
     *
     * @param fileName name of the file used to make the content of the
     * BlockChain human readable.
     * @param filename_serial name of the file used to retrieve information from
     * the BlockChain.
     */
    public BlockChain(String fileName, String filename_serial) {
        this.filename_serial = filename_serial;
        this.filename = fileName;
        chain = readFromFile(filename_serial);
    }

    /**
     * Function used to add a block to the BlockChain. It adds the block to a
     * List of Blocks and writes it's content on the .txt file and the
     * serialized one.
     *
     * @param block block to be added to the BlockChain.
     */
    public void addBlock(Block block) {
        chain.add(block);
        writeOnFile(this.filename, block);
        writeSerialized(this.filename_serial);
    }

    /**
     * Function used to add a block to the file.txt.
     */
    private static void writeOnFile(String fileName, Block block) {

        try ( PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
            pw.print(block.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Function used to add a block to the serialized file.
     */
    private void writeSerialized(String fileName) {
        try ( ObjectOutputStream dout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(this.filename_serial)))) {
            dout.writeObject(this.chain);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Function used to read from the serialized file.
     */
    private List<Block> readFromFile(String fileNameSerialized) {

        BufferedWriter writer;
        Path path = FileSystems.getDefault().getPath("/" + this.filename);
        try {
            Files.delete(path);
        } catch (NoSuchFileException exp) {
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

        try ( ObjectInputStream din = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileNameSerialized)))) {

            temporaryChain = (List) din.readObject();

        } catch (FileNotFoundException ex) {
            try {
                writer = new BufferedWriter(new FileWriter(fileNameSerialized));
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
     * Function used to print the state of the BlockChain.
     *
     * @return
     */
    @Override
    public String toString() {
        return chain.toString();
    }

}
