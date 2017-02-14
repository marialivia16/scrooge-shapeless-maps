import com.example.thrift.{Animal, AnimalData}
import org.scalatest._
import SampleData._
import SampleOutput._
import ClassToMap._
import Models.MyAnimalData.MyGoatData

class Specs extends FlatSpec with Matchers {
    it should "convert case class goat data to nested maps" in {
      val mapConversionFromGoat = caseClassGoat.data.asInstanceOf[MyGoatData].toMap
      mapConversionFromGoat should equal(mapGoatData)
    }

  it should "convert thrift class cat data to nested maps" in {
    val mapConversionFromCat = thriftCat.data.asInstanceOf[AnimalData.Cat].toMap
    mapConversionFromCat should equal(mapCatData)
  }
}
