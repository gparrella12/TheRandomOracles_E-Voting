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
     * Creates the blockchain, creating an empty
     * <code>ArrayList</code> of blocks.
     * 
     */
    public BlockChain() {
        chain = new ArrayList<>();
    }

    /**
     * Creates the <code>BlockChain</code>, by passing:
     * <ul>
     * <li><code>the name of the file on which the content of the 
     * blockchain will be written in a human readable fashion.</code></li>
     * <li><code>the name of the file from which the content of the 
     * blockchain will be read. 
     * The content is stored in a machine readable fashion</code></li>
     * </ul>  
     * 
     * @param fileName, .txt file's name on which the content of the 
     * blockchain will be written.
     * @param filename_serial binary file's name from which the content of the 
     * blockchain will be read.
     */
    public BlockChain(String fileName, String filename_serial) {
        this.filename_serial = filename_serial;
        this.filename = fileName;
        chain = readFromFile(filename_serial);
    }

    /**
     * This method adds a block to the BlockChain. 
     * In particular, it adds the block to a List of Blocks 
     * and then writes the list's content on the .txt file and on the
     * binary one.
     *
     * @param block block to be added to the BlockChain.
     */
    public void addBlock(Block block) {
        chain.add(block);
        writeOnFile(this.filename, block);
        writeSerialized(this.filename_serial);
    }

    /**
     * This method writes a block to the .txt file.
     */
    private static void writeOnFile(String fileName, Block block) {

        try ( PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
            pw.print(block.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method writes a block to the binary file
     */
    private void writeSerialized(String fileName) {
        try ( ObjectOutputStream dout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(this.filename_serial)))) {
            dout.writeObject(this.chain);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method reads a list of blocks from the binary file
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
     * This method prints the state of the BlockChain.
     *
     * @return a string containing the state of the BlockChain
     */
    @Override
    public String toString() {
        return chain.toString();
    }

}
