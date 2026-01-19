# Gestion Scolarité - Backend API

Ce projet est une API REST développée avec **Spring Boot** pour la gestion d'un établissement scolaire. Elle permet de gérer les élèves, les enseignants, les parents, les administrateurs, les salles, les séances de cours et les notes.

## 🚀 Technologies
- **Java 17+**
- **Spring Boot 3**
- **Spring Security** (Authentification BCrypt)
- **Spring Data JPA** (MySQL / H2)
- **Lombok**

## 🛠️ Installation et Lancement
1. Clonez le projet.
2. Assurez-vous d'avoir une base de données configurée dans `src/main/resources/application.properties`.
3. Lancez l'application avec Maven :
   ```bash
   ./mvnw.cmd spring-boot:run
   ```
   L'API sera accessible sur : `http://localhost:8083`

## 🔐 Sécurité & Authentification
Le système utilise **Spring Security** avec hachage **BCrypt**.

### Login
- **URL** : `POST /api/auth/login`
- **Body** : `{"email": "votre@email.com", "password": "votre_password"}`
- *Note : L'admin utilise son `username` à la place de l'email.*

## 📖 Documentation & Guides de Test
Plusieurs guides détaillés sont disponibles pour faciliter vos tests Postman :
- [Guide des Rôles (.gemini/...)](.gemini/antigravity/brain/5c55cb92-e207-4ab9-99c1-fd320a505013/README_ROLES_TESTING.md) : Comment créer et connecter chaque utilisateur.
- [Guide des Relations (.gemini/...)](.gemini/antigravity/brain/5c55cb92-e207-4ab9-99c1-fd320a505013/README_DTO_RELATIONSHIPS.md) : Comment lier des salles aux profs, des notes aux élèves, etc.

## 📂 Structure des Endpoints
- `/api/auth` : Authentification.
- `/api/eleves`, `/api/enseignants`, `/api/parents`, `/api/admins` : Gestion des utilisateurs.
- `/api/salles`, `/api/seances` : Gestion des locaux et du planning.
- `/api/notes` : Gestion des évaluations.
- `/api/matieres` : Gestion des matières.
