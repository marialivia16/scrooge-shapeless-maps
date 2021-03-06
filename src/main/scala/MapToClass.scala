import shapeless.labelled._
import shapeless._

object MapToClass {
  // FROM MAP TO CASE CLASS (http://stackoverflow.com/a/31641779)
  trait FromMap[L <: HList] {
    def apply(m: Map[String, Any]): Option[L]
  }

  trait LowPriorityFromMap {
    implicit def hconsFromMap1[K <: Symbol, V, T <: HList]
    (implicit
     witness: Witness.Aux[K],
     typeable: Typeable[V],
     fromMapT: Lazy[FromMap[T]]
    ): FromMap[FieldType[K, V] :: T] = (m: Map[String, Any]) => for {
      v <- m.get(witness.value.name)
      h <- typeable.cast(v)
      t <- fromMapT.value(m)
    } yield field[K](h) :: t
  }

  object FromMap extends LowPriorityFromMap {
    implicit val hnilFromMap: FromMap[HNil] = (m: Map[String, Any]) => Some(HNil)

    implicit def hconsFromMap0[K <: Symbol, V, R <: HList, T <: HList]
    (implicit
     witness: Witness.Aux[K],
     gen: LabelledGeneric.Aux[V, R],
     fromMapH: Lazy[FromMap[R]],
     fromMapT: Lazy[FromMap[T]]
    ): FromMap[FieldType[K, V] :: T] = (m: Map[String, Any]) => for {
      v <- m.get(witness.value.name)
      r <- Typeable[Map[String, Any]].cast(v)
      h <- fromMapH.value(r)
      t <- fromMapT.value(m)
    } yield field[K](gen.from(h)) :: t

    implicit def hconsFromMapOption[K <: Symbol, V, R <: HList, T <: HList]
    (implicit
     witness: Witness.Aux[K],
     gen: LabelledGeneric.Aux[V, R],
     fromMapH: Lazy[FromMap[R]],
     fromMapT: Lazy[FromMap[T]]
    ): FromMap[FieldType[K, Option[V]] :: T] = (m: Map[String, Any]) =>
      m(witness.value.name) match {
        case Some(v) =>
          for {
            r <- Typeable[Option[Map[String, Any]]].cast(Some(v))
            h <- r.map(fromMapH.value(_))
            t <- fromMapT.value(m)
          } yield field[K](h.map(gen.from)) :: t
        case None =>
          for {
            t <- fromMapT.value(m)
          } yield field[K](None) :: t
      }

    implicit def hconsFromMapSeq[K <: Symbol, V, R <: HList, T <: HList]
    (implicit
     witness: Witness.Aux[K],
     gen: LabelledGeneric.Aux[V, R],
     tmrH: Lazy[FromMap[R]],
     tmrT: Lazy[FromMap[T]]
    ): FromMap[FieldType[K, Seq[V]] :: T] = (map: Map[String, Any]) => {
      map(witness.value.name) match {
        case list: Seq[_] if list.nonEmpty =>
          for {
            r <- Typeable[Seq[Map[String, Any]]].cast(list)
            h = r.map(elem => tmrH.value(elem).get)
            t <- tmrT.value(map)
          } yield field[K](h.map(repr => gen.from(repr))) :: t
        case _ =>
          for {
            tail <- tmrT.value(map)
          } yield field[K](Seq()) :: tail
      }
    }

  }

  class FromMapOps[A] {
    def from[R <: HList](m: Map[String, Any])
    (implicit
     gen: LabelledGeneric.Aux[A, R],
     fromMap: Lazy[FromMap[R]]): Option[A] = fromMap.value(m).map(gen.from)
  }

  def to[A]: FromMapOps[A] = new FromMapOps[A]
}
