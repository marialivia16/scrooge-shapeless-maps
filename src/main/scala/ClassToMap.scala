import Models.{MyAnimalData, MyCat, MyGoat}
import com.example.thrift.AnimalDataAliases.CatAlias
import com.example.thrift._
import shapeless._
import shapeless.labelled._

object ClassToMap {
  // FROM CASE CLASS TO MAP (http://stackoverflow.com/a/31638390)
  trait ToMapRec[L] {
    def apply(hlist: L): Map[String, Any]
  }

  // Case for heads that don't have ToMapRec instances (hconsToMapRec1).
  // Note that we need to use a LowPriority trait to make sure that this instance is
  // prioritized properly with respect to hconsToMapRec0—if we didn't, the two would have
  // the same priority and we'd get errors about ambiguous instances.
  trait LowPriorityToMapRec {
    implicit def hconsToMapRecNode[K <: Symbol, V, T <: HList]
    (implicit
     wit: Witness.Aux[K],
     tmrT: Lazy[ToMapRec[T]]
    ): ToMapRec[FieldType[K, V] :: T] = (hlist: FieldType[K, V] :: T) => {
      println(hlist.head)
      tmrT.value(hlist.tail) + (wit.value.name -> hlist.head)
    }
  }

  object ToMapRec extends LowPriorityToMapRec {
    implicit val hNilToMapRec: ToMapRec[HNil] = (hlist: HNil) => Map.empty

    //  Case where we know how to convert the tail of the record, and we know that
    //  the head is something that we can also recursively convert
    implicit def hconsToMapRecDefault[K <: Symbol, V, H <: HList, T <: HList]
    (implicit
     wit: Witness.Aux[K],
     gen: LabelledGeneric.Aux[V, H],
     tmrT: Lazy[ToMapRec[T]],
     tmrH: Lazy[ToMapRec[H]]
    ): ToMapRec[FieldType[K, V] :: T] = (hlist: FieldType[K, V] :: T) => {
      tmrT.value(hlist.tail) + (wit.value.name -> tmrH.value(gen.to(hlist.head)))
    }

    //Inr(Inr(Inl(MyGoat(Jumpy,Some(Playful baby goat)))))
    implicit def hconsToMapRecCoproducts[K <: Symbol, V, H, T <: Coproduct]
    (implicit
     wit: Witness.Aux[K],
     tmrT: Lazy[ToMapRec[T]],
     tmrH: Lazy[ToMapRec[H]]
    ): ToMapRec[FieldType[K, H] :+: T] = (hlist: FieldType[K, H] :+: T) => {
      println("FOUND A COPRODUCT")
      hlist match {
        case Inl(h) =>
          println("head", h)
          tmrH.value(h)
        case Inr(t) =>
          println("tail", t)
          tmrT.value(t)
      }
    }

    implicit def hconsToMapRecOption[K <: Symbol, V, H <: HList, T <: HList]
    (implicit
     wit: Witness.Aux[K],
     gen: LabelledGeneric.Aux[V, H],
     tmrT: Lazy[ToMapRec[T]],
     tmrH: Lazy[ToMapRec[H]]
    ): ToMapRec[FieldType[K, Option[V]] :: T] = (hlist: FieldType[K, Option[V]] :: T) => {
      tmrT.value(hlist.tail) + (wit.value.name -> hlist.head.map(value => tmrH.value(gen.to(value))))
    }

    implicit def hconsToMapRecSeq[K <: Symbol, V, H <: HList, T <: HList]
    (implicit
     wit: Witness.Aux[K],
     gen: LabelledGeneric.Aux[V, H],
     tmrT: Lazy[ToMapRec[T]],
     tmrH: Lazy[ToMapRec[H]]
    ): ToMapRec[FieldType[K, Seq[V]] :: T] = (hlist: FieldType[K, Seq[V]] :: T) => {
      tmrT.value(hlist.tail) + (wit.value.name -> hlist.head.map(value => tmrH.value(gen.to(value))))
    }
  }

