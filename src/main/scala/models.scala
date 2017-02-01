
object models {
  case class MyAnimal
  (
    id: String,
    animalType: String,
    keywords: Seq[String],
    description: String,
    data: MyAnimalData,
    importantDates: MyImportantDates,
    flags: Option[MyFlags]
  )

  case class MyImportantDates
  (
    found: Option[MyRecord],
    adopted: Option[MyRecord]
  )

  case class MyRecord
  (
    date: Long,
    person: Option[MyPerson]
  )

  case class MyFlags
  (
    isDangerous: Option[Boolean],
    isWild: Option[Boolean]
  )

  case class MyPerson
  (
    email: String,
    firstName: Option[String],
    lastName: Option[String]
  )

  case class MyFur
  (
    colour: String,
    pattern: Option[String]
  )

  sealed trait MyAnimalData

  object MyAnimalData {
    case class MyCatData(cat: MyCat) extends MyAnimalData
    case class MyDogData(dog: MyDog) extends MyAnimalData
    case class MyGoatData(goat: MyGoat) extends MyAnimalData
  }

  case class MyCat
  (
    name: String,
    fur: Option[MyFur],
    description: Option[String]
  )

  case class MyDog
  (
    name: String,
    fur: Option[MyFur],
    description: Option[String]
  )

  case class MyGoat
  (
    name: String,
    description: Option[String]
  )
}
