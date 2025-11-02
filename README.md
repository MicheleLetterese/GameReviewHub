# GameReviewHub

**La tua destinazione gaming**

Un'applicazione web per la gestione e la visualizzazione di dati relativi a videogiochi, recensioni e vendite globali.

## Autori

*   Francesca Avallone
*   Michele Letterese
*   Gabriel Balasa

---

## 📜 Descrizione del Progetto

GameReviewHub è una web application realizzata con l'obiettivo di offrire un'interfaccia intuitiva per l'esplorazione e la consultazione di un vasto dataset di videogiochi. Sfruttando un approccio NoSQL con MongoDB, il progetto permette di gestire in maniera efficiente informazioni eterogenee che includono dettagli sui giochi, recensioni di critici e utenti, e dati di vendita suddivisi per area geografica.

L'applicazione consente di effettuare operazioni di inserimento, modifica, eliminazione e ricerca (CRUD), fornendo una gestione completa e dinamica dei dati.

### Fonte dei Dati

I dati utilizzati sono stati estratti da un dataset pubblico disponibile sulla piattaforma Kaggle: [Video Game Sales and Ratings](https://www.kaggle.com/datasets/thedevastator/video-game-sales-and-ratings/data).

---

## ✨ Funzionalità Principali

*   **Inserimento di nuovi videogiochi**: Aggiungi nuovi titoli al database, includendo informazioni su recensioni e vendite in un'unica operazione per garantire la coerenza dei dati.
*   **Visualizzazione e Ricerca**: Esplora l'elenco completo dei videogiochi e utilizza filtri avanzati per cercare titoli in base alla piattaforma o a una soglia minima di punteggio (sia degli utenti che della critica).
*   **Aggiornamento dei dati**: Modifica le informazioni esistenti di un gioco, con la possibilità di aggiornare singolarmente i dati principali, le recensioni o le vendite.
*   **Eliminazione**: Rimuovi un videogioco dal database in modo completo o parziale (eliminando solo le recensioni o solo i dati di vendita).

---

## 🛠️ Stack Tecnologico

Il progetto è stato sviluppato utilizzando le seguenti tecnologie:

*   **Database**: MongoDB, un database NoSQL document-oriented che offre flessibilità e scalabilità.
    *   **Tool di Gestione**: MongoDB Compass per la visualizzazione e il debugging dei dati.
*   **Back-end**: Java Servlet, seguendo il pattern architetturale Model-View-Controller (MVC) per una chiara separazione tra logica applicativa e presentazione.
*   **Front-end**: JSP (Java Server Pages) e il framework Bootstrap per creare un'interfaccia utente semplice, responsiva e intuitiva.
*   **IDE**: IntelliJ IDEA.
*   **Driver**: Driver ufficiale MongoDB per Java per l'interazione con il database.

---

## 🏛️ Architettura

L'applicazione adotta il pattern architetturale **Model-View-Controller (MVC)**:

*   **Model**: Rappresenta le entità principali (Videogioco, Review, Sales) e gestisce l'interazione diretta con MongoDB.
*   **View**: Implementata con JSP e Bootstrap, si occupa della presentazione dei dati all'utente e fornisce i moduli per le operazioni CRUD e di ricerca.
*   **Controller**: Realizzato tramite Servlet Java, riceve le richieste HTTP dell'utente, invoca la logica del Model e aggiorna la View.

---

## 🚀 Estensibilità Futura

Il progetto è stato progettato tenendo a mente possibili espansioni future, tra cui:

*   **Gestione degli utenti e autenticazione**: Per differenziare i ruoli (amministratore, utente base) e aumentare la sicurezza.
*   **Analisi avanzata**: Implementazione di strumenti di reportistica e visualizzazione grafica.
*   **Integrazione con API esterne**: Per arricchire il database con dati in tempo reale.
*   **Arricchimento dei dati**: Aggiunta di nuove informazioni come immagini, trailer e descrizioni.
*   **Funzioni social**: Introduzione di sezioni per commenti e valutazioni dirette da parte degli utenti.

---

## 📝 Conformità BASE e CAP

Il sistema soddisfa le proprietà **BASE** (Basically Available, Soft state, Eventual consistency), tipiche dei sistemi NoSQL. In accordo con il **Teorema CAP**, il sistema privilegia la Disponibilità (Availability) e la Tolleranza alle Partizioni (Partition Tolerance) a discapito di una consistenza immediata.
