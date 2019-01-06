# CoolBank - Semestrální práce KIV/PIA 2018-2019

Autor: Michal Drda, K16N0001P, drdam
E-mail: drdam@students.zcu.cz

## Základní informace

V bankovnictví existují dva typy uživatelů, kteří mají různé pravomoce:

1. Admin - může se přihlásit, odhlásit, změnit údaje uživatelů (i adminů),
vidí veškeré zaregistrované uživatele, může registrovat a mazat uživatele (i
administrátory, včetně sebe - poté se nemůže znovu přihlásit).
2. User - může se přihlásit, odhlásit, provádět bankovní převody, ukládat si
platby jako šablonu, změnit stávající šablonu, použít pro platbu šablonu,
upravit svoje informace účtu, změnit si heslo, vidí svoje odchozí a příchozí platby.

Nepřihlášený uživatel (host) se může přihlásit, nebo si přečíst informace o
bance na stránce about, kde může zobrazit email tvůrce webu.

Aplikace ke své funkčnosti používá Maven, Tomcat, Spring, JPA Hibernate, log4j, Mockito, MySQL DB, Bootstrap a Datatables.

## Základní uživatelé, naplnění databáze

Databáze je naplněna ihned při startu aplikace pomocí třídy Loader v package
drdm.school.pia.config, v případě, že je v properties nastaveno, že
db.state=create. Hodnota se propadne také do applicationContext.xml property hibernate.hbm2ddl.auto.

V databázi jsou založeni 2 standardní uživatelé se svými účty a kartami, a 1 administrátor:

1. jméno: User0001, heslo: 0001
2. jméno: User0002, heslo: 0002
3. jméno: Admin001, heslo: 1234

Pro každého z uživatele je vytvo?eno celkem 15 plateb (počet lze regulovat
parametrem v properties), které si poslali střídavě mezi sebou. Některé z
plateb jsou zároveň šablonou.

## Instalace aplikace

V tomto ZIP file jsou dvě složky s projekty: 

### Složka app1 obsahuje docker instalaci s využitím školní databáze.

Ve školní databázi jsou tabulky i data vytvo?ena, proto v případě, že nechcete
vytvořit data znovu změňte db.state=create > db.state=update

Start aplikace ve variantě 1.

```
cd app1
docker build -t pia/orionlogin
docker-compose up
```


### Složka app2 obsahuje docker instalaci s využitím databáze založené dockerem

Start aplikace ve variantě 2.

```
cd app2
docker build -t pia/orionlogin
docker-compose up
```

## Architektura aplikace

Aplikace využívá architekturu prezentovanou na cvičeních, založenou na
Springu, Tomcatu, Mavenu a JPA Hibernate.

Pro komunikaci s klientem slouží Servlety, filtery a auth service - package
'web'. 
Data jsou připravována pro Hibernate pomocí objektů v package 'domain'. 
Aplikační logika je tvořena v package 'manager', a přístup k datům v databázi je řešen v package dao. 
Z důvodu dobré použitelnosti autor vytvořil package 'dto', ve kterém existují objekty
sloužící pouze k vytvoření nedatabázového objektu a jeho následném vložení do
seznamu, který je posílán jako atribut k vizualizaci tabulek.
Pro potřeby práce s řetězci (kódování, generování, validace) slouží třídy v
package utils.
Pro naplnění dat do databáze slouží package 'config', kde ve třídě Loader
po spuštění vzniknou (v případě, že je tak aplikace nastavena) základní 3
uživatelé, se svými účty a kartami, a jejich platby.

Unit testy se nachází v 'src/test/*'

Hibernate a entity manager je definován v aplikačním kontextu, stejně jako
properties soubor, který obsahuje všechnyí konfigurační údaje.

## Design stylování

V aplikaci je použit Bootstrap, ke stylování stránkování tabulek je
naimportovaná knihovna Datatables, která pro svoje správné fungování potřebuje
svůj importovaný CSS. Tento styl autor v některých částech přetěžuje vlastním designem.

## Funkce webu podrobněji

### Z pohledu nepřihlášeného uživatele:
Nepřihlášený uživatel se může přihlásit, může také navštívit stránku 'About',
kde si může přečíst krátké info o bance CoolBank, a po kliknutí na tlačítko
javascriptem zobrazit e-mail tvůrce webu ve formátu mailto odkazu.

### Z pohledu administrátora:
####Administrátor po přihlášení vidí svoji hlavní stránku - 'Managing'
Na této stránce vidí tabulku s registrovanými uživateli (pouze s rolí USER),
políčko pro vyplnění e-mailu uživatele k editaci a dvě tlačítka (vymazání a editace uživatele).
Při vyplnění validního - existujícího emailu (i administrátorského) je možné
clickem na jedno z tlačítek zobrazit příslušný formulář.

