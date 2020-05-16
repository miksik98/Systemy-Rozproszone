# ZADANIE 5 - ZOOKEEPER

1) Stworzyć aplikację w środowisku Zookeeper (Java, …) która
    wykorzystując mechanizm obserwatorów (watches) umożliwia
    następujące funkcjonalności:
    - Jeśli tworzony jest znode o nazwie „z” uruchamiana jest zewnętrzna
aplikacja (dowolna, określona w linii poleceń),
    - Jeśli jest kasowany „z” aplikacja zewnętrzna jest zatrzymywana,
    - Każde dodanie potomka do „z” powoduje wyświetlenie graficznej
informacji na ekranie o aktualnej ilości potomków.

2) Dodatkowo aplikacja powinna mieć możliwość wyświetlenia całej
   struktury drzewa „z”.
3) Stworzona aplikacja powinna działać w środowisku „Replicated
   ZooKeeper”.
## Autor
*Mikołaj Sikora*
