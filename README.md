# Pokemon TCG — Backend Digital

**TPI · Programación III · UTN FRC**

Backend para un juego digital de cartas Pokemon TCG. Implementa el modelo de dominio completo (cartas, jugadores, partidas) y una API REST para matchmaking con máquina de estados.

---

## Stack tecnológico

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.4.5 |
| Build | Maven |
| Persistencia | Spring Data JPA + PostgreSQL + Flyway |
| API REST | Spring Web + SpringDoc OpenAPI (Swagger) |
| Tiempo real | Spring WebSocket + STOMP |
| Seguridad | Spring Security |
| Testing | JUnit 5 + JaCoCo (≥ 80% cobertura global) |

---

## Arquitectura

El proyecto sigue **Clean Architecture** con tres capas independientes:

```
org.example
├── domain/          ← Modelo puro. Sin dependencias de frameworks.
│   └── model/
│       ├── card/    ← Jerarquía de cartas (25 clases)
│       └── match/   ← Partida, jugadores, estados
├── application/     ← Casos de uso (@Service). Solo depende del dominio.
│   └── matchmaking/
└── presentation/    ← Controladores REST y DTOs. Solo depende de application.
    └── rest/match/
```

**Principios aplicados:** Domain-Driven Design · Open/Closed (extensión sin modificar clases existentes) · Liskov Substitution · immutabilidad defensiva con `List.copyOf()` · records Java 21 para value objects.

---

## Modelo de dominio — Cartas

```
Card (abstract)
├── PokemonCard (abstract)  — HP, ataques, debilidades, resistencias, costo de retirada
│   ├── BasicPokemonCard
│   │   └── ExPokemonCard   — otorga 2 cartas de Premio al ser eliminado
│   └── EvolutionPokemonCard (abstract)
│       ├── Stage1PokemonCard
│       └── Stage2PokemonCard
├── EnergyCard (abstract)
│   ├── BasicEnergyCard     — sin límite de copias en mazo
│   └── SpecialEnergyCard   — máx. 4 copias, efecto de texto
└── TrainerCard (abstract)
    ├── ItemCard
    │   └── AceSpecCard     — máx. 1 copia en mazo (AS TÁCTICO)
    ├── SupporterCard
    ├── StadiumCard
    └── PokemonToolCard
```

**Value objects (records):** `Attack` · `Ability` · `Weakness` · `Resistance`

**Enums:** `EnergyType` (11 tipos) · `PokemonStage` · `TrainerType` · `AbilityType` · `GameState`

**Utilidades:** `EnergyCostValidator` — valida si la energía disponible cubre el costo de un ataque, respetando que el tipo `COLORLESS (★)` lo satisface cualquier energía.

---

## Matchmaking — Máquina de estados

```
WAITING ──join()──► SETUP ──startActive()──► ACTIVE ──finish()──► FINISHED
```

| Estado | Descripción |
|--------|-------------|
| `WAITING` | Partida creada, esperando segundo jugador |
| `SETUP` | Ambos jugadores registrados; fase de preparación (mulligan, colocación inicial, cartas de Premio) |
| `ACTIVE` | Ciclo de turnos en curso |
| `FINISHED` | Condición de victoria alcanzada |

Toda transición inválida lanza `IllegalStateException`. El jugador no puede unirse a su propia partida.

---

## API REST

Base URL: `http://localhost:8080/api/matches`

Documentación interactiva: `http://localhost:8080/swagger-ui.html`

| Método | Endpoint | Descripción | Estado HTTP |
|--------|----------|-------------|-------------|
| `POST` | `/api/matches` | Crear partida (estado WAITING) | 201 |
| `GET` | `/api/matches` | Listar partidas disponibles (WAITING) | 200 |
| `POST` | `/api/matches/{id}/join` | Unirse a partida (→ SETUP) | 200 |
| `GET` | `/api/matches/{id}` | Obtener estado actual | 200 |
| `POST` | `/api/matches/{id}/start` | Iniciar partida (SETUP → ACTIVE) | 200 |
| `POST` | `/api/matches/{id}/finish` | Finalizar partida (ACTIVE → FINISHED) | 200 |

### Ejemplo: crear partida

```bash
curl -X POST http://localhost:8080/api/matches \
  -H "Content-Type: application/json" \
  -d '{
    "playerId": "ash-ketchum",
    "deckCardIds": ["card-001", "card-001", ...(60 cartas)]
  }'
```

Respuesta:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "state": "WAITING",
  "player1Id": "ash-ketchum",
  "player2Id": null,
  "createdAt": "2026-06-09T10:00:00"
}
```

### Códigos de error

| Código | Causa |
|--------|-------|
| 400 | Deck con ≠ 60 cartas · Jugador uniéndose a su propia partida |
| 404 | Partida no encontrada |
| 409 | Transición de estado inválida |

---

## Cómo ejecutar

### Requisitos

- Java 21
- Maven 3.9+
- PostgreSQL 15+ (para modo producción)

### Modo desarrollo (sin base de datos)

JPA y Security están excluidos del autoconfigure, por lo que la app levanta con un repositorio en memoria:

```bash
mvn spring-boot:run
```

### Con base de datos (producción)

Configurar en `application.properties` o variables de entorno:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pokemon_tcg
spring.datasource.username=<usuario>
spring.datasource.password=<contraseña>
# Eliminar las líneas de spring.autoconfigure.exclude
```

Flyway aplicará las migraciones automáticamente al iniciar.

---

## Tests

```bash
# Suite completa
mvn test

# Solo un test específico
mvn -Dtest=MatchTest test
```

Cobertura mínima exigida por JaCoCo:
- **80%** de líneas a nivel global
- **90%** en las clases del motor de juego (`RuleValidator`, `DamageCalculator`, `StatusEffectManager`, `TurnManager`, `VictoryConditionChecker`)

Estado actual: **85 tests · 0 fallos**

| Suite | Tests |
|-------|-------|
| `PokemonCardHierarchyTest` | 23 |
| `EnergyCardTest` | 20 |
| `TrainerCardTest` | 16 |
| `MatchTest` | 16 |
| `MatchmakingServiceTest` | 10 |

---

## Estructura del proyecto

```
src/
├── main/
│   ├── java/org/example/
│   │   ├── Main.java                          ← @SpringBootApplication
│   │   ├── domain/model/
│   │   │   ├── card/                          ← 25 clases del modelo de cartas
│   │   │   └── match/                         ← Match, Player, GameState
│   │   ├── application/matchmaking/           ← MatchmakingService, MatchNotFoundException
│   │   └── presentation/rest/match/           ← MatchController + DTOs
│   └── resources/
│       └── application.properties
└── test/
    └── java/org/example/
        ├── domain/model/card/                 ← 3 suites de tests de cartas
        ├── domain/model/match/                ← MatchTest
        └── application/matchmaking/           ← MatchmakingServiceTest
```

---

## Equipo

| Integrante | Legajo |
|------------|--------|
| Franco Amarilla | 421486 |

**Universidad Tecnológica Nacional · Facultad Regional Córdoba**  
Programación III · 2026
