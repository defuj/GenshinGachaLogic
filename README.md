# Genshin Gacha Logic
Kasus ini terinspirasi dari sistem Gacha game yang saya mainkan selama ini, yaitu game Genshin Impact. Pada kasus ini saya menerapkan Pseudo Random Number Generator (PRNG) sebagaimana yang dijelaskan pada <a href="https://informatika.stei.itb.ac.id/~rinaldi.munir/Kriptografi/2016-2017/Makalah2/Makalah2_Kripto_2016_08.pdf">Sebuah Jurnal</a> yang saya temukan.

## Setting

Variable | Tipe Data | Deskripsi | Contoh
-------- | --------- | --------- | ------
rRate | Float | Rate Rarity (Rare) dalam persentase | "rRate": 97
srRate | Float | Rate Rarity (S Rare) dalam persentase | "srRate": 2.5
ssrRate | Float | Rate Rarity (SS Rate) dalam persentase | "ssrRate": 0.5
eventRate | Float | Rate Rarity (event rate) dalam persentase | "eventRate": 1.6

## Item Gacha
Item gacha pada kasus ini terdiri dari 2 jenis, yaitu
- <a href="https://genshin-impact.fandom.com/wiki/Characters/List">Karakter</a>
- <a href="https://genshin-impact.fandom.com/wiki/Weapons">Senjata</a>
* Catalyst
* Bows
* Claymores
* Polearms
* Swords

Beberapa jenisnya terbagi dalam rarity : R, SR, SSR
rarity R ditandai dengan 4 bintang, SR dengan 4 bintang dan SSR dengan 5 bintang.

## Informasi Gacha
- Pembagian Gacha
Gacha terbagi menjadi 3 kategori; Karakter, Senjata, Umum.
* Dalam Gacha karakter, hadiah utama (event) adalah item Karakter SSR / bintang 5
* Dalam gacaha senajata, hadiah utama (event) adalah item Senjata SSr / bintang 5
* Dalam gacaha umum, hadiah utama dapat berupa Item SSR apapun kecuali Item SSR hadiah utama dari (event) Karakter & Senjata.
* Untuk hadiah lainnya yaitu antara Senjata R - SR, Karakter R - SR


- Pity
Pity adalah hitungan gacha dimulai dari pertama gacaha atau terakhir mendapatkan Item SSR atau Bintang 5. 
Sebelum mendapatkan Item SSR atau Bintang 5 hitungan pity akan selalu naik /1x gacha, ketika dalam gacha mendapatkan Item SSR, maka hitungan dimulai lagi dari 1.

- History/Counter
History/Counter ini adalah hitungan gacha dimulai dari pertama gacha sampai hitungan direset oleh sistem dan tidak dapat dipengaruhi oleh apapun. Hitungan akan selalu naik /1x gacha.

## Ketentuan Gacha
- Dalam setiap type(Karakter & Senjata) gacha ada 1 Item hadiah utama SSR (event).
- Jaminan mendapatkan setidaknya 1 item SR atau Bintang 4 untuk 10x gacha.
- Jaminan mendapatkan Item SSR pada gacha ke-90, dan terdapat rasio 50:50 untuk mendapatkan item SSR hadiah utama (event).
- Jika item SSR yang didapatkan bukan Item dalam hadiah utama (event), maka mendapat jaminan mendapatkan Item SSR / Bintang 5 (event) ketika mendapat Item SSR selanjutnya.
