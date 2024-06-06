/* JFlex example: partial Java language lexer specification */
package compiler.lexico;


import compiler.sintactico.*;
import compiler.ast.*;
import java.util.ArrayList;
import java_cup.runtime.*;
import java_cup.sym;

%%

%public
%class MiLexico
%unicode
%line
%column
%cup

%{
    int string_yyline = 0;
    int string_yycolumn = 0;
    int comment_cont = 0;

    StringBuffer string = new StringBuffer();

    private MiToken token(String nombre) {
        return new MiToken(nombre, this.yyline, this.yycolumn);
    }

    private MiToken token(String nombre, Object valor) {
        return new MiToken(nombre, this.yyline, this.yycolumn, valor);
    }

    private MiToken token(String nombre, int line, int column, Object valor) {
        return new MiToken(nombre, line, column, valor);
    }
%}

%state STRING
%state COMMENT_M

LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

identificador = [^\W\d_]\w*

constante_flotante = (\d+\.\d*)|(\d*\.\d+)

dupla = \(\s*-?{constante_flotante}\s*,\s*-?{constante_flotante}\s*\)

constante_entera = \d+

constante_booleana = true|false

comentario_unilinea = \#.*

%%

<YYINITIAL> {
/*OPERADORES ARITMÉTICOS*/
	"+"            	{ return token("MAS"); }
	"-"            	{ return token("MENOS");}
	"*"            	{ return token("MULT");}
	"/"            	{ return token("DIV");}


/*OPERADORES DE COMPARACIÓN*/
	"<"            	{ return token("MENOR"); }
	">"            	{ return token("MAYOR"); }
	"=="           	{ return token("IGUALDAD"); }
	"!="           	{ return token("DISTINTO"); }
	"<="           	{ return token("MENOR_IGUAL"); }
	">="           	{ return token("MAYOR_IGUAL"); }

	":="           	{ return token("ASIGNACION");}
	"["            	{ return token("C_ABRE"); }
	"]"            	{ return token("C_CIERRA"); }
	"("            	{ return token("P_ABRE");}
	")"            	{ return token("P_CIERRA");}
	","            	{ return token("COMA",yytext());}
	":"            	{ return token("DOS_PUNTOS");}

/*OPERADORES LÓGICOS*/
	and          	{return token("AND");}
	or            	{return token("OR");}
	not           	{return token("NOT");}


/*PALABRAS RESERVADAS*/
        fibonacci               {return token("PR_FIBONACCI");}
	repeat                	{return token("PR_REPEAT");}
	until                   {return token("PR_UNTIL");}
	break                 	{return token("PR_BREAK");}
	continue             	{return token("PR_CONTINUE");}
	unless                	{return token("PR_UNLESS");}
	then                  	{return token("PR_THEN");}
	else                  	{return token("PR_ELSE");}
	end                   	{return token("PR_END");}
	show                 	{return token("PR_SHOW");}
	input_int   	        {return token("PR_INPUT_INT");}
	input_float   	        {return token("PR_INPUT_FLOAT");}
	input_bool   	        {return token("PR_INPUT_BOOL");}
	input_duple   	        {return token("PR_INPUT_DUPLE");}
	declare.section         {return token("PR_DECLARE_SECTION");}
	enddeclare.section   	{return token("PR_ENDDECLARE_SECTION");}
	program.section         {return token("PR_PROGRAM_SECTION");}
	endprogram.section      {return token("PR_ENDPROGRAM_SECTION");}



/*TIPOS DE DATOS*/

    boolean              	{return token("PR_BOOLEAN");}
    integer              	{return token("PR_INTEGER");}
    float                	{return token("PR_FLOAT");}
    duple                  	{return token("PR_DUPLE");}

    {constante_flotante}  	{ return token ("FLOAT",Float.parseFloat(yytext())); }
    {constante_entera}	        { return token("ENTERO", Integer.parseInt(yytext())); }
    {dupla}	                { return token("DUPLE", yytext()); }
    {constante_booleana}        { return token("BOOLEAN", yytext()); }
    {identificador}   	        { return token("IDENTIFICADOR", yytext());}

         /* whitespace */
    {WhiteSpace}                   { /* ignore */ }

    {comentario_unilinea}          { /* ignore */ }

     \"                     {   string.setLength(0);
                                string_yyline = this.yyline;
                                string_yycolumn = this.yycolumn;
                                yybegin(STRING);
                            }


    "*)"                    { throw new Error("Comentario no balanceado:");}
    "(*"                    {
                            	yybegin(COMMENT_M);
                            	comment_cont++;
                            }
    [^]   { throw new Error("Caracter no permitido: <" + yytext() + ">"); }



}



<COMMENT_M>
	{
    	"(*"            	{ comment_cont++;}
        <<EOF>>             { throw new Error("Fin de archivo dentro del comentario"); }
    	"*)"            	{
                            	comment_cont--;
                            	if (comment_cont == 0)
                            	{
                                	yybegin(YYINITIAL);
                            	}
                        	}
    	[^] { }
	}

<STRING>
    	{
                "\\n"              {string.append("\n");}
                "\\"               {} // ignorar la barra
                "\\t"              {string.append("\t");}
                "\\\\"             {string.append("\\");}
                "\\\""             {string.append("\"");}


                "\""                    {
                                        yybegin(YYINITIAL);
                                        return token("STRING_LITERAL",
                                        string_yyline, string_yycolumn,
                                        string.toString());
                                        }

              /* Fin de archivo */
              <<EOF>>              { throw new Error("Fin de archivo dentro de la cadena: \n" +
                                                     string.toString()); }

              /* Cualquier otro carácter */
              [^]                  { string.append(yytext()); }
    	}
