import magnolia._

import scala.language.experimental.macros

trait ToMapMagnolia[T] {
  def apply(arg: T) : Any
}

object ToMapMagnolia {
  type Typeclass[T] = ToMapMagnolia[T]

  def combine[T](caseClass: CaseClass[Typeclass, T]): Typeclass[T] = (instance: T) =>
    caseClass.parameters.map(param => param.label -> {param.typeclass(param.dereference(instance))}).toMap

  def dispatch[T](sealedTrait: SealedTrait[Typeclass, T]): Typeclass[T] = (instance: T) =>
    sealedTrait.dispatch(instance)(subType => subType.typeclass.apply(subType.cast(instance)))

  implicit def baseCase[T <: AnyVal]: ToMapMagnolia[T] = (x: T) => x
  implicit def stringCase: ToMapMagnolia[String] = x => x

  implicit def optionCase[T](implicit toMap: Typeclass[T]): Typeclass[Option[T]] = _.map(toMap.apply)
  implicit def seqCase[T](implicit toMap: Typeclass[T]): Typeclass[Seq[T]] = _.map(toMap.apply)

  implicit def gen[T]: Typeclass[T] = macro Magnolia.gen[T]

  implicit class ToMapOps[A](a: A) {
    def toMapMagnolia(implicit tm: ToMapMagnolia[A]): Any = tm(a)
  }
}