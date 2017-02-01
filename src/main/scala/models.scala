
object models {
  case class MyAnimal
  (
    id: String,
    animalType: String,
    keywords: Seq[String],
    description: String,
//    data: AnimalData,
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
}
