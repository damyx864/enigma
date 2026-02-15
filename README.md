# Paper Enigma Implementation

The program will encrypt only letters a-z and A-Z, the other characters will not be modified, while being case-sensitive.<br>
The REST encode will consume and produce JSON while the index and editor page will allow to input a message and to encrypt it.<br>
The implementation follows the exact specification of Michael C. Koss (mike@mckoss.com) from his document https://www.apprendre-en-ligne.net/crypto/bibliotheque/PDF/paperEnigma.pdf.<br><br>

I implemented two Spring MVC Controllers one that can act as an api, and the other which can talk directly to the browser.<br>
The data used to initialize the rotors with the cipher is being fed from a properties file `enigma.properties` using Spring's plumming to scan the components and inject them the data .<br>
You should look there to change the "machine's" specs, I used the data provided in the document.<br><br>

I implemented each rotor from a common class, which created some friction with the first rotor (or the rightmost one) because it has to change the initial setting and thus it actually maintains its key.<br>
The reflector was implemented separately because it's also a special case of rotor.<br><br>

The most convenient way to implement the rotors was to use arrays as the other data structures in Java are too problematic to either shift data or to have and index.<br>
Since the encoding process starts the piece of data that gets passed around and transformed in the process is just the absolute value from the current index from either the alphabetic index of the rotors or the reflector or the input list or the index of the cipher part.<br><br>

To perform Enigma like encoding with this scheme all the three rotors and the reflector must be initialized with the ciphers, then prepared with the key.<br>
For example the key `cat` will set `t` to the rightmost rotor, `a` to the middle rotor and `c` to the leftmost rotor.<br><br>

The key must be comprised of three letters, otherwise the encryption will not be performed.<br>
For any other situation that would not validate, the message is returned as is, eitherway the key will be discarded.<br><br>


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to have installed the software

```
* Java 21/25 (Oracle or OpenJDK)
* Maven 3.9
* Docker (optional)
```

### Running

There are multiple ways to run it:

With maven

```
mvn spring-boot:run
```

Shell with Docker

```
./start.sh
```

Shell with Java

```
./run.sh
```

In case the script does not have enough rights to run, try this before running it again:

```
$ chmod +x start.sh
```

## Endpoints

* http://localhost:8080/enigma/         GET to index
* http://localhost:8080/enigma/encrypt  POST to editor
* http://localhost:8080/enigma/encode   POST to REST encoder

```
{"key":"aba", "message":"ABBA"} --> {"key":"", "message":"YYWX"}
```
Sending again the encrypted message using the same key will decrypt the message:
```
{"key":"aba", "message":"YYWX"} --> {"key":"", "message":"ABBA"}
```

## Running the tests

The project runs tests fed from a CSV file and you can add test cases very easily, just append the file with a value for two inputs, the input key and message, and another two outputs same for key and encrypted message.<br>
Remember the returned key is always empty.

```
mvn test
```

## Built With

* [Intellij Idea](http://www.jetbrains.com/) - The IDE used
* [Maven 3](https://maven.apache.org/) - Dependency Management
<strike>
* [Spring 5](https://spring.io/) - Java Framework
</strike>
* [Spring 6](https://spring.io/) - Java Framework
<strike>
* [Kubuntu 20.04](http://kubuntu.com) - The distro I use
</strike>
* [OpenSUSE Tumbleweed](http://kubuntu.com) - The distro I use
<strike>
* [OpenJDK 14](https://openjdk.java.net/) - The Java version I use
</strike>
* [OpenJDK 25](https://openjdk.java.net/) - The Java version I use
* Patience ;)