Ve formuláři pro mazání uživatele je administrátor upozorněn, že se chystá
smazat uživatele se zadaným emailem, pokud volbu potvrdí, uživatel je vymazán,
což automaticky updatuje tabulku s uživateli.
Ve formuláři pro editaci uživatele administrátor může změnit libovolná pole, a
pokud jsou vstupy validní, uživatel je okamžitě aktualizován, a je znovu načtena tabulka s uživateli.
Tabulka s registrovanými uživateli s rolí user má implementované stránkování.
Pokud nastane chyba, je adminovi zobrazena chybová hláška, pokud je vše v
pořádku, je admin upozorněn na úspěšnost operace.

####Druhá stránka, kterou může admin používat je 'Register'
Zde je možné registrovat uživatele nebo administrátory.
Všechna pole označená hvězdičkou je nutné vyplnit, vstupy jsou validovány
regex patterny, a v případě, že nějaký ze vstupů neodpovídá požadované
hodnotě, je zobrazena chyba, a dojde k vymazání hesel, captcha pole a
akceptace podmínek používání.

Pokud je vše v pořádku, je registrován nový uživatel/admin, a administrátor je
informován o úspěchu operace.

Protože administrátor nesmí vidět přihlašovací údaje uživatele, a hesla ani
přístupové údaje by se e-mailem odesílat neměly z důvodu bezpečnosti (e-mail
není zabezpečená komunikace), je zaveden p5edpoklad, že uživatel d2lá
registraci na pobočce, a je administrátorem vyzván, aby vyplnil svoje heslo
(které si může později změnit) v průběhu registrace. Uživatelské jméno je
odesláno poštou spolu s platební kartou pomocí jiné aplikace používající
stejnou databázi (neimplementováno). 

Pozn.: Pro případ této aplikace je karta prakticky k ničemu, pro ověření
správnosti generovaných hodnot není pin ani cvc zašifrováno, aby bylo vitelné
v DB - tvůrce si je vědom toho, že takovéto údaje by měly být v praxi šifrované).

Přihlašovací údaje nově registrovaného uživatele je možné vidět v logu, kam je
nový uživatel/admin zalogován, nebo přímo v databázi.

Kliknutím na název banky je uživatel přenesen na stránku 'Managing'

### Z pohledu uživatele:
####Uživatel po přihlášení vidí svoji main-page - 'Banking'

Na hlavní stránce vidí stav a číslo svého účtu, tlačítka pro změnu údajů svého
účtu, a tlačítko na změnu hesla. Dále vidí tabulku s posledními deseti
transakcemi, které se vážou k jeho účtu (příchozí a odchozí). Pro použitelnost
aplikace je každému uživateli defaultně nastaven stav účtu na 10000 Kč (z properties).

Po poklepání na tlačítko ke změně hesla je uživateli zobrazen formulář ke
změně hesla. Jeho pole nejsou při chybném vstupu vyplňována záměrně - hesla se
nevyplňují, captcha také ne.
Po poklepání na tlačítko ke změně uživatelských údajů jsou uživateli zobrazeny
údaje o jeho osobě, které může změnít a volbu odeslat potvrzením tlačítka.
Povinná pole jsou označena hvězdičkou a validována regex patterny. Adresa,
město a zip kód validovány nejsou (ani v registraci uživatele), vstupy jsou
pouze přeloženy do escapovaného řetězce pomocí hibernate - platí pro všechny
formuláře.

####Další stránkou přístupnou pro uživatele je stránka 'Payment'

Zde může uživatel buď vyplnit platební údaje, nebo použít šablonu, pokud má
nějakou uloženou. Pokud uživatel vybere šablonu a poklepá na tlačítko 'Load
template', je šablola načtena.
Pokud uživatel chce, může vyplnit číslo účtu s prefixem (odděleno '-',
kontrolováno regex validací). Kódy bank si může vybrat ze selectu, případně
vyplnit vlastní kód. Datum platby nesmí být v minulosti, čísla symbolů mohou
mít maximálně 10 znaků (standard).
Uživatel si může vybrat měnu ve které chce příkaz provést. Kurzy jsou načtené
z properties, platba vždy proběhne v korunách (učty jsou v korunách), je však
přepočítána daným kurzem.
Pokud si uživatel přeje, může platbu uložit jako šablonu (v takovém případě
musí platbu i odeslat). Při volbě uložit jako šablonu je nutné vyplnit její
název.

