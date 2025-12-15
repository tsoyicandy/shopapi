# 🎟️ Concert Ticket API – Projet individuel Spring Boot

## Sujet
API REST de **vente de tickets de concert** sans gestion de sièges.
Chaque concert dispose d’un nombre limité de tickets.  
Un utilisateur peut acheter des tickets tant que le stock est disponible.

Ce projet a été réalisé dans le cadre du cours de Java Avancée.

---

## Technologies utilisées
- Java 25
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Security (HTTP Basic)
- PostgreSQL (Docker)

---

## Modèle de données

### Entités
- **Artist** : artiste du concert
- **Concert** : concert avec un stock de tickets
- **User** : utilisateur achetant des tickets
- **Order** : commande d’achat
- **Ticket** : ticket acheté (sans siège)

### Relations
- Artist **1 → N** Concert
- Concert **1 → N** Ticket
- User **1 → N** Order
- Order **1 → N** Ticket

---

## Règle métier principale
❌ Il est **impossible d’acheter un ticket** si le concert n’a plus de tickets disponibles.

Cette règle est implémentée dans la couche `service` et protégée par une transaction.

---

## Sécurité
- Authentification **HTTP Basic**
- Rôles :
    - `ADMIN` : création des concerts, artistes
    - `USER` : achat de tickets
- Accès :
    - `GET /api/**` → public
    - `POST /api/**` → réservé à `ADMIN`

### Comptes de test
| Rôle  | Login | Mot de passe |
|-----|------|-------------|
| ADMIN | admin | admin |
| USER  | user  | user |

---

## Avant d’appeler POST Buy Ticket, assurez-vous que ces données existent en base, en exécutant ces réquêtes en BD:

INSERT INTO users (id, email)
VALUES (1, 'sandrine@example.com');

INSERT INTO orders (id, user_id)
VALUES (1, 1);



## Lancer le projet

### 1. Démarrer la base de données
```bash
docker compose up -d
