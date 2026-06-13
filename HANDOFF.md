# AI Agent Handoff: Shop-api

Ten dokument służy jako punkt wejścia i transfer wiedzy dla kolejnych agentów AI pracujących nad projektem. Zawiera kluczowe informacje o architekturze, ograniczeniach i niedawnych poprawkach.

## 1. Kontekst Projektu
**Shop-api** to mini e-commerce REST API stworzone w oparciu o **Spring Boot 3.5.x** oraz **Java 21**, wykorzystujące bazę **H2 in-memory**. Aplikacja zarządza produktami (CRUD + wyszukiwanie) oraz zamówieniami (tworzenie, anulowanie, zarządzanie stanem magazynowym). Projekt powstał jako zadanie zaliczeniowe, którego ścisłe zasady definiuje plik `TODO.md`.

## 2. Architektura i Wzorce
- **Standardowa Warstwowość**: Kod podzielono na pakiety: `controller`, `service`, `repository`, `model`, `exception`, `config`, `event`.
- **Brak DTO dla Odpowiedzi**: Ze względu na specyficzne wymogi zadania ("bez osobnego DTO"), API zwraca encje bazy danych bezpośrednio. Do tworzenia zamówień używane są jednak klasy żądań wejściowych (`OrderRequest`, `OrderItemRequest`).
- **Rozwiązywanie Cykli JSON**: Dwukierunkowa relacja `Order` <-> `OrderItem` posiada adnotację `@JsonIgnore` na polu `order` w klasie `OrderItem`, aby zapobiec `StackOverflowError` przy serializacji.
- **Spring Events**: Aplikacja wykorzystuje zdarzenia Springa. Złożenie zamówienia publikuje `OrderPlacedEvent`, który jest odbierany przez listener logujący i przydzielający próbny numer faktury za pomocą bean'a `InvoiceNumberGenerator`.

## 3. Obecny Stan i Niedawne Poprawki (KRYTYCZNE)
Projekt przeszedł niedawno gruntowny audyt i refaktoryzację. Jeśli będziesz coś zmieniać, **pamiętaj o poniższych ograniczeniach**:
1. **Zablokowana Sygnatura**: Metoda w `OrderService` **musi** mieć postać `Order placeOrder(String customerName, List<OrderItemRequest> items)`. Nie zamieniaj parametrów z powrotem na pojedynczy obiekt `OrderRequest` – prowadzi to do oblania testów sprawdzających.
2. **Walidacja**: Projekt posiada podpięty pakiet `spring-boot-starter-validation`. Encje i DTO posiadają adnotacje `@NotNull`, `@NotBlank`, `@Min`, a błędy wyłapuje `GlobalExceptionHandler` (zwracający ładny JSON z kodem 400).
3. **Scopes**: 
   - `CartService` używa `@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)`, aby zapobiec wstrzykiwaniu singletonowemu. Pomimo że klasa ta jest obecnie nieużywana, **nie usuwaj jej**, bo wymaga jej specyfikacja.
   - `InvoiceNumberGenerator` posiada statyczny licznik, będący obejściem wymogu zastosowania scopu `prototype` z jednoczesnym zachowaniem inkrementacji faktur.
4. **Wyjątki Biznesowe**: `InvalidCategoryException` służy do wyłapywania błędnego parsowania Enum'a `Category` (np. przy podaniu błędnego URL-a w wyszukiwaniu).

## 4. Ograniczenia Środowiskowe (BARDZO WAŻNE DLA AGENTA)
- **Brak lokalnego JDK**: Maszyna użytkownika, na której uruchamiany jest kod, działa w oparciu o środowisko **JRE, a nie JDK**. 
- Oznacza to, że uruchomienie polecenia `mvnw test` lub `mvnw spring-boot:run` **ZAKOŃCZY SIĘ BŁĘDEM** kompilacji (`No compiler is provided in this environment`).
- **Zalecenie**: Nie próbuj weryfikować swoich zmian poprzez uruchamianie budowy mavena w terminalu. Musisz w 100% polegać na analizie statycznej i mieć absolutną pewność co do składni Javy.

## 5. Przydatne Pliki
- `TODO.md` - Główne źródło prawdy o zasadach i punktacji.
- `README.md` - Dokumentacja dla "człowieka", diagram encji ASCII.
- `API.http` - Przeszło 14 w pełni napisanych zapytań REST do weryfikacji manualnej.
- `src/main/resources/data.sql` - Plik ładujący początkowy stan magazynowy i kategorie do bazy (nie zmieniaj mapowania Enumów!).
