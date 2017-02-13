package pro.absolutne.data.storage


import javax.sql.DataSource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import pro.absolutne.data.model.Record

@Repository
class RecordDao @Autowired()(ds: DataSource) {

  val jdbc = new JdbcTemplate(ds)

  def saveRecord(r:Record): Unit = {
    jdbc.execute("INSERT INTO record  values ('haha')")

  }

}
