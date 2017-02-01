# scrooge-shapeless-maps
**Classes to maps**

The aim of this example project is to convert thrift generated classes to nested maps using [`shapeless`](https://github.com/milessabin/shapeless)

[`scrooge`](https://github.com/twitter/scrooge) is used to generate the classes from thrift.
In parallel, case classes have been defined to mimic the thrift classes.

As suggested on [Stack Overflow](http://stackoverflow.com/a/31638390) I have defined a type class `ToMapRec` and implicits for heads, hnils and a general recursive case. On top of that a `options` implicit has been added.

**Maps to classes**

Any object that has been converted to nested maps should be able to be converted back to case classes.

This [Stack Overflow answer](http://stackoverflow.com/a/31641779) has been used for this functionality.

# Example
Given an object:
```scala
Animal(
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
```
The output should be:
```scala
Map(
  id -> animal-id, 
  animalType -> Cat,
  keywords -> List(key, work),
  description -> This is a cat,
  data -> CatData(Map(
    name -> Felix,
    fur -> Some(Map(
      colour -> black,
      pattern -> None)),
    description -> Some("Black cat"))),
  importantDates -> Map(
    adopted -> None, 
    found -> Some(Map(
      person -> Some(Map(
        lastName -> None, 
        firstName -> Some(person), 
        email -> someone@email.co.uk)), 
      date -> 36823))),
  flags -> Some(Map(
    isWild -> None, 
    isDangerous -> Some(false)))
)
```
