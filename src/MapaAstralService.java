import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MapaAstralService {

    private static final Map<String, List<MonthDay>> signsMap;
    private static final Map<String, List<LocalTime>> ascendantsMap;

    static {
        signsMap = new HashMap<>();
        signsMap.put("ARIES", List.of(MonthDay.of(3, 21), MonthDay.of(4, 20)));
        signsMap.put("TOURO", List.of(MonthDay.of(4, 21), MonthDay.of(5, 20)));
        signsMap.put("GEMEOS", List.of(MonthDay.of(5, 21), MonthDay.of(6, 20)));
        signsMap.put("CANCER", List.of(MonthDay.of(6, 21), MonthDay.of(7, 22)));
        signsMap.put("LEAO", List.of(MonthDay.of(7, 23), MonthDay.of(8, 22)));
        signsMap.put("VIRGEM", List.of(MonthDay.of(8, 23), MonthDay.of(9, 22)));
        signsMap.put("LIBRA", List.of(MonthDay.of(9, 23), MonthDay.of(10, 22)));
        signsMap.put("ESCORPIAO", List.of(MonthDay.of(10, 23), MonthDay.of(11, 21)));
        signsMap.put("SAGITÁRIO", List.of(MonthDay.of(11, 22), MonthDay.of(12, 21)));
        signsMap.put("CAPRICÓRNIO", List.of(MonthDay.of(12, 22), MonthDay.of(1, 20)));
        signsMap.put("AQUÁRIO", List.of(MonthDay.of(1, 21), MonthDay.of(2, 18)));
        signsMap.put("PEIXES", List.of(MonthDay.of(2, 19), MonthDay.of(3, 20)));

        ascendantsMap = new HashMap<>();
        ascendantsMap.put("ARIES", List.of(LocalTime.of(0, 0), LocalTime.of(1, 59)));
        ascendantsMap.put("TOURO", List.of(LocalTime.of(2, 0), LocalTime.of(3, 59)));
        ascendantsMap.put("GEMEOS", List.of(LocalTime.of(4, 0), LocalTime.of(5, 59)));
        ascendantsMap.put("CANCER", List.of(LocalTime.of(6, 0), LocalTime.of(7, 59)));
        ascendantsMap.put("LEAO", List.of(LocalTime.of(8, 0), LocalTime.of(9, 59)));
        ascendantsMap.put("VIRGEM", List.of(LocalTime.of(10, 0), LocalTime.of(11, 59)));
        ascendantsMap.put("LIBRA", List.of(LocalTime.of(12, 0), LocalTime.of(13, 59)));
        ascendantsMap.put("ESCORPIAO", List.of(LocalTime.of(14, 0), LocalTime.of(15, 59)));
        ascendantsMap.put("SAGITÁRIO", List.of(LocalTime.of(16, 0), LocalTime.of(17, 59)));
        ascendantsMap.put("CAPRICÓRNIO", List.of(LocalTime.of(18, 0), LocalTime.of(19, 59)));
        ascendantsMap.put("AQUÁRIO", List.of(LocalTime.of(20, 0), LocalTime.of(21, 59)));
        ascendantsMap.put("PEIXES", List.of(LocalTime.of(22, 0), LocalTime.of(23, 59)));

    }

    public String getLunarSign(LocalTime birthTime, String city) {

        if ("Recife".equalsIgnoreCase(city) && birthTime.isAfter(LocalTime.NOON))
            return "Casimiro";

        if ("Cuiaba".equalsIgnoreCase(city) && birthTime.isBefore(LocalTime.NOON))
            return "Odin";

        if ("Sao Paulo".equalsIgnoreCase(city))
            return "Gandalf";

        return "Dinossauro";
    }

    public String getAscendant(LocalDateTime birthDateTime) {
        var ascendants = getAscendantMap(getSign(birthDateTime.toLocalDate()));

        for (String asc : ascendants.keySet()) {
            if (isAsc(birthDateTime.toLocalTime(), asc))
                return asc;
        }
        return "ASCENDENTE NÃO ENCONTRADO!";
    }

    private Map<String, List<LocalTime>> getAscendantMap(String sign) {
        return ascendantsMap;
    }

    private boolean isAsc(LocalTime birthHour, String asc) {
        var hours = ascendantsMap.get(asc);
        return isBetween(
                birthHour,
                hours.get(0),
                hours.get(1)
        );
    }

    public String getSign(LocalDate birthDate) {

        for (String sign : signsMap.keySet()) {
            if (isSign(birthDate, sign))
                return sign;
        }
        return "SIGNO NÃO ENCONTRADO!";
    }

    private boolean isSign(LocalDate birthDate, String sign) {
        var dates = signsMap.get(sign);
        return isBetween(
                MonthDay.from(birthDate),
                dates.get(0),
                dates.get(1)
        );
    }

    private boolean isBetween(Comparable test, Comparable from, Comparable to) {
        return !(test.compareTo(from) < 0 || test.compareTo(to) > 0);
    }

    public Integer getAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now())
                .getYears();
    }

    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public boolean isLeapYear(Year year) {
        return year.isLeap();
    }

    public ZoneOffset getZoneOffset(String city) {

        ZoneId zoneId = getZoneId(city);
        return ZonedDateTime.now(zoneId).getOffset();
    }

    private static ZoneId getZoneId(String city) {

        var zoneId = ZoneId.systemDefault();

        if (Objects.isNull(city) || city.isBlank())
            return zoneId;
        var formattedCity = city.strip().replace(" ", "_").toLowerCase();

        for (String zone : ZoneId.getAvailableZoneIds()) {
            if (zone.toLowerCase().contains(formattedCity)) {
                zoneId = ZoneId.of(zone);
                break;
            }
        }
        return zoneId;
    }

}
