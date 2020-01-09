package pl.lodz.p.it.isdp;

/**
 * Rozwiązanie problemu sortowania tablicy (zawiera błędy które należy usunąć z
 * zastosowanie nadzorowanego wykonania programu (debug)). Diagnostykę należy
 * przeprowadzić z zastosowaniem programu narzędziowego debuggera jdb
 */


public class Start {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak podanej liczby całkowitej jako argumentu wywołania");
            System.exit(1);
        }
        try {
            SortTabNumbers sortExample = new SortTabNumbers(Integer.parseInt(args[0].trim()));

            System.out.println("Przed sortowaniem: " + sortExample); //niejawne wywołanie metody sortExample.toString()

            sortExample.sort();

            if (sortExample.checkMinOrderSort()) {
                System.out.println("Po sortowaniu: " + sortExample); //niejawne wywołanie metody sortExample.toString()
                Integer generatedKey = JDBCConnection.saveSortTable(args[1].trim(), args[2].trim(), args[3].trim(), sortExample);
                if(generatedKey.equals(-1)) {
                    System.out.println("Nie udało się zapisać tablicy"); 
                } else {
                System.out.println("Tablica została zapisana w bazie pod numerem id: " + generatedKey);
                }
            }

        } catch (NumberFormatException nfe) {
            System.out.println("Podany argument nie jest liczbą");
            System.exit(2);
        } catch (Throwable ex) {
            System.out.println("Zakończenie programu w wyniku zgłoszenia wyjątku typu " + ex.getClass().getName());
            System.exit(3);
        }

    }
}
