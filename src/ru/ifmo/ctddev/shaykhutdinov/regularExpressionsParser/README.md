REGULAR EXPRESSIONS
====

Regular Expressions with the concatenation operator (simple sequential writes lines), select operator (vertical bar), Kleene closure operator.
----
* The priority of the operations is standard.
* Brackets can used to change the priority.
* To identify the small letters using basic language Latin alphabet.
* Use one terminal for all characters.

Example:
( ( a b c * a | b) * a a ( b a | a * ) c ) * b