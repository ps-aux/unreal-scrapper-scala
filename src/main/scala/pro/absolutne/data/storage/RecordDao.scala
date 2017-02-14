package pro.absolutne.data.storage


import javax.sql.DataSource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import pro.absolutne.data.model.RealEstateOffer

@Repository
class RecordDao @Autowired()(ds: DataSource) {

  val jdbc = new JdbcTemplate(ds)

  def saveRecord(r: RealEstateOffer): Unit = {
    val q = "INSERT INTO record" +
      " (title,url,area, price, type, location, date, scrapSource, scrapDate)" +
      " VALUES (?,?,?,?,?,?,?,?,?)"
    val area: java.lang.Double = r.area match {
      case Some(x) => x
      case _ => null
    }

    val price: java.lang.Double = r.price match {
      case Some(x) => x
      case _ => null
    }

    jdbc.update(q, r.title, r.url, area, price, r.reType,
      r.location, r.date, r.scrapSource, r.scrapDate)
  }

}
