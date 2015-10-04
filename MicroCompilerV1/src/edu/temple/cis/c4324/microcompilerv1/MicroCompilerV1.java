package edu.temple.cis.c4324.microcompilerv1;

import edu.temple.cis.c4324.codegen.CodeGenerator;
import edu.temple.cis.c4324.micro.MicroLexer;
import edu.temple.cis.c4324.micro.MicroParser;
import java.io.FileInputStream;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.bcel.classfile.JavaClass;

public class MicroCompilerV1 {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        String inputFileName = args[0];
        int lastDot = inputFileName.lastIndexOf(".");
        String outputFileName;
        if (lastDot != -1) {
            outputFileName = inputFileName.substring(0, lastDot) + ".class";
        } else {
            outputFileName = inputFileName + ".class";
        }
        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(inputFileName));
        MicroLexer lexer = new MicroLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MicroParser parser = new MicroParser(tokens);
        ParseTree tree = parser.program();
        CodeGenerator cg = new CodeGenerator();
        CompileVisitor visitor = new CompileVisitor(inputFileName, cg);
        visitor.visit(tree);
        JavaClass javaClass = cg.getJavaClass();
        javaClass.dump(outputFileName);
    }
    
}
