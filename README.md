# Social Media Management System

A JavaFX desktop application for managing a small social network from text-based datasets.

## Features

- Load, search, add, update, remove, sort, and save users.
- Load, add, remove, browse, and save user friendships.
- Load, create, share, browse, remove, and save posts.
- Display per-user statistics, top posters, and users active during the last three weeks.
- Validate user and post input and preserve CSV content containing commas, quotes, or line breaks.

## Technologies & Tools

- Java 21: application language and target runtime.
- JavaFX 21: desktop controls, layouts, windows, and styling.
- Maven Wrapper: reproducible dependency management, builds, tests, and application launch.
- JUnit 5: regression tests for data structures, CSV handling, and bundled sample data.

## Data Structures

- `LinkedList` and `Node`: the existing custom singly linked list used for users, friendships, created posts, and shared posts.
- JavaFX `ObservableList`: keeps table views synchronized with displayed user, friendship, and post collections.
- `ArrayList`: supports checkbox selections and sorted/reporting views without replacing the core linked-list model.

## Prerequisites

- JDK 21 or newer with `JAVA_HOME` set to the JDK installation directory.
- Git for cloning the repository.

Maven and JavaFX do not need separate installations; the Maven Wrapper downloads them.

## Getting Started

```bash
git clone https://github.com/ElyasNajeh/SocialMedia-Management-System.git
cd SocialMedia-Management-System
./mvnw clean test
./mvnw javafx:run
```

On Windows PowerShell, use `./mvnw.cmd clean test` and `./mvnw.cmd javafx:run`.

Load the sample files from `src/main/resources/data` in this order: `users.txt`, `posts.txt`, then `friendships.txt`. Saved files are written to `.social-media-management-system/exports` inside the current user's home directory.

## Project Structure

- `src/main/java/FxSocial`: existing Java classes and package organization.
- `src/main/resources/FxSocial`: stylesheet and image resources.
- `src/main/resources/data`: bundled sample users, posts, and friendships.
- `pom.xml`, `mvnw`, `.mvn/`: Maven build and wrapper configuration.
