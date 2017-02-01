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
      ),
      flags = Some(Flags(isDangerous = Some(false), isWild = None))
    )

    val mapCat = thriftCat.asInstanceOf[Animal.Immutable].toMap
    println("==THRIFT==> map from cat", mapCat)
  }

  def fromCaseClass() = {
    val caseClassCat = MyAnimal(
      id = "animal-id",
      animalType = "Cat",
      keywords = Seq("key", "work"),
      description = "This is a cat",
      data = MyAnimalData.CatData(MyCat(
        name = "Felix",
        fur = Some(MyFur(colour = "black", pattern = None)),
        description = Some("Black cat")
      )),
      importantDates = MyImportantDates(
        found = Some(MyRecord(
          date = 36823L,
          person = Some(MyPerson(
            email = "someone@email.co.uk",
            firstName = Some("person"),
            lastName = None)))),
        adopted = None
      ),
      flags = Some(MyFlags(isDangerous = Some(false), isWild = None))
    )

    val mapCat = caseClassCat.toMap
    println("==CASE CLASS==> map from cat", mapCat)
  }

  def main(args: Array[String]) = {
    fromThriftClass()
//    fromCaseClass()
  }
}