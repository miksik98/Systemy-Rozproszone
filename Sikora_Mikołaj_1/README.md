#ZADANIE 1 - TCP/UDP
##Treść

 1. **Napisać aplikację typu chat** (5 pkt.) 
    * Klienci łączą się serwerem przez protokół TCP 
    * Serwer przyjmuje wiadomości od każdego klienta i rozsyła je do pozostałych (wraz z id/nickiem klienta) – Serwer jest wielowątkowy – każde połączenie od klienta powinno mieć swój wątek 
    * Proszę zwrócić uwagę na poprawną obsługę wątków
 2. **Dodać dodatkowy kanał UDP** (3 pkt.) 
    * Serwer oraz każdy klient otwierają dodatkowy kanał UDP (ten sam numer portu jak przy TCP) 
    * Po wpisaniu komendy ‘U’ u klienta przesyłana jest wiadomość przez UDP na serwer, który rozsyła ją do pozostałych klientów 
    * Wiadomość symuluje dane multimedialne (można np. wysłać ASCII Art) 
 3. **Zaimplementować powyższy punkt w wersji multicast** (2 pkt.) 
    * Nie zamiast, tylko jako alternatywna opcja do wyboru (komenda ‘M’) 
    * Multicast przesyła bezpośrednio do wszystkich przez adres grupowy (serwer może, ale nie musi odbierać)
##Instrukcje
Aby uruchomić program, należy najpierw uruchomić serwer (*Server*), a następnie od 1 do 10 klientów (*Client*). 
<br>
W programie klienta jako pierwsze wpisujemy jego nick. Następnie możemy wysyłać wiadomości (domyślnie TCP).
<br>
Jeśli użyjemy komendy **-U message** wiadomość zostanie wysłana przez protokół UDP, a gdy **-M message** przez multicast UDP. 
Zaimplementowano również komendy **-UF filename** oraz **-MF filename**, które pozwalają kolejno na przesłanie
zawartości pliku tekstowego (Ascii artu) o nazwie *filename* (znajdującego się w katalogu Ascii Arts) przez UDP lub multicast UDP.
<br>
Maksymalną liczbę klientów można zmienić poprzez edycję atrybutu Servera *maxNumberOfClients*, natomiast ścieżkę do folderu *Ascii Arts* poprzez edycję atrybutu Clienta *asciiDirPath*.

##Autor
*Mikołaj Sikora*