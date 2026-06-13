# Sklep z gadżetami IT – shop-api

Mini e-commerce REST API do zarządzania produktami i zamówieniami.
Klienci mogą przeglądać produkty, składać zamówienia, a system zarządza stanem magazynowym.

Projekt oparty o **Spring Boot 3.5 / Java 21**, baza **H2 in-memory**.

---

## 1. Opis projektu

Aplikacja udostępnia REST API w dwóch obszarach:

- **Produkty** (`/api/products`) – CRUD + wyszukiwanie po kategorii i cenie.
- **Zamówienia** (`/api/orders`) – składanie, przeglądanie i anulowanie zamówień.

Po złożeniu zamówienia publikowany jest event Springa (`OrderPlacedEvent`),
a listener loguje zamówienie i generuje mock numeru faktury.

---

## 2. Diagram encji (ASCII)

```
+-------------------+         +----------------------+         +-------------------+
|     Product       |         |      OrderItem       |         |       Order       |
+-------------------+         +----------------------+         +-------------------+
| id        Long PK |1      * | id        Long PK    | *      1| id        Long PK |
| name      String  |---------| product   Product FK |---------| customerName Str  |
| price     double  | @ManyTo | order     Order   FK | @ManyTo | orderDate  LocalDt|
| stock     int     |  One    | quantity  int        |  One    | status  OrderStat |
| category  Category|         | unitPrice double     |         | totalPrice double |
+-------------------+         +----------------------+         +-------------------+

Order 1 ---- * OrderItem   (@OneToMany mappedBy="order", cascade=ALL, orphanRemoval)
OrderItem * ---- 1 Product (@ManyToOne)

OrderStatus = { PENDING, CONFIRMED, CANCELLED }   (@Enumerated STRING)
Category    = { KEYBOARD, MOUSE, MONITOR, OTHER }  (@Enumerated STRING)
```

---

## 3. Instrukcja uruchomienia

Wymagania: JDK 21.

```bash
# uruchomienie aplikacji
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows

# testy
./mvnw test
```

Aplikacja startuje na **http://localhost:8080**.

- Konsola H2: **http://localhost:8080/h2-console**
  - JDBC URL: `jdbc:h2:mem:shopdb`
  - User: `sa` (bez hasła)
- Dane startowe ładowane są z `src/main/resources/data.sql`
  (kilka produktów + przykładowe zamówienia).
- Plik **`API.http`** zawiera gotowe wywołania wszystkich endpointów
  (uruchamiane z IntelliJ / VS Code REST Client).

### Endpointy

| Metoda | Ścieżka                      | Opis                  | Kod     |
|--------|------------------------------|-----------------------|---------|
| GET    | /api/products                | lista produktów       | 200     |
| GET    | /api/products/{id}           | szczegóły produktu    | 200/404 |
| GET    | /api/products/search         | szukaj (category, maxPrice) | 200 |
| POST   | /api/products                | dodaj produkt         | 201     |
| PUT    | /api/products/{id}           | edytuj produkt        | 200/404 |
| DELETE | /api/products/{id}           | usuń produkt          | 204/404 |
| GET    | /api/orders                  | lista zamówień        | 200     |
| GET    | /api/orders/{id}             | szczegóły zamówienia  | 200/404 |
| GET    | /api/orders/customer/{name}  | zamówienia klienta    | 200     |
| POST   | /api/orders                  | złóż zamówienie       | 201/409 |
| PUT    | /api/orders/{id}/cancel      | anuluj zamówienie     | 200/404 |

---

## 4. Opis scopów beanów i uzasadnienie

| Bean                      | Scope                | Uzasadnienie |
|---------------------------|----------------------|--------------|
| `ProductServiceImpl`, `OrderServiceImpl` | **singleton** (domyślny) | Serwisy są bezstanowe – jedna instancja współdzielona przez wszystkie żądania jest wydajna i bezpieczna. |
| `CartService` (koszyk)    | **session** / **prototype** | Koszyk przechowuje tymczasowe pozycje powiązane z konkretnym użytkownikiem/żądaniem – nie może być współdzielony między klientami. |
| `InvoiceNumberGenerator`  | **prototype**        | Każde wywołanie ma dać nową, niezależną instancję generującą unikalny numer faktury. |
| `PriceFormatter`          | **singleton**        | Bezstanowy formatter cen – wystarczy jedna instancja. |

> Przy każdym niestandardowym scope w kodzie znajduje się komentarz
> wyjaśniający wybór (wymóg z `TODO.md`).

---

## Struktura pakietów

```
org.technischools.shop
├── controller   ← tylko REST, zero logiki biznesowej
├── service      ← interfejs + implementacja dla każdego serwisu
├── repository   ← interfejsy Spring Data JPA
├── model        ← encje JPA + klasy żądań (OrderRequest, OrderItemRequest)
├── event        ← klasy eventów i listenery
├── exception    ← własne wyjątki + GlobalExceptionHandler
└── config       ← klasy @Configuration z @Bean
```
