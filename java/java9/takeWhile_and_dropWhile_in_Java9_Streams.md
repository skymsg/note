# takeWhile and dropWhile in Java9 Streams
Arguably one of the most noteworthy new features introduced in Java 8 was the Streams API. It allowed developers to declaratively manipulate collections using functional style programming. Since the release of Java 9 is just around the corner, let’s have a look at two new stream operations in Java 9—takeWhile and dropWhile.

## takeWhile
takeWhile is similar to filter in the sense that it expects a predicate and returns a new stream consisting only of the elements that match the given predicate. But there’s a catch. In an ordered stream, takeWhile takes elements from the initial stream while the predicate holds true. Meaning that when an element is encountered that does not match the predicate, the rest of the stream is discarded. Let’s have a look at the following example.
```java
Stream.of(2, 4, 6, 8, 9, 10, 12)
    .takeWhile(n -> n % 2 == 0)
    .forEach(System.out::println);

// prints out:
// 2
// 4
// 6
// 8
```
In this example, we’re taking even numbers from the initial stream until the first odd number is encountered. The stream returned by takeWhile contains 2, 4, 6, 8. It does not contain 9 since it did not match the predicate. Although 10 and 12 are even, they’re not included in the returned stream as well because the stream operation was cut off when 9 was encountered.

## dropWhile
dropWhile is essentially the opposite of takeWhile. Instead of taking elements from the stream until the first element which does not match the predicate, dropWhile drops these elements and includes the remaining elements in the returned stream.

The following is the same example we used previously with one noteworthy difference. takeWhile has been replaced with dropWhile.
```java
Stream.of(2, 4, 6, 8, 9, 10, 12)
    .dropWhile(n -> n % 2 == 0)
    .forEach(System.out::println);
// prints out:
// 9
// 10
// 12
```
## Ordered Streams
In an ordered stream, dropWhile removes the longest contiguous sequence of elements that match the given predicate. In this example we’re dropping even numbers. 2, 4, 6 and 8 are removed because applying the predicate on them returns true. 9 isn’t an even number and is therefore included in the result. Even though 10 and 12 are even numbers, they’re included in the result because they came after the first element which failed the predicate.

## Unordered streams
So far we’ve looked at how takeWhile and dropWhile behave with ordered streams. But what happens if the stream is unordered? According to the docs, if some of the elements in the stream match the predicate (but not all) then the operation is nondeterministic and an arbitrary subset of matching elements is returned or removed. Meaning that you’ll get different results for each execution.
```
Set<Integer> numbers = Set.of(2, 4, 6, 3, 8);
numbers.stream()
    .takeWhile(n -> n % 2 == 0)
    .forEach(System.out::println);
// prints out a different subset of matching elements every time
// an empty set is also a subset
```
You can expect a similar behavior if you replace takeWhile with dropWhile. It’s free to drop any subset of matching elements. That includes the empty set.

## All elements match
Regardless of whether the stream is ordered or unordered, if all elements match the given predicate then takeWhile takes and dropWhile drops all elements. The result of takeWhile is the same as the input stream. On the other hand, when all elements match, the result of dropWhile will be an empty stream.

The following is an example of takeWhile applied on an unordered stream where all the elements match the predicate.
```java
Set<Integer> numbers = Set.of(2, 4, 6, 8);
numbers.stream()
    .takeWhile(n -> n % 2 == 0)
    .forEach(System.out::println);
// always prints out 2, 4, 6, 8
// the order of course is nondeterministic because the stream is unordered
```
## No elements match
I bet you can already guess what happens if no elements match the given predicate. You’re right if you guessed that the result of takeWhile will be an empty stream. Since no elements matched, there’s nothing to take. dropWhile, on the other hand, returns the input stream if there’s nothing to drop.
```java
Stream.of(2, 4, 6, 8)
    .dropWhile(n -> n % 2 != 0)
    .forEach(System.out::println);
// prints out:
// 2
// 4
// 6
// 8
```
## Parallel streams
takeWhile and dropWhile are expensive operations on ordered parallel streams. Threads have to cooperate to find the longest contiguous sequence of matching elements in encounter order and this can significantly impact performance. Using a sequential stream may give you better results.

## Summary
takeWhile and dropWhile are new additions to the Streams API introduced in Java 9. In ordered streams, they take or drop the longest contiguous sequence of elements from the stream based on the given predicate. However, in unordered streams, their behavior is nondeterministic. They’re free to take or drop any arbitrary subset of elements.


