# Kotlin_2_Judgement_Day
Application for Ubiquitous Computing

Aspekty aplikacji

Import:

1) Import odbywa się w sposób podany w wymaganiach: Łączenie 3 stringów, w tym 1 możliwy do zmiany w settingsach, 2. w oknie projektu.
2) Można zaimportować dowolny plik z internetu, z dowolną nazwą, również taką, która się powtarza. XML także może się powtarzać - dzięki temu można mieć np. 2 projekty nr 615, można im nawet nadać taką samą nazwę.
3) Import odbywa się asynchronicznie - może trwać długo (choćby przez pobieranie obrazów), dlatego w głównym oknie aplikacji jest pasek progresu pobierania.
4) W trakcie pobierania pliku z sieci nie można rozpocząć pobierania 2. pliku (dzięki temu unikam m.in. problemów współbieżnego dostępu 2 procesów do bazy danych, a także 2-krotnego pobrania tego samego obrazka, poza tym pasek postępu jest jednoznaczny a apliakcja się nie wiesza), próba pobrania w trakcie pobierania innego pliku skutkuje komunikatem o niemożności wykonania tej operacji. Można natomiast przeglądać projekty, eksportować plik etc.
5) Import jest zamknięty w bloku try-catch, niepowodzenie pobierania (zapewne 404 albo brak serwera) powoduje odpowiedni komunikat o błędzie na stronie głównej. Pobieranie jest asynchroniczne, więc mogę nie otrzymać informacji o błędzie natychmiast.
6) Image jest pobierany wtw, gdy nie ma go w tablicy Codes.
7) Jeśli jakiegoś itema nie ma w tablicy parts, to wstawiam go do tej tablicy z odpowiednim kodem - dzięki temu przy eksporcie brakujących elementów nie zostanie on pominięty. Poza tym dodaję mu polską nazwę - dzięki temu mogę później pokazać, że polskie nazwy faktycznie są preferowane nad angielskie w widoku projektu.

Widok projektu:

1) W widoku projektu poszczególne części są posortowane w pierwszej kolejności po tym, czy są odznaczone, czy nie (czy QuantityInStore==QuantityInSet), w 2. po parametrze podanym w settings/Ordering preferenece. Można tam podać Color (sort po kolorze) albo Item Name (sort po nazwie itema). Co istotne, sort odbywa się po angielskiej nazwie itema - dzięki temu mogę pokazać, że item dodany po angielsku (Name - unknown, NamePl - nieznany) jest sortowany po angielsku (będzie na końcu listy - "unknown" jest alfabetycznie po "nieznany"), ale nazwa jest pokazywana poprawnie po polsku.
2) Pokaz pojedynczego elementu jest taki, jak w specyfikacji. - guziki +/-, image po lewej, opis - nazwa, id, kolor po prawej. Czasem kolor nie jest zgodny z obrazkiem - jeśli pobrałem obrazek bez koloru.
3) Część odznaczona jest zaznaczana na zielono, wędruje na koniec listy dopiero po powrocie na tą listę (np. powrót na ekran główny -> Project View)
4) Projekt można zarchiwizować w widoku projektu. Po archiwizacji i wyświetleniu widoku projektu można go "odarchiwizować".

Eksport:

1) W momencie przejścia na widok projektu jest zadawane zapytanie o dostęp do "External Storage": pojawia się ono zawsze dopóki ktoś odmawia (chyba, że ktoś trwale odmówi), dzięki czemu można przeanalizować skutki odmowy dostępu do zewnętrznych plików, a później działanie eksportu z dostępem do plików.
2) Plik jest zapisywany w lokacji zapisanej w settings: "file directory". Tworzone są brakujące foldery (mkdirs), natomiast przy braku możliwości stworzenia tychże (albo samego pliku - np. specjalne znaki, brak zgody na dostęp, etc.) pojawia się na ekranie stosowny komunikat w stosownym, czerwonym kolorze.
4) Plik jest zapisywany albo jako nazwa projektu, albo jako nazwa projektu+unikalne id - to dla osób, które z niezrozumiałych przyczyn chcą trzymać 2 projekty o tej samej nazwie. Wybór 1 z tych opcji zachodzi w settings/Save format. Oczywiście jeśli projekt jest już wyeksportowany pod tą samą nazwą, to jest nadpisywany - to logiczne, bo stary plik najwyraźniej jest już nieaktualny.
5) Plik xml może zawierać dodatowe pole Condition w zależności od tego, co ustawiono w setting/Condition. Można je pominąć(omit) albo użyć(used, new).

Widok główny
1) Na ekran główny składają się buttony do ustawień/nowego projektu, lista projektów i pasek opisu+opis wczytywania projektu.
2) Na pasku projektów są przyciski prowadzące do poszczególnych projektów. Jeśli nowy projekt został w całości zapisany, to lista jest dynamicznie aktualizowana.
3) Na dole ekranu jest pasek wczytywania i informacja o liczbie wczytanych itemów. Po wczytaniu wszystkich pojawi się na dole ekranu informacja o nieznalezionych nazwach w parts i obrazkach.
4) Projekty są sortowane po czasie ostatniego dostępu.
