# python regular expression
## non-greedy
```
*?, +?, ??
The '*', '+', and '?' qualifiers are all greedy; they match as much text as possible.
Sometimes this behaviour isn’t desired; if the RE <.*> is matched against '<a> b <c>',
it will match the entire string, and not just '<a>'. Adding ? after the qualifier makes 
it perform the match in non-greedy or minimal fashion; as few characters as possible will 
be matched. Using the RE <.*?> will match only '<a>' 
```
