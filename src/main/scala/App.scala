import models._
import com.example.thrift._
import SampleData._
import shapeless._
import ClassToMap._

object App {
  def fromThriftClass() = {
    val mapCat = thriftCat.asInstanceOf[Animal.Immutable].toMap
    println("==THRIFT==> map from cat", mapCat)
  }

  def fromCaseClass() = {
    val mapGoat = caseClassGoat.toMap
    println("==CASE CLASS==> map from goat", mapGoat)
  }

  def main(args: Array[String]) = {
//    fromThriftClass()
    fromCaseClass()
  }
}