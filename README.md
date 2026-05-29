# GK8.2 Document Oriented Middleware using MongoDB

**Name:** Fabian Steinhauser

**Repository:** https://github.com/FabianBoolean/DEZSYS_GK_WAREHOUSE_DOM_Steinhauser

---

# Einleitung

Ziel dieser Übung war die Umsetzung einer dokumentenorientierten Middleware mit MongoDB. Die Daten werden über REST-Schnittstellen empfangen und zentral in einer MongoDB-Datenbank gespeichert.

Für die Implementierung wurde Spring Boot mit Spring Data MongoDB verwendet. Die Produktdaten werden als JSON-Dokumente gespeichert und können sowohl über REST-Endpunkte als auch über die Mongo Shell abgefragt werden.

---

# Installation und Start

## MongoDB Container starten

```bash
docker run -d --name mongo -p 27017:27017 mongo
```

## Anwendung starten

```bash
./gradlew bootRun
```

Die Anwendung läuft anschließend unter:

```text
http://localhost:8080
```

---

# Verwendete Datenstruktur

Jedes Produkt wird als eigenes Dokument in MongoDB gespeichert.

Beispiel:

```json
{
  "warehouseID": "1",
  "productID": "00-443175",
  "productName": "Bio Orangensaft Sonne",
  "productCategory": "Getraenk",
  "productQuantity": 2500
}
```

## Felder

| Feld | Beschreibung |
|--------|--------|
| warehouseID | Lagerstandort |
| productID | Produktnummer |
| productName | Name des Produktes |
| productCategory | Produktkategorie |
| productQuantity | Lagerbestand |

Diese Struktur ermöglicht die Speicherung mehrerer Lagerstandorte und beliebig vieler Produkte.

---

# Implementierte REST-Schnittstellen

## Produkte abrufen

```bash
curl http://localhost:8080/product
```

Liefert alle gespeicherten Produkte.

---

## Einzelnes Produkt abrufen

```bash
curl http://localhost:8080/product/00-871895
```

Liefert ein bestimmtes Produkt anhand der Produkt-ID.

---

## Neues Produkt anlegen

```bash
curl -X POST http://localhost:8080/product \
-H "Content-Type: application/json" \
-d '{
  "warehouseID":"1",
  "productID":"07-777777",
  "productName":"Testprodukt Schokolade",
  "productCategory":"Suesswaren",
  "productQuantity":123
}'
```

---

## Produkt löschen

```bash
curl -X DELETE http://localhost:8080/product/07-777777
```

---

## Alle Lagerstandorte abrufen

```bash
curl http://localhost:8080/warehouse
```

---

## Produkte eines Lagerstandortes abrufen

```bash
curl http://localhost:8080/warehouse/1
```

---

# Testdaten

Für die Demonstration wurden insgesamt 10 Produkte gespeichert.

Verwendete Kategorien:

- Getraenk
- Waschmittel
- Tierfutter
- Reinigung

Verwendete Lagerstandorte:

- Lager 1
- Lager 2

---

# Mongo Shell CRUD Operationen

## 1. READ

Alle Produkte anzeigen:

```javascript
db.productData.find()
```

---

## 2. CREATE

Neues Testprodukt anlegen:

```javascript
db.productData.insertOne({
  warehouseID: "1",
  productID: "99-999999",
  productName: "Mongo Testprodukt",
  productCategory: "Test",
  productQuantity: 111
})
```

Kontrolle:

```javascript
db.productData.find({
  productID: "99-999999"
})
```

---

## 3. UPDATE

Bestand ändern:

```javascript
db.productData.updateOne(
  { productID: "99-999999" },
  { $set: { productQuantity: 222 } }
)
```

Kontrolle:

```javascript
db.productData.find({
  productID: "99-999999"
})
```

Ergebnis:

```text
productQuantity: 222
```

---

## 4. FILTER

Produkte mit Lagerbestand kleiner oder gleich 500:

```javascript
db.productData.find({
  productQuantity: { $lte: 500 }
})
```

---

## 5. DELETE

Testprodukt löschen:

```javascript
db.productData.deleteOne({
  productID: "99-999999"
})
```

Kontrolle:

```javascript
db.productData.find({
  productID: "99-999999"
})
```

Ergebnis:

```text
kein Dokument gefunden
```

---

# Beantwortung der Fragestellungen

## 1. Vorteile von NoSQL

- Flexibles Schema
- Hohe Skalierbarkeit
- Gute Performance bei großen Datenmengen
- Speicherung komplexer JSON-Dokumente

## 2. Nachteile von NoSQL

- Kein standardisiertes SQL
- Teilweise schwächere Konsistenzmodelle
- Datenredundanz möglich
- Beziehungen schwieriger abzubilden

## 3. Schwierigkeiten bei der Zusammenführung von Daten

- Unterschiedliche Datenformate
- Unterschiedliche ID-Systeme
- Datenkonflikte
- Synchronisationsprobleme

## 4. Arten von NoSQL-Datenbanken

- Dokumentenorientierte Datenbanken
- Key-Value-Datenbanken
- Spaltenorientierte Datenbanken
- Graphdatenbanken

## 5. Vertreter der einzelnen Typen

| Typ | Vertreter |
|------|------|
| Dokumentenorientiert | MongoDB |
| Key-Value | Redis |
| Spaltenorientiert | Apache Cassandra |
| Graphdatenbank | Neo4j |

## 6. CAP-Theorem

### CA

Konsistenz und Verfügbarkeit, jedoch keine Partitionstoleranz.

### CP

Konsistenz und Partitionstoleranz, jedoch eingeschränkte Verfügbarkeit bei Netzwerkproblemen.

### AP

Verfügbarkeit und Partitionstoleranz, dafür kurzfristig eventuell inkonsistente Daten.

## 7. Lagerstand eines Produktes über alle Lagerstandorte

```javascript
db.productData.find({
  productID: "00-443175"
})
```

## 8. Lagerstand eines Produktes eines bestimmten Lagerstandortes

```javascript
db.productData.find({
  warehouseID: "1",
  productID: "00-443175"
})
```

---

# Quellen

- https://spring.io/projects/spring-data-mongodb
- https://spring.io/guides/gs/accessing-data-mongodb
- https://www.mongodb.com/docs/manual
- https://www.mongodb.com/docs/mongodb-shell
- https://spring.io/projects/spring-boot
- https://spring.io/guides/gs/rest-service
- https://www.oracle.com/at/database/nosql/what-is-nosql