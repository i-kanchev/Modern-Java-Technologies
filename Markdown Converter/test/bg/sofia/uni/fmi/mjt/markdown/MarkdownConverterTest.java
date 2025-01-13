package bg.sofia.uni.fmi.mjt.markdown;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownConverterTest {

    Path mdPath, mdPath2, htmlPath, htmlPath2;
    File mdFile, mdFile2, htmlFile, htmlFile2;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        try {
            mdPath = tempDir.resolve("mdTest.md");
            mdPath2 = tempDir.resolve("mdTest2.md");
            htmlPath = tempDir.resolve("htmlTest.html");
            htmlPath2 = tempDir.resolve("htmlTest2.html");
        } catch (InvalidPathException ipe) {
            System.err.println(
                "error creating temporary test file in " +
                    this.getClass().getSimpleName());
        }

        mdFile = mdPath.toFile();
        mdFile2 = mdPath2.toFile();
        htmlFile = htmlPath.toFile();
        htmlFile2 = htmlPath2.toFile();
    }

    @Test
    void testConvertMarkdownNoFile() {

    }

    @Test
    void testConvertMarkdownFragmented() throws IOException {
        Reader testRead = new StringReader("`test`");
        Writer testWrite = new StringWriter();

        MarkdownConverter test = new MarkdownConverter();
        test.convertMarkdown(testRead, testWrite);

        assertEquals(testWrite.toString(),
            "<html>" + System.lineSeparator()
                + "<body>" + System.lineSeparator()
                + "<code>test</code>" + System.lineSeparator()
                + "</body>" + System.lineSeparator()
                + "</html>");
    }

    @Test
    void testConvertMarkdownBold() {
        Reader testRead = new StringReader("**test**");
        Writer testWrite = new StringWriter();

        MarkdownConverter test = new MarkdownConverter();
        test.convertMarkdown(testRead, testWrite);

        assertEquals(testWrite.toString(),
            "<html>" + System.lineSeparator()
                + "<body>" + System.lineSeparator()
                + "<strong>test</strong>" + System.lineSeparator()
                + "</body>" + System.lineSeparator()
                + "</html>");
    }

    @Test
    void testConvertMarkdownItalic() {
        Reader testRead = new StringReader("*test*");
        Writer testWrite = new StringWriter();

        MarkdownConverter test = new MarkdownConverter();
        test.convertMarkdown(testRead, testWrite);

        assertEquals(testWrite.toString(),
            "<html>" + System.lineSeparator()
                + "<body>" + System.lineSeparator()
                + "<em>test</em>" + System.lineSeparator()
                + "</body>" + System.lineSeparator()
                + "</html>");
    }

    @Test
    void testConvertMarkdownHeading() {
        Reader testRead = new StringReader("# test");
        Writer testWrite = new StringWriter();

        MarkdownConverter test = new MarkdownConverter();
        test.convertMarkdown(testRead, testWrite);

        assertEquals(testWrite.toString(),
            "<html>" + System.lineSeparator()
                + "<body>" + System.lineSeparator()
                + "<h1>test</h1>" + System.lineSeparator()
                + "</body>" + System.lineSeparator()
                + "</html>");
    }

    @Test
    void testConvertMarkdownHeading2() {
        Reader testRead = new StringReader("## test");
        Writer testWrite = new StringWriter();

        MarkdownConverter test = new MarkdownConverter();
        test.convertMarkdown(testRead, testWrite);

        assertEquals(testWrite.toString(),
            "<html>" + System.lineSeparator()
                + "<body>" + System.lineSeparator()
                + "<h2>test</h2>" + System.lineSeparator()
                + "</body>" + System.lineSeparator()
                + "</html>");
    }

    @Test
    void testConvertMarkdownHeading3() {
        Reader testRead = new StringReader("### test");
        Writer testWrite = new StringWriter();

        MarkdownConverter test = new MarkdownConverter();
        test.convertMarkdown(testRead, testWrite);

        assertEquals(testWrite.toString(),
            "<html>" + System.lineSeparator()
                + "<body>" + System.lineSeparator()
                + "<h3>test</h3>" + System.lineSeparator()
                + "</body>" + System.lineSeparator()
                + "</html>");
    }

    @Test
    void testConvertMarkdownHeading4() {
        Reader testRead = new StringReader("#### test");
        Writer testWrite = new StringWriter();

        MarkdownConverter test = new MarkdownConverter();
        test.convertMarkdown(testRead, testWrite);

        assertEquals(testWrite.toString(),
            "<html>" + System.lineSeparator()
                + "<body>" + System.lineSeparator()
                + "<h4>test</h4>" + System.lineSeparator()
                + "</body>" + System.lineSeparator()
                + "</html>");
    }

    @Test
    void testConvertMarkdownHeading5() {
        Reader testRead = new StringReader("##### test");
        Writer testWrite = new StringWriter();

        MarkdownConverter test = new MarkdownConverter();
        test.convertMarkdown(testRead, testWrite);

        assertEquals(testWrite.toString(),
            "<html>" + System.lineSeparator()
                + "<body>" + System.lineSeparator()
                + "<h5>test</h5>" + System.lineSeparator()
                + "</body>" + System.lineSeparator()
                + "</html>");
    }

    @Test
    void testConvertMarkdownHeading6() {
        Reader testRead = new StringReader("###### test");
        Writer testWrite = new StringWriter();

        MarkdownConverter test = new MarkdownConverter();
        test.convertMarkdown(testRead, testWrite);

        assertEquals(testWrite.toString(),
            "<html>" + System.lineSeparator()
                + "<body>" + System.lineSeparator()
                + "<h6>test</h6>" + System.lineSeparator()
                + "</body>" + System.lineSeparator()
                + "</html>");
    }

    @Test
    void testConvertMarkdownComplex() {
        Reader testRead = new StringReader("**test** `test` *test*");
        Writer testWrite = new StringWriter();

        MarkdownConverter test = new MarkdownConverter();
        test.convertMarkdown(testRead, testWrite);

        assertEquals(testWrite.toString(),
            "<html>" + System.lineSeparator()
                + "<body>" + System.lineSeparator()
                + "<strong>test</strong> <code>test</code> <em>test</em>" + System.lineSeparator()
                + "</body>" + System.lineSeparator()
                + "</html>");
    }

    @Test
    void testConvertMarkdownMultiLine() {
        Reader testRead = new StringReader("`test`" + System.lineSeparator() + "*test*");
        Writer testWrite = new StringWriter();

        MarkdownConverter test = new MarkdownConverter();
        test.convertMarkdown(testRead, testWrite);

        assertEquals(testWrite.toString(),
            "<html>" + System.lineSeparator()
                + "<body>" + System.lineSeparator()
                + "<code>test</code>" + System.lineSeparator()
                + "<em>test</em>" + System.lineSeparator()
                + "</body>" + System.lineSeparator()
                + "</html>");
    }

    @Test
    void testConvertMarkdownNoPath() {

    }

    @Test
    void testConvertMarkdownCorrectPath() {
        try {
            FileWriter fileWriterMd = new FileWriter(mdFile);
            BufferedWriter setUpMd = new BufferedWriter(fileWriterMd);
            setUpMd.write("`test`");
            setUpMd.flush();
            setUpMd.close();

            MarkdownConverter test = new MarkdownConverter();
            test.convertMarkdown(mdPath, htmlPath);

            assertEquals(Files.readString(htmlPath),
                "<html>" + System.lineSeparator()
                    + "<body>" + System.lineSeparator()
                    + "<code>test</code>" + System.lineSeparator()
                    + "</body>" + System.lineSeparator()
                    + "</html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testConvertAllMarkdownFilesNoDir() {
    }

    @Test
    void testConvertAllMarkdownMultipleFiles() {
        try {
            FileWriter fileWriterMd = new FileWriter(mdFile);
            BufferedWriter setUpMd = new BufferedWriter(fileWriterMd);
            setUpMd.write("`test`");
            setUpMd.flush();
            setUpMd.close();

            FileWriter fileWriterMd2 = new FileWriter(mdFile2);
            BufferedWriter setUpMd2 = new BufferedWriter(fileWriterMd2);
            setUpMd2.write("*test*");
            setUpMd2.flush();
            setUpMd2.close();

            MarkdownConverter test = new MarkdownConverter();
            test.convertAllMarkdownFiles(tempDir, tempDir);

            assertEquals(Files.readString(htmlPath),
                "<html>" + System.lineSeparator()
                    + "<body>" + System.lineSeparator()
                    + "<code>test</code>" + System.lineSeparator()
                    + "</body>" + System.lineSeparator()
                    + "</html>");
            assertEquals(Files.readString(htmlPath2),
                "<html>" + System.lineSeparator()
                    + "<body>" + System.lineSeparator()
                    + "<em>test</em>" + System.lineSeparator()
                    + "</body>" + System.lineSeparator()
                    + "</html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}