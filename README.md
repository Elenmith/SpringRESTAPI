# CRM Backend - Struktura Projektu

## Tabela klas w projekcie

| **Nazwa Klasy**         | **Rola**                                                                                                   | **Obowiązkowe Elementy**                                                                                                                                                       | **Kolejność** | **Odwołuje się do/Wysyła dane do**                                                                                 |
|--------------------------|-----------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------|--------------------------------------------------------------------------------------------------------------------|
| **EmployeeDto**          | Reprezentuje dane pracownika przesyłane między warstwami aplikacji (DTO).                                 | - Pola: `id`, `firstName`, `lastName`, `email` <br> - Gettery i settery <br> - Konstruktor                                             | ✔ (1)                    | - Używana przez `EmployeeController`, `EmployeeMapper`, `EmployeeServiceImpl`.                                    |
| **Employee**             | Encja JPA, reprezentuje tabelę w bazie danych.                                                           | - Adnotacje: `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`, `@Version` <br> - Pola: `id`, `firstName`, `lastName`, `email`, `version` <br> - Konstruktor, gettery i settery | ✔ (2)                    | - Zarządzana przez `EmployeeRepository`. <br> - Mapowana na DTO w `EmployeeMapper`.                              |
| **EmployeeRepository**   | Interfejs JPA do zarządzania danymi w tabeli `employees`.                                                | - Rozszerzenie `JpaRepository<Employee, Long>` <br> - Adnotacja: `@Repository`                                                          | ✔ (3)                    | - Wywoływana przez `EmployeeServiceImpl` do operacji na bazie danych.                                            |
| **EmployeeMapper**       | Mapuje dane między encją `Employee` a DTO `EmployeeDto`.                                                | - Metody: `mapToEmployeeDto(Employee)` i `mapToEmployee(EmployeeDto)`                                                                   | ✔ (4)                    | - Wywoływana przez `EmployeeServiceImpl` do konwersji między encją a DTO.                                        |
| **EmployeeService**      | Interfejs definiujący logikę biznesową dla operacji na pracownikach.                                     | - Metoda: `createEmployee(EmployeeDto)`                                                                                                  | ✔ (5)                    | - Implementowany przez `EmployeeServiceImpl`.                                                                    |
| **EmployeeServiceImpl**  | Implementacja interfejsu `EmployeeService`, zawiera logikę biznesową.                                   | - Adnotacja: `@Service` <br> - Pole: `employeeRepository` <br> - Konstruktor do wstrzyknięcia zależności <br> - Implementacja `createEmployee` | ✔ (6)                    | - Wywołuje `EmployeeRepository`, `EmployeeMapper`. <br> - Używana przez `EmployeeController`.                   |
| **EmployeeController**   | Kontroler REST, obsługuje żądania HTTP dotyczące pracowników.                                           | - Adnotacje: `@RestController`, `@RequestMapping("/api/employees")` <br> - Pole: `employeeService` <br> - Konstruktor <br> - Endpoint: `@PostMapping` | ✔ (7)                    | - Wywołuje `EmployeeServiceImpl`. <br> - Obsługuje dane od/do klienta (np. Postman).                              |
| **CrmBackendApplication**| Główna klasa aplikacji Spring Boot, inicjalizuje aplikację.                                             | - Adnotacja: `@SpringBootApplication` <br> - Metoda: `main`                                                                                | ✔ (8)                    | - Automatycznie uruchamia Spring Context i rejestruje wszystkie komponenty.                                       |

---

## Przepływ danych w aplikacji

1. **Żądanie HTTP (POST)**:
   - Klient (np. Postman) wysyła dane w formacie JSON do kontrolera `EmployeeController`.

2. **Kontroler (`EmployeeController`)**:
   - Odbiera dane w formie `EmployeeDto`.
   - Wywołuje metodę `createEmployee` w `EmployeeServiceImpl`.

3. **Serwis (`EmployeeServiceImpl`)**:
   - Konwertuje DTO na encję `Employee` za pomocą `EmployeeMapper`.
   - Zapisuje encję w bazie danych za pomocą `EmployeeRepository`.
   - Konwertuje zapisaną encję z powrotem na DTO.

4. **Repozytorium (`EmployeeRepository`)**:
   - Wykonuje operację `save` na encji `Employee`.

5. **Mapper (`EmployeeMapper`)**:
   - Mapuje dane między encją `Employee` a DTO `EmployeeDto`.

6. **Kontroler (`EmployeeController`)**:
   - Zwraca zapisane dane w formacie JSON do klienta z HTTP status `201 Created`.

---

## Kolejność tworzenia klas

1. **`EmployeeDto`** – Zacznij od klasy DTO, która przesyła dane w aplikacji.
2. **`Employee`** – Utwórz encję reprezentującą tabelę w bazie danych.
3. **`EmployeeRepository`** – Zdefiniuj repozytorium JPA do operacji na bazie danych.
4. **`EmployeeMapper`** – Utwórz mapper do konwersji między encją a DTO.
5. **`EmployeeService`** – Zdefiniuj interfejs logiczny operacji na danych.
6. **`EmployeeServiceImpl`** – Implementuj logikę biznesową.
7. **`EmployeeController`** – Utwórz kontroler REST do obsługi żądań HTTP.
8. **`CrmBackendApplication`** – Na końcu upewnij się, że główna klasa uruchamia aplikację.

---

## Dobre praktyki

- **Modularność**: Każda klasa ma jedną odpowiedzialność (Single Responsibility Principle).
- **Warstwy aplikacji**:
  - **Kontroler**: Obsługuje HTTP.
  - **Serwis**: Zawiera logikę biznesową.
  - **Repozytorium**: Obsługuje bazę danych.
  - **Mapper**: Oddziela logikę konwersji między encją a DTO.
- **DTO**: Używaj DTO do przesyłania danych zamiast operować bezpośrednio na encjach.

---

## Przykładowe żądanie w Postmanie

**URL**: `http://localhost:8080/api/employees`  
**Metoda HTTP**: `POST`  
**Body (JSON)**:
```json
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com"
}
