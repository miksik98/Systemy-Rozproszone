# ZADANIE 2 - REST API
## Treść

Celem zadania jest napisanie prostej aplikacji webowej rozszerzającej funkcjonalność  wybranego otwartego serwisu udostępniającego REST API. Stworzyć macie Państwo serwis, który:
1. odbierze zapytanie od klienta (formularz na stronie),
2. odpyta serwis publiczny (lub kilka serwisów),
3. dokona odróbki otrzymanych danych (np.: wyciągnięcie średniej, znalezienie ekstremów, porównanie wartości z różnych serwisów itp.),
4. wygeneruje i wyśle odpowiedź do klienta (strona z wynikami).

Na przykład: klient podaje miasto i okres czasu ('daty od/do' lub 'ostatnie X dni'), serwer odpytuje serwis pogodowy o temperatury w poszczególne dni, oblicza średnią i znajduje ekstrema i wysyła do klienta wszystkie wartości (tzn. prostą stronę z tymi danymi). Albo odpytuje kilka serwisów i podaje różnice w prognozach. Serwer wykonać musi co najmniej 5 zapytań.
Wybranie serwisu i funkcjonalności pozostawiam Państwa wyobraźni, zainteresowaniom i rozsądkowi. Listę różnych publicznych API można znaleźć przykładowo na https://public-apis.xyz.

## Wymagania
Klient (przeglądarka) ma wysyłać żądanie na podstawie danych z formularza (statyczny HTML) i otrzymać odpowiedź w formie prostej, wygenerowanej przez serwer strony www. Proszę użyć czystego HTML, bez stylizacji, bez dodatkowych bibliotek front-endowych. Nie musi być piękne, ma działać.
Odpowiedź dla klienta musi być generowana przez serwer na podstawie: 
1. żądań REST do publicznych serwisów 
2. obróbki uzyskanych odpowiedzi.

Serwer ma być uruchomiony na własnym serwerze aplikacyjnym (lub analogicznej technologii), działającym poza IDE.
Sugerowanym językiem jest Java i JAX-RS. Dopuszczalna jest realizacja zadania w innym wybranym języku, pod warunkiem zachowania analogicznego poziomu abstrakcji (operowanie bezpośrednio na żądaniach/odpowiedziach HTTP oraz parsing przekazywanych danych).
Nie używamy gotowych frameworków. Chcę byście użyli Państwo rozwiązań nisko poziomowych i kodowali 'na poziomie' poszczególnych wywołań HTTP.
Nie implementujemy autoryzacji, kontroli sesji itp.! Wybieramy serwisy otwarte lub np.: używające kodów deweloperskich.

## Dodatkowe wymagania
- Konfiguracja własnego środowiska do rozwijania aplikacji internetowych z wykorzystaniem ulubionego IDE.
- Prezentacja wykorzystywanych zapytań HTTP klient-serwer i serwer-serwis_publiczny z wykorzystaniem POSTMANa (do oddania proszę mieć je już zapisane).
 
## Instrukcje
Stworzona przeze mnie aplikacja webowa na podstawie podanych współrzędnych geograficznych odczytuje dokładny adres wybranego
punktu (na podstawie odpowiedniego zapytania REST do http://geocode.xyz) oraz na tej podstawie znajduje najbliższą stację
pomiaru jakości powietrza i wypisuje:
- jej nazwę
- aktualne wartości mierzonych parametrów (SO2, NO2, PM10, PM2.5, C6H6, CO, O3) 
- ich najwyższe wartości w ostatniej dobie
- ich najniższe wartości w ostatniej dobie

Powyższe dane aplikacja pobiera z serwisu http://api.gios.gov.pl.
Aby skorzystać z aplikacji należy wcześniej skonfigurować i uruchomić serwer GlassFish (najlepiej w wersji 5.0.1).
Następnie na stronie http://localhost:*numer_portu*/Mikolaj_Sikora_2_war_exploded/index.html powinien być dostępny odpowiedni formularz.
## Autor
*Mikołaj Sikora*
