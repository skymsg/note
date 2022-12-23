-- variable can be declared without initialization
local a,b
print("value of a",a," value of b",b)
a,b=10,20.2
print("value of a",a," value of b",b)
-- type of variable can change
a = 10.1
print(a)


-- Data Types
nil_value = nil
print("nil_value:",nil_value)
boolean_value = true
print("boolean_value",boolean_value)
number_value = 1 -- interger,double precision floating point
print("number_value",number_value)
string_value = "jjj"
print("string_value",string_value)
string_value = 'single quote'
print("string_value",string_value)
string_value = [[bracket]]
print("string_value",string_value)

-- type function
print(type(1.1))
