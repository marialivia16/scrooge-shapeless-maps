# scrooge-shapeless-maps

The aim of this Scala example project is to convert thrift generated classes to nested maps using [`shapeless`](https://github.com/milessabin/shapeless)

[`scrooge`](https://github.com/twitter/scrooge) is used to generate the classes from thrift.
In parallel, case classes have been defined to mimic the thrift classes.

**Classes to maps**

As suggested on [Stack Overflow](http://stackoverflow.com/a/31638390) I have defined a type class `ToMapRec` and implicits for heads, hnils and a general recursive case. On top of that a `options` implicit has been added.

**Maps to classes**

Any object that has been converted to nested maps should be able to be converted back to case classes.

This [Stack Overflow answer](http://stackoverflow.com/a/31641779) has been used for this functionality.

# Example
Given an object:
```scala
Animal(
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
```
The output should be:
```scala
Map(
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
```
