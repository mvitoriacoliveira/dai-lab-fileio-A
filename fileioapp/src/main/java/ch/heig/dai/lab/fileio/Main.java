package ch.heig.dai.lab.fileio;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

// *** TODO: Change this to import your own package ***
import ch.heig.dai.lab.fileio.mvitoriacoliveira.*;

public class Main {
    // *** TODO: Change this to your own name ***
    private static final String newName = "mvitoriacoliveira";

    /**
     * Main method to transform files in a folder.
     * Create the necessary objects (FileExplorer, EncodingSelector, FileReaderWriter, Transformer).
     * In an infinite loop, get a new file from the FileExplorer, determine its encoding with the EncodingSelector,
     * read the file with the FileReaderWriter, transform the content with the Transformer, write the result with the
     * FileReaderWriter.
     * 
     * Result files are written in the same folder as the input files, and encoded with UTF8.
     *
     * File name of the result file:
     * an input file "myfile.utf16le" will be written as "myfile.utf16le.processed",
     * i.e., with a suffixe ".processed".
     */
    public static void main(String[] args) {
        // Read command line arguments
        if (args.length != 2 || !new File(args[0]).isDirectory()) {
            System.out.println("You need to provide two command line arguments: an existing folder and the number of words per line.");
            System.exit(1);
        }
        String folder = args[0];
        int wordsPerLine = Integer.parseInt(args[1]);
        System.out.println("Application started, reading folder " + folder + "...");
        // TODO: implement the main method here

        String PROCESSED_FILE_SUFFIXE = ".processed";
        FileExplorer currentFolder = new FileExplorer(folder);
        EncodingSelector encodingSelector = new EncodingSelector();
        FileReaderWriter mainReaderWriter = new FileReaderWriter();
        Transformer fileTransformer = new Transformer(newName, wordsPerLine);

        while (true) {
            try {
                // TODO: loop over all files

                // Get a new file from the FileExplorer,
                File currentFile = currentFolder.getNewFile();
                if(currentFile == null) break;

                // Determine its encoding with the EncodingSelector
                Charset currentFileEncoding = encodingSelector.getEncoding(currentFile);
                if(currentFileEncoding == null) continue;

                // Read the file with the FileReaderWriter
                String currentFileContent = mainReaderWriter.readFile(currentFile, currentFileEncoding);
                if(currentFileContent == null) continue;

                // Transform the content with the Transformer
                String wrappedAndNumberedText = fileTransformer.wrapAndNumberLines(
                        fileTransformer.capitalizeWords(fileTransformer.replaceChuck(currentFileContent)));

                // Write the result with the FileReaderWriter
                // Set new file name: <inputFileName>.<inputFileEncoding> + ".processed"
                // Result files are written in the same folder as the input files, and encoded with UTF8.
                File processedFile = new File(currentFile.getAbsolutePath() + PROCESSED_FILE_SUFFIXE);
                mainReaderWriter.writeFile(processedFile, wrappedAndNumberedText, StandardCharsets.UTF_8);

            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
    }
}

