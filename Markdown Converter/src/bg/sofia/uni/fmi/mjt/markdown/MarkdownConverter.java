package bg.sofia.uni.fmi.mjt.markdown;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class MarkdownConverter implements MarkdownConverterAPI {
    @Override
    public void convertMarkdown(Reader source, Writer output) {
        try (var scanner = new Scanner(source)) {
            output.write("<html>" + System.lineSeparator());
            output.write("<body>" + System.lineSeparator());

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                line = checkFragmented(line);
                line = checkBold(line);
                line = checkItalic(line);
                line = checkHeading(line);

                line += System.lineSeparator();

                output.write(line.toCharArray());
            }

            output.write("</body>" + System.lineSeparator());
            output.write("</html>");

            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void convertMarkdown(Path from, Path to) {
        BufferedReader source;
        BufferedWriter output;
        try {
            source = Files.newBufferedReader(from);
            output = Files.newBufferedWriter(to);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        convertMarkdown(source, output);
    }

    @Override
    public void convertAllMarkdownFiles(Path sourceDir, Path targetDir) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir, "*.md")) {
            for (Path fileSourceDir: stream) {
                String path = targetDir + File.separator + fileSourceDir.getFileName().toString()
                    .replace("md", "html");
                Path fileTargetDir = Path.of(path);
                convertMarkdown(fileSourceDir, fileTargetDir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String simpleEditing(String text, String markdownTag, String htmlTag) {
        int indexOpeningTag = text.indexOf(markdownTag);
        int indexClosingTag = text.lastIndexOf(markdownTag);

        if (indexOpeningTag == -1 || indexOpeningTag == indexClosingTag) {
            return text;
        }

        if (markdownTag.equals("*")) {
            markdownTag = "\\*";
        } else if (markdownTag.equals("**")) {
            markdownTag = "\\*\\*";
        }

        text = text.replaceFirst(markdownTag, "<" + htmlTag + ">");
        text = text.replaceFirst(markdownTag, "</" + htmlTag + ">");

        return text;
    }

    private String checkFragmented(String text) {
        return simpleEditing(text, "`", "code");
    }

    private String checkBold(String text) {
        return simpleEditing(text, "**", "strong");
    }

    private String checkItalic(String text) {
        return simpleEditing(text, "*", "em");
    }

    private String checkHeading(String text) {
        if (text.contains("###### ")) {
            text = text.replace("###### ", "<h6>") + "</h6>";
        } else if (text.contains("##### ")) {
            text = text.replace("##### ", "<h5>") + "</h5>";
        } else if (text.contains("#### ")) {
            text = text.replace("#### ", "<h4>") + "</h4>";
        } else if (text.contains("### ")) {
            text = text.replace("### ", "<h3>") + "</h3>";
        } else if (text.contains("## ")) {
            text = text.replace("## ", "<h2>") + "</h2>";
        } else if (text.contains("# ")) {
            text = text.replace("# ", "<h1>") + "</h1>";
        }

        return text;
    }
}
