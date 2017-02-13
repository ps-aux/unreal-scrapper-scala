package pro.absolutne.data

import org.springframework.stereotype.Service
import pro.absolutne.data.model.Record

import scala.collection.mutable

@Service
class RecordSink {

  private var records = mutable.MutableList[Record]()

  def sink(r: Record): Unit = {
    records += r
  }

}
