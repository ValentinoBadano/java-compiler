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

digito = [0-9]
digitosincero = [1-9]

letra = [A-Za-z]
comilla = ["'"]

contenido = ({letra}|{digito}|.)*

comentarios = ["/*"]{contenido}*["*/"]

id = {letra}(\w)*({letra}|{digito})+

const_real =  (0|{digitosincero}){digito}*[\.]{digito}*{digitosincero} 

const_entera = {digito}* | -{digito}* 

const_string = [\"]{contenido}[\"]


   
%%
/* ------------------------Lexical Rules Section---------------------- */   
<YYINITIAL> {
 
	if                      {return token ("PR_IF-->" + yytext());}   
	else			{return token ("PR_ELSE-->" + yytext());}
	while			{return token ("PR_WHILE-->" + yytext());}   
	decvar|DECVAR		{return token ("PR_DECVAR-->" + yytext());}   
	enddec|ENDDEC		{return token ("PR_ENDDEC-->" + yytext());}  
	Integer			{return token ("PR_INTEGER-->" + yytext());}   
	Float			{return token ("PR_FLOAT-->" + yytext());}   
	write			{return token ("PR_WRITE-->" + yytext());}   
	begin|BEGIN		{return token ("PR_BEGIN-->" + yytext());}
	end|END                 {return token ("PR_END-->" + yytext());}
	
	{comilla}		{return token ("COMILLA--> " + yytext());}
	{const_string}          {return token ("CONST_STRING--> " + yytext());}
	{const_real}            {return token ("CONST_REAL--> " + yytext());}
	{const_entera}          {return token ("CONST_ENTERA--> " + yytext());}
	
	";"				{return token ("FIN DE LINEA--> " + yytext()); }
	":"				{return token ("OPERADOR ASIGNACION--> " + yytext()); }
	":="                            {return token ("OP_ASIG" + yytext()); }
	"+"				{return token ("OP_MAS--> " + yytext()); }
	"-"				{return token ("OP_MENOS--> "+ yytext()); }
	"*"				{return token ("OP_MULTIPLICACION--> "+ yytext()); }
	"/"				{return token ("OP_DIVISION--> " + yytext()); }
	"="				{return token ("IGUAL" + yytext()); }
	"{"				{return token ("LLAVE_A--> " + yytext()); }
	"}"				{return token ("LLAVE_B--> " + yytext()); }
	","				{return token ("COMA--> " + yytext()); }
	"("				{return token ("PARENTESIS_A--> " + yytext()); }
	")"				{return token ("PARENTESIS_B--> " + yytext()); }
	"<"				{return token ("OP_MENOR--> " + yytext()); }
	">"				{return token ("OP_MAYOR--> " + yytext()); }
	">="			{return token ("OP_MAYOR_IGUAL--> " + yytext()); }
	"<="			{return token ("OP_MENOR_IGUAL--> " + yytext()); }
	"!="			{return token ("OP_DISTINTO--> " + yytext()); }
	"=="			{return token ("OP_IGUALIGUAL--> " + yytext()); }
	
	{id}			{return token ("ID--> " + yytext());}

	
	{WhiteSpace}	{ /* just skip what was found, do nothing */ }  
}


/* error fallback */
[^]                              { throw new Error("Illegal character <"+yytext()+">"); }
