import com.example.thrift.Animal
import org.scalatest._
import SampleData._
import SampleOutput._
import ClassToMap._

class Specs extends FlatSpec with Matchers {
    it should "should convert case class goat to nested maps" in {
      val mapConversionFromGoat = caseClassGoat.toMap
      mapConversionFromGoat should equal(mapGoat)
    }

  it should "should convert thrift class cat to nested maps" in {
    val mapConversionFromCat = thriftCat.asInstanceOf[Animal.Immutable].toMap
    mapConversionFromCat should equal(mapCat)
  }
}
