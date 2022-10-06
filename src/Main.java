import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;

public class Main {

    private static final MapaAstralService service = new MapaAstralService();

    public static void main(String[] args) {

//        Enunciado
//        Você foi contratado para desenvolver uma aplicação que monta o mapa astral dos funcionários de uma empresa.
//        Com base na data, hora e local de nascimento, escreva métodos que printe na tela:
//
//        A idade da pessoa
//        Data de nascimento formatada (dd/MM/yyyy HH:mm)
//        ZoneOffset do local de nascimento (ex: +03:00, -01:00)
//        Se o ano de nascimento é bissexto
//        O signo da pessoa. (Implementar pelo menos dois signos.)
//        O ascedente. (Implementar pelo menos para um signo)
//        Signo lunar, com as seguites regras:
//        Se for nascido em Recife e depois das 12h00, retornar Casimiro.
//                Se for nascido em Cuiaba e antes das 12h00, retornar Odin
//        Se for nascido em São Paulo (não importa o horário), retornar Gandalf
//        Se não atender nenhuma das condições acima, retornar Dinossauro.


        System.out.println("Idade: " + service.getAge(LocalDate.of(2001, 6, 22)));
        System.out.println("Data Formatada: " + service.formatDateTime(LocalDateTime.now()));
        System.out.println("Offset local: " + service.getZoneOffset("japan"));
        System.out.println("É ano bissexto: " + service.isLeapYear(Year.of(1992)));
        System.out.println("Signo: " + service.getSign(LocalDate.now()));
        System.out.println("Ascendente: " + service.getAscendant(LocalDateTime.now()));
        System.out.println("Signo Lunar: " + service.getLunarSign(LocalTime.of(12, 15), "recife"));
    }


}