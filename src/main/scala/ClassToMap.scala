import Models.{MyAnimalData, MyCat, MyGoat}
import com.example.thrift.AnimalDataAliases.CatAlias
import com.example.thrift._
import shapeless._
import shapeless.labelled._

object ClassToMap {
  // FROM CASE CLASS TO MAP (http://stackoverflow.com/a/31638390)
  trait ToMapRec[L] {
    def apply(l: L): Map[String, Any]
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
    ): ToMapRec[FieldType[K, V] :: T] = new ToMapRec[FieldType[K, V] :: T] {
      override def apply(l: FieldType[K, V] :: T): Map[String, Any] = {
        println(l.head)
        tmrT.value(l.tail) + (wit.value.name -> l.head)
      }
    }
  }

  object ToMapRec extends LowPriorityToMapRec {
    implicit val hNilToMapRec: ToMapRec[HNil] = new ToMapRec[HNil] {
      override def apply(l: HNil): Map[String, Any] = Map.empty
    }

    //  Case where we know how to convert the tail of the record, and we know that
    //  the head is something that we can also recursively convert
    implicit def hconsToMapRecDefault[K <: Symbol, V, R <: HList, T <: HList]
    (implicit
     wit: Witness.Aux[K],
     gen: LabelledGeneric.Aux[V, R],
     tmrT: Lazy[ToMapRec[T]],
     tmrH: Lazy[ToMapRec[R]]
    ): ToMapRec[FieldType[K, V] :: T] = new ToMapRec[FieldType[K, V] :: T] {
      override def apply(l: FieldType[K, V] :: T): Map[String, Any] = {
        tmrT.value(l.tail) + (wit.value.name -> tmrH.value(gen.to(l.head)))
      }
    }

    //Inr(Inr(Inl(MyGoat(Jumpy,Some(Playful baby goat)))))
    implicit def hconsToMapRecCoproducts[K <: Symbol, V, H, T <: Coproduct]
    (implicit
     wit: Witness.Aux[K],
     tmrT: Lazy[ToMapRec[T]],
     tmrH: Lazy[ToMapRec[H]]
    ): ToMapRec[FieldType[K, H] :+: T] = new ToMapRec[FieldType[K, H] :+: T] {
      override def apply(l: FieldType[K, H] :+: T): Map[String, Any] = {
        println("FOUND A COPRODUCT")
        l match {
          case Inl(h) =>
            println("head", h)
            tmrH.value(h)
          case Inr(t) =>
            println("tail", t)
            tmrT.value(t)
        }
      }
    }

    implicit def hconsToMapRecOption[K <: Symbol, V, R <: HList, T <: HList]
    (implicit
     wit: Witness.Aux[K],
     gen: LabelledGeneric.Aux[V, R],
     tmrT: Lazy[ToMapRec[T]],
     tmrH: Lazy[ToMapRec[R]]
    ): ToMapRec[FieldType[K, Option[V]] :: T] = new ToMapRec[FieldType[K, Option[V]] :: T] {
      override def apply(l: FieldType[K, Option[V]] :: T): Map[String, Any] = {
        tmrT.value(l.tail) + (wit.value.name -> l.head.map(value => tmrH.value(gen.to(value))))
      }
    }

    implicit def hconsToMapRecSeq[K <: Symbol, V, R <: HList, T <: HList]
    (implicit
     wit: Witness.Aux[K],
     gen: LabelledGeneric.Aux[V, R],
     tmrT: Lazy[ToMapRec[T]],
     tmrH: Lazy[ToMapRec[R]]
    ): ToMapRec[FieldType[K, Seq[V]] :: T] = new ToMapRec[FieldType[K, Seq[V]] :: T] {
      override def apply(l: FieldType[K, Seq[V]] :: T): Map[String, Any] = {
        tmrT.value(l.tail) + (wit.value.name -> l.head.map(value => tmrH.value(gen.to(value))))
      }
    }
  }

  implicit class ToMapRecOps[A](val a: A) extends AnyVal {
    def toMap[L <: HList]
    (implicit
     gen: LabelledGeneric.Aux[A, L],
     tmr: Lazy[ToMapRec[L]]
    ): Map[String, Any] = {
      println(gen.to(a))
      tmr.value(gen.to(a))
    }
  }

  implicit def animalLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Animal.Immutable, L]): LabelledGeneric.Aux[Animal, L] =
    anyLabelledGeneric[Animal, Animal.Immutable, L]
  implicit def importantDatesLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[ImportantDates.Immutable, L]): LabelledGeneric.Aux[ImportantDates, L] =
    anyLabelledGeneric[ImportantDates, ImportantDates.Immutable, L]
  implicit def recordLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Record.Immutable, L]): LabelledGeneric.Aux[Record, L] =
    anyLabelledGeneric[Record, Record.Immutable, L]
  implicit def flagsLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Flags.Immutable, L]): LabelledGeneric.Aux[Flags, L] =
    anyLabelledGeneric[Flags, Flags.Immutable, L]
  implicit def furLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Fur.Immutable, L]): LabelledGeneric.Aux[Fur, L] =
    anyLabelledGeneric[Fur, Fur.Immutable, L]
  implicit def personLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Person.Immutable, L]): LabelledGeneric.Aux[Person, L] =
    anyLabelledGeneric[Person, Person.Immutable, L]
  implicit def keywordLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Keyword.Immutable, L]): LabelledGeneric.Aux[Keyword, L] =
    anyLabelledGeneric[Keyword, Keyword.Immutable, L]

  implicit def dogLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Dog.Immutable, L]): LabelledGeneric.Aux[Dog, L] =
    anyLabelledGeneric[Dog, Dog.Immutable, L]
  implicit def catLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Cat.Immutable, L]): LabelledGeneric.Aux[Cat, L] =
    anyLabelledGeneric[Cat, Cat.Immutable, L]
  implicit def goatLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Goat.Immutable, L]): LabelledGeneric.Aux[Goat, L] =
    anyLabelledGeneric[Goat, Goat.Immutable, L]

  implicit def myCatLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[MyCat, L]): LabelledGeneric.Aux[MyCat, L] =
    anyLabelledGeneric[MyCat, MyCat, L]
  implicit def myGoatLabelledGeneric[L <: HList](implicit gen: LabelledGeneric.Aux[Goat.Immutable, L]): LabelledGeneric.Aux[Goat, L] =
    anyLabelledGeneric[Goat, Goat.Immutable, L]

