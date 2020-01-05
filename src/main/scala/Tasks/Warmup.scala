package Tasks

/*
1. Big-O complexity is O(2^n)

2. We can definitely do better by iterating n times and in each iteration multiply the input number by two (as illustrated in the function faster_pow_two)
The complexity of this algorithm will be O(n)
The fastest and best solution can be achieved using the optimized math.pow function as seen in fastest_pow_two function below.
The complexity of this function is going to be O(1)
 */

object Warmup
{
  // recursively: O(n^2)
  def pow_two(n: Int): Int=
    {
      if (n == 0)
        1
      else
        pow_two(n - 1) + pow_two(n - 1)
    }

  // iteratively: O(n)
  def faster_pow_two(n: Int): Int=
    {
      var ans = 2
      if (n == 0)
        ans = 1

      for (i <- 2 to n)
        ans *= 2

      ans
    }

  // Using math.pow built-in function: O(1)
  def fastest_pow_two(n: Int): Int=
    {
      scala.math.pow(2, n).toInt
    }

  def main(args:Array[String])
  {
    println(pow_two(10))
    println(faster_pow_two(10))
    println(fastest_pow_two(10))
  }
}



