# MTG Deck Wizard
MTG Deck Wizard is a simple command line tool for extracting metrics from magic the gathering deck 
lists.

## Useful commands
### Building a runnable jar
To build a runnable jar file, run:
```
./gradlew shadowJar
```
The resulting file will be called `mtg-deck-wizard-<version>.jar` and will be available at the 
`build/libs/` directory.

### Running the application
To run the MTG Deck Wizard on the `examples/tatyova.txt` deck list, run:
```
java -jar build/libs/mtg-deck-wizard-<version>jar examples/tatyova.txt 
```

### Releasing a new version
To release a new version, run:
```
./gradlew release -Prelease.scope=<scope> -Prelease.stage=final
```
where the scope can be one of the following: `patch`, `minor` or `major`. The `stage` should always 
be final for simplicity.
