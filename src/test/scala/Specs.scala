import ClassToMap._
import MapToClass.to
import Models.MyAnimal
import SampleData._
import SampleOutput._
import com.example.thrift.Animal
import org.scalatest._

class Specs extends FlatSpec with Matchers {
  it should "convert case class goat to nested maps" in {
    val mapConversionFromGoat = caseClassGoat.toMap
    mapConversionFromGoat should equal(mapGoat)
  }

  it should "convert thrift class cat to nested maps" in {
    val mapConversionFromCat = thriftCat.toMap
    mapConversionFromCat should equal(mapCat)
  }

  it should "convert nested maps back to a goat case class" in {
    val goatFromMap = to[MyAnimal].from(mapGoat)
    goatFromMap should equal(Some(caseClassGoat))
  }

  it should "convert nested maps back to a thrift cat case class" in {
    val catFromMap = to[Animal].from(mapCat)
    catFromMap should equal(Some(thriftCat))
  }
}
