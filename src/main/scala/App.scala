import Models._
import com.example.thrift._
import SampleData._
import ClassToMap._
import MapToClass._

object App {
  def fromThriftClass() = {
//    val mapCat = thriftCat.data.asInstanceOf[AnimalData.Cat].cat.toMap
    val mapCat = thriftCat.toMap
    println("==THRIFT==> CAT MAP", mapCat)
    val backAgain = to[Animal.Immutable].from(mapCat)
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