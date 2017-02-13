package pro.absolutne.data.model

class Record(val title: String,
             val url: String,
             val area: Double,
             val price: Double,
             val location: String,
             val date: String) {


  override def toString = s"Record($price EUR, $location, $area m2, $title, $url)"

  def toLine = s"$price EUR, $location, $area m2, $date, $title, $url"
}
