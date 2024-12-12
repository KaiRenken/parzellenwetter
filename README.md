# Parzellenwetter

### Local running

The web app needs the server and the server needs the DB container to run properly. You can run each of these seperately
or combined by executing the following commands:

#### Run DB container

```sh
$ docker compose up -d postgres
```

#### Run server without web app

```sh
$ mvn clean package -DskipTests
$ java -jar ./target/parzellenwetter-1.0.0-SNAPSHOT.jar
```

#### Run web app

Running the web app using the following command will enable you to change the React code and immediately see the effects
in the browser

```sh
$ npm run --prefix src/main/react dev
```

Access UI via [http://localhost:5173](http://localhost:5173)

#### Run server providing web app
```sh
$ mvn clean package -Pui -DskipTests 
$ java -jar ./target/parzellenwetter-1.0.0-SNAPSHOT.jar
```

Access UI via [http://localhost:8080/gui](http://localhost:8080/gui)

#### Run DB container and build and run server and webapp in docker containers

```sh
$ docker compose up
```

Access UI via [http://localhost:8080/gui](http://localhost:8080/gui)
