# Elliot - IMG Arena Coding Challenge 2021

IMG Arena Software engineer coding challenge 2021.

### Testing

To run the test suite, run the following command:

```shell
mvn clean verify
```

### Running the application

#### Using docker / docker-compose

The easiest way to run the application (assuming no other services running on the same ports!) is to
use docker-compose:

```shell
docker-compose up
```

Running via docker-compose will start up a postgres database, initialized with the required
database, listening on port 5432, and the BE service, on port 8080.

Alternatively, if you've already got a database configured separately (with a database/schema
called : `img_arena_golf`), then you can run the BE service on it's on:

```shell
docker build --tag img-arena-coding-challenge-2021 .

docker run --rm -p 8080:8080 -e SPRING_DATASOURCE_URL=CHANGE_ME -e SPRING_DATASOURCE_USERNAME=CHANGE_ME -e SPRING_DATASOURCE_PASSWORD=CHANGE_ME img-arena-coding-challenge-2021
```

Finally, if you don't have docker, you can execute the following maven command, define a custom
profile with the required datasource arguments and run the following:

```shell
 mvn spring-boot:run "-Dspring-boot.run.profiles=SOME_PROFILE
```

### Usage

The application currently exposes two endpoints, the main endpoint to interact with is the POST - '
/golf/tournaments' endpoint. Requests **must** send a valid header called `X-DATA-SOURCE-ID`. For
this coding challenge, the value is either `DATA-PROVIDER-1` or `DATA-PROVIDER-2` depending on which
payload you want to send:

### Examples

```shell
curl --location --request POST 'http://localhost:8080/golf/tournaments/' \
--header 'X-DATA-SOURCE-ID: DATA-PROVIDER-1' \
--header 'Content-Type: application/json' \
--data-raw '{
	"tournamentId": "174638",
	"tournamentName": "Women'\''s Open Championship",
	"forecast": "fair",
	"courseName": "Sunnydale Golf Course",
	"countryCode": "GB",
	"startDate": "09/07/21",
	"endDate": "13/07/21",
	"roundCount": "4"
}'
```

```shell
curl --location --request POST 'http://localhost:8080/golf/tournaments/' \
--header 'X-DATA-SOURCE-ID: DATA-PROVIDER-2' \
--header 'Content-Type: application/json' \
--data-raw '{
    "tournamentUUID":"southWestInvitational",
    "golfCourse":"Happy Days Golf Club",
    "competitionName":"South West Invitational",
    "hostCountry":"United States Of America",
    "epochStart":"1638349200",
    "epochFinish":"1638468000",
    "rounds":"2",
    "playerCount":"35"
}'
```

### Extension

In order to extend the application and add in the ability to process different data providers, all
you need to do is:

1. Create your
   own [mapper](https://github.com/ElliotJBall/IMG_Arena_2021/blob/main/src/main/java/com/imgarena/coding/challenge/mapper/GolfTournamentMapper.java)
   implementation (
   See [here](https://github.com/ElliotJBall/IMG_Arena_2021/blob/main/src/main/java/com/imgarena/coding/challenge/mapper/DataProviderOneMapper.java)
   and [here](https://github.com/ElliotJBall/IMG_Arena_2021/blob/main/src/main/java/com/imgarena/coding/challenge/mapper/DataProviderTwoMapper.java)
   for examples).
2. (Optional) Define an intermediary DTO/POJO in which to parse the incoming JSON payload into, this provides a way to perform some kind of sanitation/validation on the incoming data. (Extending the following [class](https://github.com/ElliotJBall/IMG_Arena_2021/blob/main/src/main/java/com/imgarena/coding/challenge/mapper/AbstractGolfTournamentMapper.java))  
3. Provide the client with the `dataProviderId` key you've assigned to the mapper. The client will
   then send this as a header when sending data to the `/golf/tournaments/` endpoint so that your
   mapper is used to parse the incoming JSON.
4. Test coverage!