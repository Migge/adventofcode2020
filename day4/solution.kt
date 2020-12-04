// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList()
    val passports = parseMultiline(input)
    
    println("valid passports pass 1: ${validPassports1(passports)}")
    println("valid passports pass 2: ${validPassports2(passports)}")
}

fun validPassports1(passports: List<String>) = passports.filter {
    it.contains("byr:") &&
    it.contains("iyr:") &&
    it.contains("eyr:") &&
    it.contains("hgt:") &&
    it.contains("hcl:") &&
    it.contains("ecl:") &&
    it.contains("pid:")
}.count()

fun validPassports2(passports: List<String>) = passports.filter {
    validate_byr(regex_byr.find(it)?.groupValues?.get(1)) &&
    validate_iyr(regex_iyr.find(it)?.groupValues?.get(1)) &&
    validate_eyr(regex_eyr.find(it)?.groupValues?.get(1)) &&
    validate_hgt(regex_hgt.find(it)?.groupValues?.get(1)) &&
    validate_hcl(regex_hcl.find(it)?.groupValues?.get(1)) &&
    validate_ecl(regex_ecl.find(it)?.groupValues?.get(1)) &&
    validate_pid(regex_pid.find(it)?.groupValues?.get(1))
}.count()

val regex_byr = """byr:(\d+)""".toRegex()
val regex_iyr = """iyr:(\d+)""".toRegex()
val regex_eyr = """eyr:(\d+)""".toRegex()
val regex_hgt = """hgt:(\d+in|\d+cm)""".toRegex()
val regex_hcl = """hcl:(#\w+)""".toRegex()
val regex_ecl = """ecl:(\w+)""".toRegex()
val regex_pid = """pid:(\d+)""".toRegex()

fun validate_byr(str: String?) = str?.toLong()?.let { it >= 1920 && it <= 2002 } ?: false

fun validate_iyr(str: String?) = str?.toInt()?.let { it >= 2010 && it <= 2020 } ?: false

fun validate_eyr(str: String?) = str?.toInt()?.let { it >= 2020 && it <= 2030 } ?: false

fun validate_hgt(str: String?) = when {
    str?.contains("cm") == true -> hgt(str) >= 150 && hgt(str) <= 193
    str?.contains("in") == true -> hgt(str) >= 59 && hgt(str) <= 76
    else -> false
}

fun hgt(str: String) = str.dropLast(2).toInt()

fun validate_hcl(str: String?) = """#[0-9a-f]{6}""".toRegex().matches(str?:"")

fun validate_ecl(str: String?) = """amb|blu|brn|gry|grn|hzl|oth""".toRegex().matches(str?:"")

fun validate_pid(str: String?) = """[0-9]{9}""".toRegex().matches(str?:"")

fun parseMultiline(input: List<String>): List<String> {
    var str = ""
    var multiline = mutableListOf<String>()
   
    input.forEach {
        if (it.trim().isEmpty()) {
            multiline.add(str)
            str = ""
        } else {
            str += " $it"
        }
    }
    if (str.isNotEmpty()) multiline.add(str)
    return multiline
}
