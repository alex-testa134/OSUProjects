import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Alex Testa and Devin Chen
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires [<"INSTRUCTION"> is a proper prefix of tokens]
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to statement string of body of
     *          instruction at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";
        //Removes first token from the queue
        String token = tokens.dequeue();

        //Checks if the first token is INSTRUCTION
        Reporter.assertElseFatalError(token.equals("INSTRUCTION"),
                "ERROR: this token must be an INSTRUCTION token.");

        //Removes next token from the queue
        token = tokens.dequeue();

        //Checks if the second token is an identifier token
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(token),
                "ERROR: this token must be an Identifier token.");

        //Removes next token from the queue
        token = tokens.dequeue();

        //Checks if the third token if it is IS
        Reporter.assertElseFatalError(token.equals("IS"),
                "ERROR: this token must be IS.");

        body.parseBlock(tokens);

        //Removes next token from the queue
        token = tokens.dequeue();

        //Checks if the next token is  END
        Reporter.assertElseFatalError(token.equals("END"),
                "ERROR: this toekn must be END");

        //Removes next token from the queue
        token = tokens.dequeue();

        //Checks if the second token is an identifier token
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(token),
                "ERROR: this token must be an Identifier token.");

        return token;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        // Check for PROGRAM
        String token = tokens.dequeue();
        Reporter.assertElseFatalError(token.equals("PROGRAM"),
                "ERROR: this token must be PROGRAM.");

        // Check for identifier then add to program name
        token = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(token),
                "ERROR: this token must be an Identifier token.");
        this.replaceName(token);

        // Check for IS
        token = tokens.dequeue();
        Reporter.assertElseFatalError(token.equals("IS"),
                "ERROR: this token must be IS.");

        // Check for instructions and add to program context
        Map<String, Statement> c = this.newContext();
        boolean beginMain = false;
        while (!beginMain) {
            Statement instrBlock = this.newBody();

            // Determine whether next token indicates new instruction
            if (!tokens.front().equals("INSTRUCTION")) {
                beginMain = true;
            } else {
                token = parseInstruction(tokens, instrBlock);
                c.add(token, instrBlock);
            }
        }
        this.replaceContext(c);

        // Check for BEGIN
        token = tokens.dequeue();
        Reporter.assertElseFatalError(token.equals("BEGIN"),
                "ERROR: this token must be BEGIN.");

        // Parse body and add to program
        Statement block = this.newBody();
        block.parseBlock(tokens);
        this.replaceBody(block);

        // Check for END
        token = tokens.dequeue();
        Reporter.assertElseFatalError(token.equals("END"),
                "ERROR: this token must be END.");

        // Check for last identifier
        token = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(token),
                "ERROR: this token must be an Identifier token.");

        // Check for end of input
        token = tokens.dequeue();
        Reporter.assertElseFatalError(token.equals(Tokenizer.END_OF_INPUT),
                "ERROR: this token must be an END_OF_INPUT token.");
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
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