####Poslední stránkou rozhraní uživatele je 'Transaction history'
Zde může uživatel procházet veškeré předchozí platby, které se vážou k jeho účtu. Je
zde nastaveno stránkování (volitelné 10/25/50/100). Pokud dosud neuskutečnil
žádné platby, v obou tabulkách (i v Bankingu) je zobrazeno, že v tabulce
nejsou žádné vstupy.

Kliknutím na název banky je uživatel přenesen na stránku 'Banking'

###Společné funkce
Administrátor i uživatel vidí po celou dobu svého přihlášení svoje navigační
menu, a v pravém rohu tlačítko k odhlášení a informaci pod jakým účtem je přihlášen.

Po odhlášení je administrátorovi i uživateli ukázána logout stránka, z které je po 3
vteřinách (parametr z properties) přesměrován na úvodní stránku s možností
přihlášení.

## Testy

Pro objekt User a metody třídy DefaultUserManager jsou vytvořeny testy. Testy
metod jsou implementovány pomocí testovacího unit testů Mockitem.

## Známé problémy v aplikaci

### Čeština v DB z Dockeru
V dockeru aplikace nepodporuje české znaky (ěčřůďňť...). Pokud běží aplikace s
databází nevytvořenou dockerem, pak je vše v pořádku. Autorovi se nepodařilo
databázi nakonfigurovat tak, aby se toto nedělo, proto jsou v archivu dvě
aplikace, aby bylo možné funkčnost češtiny vyzkoušet. Proto je doporučeno
používat aplikaci z adresáře 'app1'.

Autor použil nastavení, jak bylo ukázáno na cvičení, ale nepodařilo se mu
zjistit jak databázi tvo?enou dockerem nakonfigurovat tak, aby vše fungovalo.

### HTML Validace datumu
Stránka 'Payment' neprochází validací kvůli input date a použití
placeholderu. Toto je způsobeno faktem, že v různých prohlížečích funguje pole
date jinak, a autorovi přišlo vhodné tohoto inputu využít. Proto je v poli
kvůli IE placeholder. Druhou možností by bylo nechat uživatele psát datum
ručně, a následně jej validovat regexem, to je však uživatelsky nepohodlné.

## Popis rozšiřujících funkcionalit aplikace
1. Možnost provádět operace v různch měnách (CZK, USD, EUR). Kurzovní lístek
řešen seznamem v properties.
2. Kontrola existence cílového účtu. Uživatel není informován, ale v případě,
že protiúčet existuje, jsou na něj přičteny peníze.
3. Kód banky ve formulářích realizovány formou selectboxu s existujícími
bankami. Select box je možné použít, nebo vyplnit vlastní kód, realizováno
seznamem z properties.
4. Pro uživatele jsou formuláře na úvodní stránce zobrazovány javascriptem
(ušetření výkonu serveru). Pro administrátora pomocí jsp (malý počet
administrátorů = nevelké zatížení serveru, lepší použitelnost a aktualizace
údajů).
5. Stránkování v tabulkách je řešeno knihovnou Datatables psané v jQuery. V
tabulkách je vypnuta možnost řazení a vyhledávání v jejich textu.
6. Všechny stránky obsahují meta description pro lepší vyhledávání dle SEO.
7. Stránky včetně tabulek jsou všechny plnně responzivní. Méně džležité
sloupce tabulek jsou při zobrazení na mobilním telefonu skryty.
8. Pro logování v aplikaci je použito log4j.
9. Většina vstupů je ošetřena pomocí regex validace, částku platby je možné
zadat s čárkou nebo tečkou. Email využívá regex dle normy RFC 5322.
10. Plnění databáze je plnně automatické, pomocí třády 'Loader', která po
startu aplikace databázi naplní, pokud je v properties db.state=create.
11. Veškeré z logiky konfigurovatelné nastavení je řešeno parametry z properties.
12. Expirace karet je počítána z aktuálního datumu přičtením 60 měsíců
(parametr z properties).
13. Role uživatelů jsou limitovány parametrem z properties (user.roles=ADMIN,USER).
14. Aplikace je připravena na to, že uživatel může mít více platebních karet,
toto řešení však není implementováno v UI.
15. Tabulka s platbami je propojena pouze logicky, nikoli databázovou vazbou,
následné zobrazení tabulek v uživatelském rozhraní je přes zvláštní objekty v
package 'dto'.
16. Pokud uživatel/administrátor přistoupí po přihlášení na stránku index, je
přesměrován na svoji úvodní stránku zobrazovanou po přihlášení.
17. Po odhlášení běží 3 sekundový limit, kdy je uživateli/adminovi zobrazena
logout page, poté je přesměrován na index.