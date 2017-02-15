import java.text.SimpleDateFormat

val r = "(\\d\\s.*byt|.*garz.*)\\s(.*)\\s(predaj)".r

val in = "dvojgarzonka Miloslavov predaj"

r.findAllIn(in).group(1)

val d = "9.2.2017"

val f = new SimpleDateFormat("dd.MM.yyyy")

f.parse(d)