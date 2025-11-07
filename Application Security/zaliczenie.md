# Lista zadań zaliczeniowych – Bezpieczeństwo aplikacji

Zadania można wykonać samodzielnie - można w grupach, maksymalnie 3 osobowych. Przy wykonywaniu grupowym ocena będzie nieco bardziej restrykcyjna. Trochę jak w skokach narciarskich

Punkty za wiatr pod narty i w plecy…

Samo zaliczenie - wykonanie do ostatnich zajęć. Wrzucamy na Moodla. Wrzuca każdy z grupy.

Na zaliczenie minimum 60% punktów. 

do 80% ocena dst

81%-95% db

powyżej 95% bdb

---

### 1. Analiza zagrożeń (Threat Modeling)

Przygotuj prosty diagram przykładowej aplikacji (np. sklep internetowy, bankowość online, portal społecznościowy).  
**Zadanie:** zaznacz, które elementy są narażone na ataki (np. logowanie, koszyk, API).  
**Cel:** zrozumienie, że każde wejście do aplikacji to potencjalny punkt ataku.

---

### 2. Symulacja SQL Injection

Weź przykładowy formularz logowania (np. pseudo-kod lub prosty HTML).  
**Zadanie:** spróbuj wymyślić dane wejściowe, które mogłyby „oszukać” system (np. `' OR '1'='1`).  
**Cel:** poznanie zagrożeń związanych z brakiem walidacji i nieużywaniem bezpiecznych zapytań (prepared statements).

---

### 3. Ocena siły haseł

Dostajesz zestaw haseł: `123456`, `qwerty123`, `P@ssw0rd!`, `iloveu2024`.  
**Zadanie:** oceń, które są silne, które słabe i dlaczego. Zaproponuj mocniejsze odpowiedniki.  
**Cel:** uświadomienie znaczenia polityki haseł.

---

### 4. Analiza funkcji hashujących

Sprawdź, jak wygląda hash hasła `password` w MD5 i w SHA256 (np. przez stronę online).  
**Zadanie:** porównaj wyniki, poszukaj informacji, dlaczego MD5 jest dziś niewystarczający.  
**Cel:** zrozumienie problemu starych algorytmów i potrzeby stosowania soli.

---

### 5. Zarządzanie sesją

Wyobraź sobie, że logujesz się w kawiarni na publicznym WiFi.  
**Zadanie:** opisz, co może się stać, jeśli ktoś przechwyci Twoje ciasteczko sesyjne.  
**Cel:** poznanie ryzyka i znaczenia flag `HttpOnly`, `Secure`, `SameSite`.

---

### 6. Klasyfikacja danych

Masz listę: PESEL, adres e-mail, adres IP, numer karty kredytowej, token API, grupa krwi.  
**Zadanie:** przyporządkuj je do kategorii: dane osobowe zwykłe, szczególne, biznesowo wrażliwe.  
**Cel:** zrozumienie związku bezpieczeństwa aplikacji z przepisami prawnymi (np. RODO).

---

### 7. Symulacja XSS

W komentarzu na stronie wpisano: `<script>alert("Hacked!")</script>`.  
**Zadanie:** opisz, co się stanie w przeglądarce i jak można temu zapobiec (walidacja, escapowanie, CSP).  
**Cel:** zrozumienie problemu Cross-Site Scripting.

---

### 8. Analiza API – IDOR

Masz adres: `/api/user/12345`.  
**Zadanie:** zastanów się, co się stanie, jeśli ktoś ręcznie zmieni numer ID w URL. Jak temu zapobiec?  
**Cel:** poznanie ataku typu IDOR i sposobów jego blokady (kontrola dostępu, tokeny).

---

### 9. Sprawdzenie zależności

Wyobraź sobie projekt, który korzysta z: `django 2.0`, `express 4.0`, `openssl 1.0`.  
**Zadanie:** sprawdź, które wersje są nieaktualne i mogą zawierać luki (np. wpisując w Google nazwę + CVE).  
**Cel:** świadomość zagrożeń w łańcuchu dostaw (supply chain).

---

### 10. Bezpieczeństwo w chmurze

Firma wystawia aplikację w AWS/Azure/GCP.  
**Zadanie:** wymień 3 potencjalne zagrożenia (np. publiczne bucket S3, złe uprawnienia IAM, brak szyfrowania).  
**Cel:** poznanie specyfiki zagrożeń w środowisku cloud.

---

