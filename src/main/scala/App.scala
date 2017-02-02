import Models._
import com.example.thrift._
import SampleData._
import ClassToMap._
import MapToClass._

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
  def fromThriftClass() = {
//    val mapCat = thriftCat.data.asInstanceOf[AnimalData.Cat].cat.toMap
    val mapCat = thriftCat.toMap
    println("==THRIFT==> CAT MAP", mapCat)

    val updatedCatMap = updateNestedMap(map = mapCat, path = "flags.isWild".split('.').toList, value = Some(false))

    val backAgain = to[Animal.Immutable].from(updatedCatMap)
    println("==CAT MAP==> THRIFT", backAgain)
  }

  def fromCaseClass() = {
//    val mapGoat = caseClassGoat.data.asInstanceOf[MyAnimalData.MyGoatData].goat.toMap
    val mapGoat = caseClassGoat.toMap
    println("==CASE CLASS==> GOAT MAP", mapGoat)
    val backAgain = to[MyAnimal].from(mapGoat)
    println("==GOAT MAP==> CASE CLASS", backAgain)
  }

  def main(args: Array[String]) = {
    fromThriftClass()
    println()
    fromCaseClass()
  }
}