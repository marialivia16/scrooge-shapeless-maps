import com.example.thrift.AnimalType

object SampleOutput {
  val mapCat = Map(
    "_passthroughFields" -> Map(),
    "id" -> "cat-id",
    "animalType" -> AnimalType.Cat,
    "keywords" -> List(
      Map(
        "_passthroughFields" -> Map(),
        "name" -> "cat"),
      Map(
        "_passthroughFields" -> Map(),
        "name" -> "feline")),
    "description" -> "The fluffiest fluff ever to fluff.",
    "data" -> Map(
      "_passthroughFields" -> Map(),
      "name" -> "Felix",
      "fur" -> Some(Map(
        "_passthroughFields" -> Map(),
        "colour" -> "black",
        "pattern" -> None)),
      "description" -> Some("Black cat")),
    "importantDates" -> Map(
      "_passthroughFields" -> Map(),
      "adopted" -> None,
      "found" -> Some(Map(
        "_passthroughFields" -> Map(),
        "person" -> Some(Map(
          "_passthroughFields" -> Map(),
          "lastName" -> None,
          "firstName" -> Some("stranger"),
          "email" -> "someone@email.co.uk")),
        "date" -> 36823L))),
  "flags" -> Some(Map(
    "_passthroughFields" -> Map(),
    "isWild" -> None,
    "isDangerous" -> Some(false)))
  )

  val mapCatData = Map(
    "cat" -> Map(
      "_passthroughFields" -> Map(),
      "name" -> "Felix",
      "fur" -> Some(Map(
        "_passthroughFields" -> Map(),
        "colour" -> "black",
        "pattern" -> None)),
      "description" -> Some("Black cat"))
  )

  val mapGoat = Map(
    "id" -> "goat-id",
    "animalType" -> "Goat",
    "keywords" -> List(Map("name" -> "goat"), Map("name" -> "funny")),
    "description" -> "This is a goat",
    "data" -> Map(
      "name" -> "Jumpy",
      "description" -> Some("Playful baby goat")),
    "importantDates" -> Map(
      "adopted" -> None,
      "found" -> Some(Map(
        "person" -> Some(Map(
          "lastName" -> None,
          "firstName" -> Some("vet"),
          "email" -> "someoneelse@email.co.uk")),
        "date" -> 11123L))),
    "flags" -> Some(Map(
      "isWild" -> Some(false),
      "isDangerous" -> Some(false)))
  )

  val mapGoatData = Map(
    "goat" -> Map(
      "name" -> "Jumpy",
      "description" -> Some("Playful baby goat"))
  )
}
