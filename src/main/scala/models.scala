object Models {
  case class MyAnimal
  (
    id: String,
    animalType: String,
    keywords: Seq[MyKeyword],
    description: String,
    data: MyAnimalData,
    importantDates: MyImportantDates,
    flags: Option[MyFlags]
  )

  case class MyKeyword(name: String)

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

  /**
    * This is how thrift generated classes look like:
    *
    * sealed trait MyAnimalData
    * object MyAnimalData {
    *   case class MyCatData(cat: MyCat) extends MyAnimalData
    *   case class MyDogData(dog: MyDog) extends MyAnimalData
    *   case class MyGoatData(goat: MyGoat) extends MyAnimalData
    * }
    *
    */

  sealed trait MyAnimalData

  case class MyCat
  (
    name: String,
    fur: Option[MyFur],
    description: Option[String]
  ) extends MyAnimalData

  case class MyDog
  (
    name: String,
    fur: Option[MyFur],
    description: Option[String]
  ) extends MyAnimalData

  case class MyGoat
  (
    name: String,
    description: Option[String]
  ) extends MyAnimalData
}
