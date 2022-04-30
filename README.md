# OOPokemon

OOPokemon adalah program berbentuk permainan yang dibuat dalam rangka memenuhi Tugas Besar IF2212 Pemrograman Berbasis Objek STI.

## Deskripsi 
Game dapat dimainkan oleh 2 pemain. Pada tiap awal game, satu player mendapatkan 6 monster. Setiap monster memiliki beragam nilai Stats dan beberapa Moves yang dapat dilakukan. Monster juga memiliki tipe elemen yang berpengaruh kepada efektifitas move yang dilakukan terhadap lawan. Setiap move kecuali default move juga memiliki amunisi atau kesempatan penggunaan yang terbatas.

Dalam permainan, setiap pemain akan memilih salah satu monster dan satu move yang dimiliki monster tersebut. Setiap move dapat mempengaruhi monster yang melakukan move tersebut maupun monster yang dipilih oleh pemain lawan. Apabila health point dari suatu monster sudah habis maka monster akan mati dan tidak dapat dipilih lagi. Permainan akan dimenangkan apabila semua monster dari pemain lawan sudah mati.

## Cara Menjalankan

On linux:
```bash
./gradlew -q --console plain run
```

On windows:
```cmd
gradlew -q --console plain run
```
