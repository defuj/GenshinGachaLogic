import kotlin.math.floor

var rRate = 97.000F
var srRate = 2.500F
var ssrRate = 0.500F
var eventRate = 1.6F

var pity = 1
var counter = 1
var rateUp = false
var rateUpItem = ModelItem("char_116","Character","Shenhe","SSR",true)

var weight : Float = 0F
var R = 0F
var SR = 0F
var SSR = 0F
var EVENT = 0F

private var items : MutableList<ModelItem> = mutableListOf()
private var resultItems : MutableList<ModelItem> = mutableListOf()
private var res : MutableList<ModelItem> = mutableListOf()

fun main() {
    for (i in 1..180){ startLogic("character") }
}
private fun startLogic(type: String ? = "all") {
    // mengatur hadiah gacha berdasarkan type gacha nya
    when(type){
        "all" -> {
            items.apply {
                addAll(characterSSR())
                addAll(characterSR())
                addAll(weaponSSR())
                addAll(weaponSR())
                addAll(weaponR())
            }
        }
        "character" -> {
            items.apply {
                addAll(characterSSR())
                addAll(characterSR())
                addAll(weaponSR())
                addAll(weaponR())
            }
        }
        "weapon" -> {
            items.apply {
                addAll(weaponSSR())
                addAll(characterSR())
                addAll(weaponSR())
                addAll(weaponR())
            }
        }
    }

    // Logika dasar:
    // Pertama, cari range untuk tiap rate, caranya rate_sebelumnya + rate_yang_dikerja.
    // Kedua, jumlahkan semua rate untuk membuat rentang rate.
    // Ketiga, kita acak angkanya berdasarkan rentang ratenya.
    // Keempat, setelah diacak, cocokkan dengan rentang ratenya.

    // Karena settingan pertama kita adalah persen, kita kalikan 100 biar hitungannya
    // lebih kapitalis karena rentangnya terlalu besar.

    rRate *=100
    srRate *=100
    ssrRate *=100
    eventRate *=100

    // setup range rate
    weight = rRate + srRate + ssrRate

    // set range to every rate
    R = rRate
    SR = R + srRate
    SSR = SR + ssrRate
    EVENT = SSR + eventRate

    // Penjelasan PITY
    // pity adalah hitungan dimulai dari awal gacha,
    // hitungan pity akan selalu reset ke 1 apabila mendapatkan item SSR

    // Penjelasan COUNTER
    // Counter hanya hitunga gacha setiap 1 item di mulai dari awal hingga akhir

    // Penjelasan RATE UP
    // rateUp adalah status kepastian untuk mendapatkan rateUpItem atau item SSR terpilih
    // ketika hasil gacha mendapatkan item SSR, jika hasilnya != rateUpItem maka rateUp <- true
    // dan dipastikan akan mendapatkan item SSR rateUpItem ketika mendapatkan item SSR selanjutnya
    // ketika hasil gacha == rateUpItem, maka rateUp <- false

    // setiap pity == 90, pasti dapat 1 item SSR
    // setiap 10 counter (counter % 10 == 0) pasti dapat 1 item SR
    if(pity == 90){
        // rateUp == true, definitely get rateUpItem
        // rateUp == false, get random SSR item
        if(rateUp){
            resultItems.add(rateUpItem)
            // after get rateUpItem
            // rateUp <- false
            rateUp = false
            println("[$counter] [$pity] Mendapatkan [${rateUpItem.ratity}] ${rateUpItem.type} - ${rateUpItem.name}")
        }else{
            // get [result] random SSR item
            res = whenRarity("SSR").toMutableList()
            resultItems.add(res[reloadNumber(res.size)])
            // if [result] == rateUpItem : rateUp <- false
            // if [result] != rateUpItem : rateUp <- true
            rateUp = res[reloadNumber(res.size)] != rateUpItem
            println("[$counter] [$pity] Mendapatkan [${res[reloadNumber(res.size)].ratity}] ${res[reloadNumber(res.size)].type} - ${res[reloadNumber(res.size)].name}")
        }

        // after get SSR item, reset pity = 1
        pity = 1
        counter += 1
    }else{
        if(counter > 9){
            if(counter.mod(10) == 0){
                res = whenRarity("SR").toMutableList()
                resultItems.add(res[reloadNumber(res.size)])
                println("[$counter] [$pity] Mendapatkan [${res[reloadNumber(res.size)].ratity}] ${res[reloadNumber(res.size)].type} - ${res[reloadNumber(res.size)].name}")
                pity+=1
                counter+=1
            }else{
                rollInUp()
            }
        }else{
            rollInUp()
        }
    }
}

