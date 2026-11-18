# UniAccounts - TP1 (Creational Design Patterns)

University student account management system in Java.
Work 1: core of the system using three creational patterns (Builder, Singleton, Prototype).

## Team

- عائشة ذياب (Aicha Diab) - Groupe 04
- راضية بحري (Radhia Bahri) - Groupe 04

## Run (JDK 11 or later)

```
mkdir out
javac -d out src/model/*.java src/audit/*.java src/repository/*.java src/app/*.java
java -cp out app.Main
```

Run the commands from the project root folder (the one that contains `src`).
If the `*.java` wildcard is not accepted by your terminal, compile with this equivalent command instead:

```
javac -d out -sourcepath src src/app/Main.java
```

## Project structure

```
src/
  app/Main.java                      -> demo of the three patterns
  audit/AuditLogger.java             -> Singleton (one audit log)
  model/StudentAccount.java          -> Builder + Prototype
  repository/AccountRepository.java  -> Singleton (one repository, unique ids)
docs/
  README.md
  RUN.md
  uml.png
  screenshot_run.png
  Rapport_Work1.pdf
```

## Patterns

| Pattern   | Class(es)                                  | Problem solved |
|-----------|--------------------------------------------|----------------|
| Builder   | `StudentAccount.Builder`                   | 8 fields, some optional: readable creation, validation, no constructor with 8 arguments |
| Singleton | `AccountRepository`, `AuditLogger`         | exactly one repository and one audit log in the whole program |
| Prototype | `StudentAccount.clone()` / `withIdentity()` | create many accounts of a cohort by copying a ready template (deep copy of the course list) |

## Class diagram

![UML](uml.png)

## Notes
- Every pattern is marked in the code with a comment (`// Singleton`, `// Builder`, `// Prototype`).
- Validation rules: id not empty and unique, full name not empty, email contains `@`, level in L1/L2/L3/L4/M1/M2 (default L1).
