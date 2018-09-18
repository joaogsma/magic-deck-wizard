# MTG Deck Wizard
MTG Deck Wizard is a simple command line tool for extracting metrics from magic the gathering deck lists.

## Installation
To install the application, run:
```
./gradlew installShadowDist
```
This should install a distribution on the `build/install/mtg-deck-wizard-shadow` directory. To the
installed distribution, first go to its directory, and then run:
```
# Unix
bin/mtg-deck-wizard <filename>

# Windows
bin/mtg-deck-wizard.bat <filename>
```
where `<filename>` is a path for a deck list, such as `../../../examples/tatyova.txt`
To build a runnable jar file, run:
```
# Unix
./gradlew shadowJar

# Windows
./gradlew.bat shadowJar
```

Alternatively, because the `lib/mtg-deck-wizard-<version>.jar` file is a runnable jar, you can also
run the application with the command:
```
java -jar lib/mtg-deck-wizard-<version>.jar <filename>
```

## Releasing a new version
To release a new version, run:
```
# Unix
./gradlew release -Prelease.scope=<scope> -Prelease.stage=final

# Windows
./gradlew.bat release -Prelease.scope=<scope> -Prelease.stage=final
```
where the scope can be `patch`, `minor` or `major`. The `stage` should always be final for simplicity.
