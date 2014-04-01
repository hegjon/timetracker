grammar Time;

root	:	'year' ':' WS* INT NEWLINE+
		month* ;
		
month	:	MONTH_NAME ':' NEWLINE
		(INT  ':' WS* hours NEWLINE)* ;
		
hours	:	(INT 'h' INT 'm') | (INT ('.' INT)? '-' INT ('.' INT)?) ;
		
INT	:	'0'..'9'+ ;

MONTH_NAME
	:	'january' 
	| 	'february' 
	| 	'march'
	| 	'april' 
	|	'may'
	|	'june'
	|	'july'
	|	'august'
	|	'september'
	|	'october'
	|	'november'
	|	'december' ;

WS	:	(' ' | '\t')+ ;
NEWLINE	:	'\r' ? '\n' ;
