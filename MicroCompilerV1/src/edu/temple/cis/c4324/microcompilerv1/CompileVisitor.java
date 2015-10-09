package edu.temple.cis.c4324.microcompilerv1;

import edu.temple.cis.c4324.codegen.CodeGenerator;
import edu.temple.cis.c4324.codegen.InstructionList;
import edu.temple.cis.c4324.micro.MicroBaseVisitor;
import edu.temple.cis.c4324.micro.MicroParser;
import edu.temple.cis.c4324.micro.MicroParser.ArithopContext;
import edu.temple.cis.c4324.micro.MicroParser.Assignment_statementContext;
import edu.temple.cis.c4324.micro.MicroParser.CompopContext;
import edu.temple.cis.c4324.micro.MicroParser.Do_until_statementContext;
import edu.temple.cis.c4324.micro.MicroParser.Else_partContext;
import edu.temple.cis.c4324.micro.MicroParser.Else_if_partContext;
import edu.temple.cis.c4324.micro.MicroParser.IdContext;
import edu.temple.cis.c4324.micro.MicroParser.If_statementContext;
import edu.temple.cis.c4324.micro.MicroParser.IntContext;
import edu.temple.cis.c4324.micro.MicroParser.PowopContext;
import edu.temple.cis.c4324.micro.MicroParser.ProgramContext;
import edu.temple.cis.c4324.micro.MicroParser.Read_statementContext;
import edu.temple.cis.c4324.micro.MicroParser.Statement_listContext;
import edu.temple.cis.c4324.micro.MicroParser.UnaryopContext;
import edu.temple.cis.c4324.micro.MicroParser.While_statementContext;
import edu.temple.cis.c4324.micro.MicroParser.Write_statementContext;
import java.util.List;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.MethodGen;

public class CompileVisitor extends MicroBaseVisitor<InstructionList> {

    private final CodeGenerator cg;
    private final String sourceFileName;
    private boolean inDefined;

    public CompileVisitor(String sourceFileName, CodeGenerator cg) {
        this.cg = cg;
        this.sourceFileName = sourceFileName;
        inDefined = false;
    }

    @Override
    public InstructionList visitProgram(ProgramContext ctx) {
        cg.beginClass(sourceFileName, ctx.ID().getText());
        MethodGen mg = cg.beginMain();
        InstructionList il = cg.newInstructionList();
        il.append(visit(ctx.statement_list()));
        il.addInstruction("return");
        mg.getInstructionList().append(il);
        cg.endMethod();
        return null;
    }

    @Override
    public InstructionList visitStatement_list(Statement_listContext ctx) {
        InstructionList il = cg.newInstructionList();
        int numChildren = ctx.getChildCount();
        for (int i = 0; i < numChildren; i++) {
            il.append(visit(ctx.getChild(i)));
        }
        return il;
    }

    @Override
    public InstructionList visitRead_statement(Read_statementContext ctx) {
        InstructionList il = cg.newInstructionList();
        if (!inDefined) {
            cg.addLocalVariable("$in", "java.util.Scanner");
            il.addInstruction("new", "java.util.Scanner");
            il.addInstruction("dup");
            il.addInstruction("getstatic", "java.lang.System.in", "java.io.InputStream");
            il.addInstruction("invokespecial", "java.util.Scanner.<init>", "void", "java.io.InputStream");
            il.addInstruction("astore", "$in");
            inDefined = true;
        }
        ctx.id_list().ID().forEach(id -> {
            cg.addLocalVariable(id.getText(), "int");
            il.addInstruction("aload", "$in");
            il.addInstruction("invokevirtual", "java.util.Scanner.nextInt", "int");
            il.addInstruction("istore", id.getText());
        });
        return il;
    }

    @Override
    public InstructionList visitAssignment_statement(Assignment_statementContext ctx) {
        String target = ctx.ID().getText();
        cg.addLocalVariable(target, "int");
        InstructionList il = cg.newInstructionList();
        il.append(visit(ctx.expr()));
        il.addInstruction("istore", target);
        return il;
    }
    
    @Override
    public InstructionList visitUnaryop(UnaryopContext ctx) {
        InstructionList il = cg.newInstructionList();
        il.append(visit(ctx.expr()));
        switch (ctx.op.getText()) {
            case "-":
                il.addInstruction("neg", "int");
                break;
            case "~":
                il.addInstruction("const", "-1");
                il.addInstruction("op", "^", "int");
                break;
            case "\u00ac":
                il.addInstruction("const", "1");
                il.addInstruction("op", "^", "int");
                break;
        }
        return il;
    }

    @Override
    public InstructionList visitArithop(ArithopContext ctx) {
        InstructionList il = cg.newInstructionList();
        il.append(visit(ctx.expr(0)));
        il.append(visit(ctx.expr(1)));
        il.addInstruction("op", ctx.op.getText(), "int");
        return il;
    }
    
