# Projekt 3 – Sklep z gadżetami IT

## Opis projektu
Mini e-commerce do zarządzania produktami i zamówieniami.
Klienci mogą przeglądać produkty, składać zamówienia, a system zarządza stanem magazynowym.
Projekt realizowany w zespole 2–3 osobowym w ciągu 2 tygodni.

---

## Encje

### Product
| Pole      | Typ    | Opis                          |
|-----------|--------|-------------------------------|
| id        | Long   | klucz główny, auto            |
| name      | String | nazwa produktu                |
| price     | double | cena brutto                   |
| stock     | int    | dostępna ilość                |
| category  | String | kategoria (KEYBOARD, MOUSE, MONITOR, OTHER) |

### Order
| Pole       | Typ         | Opis                          |
|------------|-------------|-------------------------------|
| id         | Long        | klucz główny, auto            |
| customerName | String    | imię i nazwisko klienta       |
| orderDate  | LocalDate   | data złożenia zamówienia      |
| status     | OrderStatus | PENDING, CONFIRMED, CANCELLED |
| totalPrice | double      | łączna kwota                  |

### OrderItem
| Pole     | Typ        | Opis                          |
|----------|------------|-------------------------------|
| id       | Long       | klucz główny, auto            |
| order    | Order (FK) | powiązane zamówienie          |
| product  | Product (FK)| powiązany produkt            |
| quantity | int        | ilość sztuk                   |
| unitPrice| double     | cena w momencie zakupu        |

---

## Wymagane endpointy REST

### Produkty (/api/products)
| Metoda | Ścieżka                    | Opis                               | Kod     |
|--------|----------------------------|------------------------------------|---------|
| GET    | /api/products              | lista produktów                    | 200     |
| GET    | /api/products/{id}         | szczegóły produktu                 | 200/404 |
| GET    | /api/products/search       | szukaj (?category=, ?maxPrice=)    | 200     |
| POST   | /api/products              | dodaj produkt                      | 201     |
| PUT    | /api/products/{id}         | edytuj produkt                     | 200/404 |
| DELETE | /api/products/{id}         | usuń produkt                       | 204/404 |

### Zamówienia (/api/orders)
| Metoda | Ścieżka                       | Opis                          | Kod     |
|--------|-------------------------------|-------------------------------|---------|
| GET    | /api/orders                   | lista zamówień                | 200     |
| GET    | /api/orders/{id}              | szczegóły zamówienia          | 200/404 |
| GET    | /api/orders/customer/{name}   | zamówienia klienta            | 200     |
| POST   | /api/orders                   | złóż zamówienie               | 201/409 |
| PUT    | /api/orders/{id}/cancel       | anuluj zamówienie             | 200/404 |

---

## Wymagania techniczne

### Struktura pakietów
```
com.example.itshop
├── controller     ← tylko REST, zero logiki biznesowej
├── service        ← interfejs + implementacja dla każdego serwisu
├── repository     ← interfejsy Spring Data JPA
├── model          ← encje JPA + klasy żądań/odpowiedzi (bez osobnego dto)
├── event          ← klasy eventów i listenery
├── exception      ← własne wyjątki + GlobalExceptionHandler
└── config         ← klasy @Configuration z @Bean
```

### Interfejsy serwisów (obowiązkowe)
```java
public interface OrderService {
    Order placeOrder(String customerName, List<OrderItemRequest> items);
    Order cancelOrder(Long orderId);
    List<Order> findByCustomer(String customerName);
}
```
Controller wstrzykuje interfejs, nie implementację.

### JPA
- Order -> OrderItem: @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
- OrderItem -> Product: @ManyToOne
- Własna metoda: List<Order> findByCustomerNameIgnoreCase(String name);
- Własna metoda: List<Product> findByCategoryAndPriceLessThanEqual(String category, double price);

### Eventy Spring
Po złożeniu zamówienia opublikuj event:
```java
public class OrderPlacedEvent extends ApplicationEvent {
    private final Order order;
}
```
Listener (@EventListener) loguje zamówienie i generuje mock numeru faktury (np. "FV/2024/001").

### Scopy beanów
- singleton – serwisy (domyślne)
- session lub prototype – koszyk (CartService) przechowujący tymczasowe pozycje

Komentarz w kodzie wyjaśniający wybór scope.

### Własna konfiguracja @Bean
```java
@Bean
public PriceFormatter priceFormatter() { /* formatowanie cen do 2 miejsc */ }

@Bean
@Scope("prototype")
public InvoiceNumberGenerator invoiceNumberGenerator() { /* unikalne numery */ }
```

### Obsługa wyjątków
@RestControllerAdvice z obsługą:
- ResourceNotFoundException (404) – brak produktu lub zamówienia
- InsufficientStockException (409) – za mało towaru na stanie
- Odpowiedź JSON: { "status": 409, "message": "...", "timestamp": "..." }

Brak try/catch w kontrolerach.

### Testy jednostkowe
Min. 5 testów dla OrderServiceImpl:
- should_placeOrder_when_stockIsSufficient
- should_throwException_when_stockInsufficient
- should_decreaseStock_when_orderPlaced
- should_restoreStock_when_orderCancelled
- should_throwException_when_orderNotFound

### Plik API.http
Plik API.http z min. 10 wywołaniami pokrywającymi wszystkie endpointy.

---

## Baza danych
- H2/Sqlite in-memory
- Dane inicjalne: kilka produktów przez data.sql lub CommandLineRunner

---

## README.md (wymagany)
1. Opis projektu
2. Diagram encji (ASCII)
3. Instrukcja uruchomienia
4. Opis scopów i uzasadnienie

---

## Punktacja
| Element                              | Punkty |
|--------------------------------------|--------|
| Struktura projektu i pakiety         | 10     |
| JPA – encje, relacje, zapytania      | 15     |
| REST API + kody odpowiedzi           | 20     |
| Interfejsy serwisów + DI             | 10     |
| Eventy Spring                        | 10     |
| Scopy beanów                         | 10     |
| Obsługa wyjątków (@ControllerAdvice) | 10     |
| Testy jednostkowe (min. 5)           | 15     |
| Suma                                 | 100    |

### Przelicznik na ocenę
| Punkty | Ocena           |
|--------|-----------------|
| 90–100 | 6 celujący      |
| 80–89  | 5 bardzo dobry  |
| 65–79  | 4 dobry         |
| 50–64  | 3 dostateczny   |
| 30–49  | 2 dopuszczający |
| 0–29   | 1 niedostateczny|

### Typowe błędy odejmujące punkty
- Logika biznesowa w kontrolerze: -5 pkt
- Brak pliku API.http: -5 pkt
- Controller wstrzykuje implementację zamiast interfejsu: -3 pkt
- Brak komentarza przy niestandardowym scopie: -2 pkt