  implicit class ToMapRecOps[A](val a: A) extends AnyVal {
    def toMap[L <: HList]
    (implicit
     gen: LabelledGeneric.Aux[A, L],
     tmr: Lazy[ToMapRec[L]]
    ): Map[String, Any] = tmr.value(gen.to(a))
  }

  implicit def animalLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Animal.Immutable, L]): LabelledGeneric.Aux[Animal, L] =
    helperLabelledGeneric[Animal, Animal.Immutable, L]
  implicit def importantDatesLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[ImportantDates.Immutable, L]): LabelledGeneric.Aux[ImportantDates, L] =
    helperLabelledGeneric[ImportantDates, ImportantDates.Immutable, L]
  implicit def recordLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Record.Immutable, L]): LabelledGeneric.Aux[Record, L] =
    helperLabelledGeneric[Record, Record.Immutable, L]
  implicit def flagsLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Flags.Immutable, L]): LabelledGeneric.Aux[Flags, L] =
    helperLabelledGeneric[Flags, Flags.Immutable, L]
  implicit def furLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Fur.Immutable, L]): LabelledGeneric.Aux[Fur, L] =
    helperLabelledGeneric[Fur, Fur.Immutable, L]
  implicit def personLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Person.Immutable, L]): LabelledGeneric.Aux[Person, L] =
    helperLabelledGeneric[Person, Person.Immutable, L]
  implicit def keywordLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Keyword.Immutable, L]): LabelledGeneric.Aux[Keyword, L] =
    helperLabelledGeneric[Keyword, Keyword.Immutable, L]

  implicit def dogLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Dog.Immutable, L]): LabelledGeneric.Aux[Dog, L] =
    helperLabelledGeneric[Dog, Dog.Immutable, L]
  implicit def catLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Cat.Immutable, L]): LabelledGeneric.Aux[Cat, L] =
    helperLabelledGeneric[Cat, Cat.Immutable, L]
  implicit def goatLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Goat.Immutable, L]): LabelledGeneric.Aux[Goat, L] =
    helperLabelledGeneric[Goat, Goat.Immutable, L]

  implicit def myCatLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[MyCat, L]): LabelledGeneric.Aux[MyCat, L] =
    helperLabelledGeneric[MyCat, MyCat, L]
  implicit def myGoatLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Goat.Immutable, L]): LabelledGeneric.Aux[Goat, L] =
    helperLabelledGeneric[Goat, Goat.Immutable, L]

  implicit def myAnimalDataGeneric[V, L <: HList]
  (implicit genGoat: LabelledGeneric.Aux[MyGoat, L]
  ): LabelledGeneric.Aux[MyAnimalData, L] =
    new LabelledGeneric[MyAnimalData] {
      override type Repr = L

      override def to(t: Models.MyAnimalData): Repr = t match {
        case goat: MyGoat => genGoat.to(goat)
      }

      override def from(r: Repr): Models.MyAnimalData = genGoat.from(r)
    }

  implicit def animalDataGeneric[V, L <: HList]
  (implicit genCat: LabelledGeneric.Aux[CatAlias, L]
  ): LabelledGeneric.Aux[AnimalData, L] =
    new LabelledGeneric[AnimalData] {
      override type Repr = L

      override def to(ad: AnimalData): Repr = ad match {
        case AnimalData.Cat(cat) => genCat.to(cat)
      }

      override def from(r: Repr): AnimalData = AnimalData.Cat(genCat.from(r))
    }

  def helperLabelledGeneric[A, I <: A, L <: HList](implicit gen: LabelledGeneric.Aux[I, L]): LabelledGeneric.Aux[A, L] =
    new LabelledGeneric[A] {
      override type Repr = L
      override def to(t: A): Repr = gen.to(t.asInstanceOf[I])
      override def from(r: Repr): A = gen.from(r)
    }
}