

val r = "(\\d\\s.*byt|.*garz.*)\\s(.*)\\s(predaj)".r

val in = "dvojgarzonka Miloslavov predaj"

r.findAllIn(in).group(1)