    @Override
    public InstructionList visitPowop(PowopContext ctx) {
        InstructionList il = cg.newInstructionList();
        il.append(visit(ctx.expr(0)));
        il.addInstruction("cast", "int", "double");
        il.append(visit(ctx.expr(1)));
        il.addInstruction("cast", "int", "double");
        il.addInstruction("invokestatic", "java.lang.Math.pow", "double", "double", "double");
        il.addInstruction("cast", "double", "int");
        return il;
    }
       
    @Override 
    public InstructionList visitCompop(CompopContext ctx) {
        InstructionList il = cg.newInstructionList();
        il.append(visit(ctx.expr(0)));
        il.append(visit(ctx.expr(1)));
        String cmpop = ctx.op.getText();
        if (cmpop.equals("=")) cmpop = "==";
        InstructionList il2 = cg.newInstructionList();
        InstructionHandle load1 = il2.addInstruction("const", "1", "int");
        InstructionHandle end = il2.addInstruction("nop");
        il.createIf(cmpop, "int", load1);
        il.addInstruction("const", "0", "int");
        il.createGoTo(end);
        il.append(il2);
        return il;
    }
    
    @Override
    public InstructionList visitId(IdContext ctx) {
        InstructionList il = cg.newInstructionList();
        il.addInstruction("iload", ctx.getText());
        return il;
    }
    
    @Override 
    public InstructionList visitInt(IntContext ctx) {
        InstructionList il = cg.newInstructionList();
        il.addInstruction("const", ctx.getText(), "int");
        return il;
    }

    @Override
    public InstructionList visitWrite_statement(Write_statementContext ctx) {
        InstructionList il = cg.newInstructionList();
        ctx.expr_list().expr().forEach(expr -> {
            il.addInstruction("getstatic", "java.lang.System.out", "java.io.PrintStream");
            il.append(visit(expr));
            il.addInstruction("invokevirtual", "java.io.PrintStream.println", "void", "int");
        });
        return il;
    }
    
    @Override
    public InstructionList visitWhile_statement(While_statementContext ctx) {
        InstructionList il = cg.newInstructionList();
        InstructionHandle topOfLoop = il.addInstruction("nop");
        InstructionHandle endOfLoop = il.createGoTo(topOfLoop);
        InstructionHandle outOfLoop = il.addInstruction("nop");
        InstructionList ifStatement = cg.newInstructionList();
        ifStatement.createIf("==0", "int", outOfLoop);
        il.append(topOfLoop, ifStatement);
        il.append(topOfLoop, visit(ctx.expr()));
        il.insert(endOfLoop, visit(ctx.statement_list()));
        return il;
    }
       
    @Override
    public InstructionList visitIf_statement(If_statementContext ctx) {
        InstructionList il = cg.newInstructionList();
        il.append(visit(ctx.expr()));
        InstructionList il1 = cg.newInstructionList();
        InstructionHandle theEnd = il1.addInstruction("nop");
        InstructionList il2 = cg.newInstructionList();
        InstructionHandle falseTarget = il2.addInstruction("nop");
        il.createIf("==0", "int", falseTarget);
        il.append(visit(ctx.statement_list()));
        Else_partContext elsePart = ctx.else_part(); 
        
        List<Else_if_partContext> elifPart = ctx.else_if_part();
        if(!elifPart.isEmpty()){
            il.createGoTo(theEnd);
            for(Else_if_partContext elif_stmt : elifPart){
                il.append(il2);
                il.append(visit(elif_stmt.expr()));
                if(elifPart.get(elifPart.size() -1) != elifPart || ctx.else_if_part() != null ){
                    il2 = cg.newInstructionList();
                    falseTarget = il2.addInstruction("nop");
                }
                il.createIf("==0", "int", falseTarget);
                il.append(visit(elif_stmt.statement_list()));
                il.createGoTo(theEnd);
            }
        }
        
        if (ctx.else_part() != null && elifPart.isEmpty()) {
            il.createGoTo(theEnd);
        }
        il.append(il2);
        if (ctx.else_part() != null) {
            il.append(visit(elsePart.statement_list()));
        }
        if (ctx.else_part() != null) {
            il.append(il1);
        } else {
            il1.dispose();
        }
        return il;
    }
    
    @Override
    public InstructionList visitDo_until_statement(Do_until_statementContext ctx){
        InstructionList il = cg.newInstructionList();
        InstructionList il2 = cg.newInstructionList();
        InstructionHandle topOfLoop = il.addInstruction("nop");
        //InstructionHandle endOfLoop = il.createGoTo(topOfLoop);
        InstructionHandle outOfLoop = il2.addInstruction("nop");
        InstructionList ifStatement = cg.newInstructionList();
        ifStatement.createIf("!=0", "int", outOfLoop);
        
        il.append(visit(ctx.statement_list()));
        il.append(visit(ctx.expr()));
        il.append(ifStatement);
        il.createGoTo(topOfLoop);
        
        il.append(il2);
        return il;
    }

}