//  implicit def myAnimalDataGeneric[V, L <: HList]
//  (implicit genGoat: LabelledGeneric.Aux[MyGoat, L]
//  ): LabelledGeneric.Aux[MyAnimalData, L] =
//    new LabelledGeneric[MyAnimalData] {
//      override type Repr = L
//
//      override def to(t: Models.MyAnimalData): Repr = t match {
//        case goat: MyGoat => genGoat.to(goat)
//      }
//
//      override def from(r: Repr): Models.MyAnimalData = genGoat.from(r)
//    }

//  implicit def hisAnimalDataGeneric[L <: HList, G](
//    implicit goatLabelledGeneric : LabelledGeneric[MyGoat, G],
//    catLabelledGeneric: LabelledGeneric[MyCat, G]
//  ): LabelledGeneric.Aux[MyAnimalData, L] = new LabelledGeneric[MyAnimalData] {
//    type Repr = this.type
//
//    def to(t: Models.MyAnimalData): this.Repr = ???
//
//    def from(r: this.Repr): Models.MyAnimalData = ???
//}

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

  def anyLabelledGeneric[A, I <: A, L <: HList](implicit gen: LabelledGeneric.Aux[I, L]): LabelledGeneric.Aux[A, L] =
    new LabelledGeneric[A] {
      override type Repr = L
      override def to(t: A): Repr = gen.to(t.asInstanceOf[I])
      override def from(r: Repr): A = gen.from(r)
    }
}