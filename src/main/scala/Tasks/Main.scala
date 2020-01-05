package Tasks

import JSON_convertor._

object Main extends App {
  val boolean_expr = And(
    And(
      Or(Variable("A"), Not(Variable("D"))),
      Or(False, Variable("F"))),
    Or(
      True,
      And(
        Or(True, False),
        And(Variable("K"), Not(Variable("Z")))
      )
    )
  )

  // Serialize BooleanExpression to readable JSON format
  println(s"Serialize BooleanExpression to readable JSON format:")
  val readable_JSON = to_JSON.expr_to_readable_JSON(boolean_expr)
  println(readable_JSON)
  println()

  // Serialize BooleanExpression to JSON string
  println(s"Serialize BooleanExpression to JSON:")
  val minified_JSON = to_JSON.expr_to_JSON(boolean_expr)
  println(minified_JSON)
  println()

  // Deserialize BooleanExpression from JSON
  println("Deserialize BooleanExpression from JSON:")
  val JSON_string = """{"_type":"And","e1":{"_type":"And","e1":{"_type":"And","e1":{"_type":"True"},"e2":{"e":{"symbol":"K","_type":"Variable"},"_type":"Not"}},"e2":{"_type":"Or","e1":{"_type":"False"},"e2":{"symbol":"F","_type":"Variable"}}},"e2":{"_type":"And","e1":{"_type":"False"},"e2":{"_type":"And","e1":{"_type":"True"},"e2":{"_type":"And","e1":{"symbol":"B","_type":"Variable"},"e2":{"e":{"symbol":"N","_type":"Variable"},"_type":"Not"}}}}}"""
  val expression = BooleanExpression.JSON_to_expr(JSON_string)
  println(expression)
}