package Tasks

import JSON_convertor._
import org.scalatest._
import play.api.libs.json.{JsResultException, Json}

class JSON_Serializer_Test extends FlatSpec with Matchers {
  "JSON format" should "be parsed correctly" in
    {
    val JSON_format =
      """{
        |  "_type" : "And",
        |  "e1" : {
        |    "_type" : "And",
        |    "e1" : {
        |      "_type" : "And",
        |      "e1" : {
        |        "_type" : "True"
        |      },
        |      "e2" : {
        |        "e" : {
        |          "symbol" : "K",
        |          "_type" : "Variable"
        |        },
        |        "_type" : "Not"
        |      }
        |    },
        |    "e2" : {
        |      "_type" : "Or",
        |      "e1" : {
        |        "_type" : "False"
        |      },
        |      "e2" : {
        |        "symbol" : "F",
        |        "_type" : "Variable"
        |      }
        |    }
        |  },
        |  "e2" : {
        |    "_type" : "And",
        |    "e1" : {
        |      "_type" : "False"
        |    },
        |    "e2" : {
        |      "_type" : "And",
        |      "e1" : {
        |        "_type" : "True"
        |      },
        |      "e2" : {
        |        "_type" : "And",
        |        "e1" : {
        |          "symbol" : "B",
        |          "_type" : "Variable"
        |        },
        |        "e2" : {
        |          "e" : {
        |            "symbol" : "N",
        |            "_type" : "Variable"
        |          },
        |          "_type" : "Not"
        |        }
        |      }
        |    }
        |  }
        |}""".stripMargin
    val expression = BooleanExpression.JSON_to_expr(JSON_format)
    expression shouldEqual And(And(And(True,Not(Variable("K"))),Or(False,Variable("F"))),And(False,And(True,And(Variable("B"),Not(Variable("N"))))))
  }

  // Testing conversion for type False
  "False" should "be converted correctly to JSON" in
  {
    Json.parse(to_JSON.expr_to_JSON(False)) shouldEqual Json.obj("_type" -> "False")
  }

  "False" should "be parsed correctly from JSON" in
  {
    BooleanExpression.JSON_to_expr("""{"_type": "False"}""") shouldEqual False
  }

  // Testing conversion for type True
  "True" should "be converted correctly to JSON" in
  {
    Json.parse(to_JSON.expr_to_JSON(True)) shouldEqual Json.obj("_type" -> "True")
  }

  "True" should "be parsed correctly from JSON" in
  {
    BooleanExpression.JSON_to_expr("""{"_type": "True"}""") shouldEqual True
  }

  // Testing conversion for type Variable
  "Variable" should "be converted correctly to JSON" in
  {
    Json.parse(to_JSON.expr_to_JSON(Variable("X"))) shouldEqual Json.obj("_type" -> "Variable", "symbol" -> "X")
  }

  "Variable" should "be parsed correctly from JSON" in
  {
    BooleanExpression.JSON_to_expr("""{"_type": "Variable", "symbol": "X"}""") shouldEqual Variable("X")
  }

  it should "throw JsResultException if the symbol is NOT a string when parsing" in
  {
    the[JsResultException] thrownBy
    {
      BooleanExpression.JSON_to_expr("""{"_type": "Variable", "symbol": 77}""")
    } should have message "JsResultException(errors:List((/symbol,List(JsonValidationError(List(error.expected.jsstring),WrappedArray())))))"
  }

  // Testing conversion for type Not
  "Not" should "be converted correctly to JSON" in
    {
      Json.parse(to_JSON.expr_to_JSON(Not(False))) shouldEqual Json.obj("_type" -> "Not", "e" -> Json.parse(to_JSON.expr_to_JSON(False)))
    }

  "Not" should "be parsed correctly" in
  {
    BooleanExpression.JSON_to_expr("""{"_type": "Not", "e": {"_type": "False"}}""") shouldEqual Not(False)
  }

  it should "throw JsResultException if boolean expression is missing" in
  {
    the[JsResultException] thrownBy
    {
      BooleanExpression.JSON_to_expr("""{"_type": "Not"}""")
    } should have message "JsResultException(errors:List((/e,List(JsonValidationError(List(error.path.missing),WrappedArray())))))"
  }

  // Testing conversion for type Or
  "Or" should "be converted correctly to JSON" in
    {
      Json.parse(to_JSON.expr_to_JSON(Or(False, True))) shouldEqual
        Json.obj("_type" -> "Or",
        "e1" -> Json.parse(to_JSON.expr_to_JSON(False)),
        "e2" -> Json.parse(to_JSON.expr_to_JSON(True)))
    }

  "Or" should "be parsed correctly from JSON" in
  {
    BooleanExpression.JSON_to_expr("""{"_type": "Or", "e1": {"_type": "False"}, "e2": {"_type": "True"}}""") shouldEqual Or(False, True)
  }

  it should "throw JsResultException if the first boolean expression is missing" in
  {
    the[JsResultException] thrownBy
    {
      BooleanExpression.JSON_to_expr("""{"_type": "Or", "e2": {"_type": "False"}}""")
    } should have message "JsResultException(errors:List((/e1,List(JsonValidationError(List(error.path.missing),WrappedArray())))))"
  }

  it should "throw JsResultException if the second boolean expression is missing" in
  {
    the[JsResultException] thrownBy
    {
      BooleanExpression.JSON_to_expr("""{"_type": "Or", "e1": {"_type": "False"}}""")
    } should have message "JsResultException(errors:List((/e2,List(JsonValidationError(List(error.path.missing),WrappedArray())))))"
  }

  // Testing conversion for type And
  "And" should "be converted correctly to JSON" in
    {
      Json.parse(to_JSON.expr_to_JSON(And(False, True))) shouldEqual
        Json.obj("_type" -> "And",
        "e1" -> Json.parse(to_JSON.expr_to_JSON(False)),
        "e2" -> Json.parse(to_JSON.expr_to_JSON(True)))
    }

  "And" should "be parsed correctly from JSON" in
  {
    BooleanExpression.JSON_to_expr("""{"_type": "And", "e1": {"_type": "False"}, "e2": {"_type": "True"}}""") shouldEqual
      And(False, True)
  }

  // Testing for unknown types
  "Unknown inner type" should "throw JsResultException" in
    {
      the[JsResultException] thrownBy
      {
        BooleanExpression.JSON_to_expr("""{"_type": "random", "e1": {"_type": "False"}, "e2": {"_type": "True"}}""")
      } should have message  "JsResultException(errors:List((,List(JsonValidationError(List(error.invalid),WrappedArray())))))"
    }

  "Unknown outer type" should "throw JsResultException" in
  {
    the[JsResultException] thrownBy
    {
      BooleanExpression.JSON_to_expr("""{"_type": "And", "e1": {"_type": "random"}, "e2": {"_type": "True"}}""")
    } should have message "JsResultException(errors:List((/e1,List(JsonValidationError(List(error.invalid),WrappedArray())))))"
  }
}