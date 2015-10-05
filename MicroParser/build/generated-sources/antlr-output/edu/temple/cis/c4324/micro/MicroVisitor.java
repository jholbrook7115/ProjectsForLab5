// Generated from edu\temple\cis\c4324\micro\Micro.g4 by ANTLR 4.5.1

package edu.temple.cis.c4324.micro;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MicroParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MicroVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MicroParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MicroParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#statement_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_list(MicroParser.Statement_listContext ctx);
	/**
	 * Visit a parse tree produced by the {@code read}
	 * labeled alternative in {@link MicroParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead(MicroParser.ReadContext ctx);
	/**
	 * Visit a parse tree produced by the {@code write}
	 * labeled alternative in {@link MicroParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWrite(MicroParser.WriteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link MicroParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(MicroParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if_stmt}
	 * labeled alternative in {@link MicroParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stmt(MicroParser.If_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code while_stmt}
	 * labeled alternative in {@link MicroParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stmt(MicroParser.While_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code do_until_stmt}
	 * labeled alternative in {@link MicroParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDo_until_stmt(MicroParser.Do_until_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#read_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead_statement(MicroParser.Read_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#id_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_list(MicroParser.Id_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#write_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWrite_statement(MicroParser.Write_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#expr_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_list(MicroParser.Expr_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#assignment_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_statement(MicroParser.Assignment_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#if_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statement(MicroParser.If_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#else_if_part}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse_if_part(MicroParser.Else_if_partContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#else_part}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse_part(MicroParser.Else_partContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#do_until_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDo_until_statement(MicroParser.Do_until_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#while_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_statement(MicroParser.While_statementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link MicroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(MicroParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code powop}
	 * labeled alternative in {@link MicroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowop(MicroParser.PowopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryop}
	 * labeled alternative in {@link MicroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryop(MicroParser.UnaryopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalop}
	 * labeled alternative in {@link MicroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalop(MicroParser.LogicalopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link MicroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(MicroParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link MicroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(MicroParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithop}
	 * labeled alternative in {@link MicroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithop(MicroParser.ArithopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compop}
	 * labeled alternative in {@link MicroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompop(MicroParser.CompopContext ctx);
}