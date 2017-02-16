import java.text.SimpleDateFormat

val r = "(\\d\\s.*byt|.*garz.*)\\s(.*)\\s(predaj)".r

val in = "ee"

r.findAllIn(in).hasNext

val d = "9.2.2017"

val f = new SimpleDateFormat("dd.MM.yyyy")

f.parse(d)