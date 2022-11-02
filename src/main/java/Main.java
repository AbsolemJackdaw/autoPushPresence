import java.time.format.DateTimeFormatter;

public class Main {

//    static LocalDate[] dates = new LocalDate[]{
//            LocalDate.of(2022, 9, 22),
//            LocalDate.of(2022, 9, 29),
//            LocalDate.of(2022, 10, 6),
//            LocalDate.of(2022, 10, 27)
//    };

    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
//        String date = LocalDate.now().format(dtf);
//        for (LocalDate date : dates) {
//            new EditJson("Axel", "Cornelis", date.format(dtf));
//        }
    }
}
