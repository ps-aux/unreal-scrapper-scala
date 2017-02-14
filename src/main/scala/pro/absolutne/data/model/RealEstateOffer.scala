package pro.absolutne.data.model

import java.util.Date

class RealEstateOffer(val title: String,
                      val url: String,
                      val area: Option[Double],
                      val price: Option[Double],
                      val reType: String,
                      val location: String,
                      val date: Date,
                      val scrapSource: String,
                      val scrapDate: Date) {


  override def toString = s"Record($price EUR, $location, $reType, $area m2, $title, $url, $scrapSource, $scrapDate)"

}
