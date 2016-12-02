# Tiger Zone

An implementation of a Carcassonne derivative.

Created by Team I, composed of:

  * Logan Barkes (@lbarkes)
  * Kyle Hardin (@khardin2012)
  * Michael Lowes (@mlowe12)
  * Steven Remington (@StevenRemington)
  * Aubree Richards (@AubreeR)
  * Kenan Yildirim (@KenanY)

## Building

[Maven](https://maven.apache.org/) is used for both installing dependencies and
building the project itself. With it installed, compilation can be done in a
single command:

``` bash
$ mvn package
```

This will generate `build/TigerZone.jar`.

## Running

After following the above instructions:

``` bash
$ java -jar build/TigerZone.jar
Usage:
  tigerzone connect <ip> <port> <tournament-password> <username> <password>
  tigerzone localduel
  tigerzone (-h | --help)
```

The client supports two modes of operation: network play and local duel. Network
play is done through the `connect` command, like so:

``` bash
$ java -jar build/TigerZone.jar connect <ip> <port> <tournament-password> <username> <password>
```

This will connect the client to an instance of
[the Tiger Zone server](https://github.com/chausen/TigerZoneServer) that's
running on `<ip>:<port>`.

The local duel mode pits the AI against itself in a single offline game:

``` bash
$ java -jar build/TigerZone.jar localduel
```

## Testing

Unit testing is done via [JUnit](http://junit.org/junit4/). One may run the test
suite with Maven:

``` bash
$ mvn test
```
