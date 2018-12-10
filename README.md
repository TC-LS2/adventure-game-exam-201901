Adventure Game - the exam
=========================

Llegeix l'enunciat en paper detingudament. 
Escriu el teu nom, cognoms i DNI en el paper. 
Recorda que hauràs d'entregar el paper en acabat l'examen.

Durant l'examen:

- Fes commit i push de la teva solució com a mínim cada 30 minuts.
  No fer-ho implicarà una penalització en la nota.
  
- Només pots modificar els següents fitxers:

  - `README.md`
  - `src/main/java/com/drpicox/game/combinations/CombinationsWorldParser.java`
  - `src/test/java/com/drpicox/game/CombineAndCreateTests.java`
  - `src/test/java/com/drpicox/game/TestHelper.java`
  - `src/www/blog/index.js`
  - `src/www/blog/combine-and-create.js`
  - `src/www/__test__/combineAndCreate.spec.js`
  - `src/www/ducks/world-builder/__test__/StateWorldEditor.spec.js`
  - `src/www/ducks/world-builder/components/WorldEditor.js`
  - `src/www/ducks/game/components/CombineView.js`

- Pots crear tants fitxers com creguis necessaris dins de  
  `src/main/java/com/drpicox/game/combinations`.
  També pots crear un nou test backend, un nou test frontend i un blogpost.

- Qualsevol codi afegit o modificat que pugui ser borrat o 
  simplificat i els tests seguint pasant penalitzaran la nota.
  
- Un test nou i correctament escrit, serà avaluat positivament,
  encara que no pasi perqué el codi no estigui dessenvolupat.

## 0) Setup

Clona el repositori en el teu ordinador.
El entorn de treball ha sigut provat amb IntelliJ IDEA 2018.3, 
apache-maven-3.5.3, jdk-11.0.1, node v8.11.2 i yarn 1.7.0. També s'ha fet funcionar en
jdk-10.0.1 i en openjdk-10.0.2.

**Backend**:

- Importa el repositori clonat amb el IntelliJ com a **projecte Maven**
- Executa tots els tests per verificar que tot funciona.

**Frontend**

- Executa `yarn install`
- Executa tots els tests amb `yarn test` per verificar que tot funciona.


## 1) Les teves dades  (-1pt si ausent)

- Escriu el teu nom, cognoms i DNI a continuació:

Nom: ____________________  
Cognoms: ________________  
DNI: ____________________  


## 2) Explica el refactor (1pt)

Algun dels participants del projecte s'han queixat de què el codi
no té prou qualitat. A conseqüència, el cap del projecte ha contractat
a un Arquitecte Spring extern per a refactoritzar el codi.

Gràcies als nombrosos tests i sense tocar ni una línia dels tests existents,
l'arquitecte hi ha refactoritzat completament els packages `com.drpicox.game.bags`
i `com.drpicox.game.world`. També ens ha comentat que ha fet una gran
feina a `com.drpicox.game.commands`. però queda incomplet en espera de
refactoritzar `com.drpicox.game.items`, `com.drpicox.game.monsters` i 
`com.drpicox.game.rooms`. D'aquests ens comenta que tan sols ha modificat 
els corresponents parsers de World.

L'arquitecte ens explica que la clau del refactor ha sigut invertir
el control d'algunes de les col·leccions existents. En comptes de construir
i mantenir manualment llistes a unes clases centralitzades,
s'utilitza l'Spring per detectar i afegir automàticament coses en aquestes
llistes.

Per aconseguir-ho aprofita que el Spring, quan injecta una llista
d'un tipus, aquest afegeix tots els components d'aquell tipus. Ex:

```java
@Component
public class MyConcreteType implements MyType {
    // ...
}

@Service
public class MyService {
    public MyService(List<MyType> myTypeList) {
        // myTypeList conté un MyConcreteType
        // i qualsevol altre del mateix tipus
    }
}
```

A més, per a evitar l'ús accidental d'algun d'aquests components,
ha creat noves anotacions que permeten el registre específic i controlat
mitjançant l'anotació `@Qualifier` d'Spring.

A manera d'exemple ens ha ensenyat que al package 'com.drpicox.game.world':

- Ha creat la interfície `WorldParser` que des d'aquest moment farà el parseig del `World`.

  ```java
  public interface WorldParser {
      void parse(World world);
  }
  ```

