Faza 1 — razem (~1h) · projekt setup + encje + interfejsy
Spring Boot project + zależności (JPA, H2, Web, Lombok)
Encje: Product, Order, OrderItem + enums (OrderStatus, Category)
Interfejsy serwisów: ProductService, OrderService
Puste repozytoria (stubs) — żeby obie osoby mogły importować

↓ od tu można robić równolegle ↓
Faza 2 — równolegle (~8–10h)
Osoba A — Produkty & Infrastruktura
ProductRepository
+ findByCategoryAndPriceLessThanEqual
  ProductServiceImpl
  (CRUD + search logika)
  ProductController
  /api/products – wszystkie endpointy
  Wyjątki + GlobalExceptionHandler
  ResourceNotFoundException, InsufficientStockException, @RestControllerAdvice z JSON
  Config: PriceFormatter @Bean
  klasa @Configuration
  CartService @Scope("prototype")
+ komentarz uzasadniający scope
  Dane inicjalne
  data.sql lub CommandLineRunner (kilka produktów)
  API.http – endpointy produktów
  GET lista, GET id, GET search, POST, PUT, DELETE


Osoba B — Zamówienia, Eventy & Testy
OrderRepository + OrderItemRepository
+ findByCustomerNameIgnoreCase
  OrderServiceImpl
  placeOrder (stock check), cancelOrder, findByCustomer
  OrderController
  /api/orders – wszystkie endpointy
  OrderPlacedEvent + @EventListener
  logowanie + mock numeru faktury
  Config: InvoiceNumberGenerator @Bean @Scope("prototype")
  unikalne numery FV/YYYY/NNN
  Testy jednostkowe (min. 5)
  should_placeOrder…, should_throw…, should_decrease…, should_restore…, should_throwNotFound
  API.http – endpointy zamówień
  GET lista, GET id, GET /customer/{name}, POST, PUT cancel
  ⚠ Potrzebuje ProductRepository z fazy 1 — stock check w placeOrder

Faza 3 — razem (~30min) · merge + dokumentacja
Merge branchy + fix konfliktów
Połącz pliki API.http w jeden (min. 10 wywołań)
README.md: opis, diagram ASCII encji, instrukcja, opis scopów
Smoke test całości + weryfikacja punktacji