grammar Micro;
@header {
package edu.temple.cis.c4324.micro;
}


program:            'program' ID 'begin' statement_list 'end' ';'
                    ;

statement_list:     statement*
              ;

statement:          read_statement          #read
         |          write_statement         #write
         |          assignment_statement    #assign
         |          if_statement            #if_stmt
         |          while_statement         #while_stmt
         |          do_until_statement      #do_until_stmt
         ;

read_statement:     'read' '(' id_list ')' ';'
              ;

id_list:            ID (',' ID)*
       ;

write_statement:    'write' '(' expr_list ')' ';';

expr_list:          expr (',' expr)*
         ;

assignment_statement:   ID ':=' expr ';';

if_statement:       'if' expr 
                    'then' statement_list
                    else_if_part*
                    else_part?
                    'fi' ';'
            ;

else_if_part:          'elif' expr 'then' statement_list
            ;

else_part:      'else' statement_list
         ;

do_until_statement:     'do' statement_list 'od' 'until' expr ';'     
                  ;

while_statement:    'while' expr 'do' statement_list 'od' ';'
               ;


expr :   op=('+'|'-'|'~'|'\u00ac') expr     #unaryop
     |   <assoc=right> expr op='**' expr    #powop
     |   expr op=('+'|'-') expr             #arithop
     |   expr op=('*'|'/'|'%') expr         #arithop
     |   expr op=('<<'|'>>'|'>>>') expr     #arithop
     |   expr op=('<'|'<='|'>='|'>') expr   #compop
     |   expr op=('='|'!=') expr            #compop
     |   expr op=('&'|'^'|'|')              #arithop
     |   expr op=('\u2227'|'\u2228') expr     #logicalop
     |   ID                                 #id
     |   INT                                #int
     |   '(' expr ')'                       #parens
     ;


fragment LETTER:    [a-zA-Z_];
fragment DIGIT:     [0-9];
ID:                 LETTER (LETTER | DIGIT)*;
INT:                DIGIT+;
WS : [ \t\r\n]+ -> skip ;
