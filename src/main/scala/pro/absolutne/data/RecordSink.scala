package pro.absolutne.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pro.absolutne.data.model.RealEstateOffer
import pro.absolutne.data.storage.RecordDao

@Service
class RecordSink @Autowired()(dao: RecordDao) {

  def sink(r: RealEstateOffer): Unit = {
    dao.saveRecord(r)
  }

}
