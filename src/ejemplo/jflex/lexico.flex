/* JFlex example: partial Java language lexer specification */
package ejemplo.jflex;

/**
 * This class is a simple example lexer.
 */
%%

%public
%class MiLexico
%unicode
%type MiToken
%line
%column

%{
    /*************************************************************************
    * En esta sección se puede incluir código que se copiará textualmente
    * como parte de la definición de la clase del analizador léxico.
    * Típicamente serán variables de instancia o nuevos métodos de la clase.
    *************************************************************************/

    int string_yyline = 0;
    int string_yycolumn = 0;
    int comment_cont =0;

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


identificador = ([a-zA-Z])\w*

constante_flotante = ([0]\.[1-9]*)|([0]\ \d+|[1-9]*\.\d*)

dupla = \(-*(([0]\.[1-9]*)|([0]\ \d+|[1-9]*\.\d*)),-*(([0]\.[1-9]*)|([0]\ \d+|[1-9]*\.\d*))\)

constante_entera = \d+

constante_booleana = true|false

comentario_unilinea = \#.+

%%

<YYINITIAL> {
/*OPERADORES ARITMÉTICOS*/
	"+"            	{ return token("MAS", yytext()); }
	"-"            	{ return token("MENOS", yytext());}
	"*"            	{ return token("MULTIPLICACION", yytext());}
	"/"            	{ return token("DIVISION", yytext());}
  

/*OPERADORES DE COMPARACIÓN*/
	"<"            	{ return token("MENOR", yytext()); }
	">"            	{ return token("MAYOR", yytext()); }
	"=="           	{ return token("IGUAL", yytext()); }
	"!="           	{ return token("DISTINTO", yytext()); }
	"<="           	{ return token("MENOR_IGUAL", yytext()); }
	">="           	{ return token("MAYOR_IGUAL", yytext()); }
    
 
	"="            	{ return token("SIGNO_IGUAL", yytext()); }
	":="           	{ return token("ASIG", yytext());}
	"{"            	{ return token("LLAVE_A", yytext()); }
	"}"            	{ return token("LLAVE_B", yytext()); }
	"["            	{ return token("C_ABRE", yytext()); }
	"]"            	{ return token("C_CIERRA", yytext()); }
	"("            	{ return token("P_ABRE", yytext());}
	")"            	{ return token("P_CIERRA", yytext());}
	","            	{ return token("COMA", yytext());}
	";"            	{ return token("PUNTO_COMA", yytext());}
	":"            	{ return token("DOS_PUNTOS",yytext());}
    
/*OPERADORES LÓGICOS*/
	and          	{return token("AND", yytext());}
	or            	{return token("OR", yytext());}
	not           	{return token("NOT", yytext());}
   

/*PALABRAS RESERVADAS*/  
	repeat                	{return token("PR_REPEAT", yytext());}
	until                   {return token("PR_UNTIL", yytext());}
	break                 	{return token("PR_BREAK", yytext());}
	continue             	{return token("PR_CONTINUE", yytext());}
	unless                	{return token("PR_UNLESS", yytext());}
	then                  	{return token("PR_THEN", yytext());}
	else                  	{return token("PR_ELSE", yytext());}
	end                   	{return token("PR_END", yytext());}
	show                 	{return token("PR_SHOW", yytext());}
	input_int   	        {return token("PR_INPUT_INT", yytext());}
	input_float   	        {return token("PR_INPUT_FLOAT", yytext());}
	input_bool   	        {return token("PR_INPUT_BOOL", yytext());}
	input_duple   	        {return token("PR_INPUT_DUPLE", yytext());}
	declare.section         {return token("PR_DECLARE.SECTION", yytext());}
	enddeclare.section   	{return token("PR_ENDDECLARE.SECTION", yytext());}
	program.section         {return token("PR_PROGRAM.SECTION", yytext());}
	endprogram.section      {return token("PR_ENDPROGRAM.SECTION", yytext());}
    

    
/*TIPOS DE DATOS*/
	boolean              	{return token("PR_BOOLEAN", yytext());}
	integer              	{return token("PR_INTEGER", yytext());}
	float                	{return token("PR_FLOAT", yytext());}
    duple                  	{return token("PR_DUPLE", yytext());}
    
	{dupla}	                { return token("DUPLE", yytext()); }
	{constante_entera}	    { return token("INTEGER", Integer.parseInt(yytext())); }
	{constante_flotante}  	{ return token ("FLOAT",Float.parseFloat(yytext())); }
	{constante_booleana}    { return token("BOOLEAN", yytext()); }
	{identificador}   	    { return token("IDENTIFICADOR", yytext()); }

         /* whitespace */
    {WhiteSpace}                   { /* ignore */ }

    {comentario_unilinea}      { /* ignore */ }

     \"                     {   string.setLength(0);
                                string_yyline = this.yyline;
                                string_yycolumn = this.yycolumn;
                                yybegin(STRING);
                            }


    "*)"                    { throw new Error("Comentario no balanceado:");}
	"(*"                	{
                            	yybegin(COMMENT_M);
                            	comment_cont++;
                        	}

	<COMMENT_M>
	{
    	"(*"            	{ comment_cont++;}

    	"*)"            	{
                            	comment_cont--;
                            	if (comment_cont == 0)
                            	{
                                	yybegin(YYINITIAL);
                            	}
                        	}
    	{WhiteSpace} { }
	}
}

	<STRING>
	{
            "\\n"             {string.append("\n");}
            "\\t"             {string.append("\t");}
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

[^]   { throw new Error("Caracter no permitido: <" + yytext() + ">"); }



