package com.example.expressionevaluator;

import com.example.expressionevaluator.ast.Expression;
import com.example.expressionevaluator.env.Environment;
import com.example.expressionevaluator.evaluator.EvaluationVisitor;
import com.example.expressionevaluator.lexer.Lexer;
import com.example.expressionevaluator.parser.PrattParser;

import java.util.Scanner;

public class ExpressionEvaluatorCLI {

    private final Environment environment;
    private final EvaluationVisitor evaluator;
    private static final String ASCII_ART = """
            ____    __    ____  _______  __        ______   ______   .___  ___.  _______    .___________.  ______ \s
            \\   \\  /  \\  /   / |   ____||  |      /      | /  __  \\  |   \\/   | |   ____|   |           | /  __  \\\s
             \\   \\/    \\/   /  |  |__   |  |     |  ,----'|  |  |  | |  \\  /  | |  |__      `---|  |----`|  |  |  |
              \\            /   |   __|  |  |     |  |     |  |  |  | |  |\\/|  | |   __|         |  |     |  |  |  |
               \\    /\\    /    |  |____ |  `----.|  `----.|  `--'  | |  |  |  | |  |____        |  |     |  `--'  |
                \\__/  \\__/     |_______||_______| \\______| \\______/  |__|  |__| |_______|       |__|      \\______/\s
                                                                                                                  \s
            .___________. __    __   _______                                                                      \s
            |           ||  |  |  | |   ____|                                                                     \s
            `---|  |----`|  |__|  | |  |__                                                                        \s
                |  |     |   __   | |   __|                                                                       \s
                |  |     |  |  |  | |  |____                                                                      \s
                |__|     |__|  |__| |_______|                                                                     \s
                                                                                                                  \s
             __________   ___ .______   .______       _______     _______.     _______. __    ______   .__   __.  \s
            |   ____\\  \\ /  / |   _  \\  |   _  \\     |   ____|   /       |    /       ||  |  /  __  \\  |  \\ |  |  \s
            |  |__   \\  V  /  |  |_)  | |  |_)  |    |  |__     |   (----`   |   (----`|  | |  |  |  | |   \\|  |  \s
            |   __|   >   <   |   ___/  |      /     |   __|     \\   \\        \\   \\    |  | |  |  |  | |  . `  |  \s
            |  |____ /  .  \\  |  |      |  |\\  \\----.|  |____.----)   |   .----)   |   |  | |  `--'  | |  |\\   |  \s
            |_______/__/ \\__\\ | _|      | _| `._____||_______|_______/    |_______/    |__|  \\______/  |__| \\__|  \s
                                                                                                                  \s
             ___________    ____  ___       __       __    __       ___   .___________.  ______   .______       __\s
            |   ____\\   \\  /   / /   \\     |  |     |  |  |  |     /   \\  |           | /  __  \\  |   _  \\     |  |
            |  |__   \\   \\/   / /  ^  \\    |  |     |  |  |  |    /  ^  \\ `---|  |----`|  |  |  | |  |_)  |    |  |
            |   __|   \\      / /  /_\\  \\   |  |     |  |  |  |   /  /_\\  \\    |  |     |  |  |  | |      /     |  |
            |  |____   \\    / /  _____  \\  |  `----.|  `--'  |  /  _____  \\   |  |     |  `--'  | |  |\\  \\----.|__|
            |_______|   \\__/ /__/     \\__\\ |_______| \\______/  /__/     \\__\\  |__|      \\______/  | _| `._____|(__)
            """;

    public ExpressionEvaluatorCLI() {
        this.environment = new Environment();
        this.evaluator = new EvaluationVisitor(environment);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println(ASCII_ART);
        System.out.println("Expression Evaluator CLI");
        System.out.println("Type an expression to evaluate or 'exit' to quit:");

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input.trim())) {
                break;
            }

            evaluateExpression(input);
        }

        scanner.close();
        System.out.println("Exiting Expression Evaluator CLI.");
    }
    private void evaluateExpression(String input) {
        try {
            Lexer lexer = new Lexer(input);
            PrattParser parser = new PrattParser(lexer, environment);
            Expression expression = parser.parse();
            Integer result = expression.accept(evaluator);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ExpressionEvaluatorCLI cli = new ExpressionEvaluatorCLI();
        cli.start();
    }
}
