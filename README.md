# Chatroom

- Kompileerida saab CL käsuga mvn package
- Jar faili saab jooksutada CL käsuga mvn exec:java

### Etapp 1
####
#### Summary
Primitiivne toorik

Etapp 1 peab ss pmst järgmise nädala lõpuks valmis olema
Teeks siis esimeseks etapiks serveri, mis toetab ainult ühte chatti, mitme client'i vahel, ja salvestab selle txt faili
```
= Server =
* Salvestab chati local txt faili
* Võimaldab mitmel client'il sõnumeid korraga edastada
* Broadcast'ib sõnumeid kõigile connected client'itele
= Client =
* Võtab cl sisendiks username ja sõnumi, ning edastab selle serverile
* Kui programmi ei lõpetata, siis jääb uusi sõnumeid kuulama
```

Etapp 2 plaan: Lisame selle, et server edastab chati ajalugu/previous chatti ning optimiseerime seda, samuti paneme client'i kuulama, kas ta on serveriga ühendatud, enne kui see midagi saatma hakkab
Samuti kui aega üle jääb, ss võime selle ühenduse pealt saadetavad sõnumid ära krüpteerida ning tekitada cl asemel mingi visuaalse lahenduse nt javaFXiga