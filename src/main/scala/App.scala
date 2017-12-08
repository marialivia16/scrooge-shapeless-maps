import ClassToMap._
import MapToClass._
import Models._
import SampleData._
import com.example.thrift._
import ToMapMagnolia._

object App {

  def updateNestedMap(map: Map[String, Any], path: List[String], value: Any): Map[String, Any] = path match {
    case key :: Nil => map.updated(key, value)
    case key :: tail =>
      map(key) match {
        case None => map.updated(key, Some(updateNestedMap(Map(), tail, value)))
        case optionMap: Option[Map[String, Any]] => map.updated(key, Some(updateNestedMap(optionMap.get, tail, value)))
        case simpleMap: Map[String, Any] => map.updated(key, updateNestedMap(simpleMap, tail, value))
        case somethingElse => throw new UnsupportedOperationException(s"Unexpected type found during update: $somethingElse")
      }
  }

  def fromThriftClass(withUpdate: Boolean = false) = {
    val mapCat = thriftCat.toMap
    println("==THRIFT==> CAT MAP", mapCat)

    val updatedCatMap: Map[String, Any] =
      if(withUpdate)
        updateNestedMap(map = mapCat, path = "flags.isWild".split('.').toList, value = Some(false))
      else mapCat

    val backAgain = to[Animal.Immutable].from(updatedCatMap)
    println("==CAT MAP==> THRIFT", backAgain)
  }

  def fromCaseClass() = {
    val mapGoat = caseClassGoat.toMap
    println("==CASE CLASS==> GOAT MAP", mapGoat)
    val backAgain = to[MyAnimal].from(mapGoat)
    println("==GOAT MAP==> CASE CLASS", backAgain)
  }

  def fromCaseClassUsingMagnolia() = {
    val mapGoat = caseClassGoat.toMapMagnolia
    println("==CASE CLASS==> GOAT MAP", mapGoat)
//    val backAgain = to[MyAnimal].from(mapGoat)
//    println("==GOAT MAP==> CASE CLASS", backAgain)
  }

  def main(args: Array[String]): Unit = {
    println("---THRIFT TO MAP AND BACK---")
    fromThriftClass()
    println("---CASE CLASS TO MAP AND BACK---")
    fromCaseClass()
    println("---CASE CLASS TO MAP AND BACK USING MAGNOLIA---")
    fromCaseClassUsingMagnolia()
  }
}