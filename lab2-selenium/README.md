### Scenariusz testowy dla `XkomTest`

#### Cel testu:
Zweryfikowanie poprawności działania procesu dodawania produktu do koszyka, przeglądania koszyka i usuwania produktu na stronie x-kom.pl.

---

#### Kroki testowe:

1. **Otwórz stronę główną x-kom.pl**
    - **Sprawdź**: Tytuł strony to „x-kom.pl - Sklep komputerowy”.

2. **Sprawdź obecność dymka czatu**
    - **Oczekiwany rezultat**: Dymek czatu z atrybutem `aria-label='Czat'` jest widoczny na stronie.

3. **Wyszukaj produkt "smartfon"**
    - **Kroki**:
        - Znajdź pole wyszukiwania oznaczone jako „Czego szukasz?”.
        - Wpisz „smartfon” i wciśnij Enter.
    - **Oczekiwany rezultat**: Przekierowanie na stronę kategorii „Smartfony i telefony”.

4. **Sprawdź tytuł strony wyników wyszukiwania**
    - **Oczekiwany rezultat**: Tytuł strony zmienia się na „Smartfony i telefony - Sklep komputerowy - x-kom.pl”.

5. **Dodaj pierwszy dostępny produkt do koszyka**
    - **Kroki**:
        - Znajdź kartę produktu (pierwszy wynik w wynikach wyszukiwania).
        - Pobierz cenę produktu.
        - Kliknij przycisk „Dodaj do koszyka”.
    - **Oczekiwany rezultat**: Produkt zostaje dodany do koszyka, a liczba produktów w koszyku wynosi „1”.

6. **Sprawdź, czy wyświetla się modal „Dodano do koszyka”**
    - **Oczekiwany rezultat**: Modal z informacją „Dodano do koszyka” jest widoczny.

7. **Zamknij modal**
    - **Kroki**:
        - Kliknij przycisk „Zamknij” na modalu.
    - **Oczekiwany rezultat**: Modal zamyka się.

8. **Przejdź do koszyka**
    - **Kroki**:
        - Kliknij na ikonę koszyka lub wybierz „Koszyk” z menu.
    - **Oczekiwany rezultat**: Przekierowanie do strony koszyka, adres URL zawiera „koszyk”.

9. **Sprawdź zgodność ceny produktu w koszyku**
    - **Oczekiwany rezultat**: Cena produktu w koszyku jest taka sama, jak cena produktu dodanego w kroku 5.

10. **Usuń produkt z koszyka**
    - **Kroki**:
        - Kliknij przycisk „Usuń z koszyka”.
    - **Oczekiwany rezultat**: Produkt zostaje usunięty, a komunikat „Koszyk jest pusty” jest widoczny na stronie.

---

### Scenariusz testowy dla `FilmwebTest`

#### Cel testu:
Zweryfikowanie, czy użytkownik może wyszukać film na Filmwebie i uzyskać szczegółowe informacje o nim.

---

#### Kroki testowe:

1. **Otwórz stronę główną Filmweb**
   - **Sprawdź**: Tytuł strony to „Filmweb - filmy takie jak Ty!”.

2. **Wyszukaj film „Skazani na Shawshank”**
   - **Kroki**:
      - Znajdź pole wyszukiwania oznaczone jako `input[name='q']`.
      - Wpisz „Skazani na Shawshank” i wciśnij Enter.
   - **Oczekiwany rezultat**: Wyniki wyszukiwania zawierają film „Skazani na Shawshank”.

3. **Kliknij w film „Skazani na Shawshank”**
   - **Kroki**:
      - Znajdź element zawierający odnośnik do strony filmu („Skazani na Shawshank”) i kliknij.
   - **Oczekiwany rezultat**: Przekierowanie do strony filmu.

4. **Sprawdź tytuł strony filmu**
   - **Oczekiwany rezultat**: Tytuł strony zawiera „Skazani na Shawshank (1994) - Filmweb”.

5. **Sprawdź obecność trailera filmu**
   - **Oczekiwany rezultat**: Przycisk „Zwiastun” jest widoczny na stronie, co oznacza dostępność trailera.

6. **Sprawdź ocenę filmu**
   - **Oczekiwany rezultat**: Ocena filmu jest widoczna i znajduje się w elemencie o klasie `filmRating__rateValue`.

7. **Sprawdź rok produkcji filmu**
   - **Oczekiwany rezultat**: Rok produkcji wynosi „1994” i jest widoczny w elemencie o klasie `filmCoverSection__year`.

8. **Sprawdź czas trwania filmu**
   - **Oczekiwany rezultat**: Czas trwania filmu wynosi „2h 22m” i jest widoczny w elemencie o klasie `filmCoverSection__duration`.

9. **Sprawdź obecność reżysera filmu**
   - **Oczekiwany rezultat**: Reżyser Frank Darabont jest wymieniony i widoczny na stronie (odnośnik do jego strony istnieje).

10. **Sprawdź gatunek filmu**
   - **Oczekiwany rezultat**: Gatunek „Dramat” jest widoczny na stronie w elemencie `span[itemprop*='genre']`.

---

### Scenariusz testowy dla: `BMICalcTest`

#### Cel testu:
Przeprowadzenie testu end-to-end aplikacji kalkulatora BMI na stronie Calculator.net, aby upewnić się, że wyniki są poprawnie obliczane i wyświetlane, a kluczowe elementy interfejsu działają zgodnie z oczekiwaniami.

#### Scenariusz:

1. **Otwórz stronę kalkulatora BMI**
   - **Wejdź na stronę**: https://www.calculator.net/bmi-calculator.html
   - **Kryterium sukcesu**: Tytuł strony to "BMI Calculator".

2. **Wprowadź dane użytkownika**
   - **Wzrost**: W polu `Height` wpisz "170" cm.
   - **Waga**: W polu `Weight` wpisz "70" kg.
   - **Kryterium sukcesu**: Pola są wypełnione poprawnie.

3. **Oblicz BMI**
   - **Działanie**: Kliknij przycisk "Calculate".
   - **Kryterium sukcesu**: Wynik BMI jest wyświetlany na stronie i wynosi "24.2".

4. **Sprawdź interpretację BMI**
   - **Działanie**: Sprawdź tekst interpretacji wyniku (np. "Normal").
   - **Kryterium sukcesu**: Interpretacja wyniku to "Normal".

5. **Sprawdź, czy wynik i dane są zachowane po odświeżeniu strony**
   - **Działanie**: Odśwież stronę.
   - **Kryterium sukcesu**: Wartości w polach `Height` i `Weight` oraz wynik BMI pozostają takie same (170, 70, 24.2).

6. **Sprawdź obecność logo strony**
   - **Działanie**: Znajdź element reprezentujący logo "Calculator.net".
   - **Kryterium sukcesu**: Logo jest widoczne na stronie.

7. **Sprawdź obecność linku do strony głównej**
   - **Działanie**: Znajdź link do strony głównej (`href="/"`).
   - **Kryterium sukcesu**: Link jest widoczny na stronie i prowadzi do strony głównej kalkulatora.

#### Oczekiwany wynik:
Po wykonaniu testu powinny być spełnione wszystkie kryteria sukcesu, co potwierdzi prawidłowe działanie kalkulatora BMI, wyświetlanie wyników i interpretacji oraz obecność kluczowych elementów na stronie.
