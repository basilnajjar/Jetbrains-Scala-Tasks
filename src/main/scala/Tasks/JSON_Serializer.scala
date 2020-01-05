package Tasks

import play.api.libs.json._

// set of case classes allowing encoding Boolean expressions
sealed trait BooleanExpression
case object True extends BooleanExpression
case object False extends BooleanExpression
case class Variable(symbol: String) extends BooleanExpression
case class Not(e: BooleanExpression) extends BooleanExpression
case class Or(e1: BooleanExpression, e2: BooleanExpression) extends BooleanExpression
case class And(e1: BooleanExpression, e2: BooleanExpression) extends BooleanExpression


object JSON_convertor
{
  /**
     JSON formatting
    */
  trait JsonFormat
  {
    // show the class name in the JSON type field, otherwise it will be in the form of package.BooleanExpression.type
    implicit val cfg = JsonConfiguration(typeNaming = JsonNaming
      {
        fullName => fullName.split('.').last
      }
    )

    implicit val booleanExpression_f: OFormat[BooleanExpression] = Json.format[BooleanExpression]
    implicit val true_f: OFormat[True.type] = Json.format[True.type]
    implicit val false_f: OFormat[False.type] = Json.format[False.type]
    implicit val symbol_r: Reads[Variable] = (JsPath \ "symbol").read[String].map(Variable)
    implicit val variable_f: OFormat[Variable] = Json.format[Variable]
    implicit val not_f: OFormat[Not] = Json.format[Not]
    implicit val or_f: OFormat[Or] = Json.format[Or]
    implicit val and_f: OFormat[And] = Json.format[And]
  }

   object to_JSON extends JsonFormat
   {
    /**
       Convert a BooleanExpression to a JSON string
      */
    def expr_to_JSON(expr: BooleanExpression): String=
    {
      Json.stringify(Json.toJson(expr))
    }

    /**
       Convert a BooleanExpression to a readable JSON string format
      */
    def expr_to_readable_JSON(expr: BooleanExpression): String=
    {
      Json.prettyPrint(Json.toJson(expr))
    }
  }

  object BooleanExpression extends JsonFormat {
    /**
       Convert a JSON string to a BooleanExpression
      */
    def JSON_to_expr(JSON: String): BooleanExpression=
    {
      Json.parse(JSON).as[BooleanExpression]
    }
  }
}