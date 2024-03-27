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

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Identifier = [:jletter:] [:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*

/* agrego los floats */
FloatLiteral = [1-9][0-9]* "." [0-9]* | 0 "." [0-9]*

/* palabras reservadas */
while   = WHILE|while
integer = INTEGER|integer
float   = FLOAT|float
write   = WRITE|write
else    = ELSE|else
if  = IF|if

%state STRING

%%

<YYINITIAL> {

  /* keywords */
  "abstract"           { return token("ABSTRACT", yytext());}
  "boolean"            { return token("BOOLEAN", yytext()); }
  "break"              { return token("BREAK", yytext()); }
  "Integer"            { return token("INTEGER", yytext());}
  "Float"              { return token("FLOAT", yytext()); }

  DECVAR               { return token("DECVAR", yytext()); }
  ENDDEC               { return token("ENDDEC", yytext()); }
  BEGIN                { return token("BEGIN", yytext()); }
  END                  { return token("END", yytext()); }
  {while}              { return token("WHILE", yytext()); }
  {integer}            { return token("INTEGER", yytext()); }
  {float}              { return token("FLOAT", yytext()); }
  {write}              { return token("WRITE", yytext()); }
  {if}                 { return token("IF", yytext()); }
  {else}               { return token("ELSE", yytext()); }
    

  /* literals */
  {DecIntegerLiteral}            { return token("INTEGER_LITERAL", yytext()); }

  \"                             {  string.setLength(0); 
                                    yybegin(STRING); 
                                    string_yyline = this.yyline;
                                    string_yycolumn = this.yycolumn; }

  {FloatLiteral}                 { return token("FLOAT_LITERAL", yytext()); }
  ":"                  {return token("DOS_PUNTOS", yytext());}
  ","                  {return token("COMA", yytext());}
  ";"                  { return token("PUNTO_COMA", yytext()); }
  "("                  {return token("PARENTESIS_APERTURA", yytext());}
  ")"                  {return token("PARENTESIS_CIERRE", yytext());}
  "["                  {return token("CORCHETE_APERTURA", yytext());}
  "]"                  {return token("CORCHETE_CIERRE", yytext());}
  "{"                  { return token("LLAVE_APERTURA", yytext());}
  "}"                  { return token("LLAVE_CIERRE", yytext()); }


  /* operators */
  "="                            { return token("EQ", yytext()); }
  "=="                           { return token("EQEQ", yytext()); }
  "+"                            { return token("PLUS", yytext()); }
  "-"                            {return token("RESTA", yytext());}
  "/"                            {return token("DIVISION", yytext());}
  "*"                            {return token("MULTIPLICACION", yytext());}
  "<"                            { return token("MENOR", yytext());}
  ">"                            { return token("MAYOR", yytext());}
  "!"                            { return token("NEGACION", yytext()); }  
  "<="                           { return token("MENOR_EQ", yytext()); }
  ">="                           { return token("MAYOR_EQ", yytext()); }
  "!="                           { return token("DISTINTO", yytext()); }


  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */
  {Identifier}                   { return token("IDENTIFIER", yytext()); }
}

<STRING> {
  \"                             { yybegin(YYINITIAL);
                                   return token("STRING_LITERAL", string_yyline, string_yycolumn, string.toString()); }
  [^\n\r\"\\]+                   { string.append( yytext() ); }
  \\t                            { string.append('\t'); }
  \\n                            { string.append('\n'); }

  \\r                            { string.append('\r'); }
  \\\"                           { string.append('\"'); }
  \\                             { string.append('\\'); }
}

/* error fallback */
[^]                              { throw new Error("Illegal character <"+yytext()+">"); }