private fun characterSSR(): MutableList<ModelItem> {
    return mutableListOf(
        ModelItem("char_100","Character","Albedo","SSR",false),
        ModelItem("char_100","Character","Albedo","SSR",false),
        ModelItem("char_101","Character","Arataki Itto","SSR",false),
        ModelItem("char_102","Character","Aloy","SSR",false),
        ModelItem("char_103","Character","Diluc","SSR",false),
        ModelItem("char_104","Character","Eula","SSR",false),
        ModelItem("char_105","Character","Ganyu","SSR",false),
        ModelItem("char_106","Character","Hu Tao","SSR",false),
        ModelItem("char_107","Character","Jean","SSR",false),
        ModelItem("char_108","Character","Kaedehara Kazuha","SSR",false),
        ModelItem("char_109","Character","Kamisato Ayaka","SSR",false),
        ModelItem("char_110","Character","Keqing","SSR",false),
        ModelItem("char_111","Character","Klee","SSR",false),
        ModelItem("char_112","Character","Mona","SSR",false),
        ModelItem("char_113","Character","Qiqi","SSR",false),
        ModelItem("char_114","Character","Raiden Shogun","SSR",false),
        ModelItem("char_115","Character","Sangonomiya Kokomi","SSR",false),
        ModelItem("char_116","Character","Shenhe","SSR",true),
        ModelItem("char_117","Character","Tartaglia","SSR",false),
        ModelItem("char_118","Character","Venti","SSR",false),
        ModelItem("char_119","Character","Yoimiya","SSR",false),
        ModelItem("char_120","Character","Zhongli","SSR",false)
    )
}
private fun characterSR(): MutableList<ModelItem> {
    return mutableListOf(
        ModelItem("char_121","Character","Amber","SR",false),
        ModelItem("char_122","Character","Barbara","SR",false),
        ModelItem("char_123","Character","Beidou","SR",false),
        ModelItem("char_124","Character","Bennet","SR",false),
        ModelItem("char_125","Character","Chongyun","SR",false),
        ModelItem("char_126","Character","Diona","SR",false),
        ModelItem("char_127","Character","Fischl","SR",false),
        ModelItem("char_128","Character","Gorou","SR",false),
        ModelItem("char_129","Character","Kaeya","SR",false),
        ModelItem("char_130","Character","Kujou Sara","SR",false),
        ModelItem("char_131","Character","Lisa","SR",false),
        ModelItem("char_132","Character","Ningguang","SR",false),
        ModelItem("char_133","Character","Noelle","SR",false),
        ModelItem("char_134","Character","Razor","SR",false),
        ModelItem("char_135","Character","Rosaria","SR",false),
        ModelItem("char_136","Character","Sayu","SR",false),
        ModelItem("char_137","Character","Sucrose","SR",false),
        ModelItem("char_138","Character","Thoma","SR",false),
        ModelItem("char_139","Character","Xiangling","SR",false),
        ModelItem("char_140","Character","Xiao","SR",false),
        ModelItem("char_141","Character","Xinqiu","SR",false),
        ModelItem("char_142","Character","Xinyan","SR",false),
        ModelItem("char_143","Character","Yanfei","SR",false),
        ModelItem("char_144","Character","Yun Jin","SR",false)
    )
}
private fun weaponSSR() : MutableList<ModelItem> {
    return mutableListOf(
        // Bow
        ModelItem("bows_100","Weapon Bow","Polar Star","SSR",false),
        ModelItem("bows_101","Weapon Bow","Thundering Pulse","SSR",false),
        ModelItem("bows_102","Weapon Bow","Elegy for the End","SSR",false),
        ModelItem("bows_103","Weapon Bow","Skyward Harp","SSR",false),
        ModelItem("bows_104","Weapon Bow","Amos' Bow","SSR",false),
        // Catalyst
        ModelItem("catalyst_100","Weapon Catalyst","Lost Prayer to the Sacred Winds","SSR",false),
        ModelItem("catalyst_101","Weapon Catalyst","Skyward Atlas","SSR",false),
        ModelItem("catalyst_102","Weapon Catalyst","Everlasting Moonglow","SSR",false),
        ModelItem("catalyst_103","Weapon Catalyst","Memory of Dust","SSR",false),
        // Claymores
        ModelItem("claymores_100","Weapon Claymores","Wolf's Gravestone","SSR",false),
        ModelItem("claymores_101","Weapon Claymores","Skyward Pride","SSR",false),
        ModelItem("claymores_102","Weapon Claymores","The Unforged","SSR",false),
        ModelItem("claymores_103","Weapon Claymores","Song of Broken Pines","SSR",false),
        ModelItem("claymores_104","Weapon Claymores","Redhorn Stonethresher","SSR",false),
        // Polearms
        ModelItem("polearms_100","Weapon Polearms","Engulfing Lightning","SSR",false),
        ModelItem("polearms_101","Weapon Polearms","Skyward Spine","SSR",false),
        ModelItem("polearms_102","Weapon Polearms","Primordial Jade Winged-Spear","SSR",false),
        ModelItem("polearms_103","Weapon Polearms","Calamity Queller","SSR",false),
        ModelItem("polearms_104","Weapon Polearms","Staff of Homa","SSR",false),
        ModelItem("polearms_105","Weapon Polearms","Vortex Vanquisher","SSR",false),
        // Sword
        ModelItem("sword_100","Weapon Sword","Mistsplitter Reforged","SSR",false),
        ModelItem("sword_101","Weapon Sword","Aquila Favonia","SSR",false),
        ModelItem("sword_102","Weapon Sword","Summit Shaper","SSR",false),
        ModelItem("sword_103","Weapon Sword","Skyward Blade","SSR",false),
        ModelItem("sword_104","Weapon Sword","Freedom-Sworn","SSR",false),
        ModelItem("sword_105","Weapon Sword","Primordial Jade Cutter","SSR",false),
    )
}
private fun weaponSR() : MutableList<ModelItem> {
    return mutableListOf(
        // Bow
        ModelItem("bows_105","Weapon Bow","Alley Hunter","SR",false),
        ModelItem("bows_106","Weapon Bow","The Viridescent Hunt","SR",false),
        ModelItem("bows_107","Weapon Bow","The Stringless","SR",false),
        ModelItem("bows_108","Weapon Bow","Sacrificial Bow","SR",false),
        ModelItem("bows_109","Weapon Bow","Rust","SR",false),
        ModelItem("bows_110","Weapon Bow","Royal Bow","SR",false),
        ModelItem("bows_111","Weapon Bow","Predator","SR",false),
        ModelItem("bows_112","Weapon Bow","Prototype Crescent","SR",false),
        ModelItem("bows_113","Weapon Bow","Mouun's Moon","SR",false),
        ModelItem("bows_114","Weapon Bow","Mitternachts Waltz","SR",false),
        ModelItem("bows_115","Weapon Bow","Hamayumi","SR",false),
        ModelItem("bows_116","Weapon Bow","Favonius Warbow","SR",false),
        ModelItem("bows_117","Weapon Bow","Compound Bow","SR",false),
        ModelItem("bows_118","Weapon Bow","Blackcliff Warbow","SR",false),
        ModelItem("bows_119","Weapon Bow","Windblume Ode","SR",false),
        // Catalyst
        ModelItem("catalyst_104","Weapon Catalyst","Wine and Song","SR",false),
        ModelItem("catalyst_105","Weapon Catalyst","The Widsith","SR",false),
        ModelItem("catalyst_106","Weapon Catalyst","Solar Pearl","SR",false),
        ModelItem("catalyst_107","Weapon Catalyst","Sacrificial Fragments","SR",false),
        ModelItem("catalyst_108","Weapon Catalyst","Royal Grimoire","SR",false),
        ModelItem("catalyst_109","Weapon Catalyst","Prototype Amber","SR",false),
        ModelItem("catalyst_110","Weapon Catalyst","Mappa Mare","SR",false),
        ModelItem("catalyst_111","Weapon Catalyst","Hakushin Ring","SR",false),
        ModelItem("catalyst_112","Weapon Catalyst","Frostbearer","SR",false),
        ModelItem("catalyst_113","Weapon Catalyst","Favonius Codex","SR",false),
        ModelItem("catalyst_114","Weapon Catalyst","Eye of Perception","SR",false),
        ModelItem("catalyst_115","Weapon Catalyst","Dodoco Tales","SR",false),
        ModelItem("catalyst_116","Weapon Catalyst","Blackcliff Agate","SR",false),
        // Claymores
        ModelItem("claymores_105","Weapon Claymores","Akuoumaru","SR",false),
        ModelItem("claymores_106","Weapon Claymores","Royal Greatsword","SR",false),
        ModelItem("claymores_107","Weapon Claymores","Whiteblind","SR",false),
        ModelItem("claymores_108","Weapon Claymores","The Bell","SR",false),
        ModelItem("claymores_109","Weapon Claymores","Snow-Tombed Starsilver","SR",false),
        ModelItem("claymores_110","Weapon Claymores","Favonius Greatsword","SR",false),
        ModelItem("claymores_111","Weapon Claymores","Katsuragikiri Nagamasa","SR",false),
        ModelItem("claymores_112","Weapon Claymores","Sacrificial Greatsword","SR",false),
        ModelItem("claymores_113","Weapon Claymores","Serpent Spine","SR",false),
        ModelItem("claymores_114","Weapon Claymores","Blackcliff Slasher","SR",false),
        ModelItem("claymores_115","Weapon Claymores","Rainslasher","SR",false),
        ModelItem("claymores_116","Weapon Claymores","Prototype Archaic","SR",false),
        ModelItem("claymores_117","Weapon Claymores","Luxurious Sea-Lord","SR",false),
        ModelItem("claymores_118","Weapon Claymores","Lithic Blade","SR",false),
        // Polearms
        ModelItem("polearms_106","Weapon Polearms","Prototype Starglitter","SR",false),
        ModelItem("polearms_107","Weapon Polearms","Lithic Spear","SR",false),
        ModelItem("polearms_108","Weapon Polearms","Kitain Cross Spear","SR",false),
        ModelItem("polearms_109","Weapon Polearms","The Catch","SR",false),
        ModelItem("polearms_110","Weapon Polearms","Favonius Lance","SR",false),
        ModelItem("polearms_111","Weapon Polearms","Dragonspine Spear","SR",false),
        ModelItem("polearms_112","Weapon Polearms","Dragon's Bane","SR",false),
        ModelItem("polearms_113","Weapon Polearms","Deathmatch","SR",false),
        ModelItem("polearms_114","Weapon Polearms","Crescent Pike","SR",false),
        ModelItem("polearms_115","Weapon Polearms","Blackcliff Pole","SR",false),
        ModelItem("polearms_116","Weapon Polearms","Wavebreaker's Fin","SR",false),
        ModelItem("polearms_117","Weapon Polearms","Royal Spear","SR",false),
        // Sword
        ModelItem("sword_106","Weapon Sword","The Flute","SR",false),
        ModelItem("sword_107","Weapon Sword","The Black Sword","SR",false),
        ModelItem("sword_108","Weapon Sword","The Alley Flash","SR",false),
        ModelItem("sword_109","Weapon Sword","Sword of Descension","SR",false),
        ModelItem("sword_110","Weapon Sword","Sacrificial Sword","SR",false),
        ModelItem("sword_111","Weapon Sword","Royal Longsword","SR",false),
        ModelItem("sword_112","Weapon Sword","Prototype Rancour","SR",false),
        ModelItem("sword_113","Weapon Sword","Amenoma Kageuchi","SR",false),
        ModelItem("sword_114","Weapon Sword","Lion's Roar","SR",false),
        ModelItem("sword_115","Weapon Sword","Iron Sting,","SR",false),
        ModelItem("sword_116","Weapon Sword","Festering Desire","SR",false),
        ModelItem("sword_117","Weapon Sword","Favonius Sword","SR",false),
        ModelItem("sword_118","Weapon Sword","Cinnabar Spindle","SR",false),
        ModelItem("sword_119","Weapon Sword","Blackcliff Longsword","SR",false),
    )
}
private fun weaponR() : MutableList<ModelItem> {
    return mutableListOf(
        // Bow
        ModelItem("bows_120","Weapon Bow","Raven Bow","R",false),
        ModelItem("bows_121","Weapon Bow","Recurve Bow","R",false),
        ModelItem("bows_122","Weapon Bow","Messenger","R",false),
        ModelItem("bows_123","Weapon Bow","Sharpshooter's Oath","R",false),
        ModelItem("bows_124","Weapon Bow","Slingshot","R",false),
        ModelItem("bows_125","Weapon Bow","Ebony Bow","R",false),
        // Catalyst
        ModelItem("catalyst_117","Weapon Catalyst","Magic Guide","R",false),
        ModelItem("catalyst_118","Weapon Catalyst","Otherworldly Story","R",false),
        ModelItem("catalyst_119","Weapon Catalyst","Emerald Orb","R",false),
        ModelItem("catalyst_120","Weapon Catalyst","Thrilling Tales of Dragon Slayers","R",false),
        ModelItem("catalyst_121","Weapon Catalyst","Twin Nephrite","R",false),
        ModelItem("catalyst_122","Weapon Catalyst","Amber Catalyst","R",false),
        // Claymores
        ModelItem("claymores_119","Weapon Claymores","Quartz","R",false),
        ModelItem("claymores_120","Weapon Claymores","Skyrider Greatsword","R",false),
        ModelItem("claymores_121","Weapon Claymores","Debate Club","R",false),
        ModelItem("claymores_122","Weapon Claymores","Bloodtainted Greatsword","R",false),
        ModelItem("claymores_123","Weapon Claymores","White Iron Greatsword","R",false),
        ModelItem("claymores_124","Weapon Claymores","Ferrous Shadow","R",false),
        // Polearms
        ModelItem("polearms_118","Weapon Polearms","Halberd","R",false),
        ModelItem("polearms_119","Weapon Polearms","Black Tassel","R",false),
        ModelItem("polearms_120","Weapon Polearms","White Tassel","R",false),
        // Sword
        ModelItem("sword_120","Weapon Sword","Harbinger of Dawn","R",false),
        ModelItem("sword_121","Weapon Sword","Fillet Blade","R",false),
        ModelItem("sword_122","Weapon Sword","Skyrider Sword","R",false),
        ModelItem("sword_123","Weapon Sword","Dark Iron Sword","R",false),
        ModelItem("sword_124","Weapon Sword","Cool Steel","R",false),
        ModelItem("sword_125","Weapon Sword","Traveler's Handy Sword","R",false),
    )
}
private fun rollInUp(total : Int ? = 1){
    for(i in 1..total!!){
        val randNumber = floor(Math.random() * weight)
        if(SSR < randNumber && randNumber <= EVENT){
            res = whenEvent().toMutableList()
            resultItems.add(res[reloadNumber(res.size)])

            rateUp = res[reloadNumber(res.size)] != rateUpItem
        }else if(SR < randNumber && randNumber <= SSR){
            res = whenRarity("SSR").toMutableList()
            resultItems.add(res[reloadNumber(res.size)])

            rateUp = res[reloadNumber(res.size)] != rateUpItem
        }else if(R < randNumber && randNumber <= SR){
            res = whenRarity("SR").toMutableList()
            resultItems.add(res[reloadNumber(res.size)])
        }else if(randNumber <= R){
            res = whenRarity("R").toMutableList()
            resultItems.add(res[reloadNumber(res.size)])
        }

        println("[$counter] [$pity] Mendapatkan [${res[reloadNumber(res.size)].ratity}] ${res[reloadNumber(res.size)].type} - ${res[reloadNumber(res.size)].name}")
        when (res[reloadNumber(res.size)].ratity) {
            "SSR" -> pity = 1
            "SR" -> pity += 1
            else -> pity += 1
        }
        counter += 1
    }
}
private fun reloadNumber(length : Int) : Int = floor(Math.random() * length).toInt()
private fun whenEvent(): List<ModelItem> = items.filter { it.rateUp }.toList()
private fun whenRarity(rarity : String): List<ModelItem> = items.filter { it.ratity == rarity }.toList()