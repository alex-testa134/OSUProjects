import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program counts word occurrences in a given input file and outputs an
 * HTML document with a table of the words and counts listed in alphabetical
 * order.
 *
 * @author Alex Testa
 *
 */
public final class WordCounter {

    public static Set<String> separators = new Set1L<>();
    public static Set<String> names = new Set1L<>();
    public static int position1 = 0;
    public static String name = "";
    public static String quote = "";

    private static void nextWordOrSeparator(Set<String> text, int position,
            Set<String> separators) {
        for (int i = position; i < text.size(); i++) {
            for (String text2 : text) {
                for (String x : separators) {
                    if ((!(x.equals(text2))) && x.equals("\n")) {
                        name = text2;

                    } else if ((!(x.equals(text2)))) {
                        i = text.size();
                    }
                    if (name != "") {
                        names.add(name);
                    }

                    position1++;
                }
            }
        }
    }

    public static Set<String> linesFromInput(SimpleReader input) {
        assert input != null : "Violation of: input is not null";
        assert input.isOpen() : "Violation of: input.is_open";

        Set<String> in = new Set1L<>();

        in.add(input.nextLine());

        for (String x : in) {
            if (x.indexOf(input.nextLine()) == -1) {
                in.add(input.nextLine());
            }
        }

        return in;
    }

    private static void outputHeader(String nameOfFile, SimpleReader file,
            SimpleWriter out, Set<String> separators) {
        String temp;
        String curr = "";
        int totalNum = 0;
        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        out.println("Words Counted in data/" + nameOfFile + ".txt</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>");
        out.println("Words Counted in data/" + nameOfFile + ".txt</h2>");
        out.println("<hr />");
        out.println("<table border = \"1\" >");
        out.println("<tr>");
        out.println("<th>Words</th>");
        out.println("<th>Counts</th>");
        out.println("</tr>");
        nextWordOrSeparator(linesFromInput(file), position1, separators);
        while (names.size() > 0) {
            temp = names.removeAny();
            out.println("<tr>");
            out.println("<td>" + temp + "</td>");
            out.println("<td>1</td>");
            out.println("</tr>");
        }
    }

    private static void outputFooter(SimpleWriter out) {
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */

    public static void main(String[] args) {

        separators.add("\t");
        separators.add("\n");
        separators.add("\r");
        separators.add(",");
        separators.add("-");
        separators.add(".");
        separators.add("!");
        separators.add("?");
        separators.add("[]");
        separators.add("'");
        separators.add(";");
        separators.add(":");
        separators.add("/");
        separators.add("()");

        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        String nameOfFile;

        System.out.print("Enter a name for the file: ");
        nameOfFile = in.nextLine();

        SimpleReader file = new SimpleReader1L(nameOfFile + ".txt");

        System.out.print("Enter a name for the output file: ");
        String outputName = in.nextLine();

        SimpleWriter fileOutput = new SimpleWriter1L(outputName + ".html");

        outputHeader(nameOfFile, file, fileOutput, separators);

        outputFooter(fileOutput);

        in.close();
        out.close();
    }

}
