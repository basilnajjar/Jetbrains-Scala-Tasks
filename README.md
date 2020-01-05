# Jetbrains Tasks 

## Warmup
1. Big-O complexity the given recursive function is O(2^n).

2. We can definitely do better by iterating n times and in each iteration multiply the input number by two (as illustrated in the function faster_pow_two)
The complexity of this algorithm will be O(n).
The fastest and best solution can be achieved using the optimized built-in math.pow function as seen in fastest_pow_two function below.
The complexity of this function is going to be O(1).

You can see the code in file [Warmup.scala](https://github.com/basilnajjar/Jetbrains-Scala-Tasks/tree/master/src/main/scala/Tasks/Warmup.scala) to look at the implementation of the given recursive function and the more efficient ones for the same problem.

## JSON serialization
I used the well-documented [Play Framework](https://www.playframework.com/documentation/2.8.x/ScalaJson) for JSON formatting. This frameworks has many built-in functions which come in handy and makes it very easy for JSON formatting.
For testing, I used [ScalaTest](http://www.scalatest.org/) which is also very well-documented and provides simple and clear syntax.

My program can:
- Convert a BooleanExpression to a JSON format.
- Convert a JSON format to a BooleanExpression.

You can run the program [Main.scala](https://github.com/basilnajjar/Jetbrains-Scala-Tasks/tree/master/src/main/scala/Tasks/Main.scala) to see the conversion in action.