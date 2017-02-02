object SampleOutput {
  val mapCat = Map(
    "id" -> "cat-id",
    "animalType" -> "Cat",
    "keywords" -> List("key", "word"),
    "description" -> "This is a cat",
    "data" -> Map(
      "cat" -> Map(
        "name" -> "Felix",
        "fur" -> Some(Map(
          "colour" -> "black",
          "pattern" -> None)),
        "description" -> Some("Black cat"))),
    "importantDates" -> Map(
      "adopted" -> None,
      "found" -> Some(Map(
        "person" -> Some(Map(
          "lastName" -> None,
          "firstName" -> Some("stranger"),
          "email" -> "someone@email.co.uk")),
  "date" -> 36823L))),
  "flags" -> Some(Map(
    "isWild" -> None,
    "isDangerous" -> Some(false)))
  )

  val mapGoat = Map(
    "id" -> "goat-id",
    "animalType" -> "Goat",
    "keywords" -> List("goat", "funny"),
    "description" -> "This is a goat",
    "data" -> Map(
      "goat" -> Map(
        "name" -> "Jumpy",
        "description" -> Some("Playful baby goat"))),
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
}
