import models._
import com.example.thrift._
import shapeless._
import ClassToMap._

object App {
  def fromThriftClass() = {
    val thriftCat = Animal(
      id = "animal-id",
      animalType = AnimalType(0),
      keywords = Seq("key", "work"),
      description = "This is a cat",
      data = AnimalData.Cat(Cat(
        name = "Felix",
        fur = Some(Fur(colour = "black", pattern = None)),
        description = Some("Black cat")
      )),
      importantDates = ImportantDates(
        found = Some(Record(36823L, None)),
        adopted = None
      )
    )
    println("Hello there!", thriftCat)

    val mapCat = thriftCat.asInstanceOf[Animal.Immutable].toMap
    println("==THRIFT==> map from cat", mapCat)
  }

  def fromCaseClass() = {
    val caseClassCat = MyAnimal(
      id = "animal-id",
      animalType = "Cat",
      keywords = Seq("key", "work"),
      description = "This is a cat",
//      data = AnimalData.Cat(Cat(
//        name = "Felix",
//        fur = Some(Fur(colour = "black", pattern = None)),
//        description = Some("Black cat")
//      )),
      importantDates = MyImportantDates(
        found = Some(MyRecord(36823L, Some(MyPerson(email = "someone@email.co.uk", firstName = Some("person"), lastName = None))),
        adopted = None
      ),
      flags = Some(MyFlags(isDangerous = Some(false), isWild = None))
    )
    println("Hello there!", caseClassCat)

    val mapCat = caseClassCat.toMap
    println("==CASE CLASS==> map from cat", mapCat)
  }

  def main(args: Array[String]) = {
    fromThriftClass()
    fromCaseClass()
  }
}