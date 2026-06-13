# WormBooks

WormBooks is a JavaFX desktop word game inspired by Bookworm. Players connect adjacent letters to build valid words, turn those words into attacks, and survive an escalating sequence of enemies and bosses.

The project was created as a final project for the CPE112 course and demonstrates practical use of data structures in a complete game loop.

## Features

- Drag across adjacent tiles on a responsive 5 x 12 letter grid to form words.
- Validate words against a bundled dictionary stored in an AVL tree.
- Track the current selection and support backtracking with a stack.
- Calculate attack damage from per-letter values and the selected character class.
- Choose from Warrior, Mage, Rogue, and English Teacher classes with different stats.
- Fight increasingly strong enemies, including a boss every fifth encounter.
- Use responsive windowed or fullscreen layouts with music and sound effects.
- View an in-game tutorial, pause menu, score summary, and developer controls.

## Tech Stack

- Java, compiled with the Java 11 release target
- JavaFX 13 (`controls`, `fxml`, and `media`)
- Apache Maven
- Maven Shade Plugin for packaged JAR output

## Getting Started

### Prerequisites

- JDK 11 or newer
- Apache Maven 3.x
- A local JavaFX SDK for launching the modular application

The Maven build has been verified with JDK 17 and Maven 3.9.9.

### Installation

```bash
git clone https://github.com/ArmmyC/BookWorm.git
cd BookWorm
mvn package
```

The package command compiles the game, copies its resources, and creates these artifacts:

```text
target/cpe112finalproject-1.0-SNAPSHOT.jar
target/cpe112finalproject-1.0-SNAPSHOT-shaded.jar
```

### Running From Source

Build the project first, then point Java at both the compiled classes and the `lib` directory of a local JavaFX SDK.

PowerShell:

```powershell
$env:PATH_TO_FX = "C:\path\to\javafx-sdk\lib"
java --module-path "target\classes;$env:PATH_TO_FX" --module cpe112finalproject/cpe112.finalproject.App
```

macOS or Linux:

```bash
PATH_TO_FX=/path/to/javafx-sdk/lib
java --module-path "target/classes:$PATH_TO_FX" --module cpe112finalproject/cpe112.finalproject.App
```

> [!NOTE]
> The current `pom.xml` declares `javafx-maven-plugin` version `8.8.3`, which is not available from Maven Central. As a result, `mvn javafx:run` does not work without correcting that plugin configuration. The normal `compile`, `test`, and `package` lifecycle commands do work.

## How to Play

1. Select **Play** from the main menu.
2. Enter a player name and choose a character class.
3. Press and drag across adjacent letter tiles to form a word.
4. Release the mouse to submit the word and attack if it is valid.
5. Defeat enemies before their recurring attacks reduce your health to zero.
6. Use the menu button or `Esc` to access fullscreen, return-to-menu, exit, and developer options.

Longer words and higher-value letters deal more damage. Used tiles receive new letters after a successful attack.

## Maven Commands

| Command | Description |
|---|---|
| `mvn compile` | Compile the application and copy resources. |
| `mvn test` | Run the Maven test lifecycle. No automated tests are currently present. |
| `mvn package` | Build the regular and shaded JAR artifacts in `target/`. |

## Project Structure

```text
.
|-- assets/                         # README screenshots
|-- src/main/java/
|   |-- module-info.java            # Java module definition
|   `-- cpe112/finalproject/
|       |-- Constants/              # Shared names, paths, fonts, and styles
|       |-- Data/                   # AVL tree and word dictionary
|       |-- Entities/               # Player and enemy UI entities
|       |-- Handlers/               # Keyboard and mouse input
|       |-- Layout/                 # JavaFX screens and interface components
|       |-- Logic/                  # Puzzle, combat, health, and menu behavior
|       |-- Managers/               # Player, enemy, scene, image, and sound state
|       |-- Responsives/            # Responsive control sizing
|       |-- Scenes/                 # Main menu, class selector, and game scenes
|       `-- App.java                # Application entry point
|-- src/main/resources/cpe112/finalproject/
|   |-- fonts/
|   |-- images/
|   |-- sounds/
|   `-- words.txt                   # Bundled word list
|-- LICENSE
`-- pom.xml
```

## Screenshots

### Main Menu

![WormBooks main menu](assets/MainMenu.png)

### Character Selection

![WormBooks character selector](assets/ClassSelector.png)

### Gameplay

![WormBooks combat and word grid](assets/GamePlay.png)

![WormBooks word selection](assets/GamePlay2.png)

### Game Over

![WormBooks game-over screen](assets/GameOver.png)

## Testing

Run the Maven test lifecycle with:

```bash
mvn test
```

The repository does not currently contain automated test sources. This command still verifies resource processing and compilation before Maven reports that there are no tests to run.

## License

This project is distributed under a custom non-commercial license that permits use, modification, redistribution, and sublicensing for non-commercial purposes with attribution. See [LICENSE](LICENSE) for the complete terms.
