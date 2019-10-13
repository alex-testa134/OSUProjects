import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Alex Testa and Devin Chen
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer
                .isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [<"IF"> is a proper prefix of tokens]
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF") : ""
                + "Violation of: <\"IF\"> is proper prefix of tokens";
        Statement s1 = s.newInstance();
        Statement s2 = s.newInstance();

        //Removes first token from the queue
        String token = tokens.dequeue();

        //Checks if the first token is IF
        Reporter.assertElseFatalError(token.equals("IF"),
                "ERROR: this token must be an IF token.");

        //Removes next token from the queue
        token = tokens.dequeue();

        //Checks if the second token is an condition token
        Reporter.assertElseFatalError(Tokenizer.isCondition(token),
                "ERROR: this token must be a Condition token.");
        Condition c = parseCondition(token);

        //Removes next token from the queue
        token = tokens.dequeue();

        //Checks if the third token is THEN
        Reporter.assertElseFatalError(token.equals("THEN"),
                "ERROR: this token must be THEN.");

        //Parses tokens
        s1.parseBlock(tokens);

        //Removes next token from the queue
        token = tokens.dequeue();

        //Checks if the next token is ELSE or END
        if (token.equals("ELSE")) {
            s2.parseBlock(tokens);
            s.assembleIfElse(c, s1, s2);
            token = tokens.dequeue();
        } else {
            s.assembleIf(c, s1);
        }
        Reporter.assertElseFatalError(token.equals("END"),
                "ERROR: this token must be END");

        //Removes next token from the queue
        token = tokens.dequeue();

        //Checks if the second token is an IF token
        Reporter.assertElseFatalError(token.equals("IF"),
                "ERROR: this token must be IF");

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [<"WHILE"> is a proper prefix of tokens]
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE") : ""
                + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        // Remove WHILE
        String token = tokens.dequeue();
        Reporter.assertElseFatalError(token.equals("WHILE"),
                "ERROR: this token must be WHILE");

        // Remove condition
        token = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(token),
                "ERROR: this token must be a condition token");
        Condition c = parseCondition(token);

        // Remove DO
        token = tokens.dequeue();
        Reporter.assertElseFatalError(token.equals("DO"),
                "ERROR: this token must be DO");

        // Get block
        Statement block = s.newInstance();
        block.parseBlock(tokens);

        // Remove WHILE
        token = tokens.dequeue();
        Reporter.assertElseFatalError(token.equals("END"),
                "ERROR: this token must be END");

        // Remove WHILE
        token = tokens.dequeue();
        Reporter.assertElseFatalError(token.equals("WHILE"),
                "ERROR: this token must be WHILE");

        s.assembleWhile(c, block);
    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0
                && Tokenizer.isIdentifier(tokens.front()) : ""
                        + "Violation of: identifier string is proper prefix of tokens";

        String token = tokens.dequeue();
        s.assembleCall(token);
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";
        // Get front and determine which parse to use
        String front = tokens.front();
        switch (front) {
            case ("IF"):
                parseIf(tokens, this);
                break;
            case ("WHILE"):
                parseWhile(tokens, this);
                break;
            default:
                Reporter.assertElseFatalError(Tokenizer.isIdentifier(front),
                        "ERROR: this token must be an Identifier token.");
                parseCall(tokens, this);
                break;
        }

    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";
        String front = tokens.front();
        int i = 0;

        // Loop while next token indicates a statement
        while (front.equals("IF") || front.equals("WHILE")
                || Tokenizer.isIdentifier(front)) {

            // Parse statement and then add to block
            Statement s = this.newInstance();
            s.parse(tokens);
            this.addToBlock(i, s);

            // Determine next token and increment block index
            front = tokens.front();
            i++;
        }

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
