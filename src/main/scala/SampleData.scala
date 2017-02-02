import Models._
import com.example.thrift._

object SampleData {
  val thriftCat = Animal(
    id = "cat-id",
    animalType = AnimalType(0),
    keywords = Seq("cat", "feline"),
    description = "The fluffiest fluff ever to fluff.",
    data = AnimalData.Cat(Cat(
      name = "Felix",
      fur = Some(Fur(colour = "black", pattern = None)),
      description = Some("Black cat"))),
    importantDates = ImportantDates(
      found = Some(Record(36823L,
        person = Some(Person(
          email = "someone@email.co.uk",
          firstName = Some("stranger"),
          lastName = None)))),
      adopted = None),
    flags = Some(Flags(
      isDangerous = Some(false),
      isWild = None))
  )

  val caseClassGoat = MyAnimal(
    id = "goat-id",
    animalType = "Goat",
    keywords = Seq("goat", "funny"),
    description = "This is a goat",
    data = MyAnimalData.MyGoatData(MyGoat(
      name = "Jumpy",
      description = Some("Playful baby goat"))),
    importantDates = MyImportantDates(
      found = Some(MyRecord(
        date = 11123L,
        person = Some(MyPerson(
          email = "someoneelse@email.co.uk",
          firstName = Some("vet"),
          lastName = None)))),
      adopted = None),
    flags = Some(MyFlags(
      isDangerous = Some(false),
      isWild = Some(false)))
  )
}
