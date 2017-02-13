val takeDigits = "[\\d(\\d )]+".r

val r = takeDigits.findFirstIn("cena dohodou").get

s"---$r---"