- `WorldParserCollector`: recull tots els `WorldParser` amb l'anotació `WorldParserComponent`
i els executa.

  ```java
  @Component
  public class WorldParserCollector implements WorldParser {
      private List<WorldParser> worldParserList;
      public WorldParserCollector(
              @Qualifier("com.drpicox.game.world.worldParserList")
              List<WorldParser> worldParserList
      ) {
          this.worldParserList = worldParserList;
      }
      // ...
  }
  ```
  
- `WorldParserComponent` és la anotació que cal que usin
  els components que vulguin ser recollits pel `WorldParserCollector`.
  
  ```java
  @Component
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Qualifier("com.drpicox.game.world.worldParserList")
  public @interface WorldParserComponent {}
  ```

  L'exemple d'ús ens el mostra a `com.drpicox.game.items.ItemWorldParser`:
  
  ```java
  @WorldParserComponent
  public class ItemWorldParser implements WorldParser {
    // ...
  }
  ```

- A més ha modificat `World` per admetre més tipus de dades,
  i `WorldRestController` delega a `WorldParserComponent` el parseig de `World`.
  

### Respon aquí:

- Quin altre _Collector_ ha fet?

________________________________________________

- Qui delega en aquest _Collector_?

________________________________________________

- Quina interfície recull i implementa aquest _Collector_?

________________________________________________

- Quines anotacions cal usar pels components que vulguin ser 
  recollits per aquest _Collector_?

________________________________________________
________________________________________________
________________________________________________
________________________________________________

- Mira el codi del _Collector_. Quin rol satisfà cada una de les anotacions?

________________________________________________
________________________________________________
________________________________________________
________________________________________________

- Busca els usos de les anotacions. Una de les anotacions porta 
  un paràmetre, ex: `@Anotació("parametre")`.
  Quina anotació és? Que configura el paràmetre?

________________________________________________
________________________________________________
________________________________________________
________________________________________________

- La majoria de les classes que implementen la interfície 
  d'aquell _Collector_ estan implementades al mateix package. 
  Però n'hi ha unes poques que estan en un altre package. 
  Quin package és? Quines classes son? Que fan cada classe?

________________________________________________
________________________________________________
________________________________________________
________________________________________________
________________________________________________
________________________________________________

- Mira el package `com.drpicox.game.bags`. 
  Com es relaciona `Bag` amb `com.drpicox.game.players.Player`?

________________________________________________
________________________________________________
________________________________________________

- Qui i com fa treballar junts `Bag` i `Player` a l'acció `get`?

________________________________________________
________________________________________________
________________________________________________



## 3) Fase 1: Implementa el codi de test de la fase 1 a backend  (2pt)

En el fitxer `src/test/java/com/drpicox/game/CombineAndCreate.java`:

- No toquis `decorateMustache`, ja està implementat.

- Implementa el mètode `buildWorld`:
  Fixa't que WorldBuilder admet el mètode `lines`.

- Implementa els snapshots de `world_text_snapshot` i `world_map_snapshot` 
  Fixa't en com han quedat amb el nou `lines`.

- Implementa els tests que creguis adients i posa els noms dels títols del blog.


## 4) Fase 1: Implementa el codi de producció de la funcionalitat backend  (2pt)


- Crea el controlador del package.

- Afegeix les entitats que creguis necessaris.

- Afegeix els repositoris que creguis necessaris. 

- Actualitza el TestHelper per netejar el repositori.

- Actualitza el CombinationsWorldParser com creguis convenient.

- Crea i implementa la comanda.


## 5) Fase 1: Completa el POST del Blog del frontend  (1pt)

- `src/www/blog/combine-and-create.js`:
  Escriu el post i els seus continguts.
  
## 6) Fase 1: Implementa els tests del frontend  (1pt)

- `src/www/ducks/world-builder/__test__/StateWorldEditor.spec.js`
  Verifica que l'editor pot afegir noves combinacions. 
  
- `src/www/__test__/combineAndCreate.spec.js`:
  Verifica que l'usuari sigui capaç de realitzar l'acció.
  

## 7) Fase 1: Implementa el codi del frontend  (1pt)

- `src/www/ducks/world-builder/components/WorldEditor.js`:
  Ofereix la possibilitat d'afegir noves funcionalitats.

- `src/www/ducks/game/components/CombineView.js`:
  Ofereix la possibilitat de realitzar l'acció.


## 8) Implementa la Fase 2  (2pt)

Implementa la Fase 2, creant un nou fitxer de test frontend, un nou de backend, 
el post del blog corresponent, modifica el CombineView, i crea un nou package java amb
els corresponents fitxers.