### 11. Symulacja incydentu

Scenariusz: w logach serwera widać ruch z nietypowych adresów IP (np. z Rosji, Chin).  
**Zadanie:** wypisz pierwsze 3 kroki, jakie podejmiesz jako zespół bezpieczeństwa.  
**Cel:** ćwiczenie w reagowaniu na incydenty.

---

### 12. Porównanie uwierzytelniania

**Zadanie:** wypisz różnice między hasłem, kodem SMS, kluczem U2F/FIDO. Które rozwiązanie jest najbezpieczniejsze i dlaczego?  
**Cel:** poznanie różnych metod MFA (multi-factor authentication).

---

### 13. Atak typu Brute Force

**Zadanie:** policz, ile kombinacji ma hasło 6-znakowe tylko z cyfr. Następnie z liter i cyfr.  
**Cel:** zrozumienie, dlaczego długość i złożoność hasła mają znaczenie.

---

### 14. Bezpieczne przechowywanie danych

**Zadanie:** wymyśl zasady przechowywania: haseł, numerów kart kredytowych, tokenów API w aplikacji.  
**Cel:** praktyczna refleksja nad tym, co wolno, a czego nie wolno zapisywać w bazie wprost.

---

### 15. Nagłówki HTTP

Dostajesz przykład odpowiedzi serwera:

```
HTTP/1.1 200 OK  
Content-Type: text/html  
```

**Zadanie:** zaproponuj dodatkowe nagłówki, które poprawią bezpieczeństwo (np. `Strict-Transport-Security`).  
**Cel:** poznanie podstawowych zabezpieczeń na poziomie protokołu HTTP.

---

### 16. Atak CSRF

Wyobraź sobie formularz „przelej pieniądze” bez zabezpieczenia tokenem.  
**Zadanie:** opisz, jak napastnik mógłby wykorzystać brak CSRF tokenu.  
**Cel:** uświadomienie roli zabezpieczenia formularzy.

---

### 17. Bezpieczeństwo mobilne

**Zadanie:** wskaż 3 zagrożenia specyficzne dla aplikacji mobilnych (np. brak szyfrowania w storage, dostęp do mikrofonu/kamery, przechwytywanie SMS).  
**Cel:** rozszerzenie perspektywy poza web.

---

### 18. Analiza case study

Przeczytaj artykuł o jednym z dużych wycieków danych (np. LinkedIn, Allegro, PlayStation Network).  
**Zadanie:** podsumuj, co się stało i jak firma mogła się zabezpieczyć.  
**Cel:** nauka na błędach innych.

---

### 19. Bezpieczeństwo a UX

**Zadanie:** znajdź przykład, gdzie nadmiar zabezpieczeń utrudnia użytkownikowi korzystanie z aplikacji. Jak można to zbalansować?  
**Cel:** refleksja nad kompromisem między użytecznością a bezpieczeństwem.

---

### 20. Twoja rekomendacja

**Zadanie:** zaproponuj jedną zasadę bezpieczeństwa, którą każdy programista powinien stosować w codziennej pracy. Uzasadnij wybór.  
**Cel:** utrwalenie wiedzy w formie osobistego wniosku.

---

# Materiały pomocnicze

- [OWASP Top 10 (2021)](https://owasp.org/Top10/) – lista najważniejszych zagrożeń bezpieczeństwa aplikacji.
- [PortSwigger Web Security Academy](https://portswigger.net/web-security) – darmowe laboratoria i kursy o bezpieczeństwie web.
- [OWASP Cheat Sheets](https://cheatsheetseries.owasp.org/) – praktyczne ściągawki bezpieczeństwa.
- [CVE Details](https://www.cvedetails.com/) – baza znanych podatności.
- [Have I Been Pwned](https://haveibeenpwned.com/) – sprawdzanie wycieków haseł.
- [Mozilla Observatory](https://observatory.mozilla.org/) – analiza bezpieczeństwa nagłówków HTTP.
- [Google Cloud Security Best Practices](https://cloud.google.com/security/best-practices) – dobre praktyki bezpieczeństwa w chmurze.
- [OWASP Juice Shop](https://owasp.org/www-project-juice-shop/) – aplikacja testowa do nauki ataków.
- <https://www.dehashed.com> - klasyka, niestety nie wszystko za darmo 
- <https://databreach.com> - podobne do powyższego
- <https://breachdirectory.org> - ciekawe rzeczy pokazuje