-- Generic For Iterator
print("Generic For Iterator")
languages = {'Lua','Javascript','Python','Java','C++','C',"Go","Rust",'Scala'}
for k,language in ipairs(languages)
do
    print(k,language)
end

-- Stateless Iterator
print("\nStateless Iterator")
function square(iteratorMaxCount,currentNumber)
    if currentNumber<iteratorMaxCount
    then
        currentNumber = currentNumber+1
        return currentNumber,currentNumber*currentNumber
    end
end

function squares(iteratorMaxCount)
    return square,iteratorMaxCount,0 -- 看起来无状态迭代器的syntax是 function,iterMaxCount,initIterCount
end

for i,n in squares(3)
do
    print(i,n) 
end

-- Stateful Iterators
print("\nStatefunl Iterators")
function elementIterator(collection)
    local index =0
    local count = #collection

    -- The closure function is returned
    return function()
        index = index +1
        if index <= count
        then
            return collection[index]
        end
    end
end

for element in elementIterator(languages)
do
    print(element)
end
