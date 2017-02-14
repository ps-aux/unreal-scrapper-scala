package pro.absolutne.scrapping

import pro.absolutne.scrapping.Action.Action

/**
  * Created by arkonix on 2/14/17.
  */
class Filter(val action: Action, val flatTypes: Seq[FlatType]) {

  if (flatTypes.contains(FlatType.ALL) && flatTypes.size > 1)
    throw new IllegalArgumentException("There can be only single flat" +
      " type if ALL type is present")

}

object Filter {

  def apply(action: Action, flatType: FlatType*) = new Filter(action, flatType)

}

object Action extends Enumeration {
  type Action = Value
  val BUY = Value("kupa")
  val SELL = Value("predaj")
}


case class FlatType(name: String, roomCount: Int)

object FlatType {

  val GARZONKA = new FlatType("garzonka", 0)

  val ROOM_1 = FlatType("1-izbak", 1)

  val ROOM_2 = FlatType("2-izbak", 2)

  val ROOM_3 = FlatType("3-izbak", 3)

  val ROOM_4 = FlatType("4-izbak", 4)

  val ROOM_5 = FlatType("5-izbak", 5)

  val ALL = FlatType("all", Int.MaxValue)

}
