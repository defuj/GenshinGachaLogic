import kotlin.math.floor

// default setting rate
var rRate = 97.000F
var srRate = 2.500F
var ssrRate = 0.500F
var eventRate = 1.6F

// default pity, counter & rateUp status
var pity = 1
var counter = 1
var rateUp = false

// rateUp Item
var rateUpCharacter = ModelItem("char_119","Character","Xiao","*****",true)
var rateUpWeapon = ModelItem("polearms_102","Weapon Polearms","Primordial Jade Winged-Spear","*****",false)

// setup rateUp Item with implement lambda expression
val rateUpItem : (String) -> ModelItem = {
    when(it){
        "character" -> rateUpCharacter
        else -> rateUpWeapon
    }
}

var weight : Float = 0F
var R = 0F
var SR = 0F
var SSR = 0F
var EVENT = 0F

var items : MutableList<ModelItem> = mutableListOf()
var resultItems : MutableList<ModelItem> = mutableListOf()
var resultTemp : MutableList<ModelItem> = mutableListOf()

// extension function with single expression function
fun String.println() : Unit = println(this)

fun main() {
    //for (i in 1..90) startLogic("character")
    for (i in 1..180) startLogic("weapon")
}

fun startLogic(type: String? = "none") {
    // mengatur hadiah gacha berdasarkan type gacha
    when(type){
        "none" -> {
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
            resultItems.add(rateUpItem(type!!))
            // after get rateUpItem
            // rateUp <- false
            rateUp = false
            "[$counter] [$pity] Mendapatkan [${rateUpItem(type).star}] ${rateUpItem(type).type} - ${rateUpItem(type).name}".println()
        }else{
            // get [result] random SSR item
            resultTemp = whenRarity("*****").toMutableList()
            val result = resultTemp[reloadNumber(resultTemp.size)]
            resultItems.add(result)
            // if [result] == rateUpItem : rateUp <- false
            // if [result] != rateUpItem : rateUp <- true
            rateUp = result != rateUpItem(type!!)
            "[$counter] [$pity] Mendapatkan [${result.star}] ${result.type} - ${result.name}".println()
        }

        // after get SSR item, reset pity = 1
        pity = 1
        counter += 1
    }else{
        if(counter > 9){
            if(counter.mod(10) == 0){
                resultTemp = whenRarity("****").toMutableList()
                val result = resultTemp[reloadNumber(resultTemp.size)]
                resultItems.add(result)
                "[$counter] [$pity] Mendapatkan [${result.star}] ${result.type} - ${result.name}".println()
                pity+=1
                counter+=1
            }else{
                rollInUp(1, type)
            }
        }else{
            rollInUp(1, type)
        }
    }
}
fun characterSSR(): MutableList<ModelItem> {
    return mutableListOf(
        ModelItem("char_100","Character","Albedo","*****",false),
        ModelItem("char_101","Character","Arataki Itto","*****",false),
        ModelItem("char_102","Character","Aloy","*****",false),
        ModelItem("char_103","Character","Diluc","*****",false),
        ModelItem("char_104","Character","Eula","*****",false),
        ModelItem("char_105","Character","Ganyu","*****",false),
        ModelItem("char_106","Character","Hu Tao","*****",false),
        ModelItem("char_107","Character","Jean","*****",false),
        ModelItem("char_108","Character","Kaedehara Kazuha","*****",false),
        ModelItem("char_109","Character","Kamisato Ayaka","*****",false),
        ModelItem("char_110","Character","Keqing","*****",false),
        ModelItem("char_111","Character","Klee","*****",false),
        ModelItem("char_112","Character","Mona","*****",false),
        ModelItem("char_113","Character","Qiqi","*****",false),
        ModelItem("char_114","Character","Raiden Shogun","*****",false),
        ModelItem("char_115","Character","Sangonomiya Kokomi","*****",false),
        ModelItem("char_116","Character","Shenhe","*****",false),
        ModelItem("char_117","Character","Tartaglia","*****",false),
        ModelItem("char_118","Character","Venti","*****",false),
        // RateUp Item
        ModelItem("char_119","Character","Xiao","*****",true),
        ModelItem("char_120","Character","Yoimiya","*****",false),
        ModelItem("char_121","Character","Zhongli","*****",false)
    )
}
fun characterSR(): MutableList<ModelItem> {
    return mutableListOf(
        ModelItem("char_122","Character","Amber","****",false),
        ModelItem("char_123","Character","Barbara","****",false),
        ModelItem("char_124","Character","Beidou","****",false),
        ModelItem("char_125","Character","Bennet","****",false),
        // RateUp Item
        ModelItem("char_126","Character","Chongyun","****",true),
        ModelItem("char_127","Character","Diona","****",false),
        ModelItem("char_128","Character","Fischl","****",false),
        ModelItem("char_129","Character","Gorou","****",false),
        ModelItem("char_130","Character","Kaeya","****",false),
        ModelItem("char_131","Character","Kujou Sara","****",false),
        ModelItem("char_132","Character","Lisa","****",false),
        // RateUp Item
        ModelItem("char_133","Character","Ningguang","****",true),
        ModelItem("char_134","Character","Noelle","****",false),
        ModelItem("char_135","Character","Razor","****",false),
        ModelItem("char_136","Character","Rosaria","****",false),
        ModelItem("char_137","Character","Sayu","****",false),
        ModelItem("char_138","Character","Sucrose","****",false),
        ModelItem("char_139","Character","Thoma","****",false),
        ModelItem("char_140","Character","Xiangling","****",false),
        ModelItem("char_141","Character","Xinqiu","****",false),
        ModelItem("char_142","Character","Xinyan","****",false),
        ModelItem("char_143","Character","Yanfei","****",false),
        // RateUp Item
        ModelItem("char_144","Character","Yun Jin","****",true)
    )
}
fun weaponSSR() : MutableList<ModelItem> {
    return mutableListOf(
        // Bow
        ModelItem("bows_100","Weapon Bow","Polar Star","*****",false),
        ModelItem("bows_101","Weapon Bow","Thundering Pulse","*****",false),
        ModelItem("bows_102","Weapon Bow","Elegy for the End","*****",false),
        ModelItem("bows_103","Weapon Bow","Skyward Harp","*****",false),
        ModelItem("bows_104","Weapon Bow","Amos' Bow","*****",false),
        // Catalyst
        ModelItem("catalyst_100","Weapon Catalyst","Lost Prayer to the Sacred Winds","*****",false),
        ModelItem("catalyst_101","Weapon Catalyst","Skyward Atlas","*****",false),
        ModelItem("catalyst_102","Weapon Catalyst","Everlasting Moonglow","*****",false),
        ModelItem("catalyst_103","Weapon Catalyst","Memory of Dust","*****",false),
        // Claymores
        ModelItem("claymores_100","Weapon Claymores","Wolf's Gravestone","*****",false),
        ModelItem("claymores_101","Weapon Claymores","Skyward Pride","*****",false),
        ModelItem("claymores_102","Weapon Claymores","The Unforged","*****",false),
        ModelItem("claymores_103","Weapon Claymores","Song of Broken Pines","*****",false),
        ModelItem("claymores_104","Weapon Claymores","Redhorn Stonethresher","*****",false),
        // Polearms
        ModelItem("polearms_100","Weapon Polearms","Engulfing Lightning","*****",false),
        ModelItem("polearms_101","Weapon Polearms","Skyward Spine","*****",false),
        ModelItem("polearms_102","Weapon Polearms","Primordial Jade Winged-Spear","*****",true),
        ModelItem("polearms_103","Weapon Polearms","Calamity Queller","*****",false),
        ModelItem("polearms_104","Weapon Polearms","Staff of Homa","*****",false),
        ModelItem("polearms_105","Weapon Polearms","Vortex Vanquisher","*****",false),
        // Sword
        ModelItem("sword_100","Weapon Sword","Mistsplitter Reforged","*****",false),
        ModelItem("sword_101","Weapon Sword","Aquila Favonia","*****",false),
        ModelItem("sword_102","Weapon Sword","Summit Shaper","*****",false),
        ModelItem("sword_103","Weapon Sword","Skyward Blade","*****",false),
        ModelItem("sword_104","Weapon Sword","Freedom-Sworn","*****",false),
        ModelItem("sword_105","Weapon Sword","Primordial Jade Cutter","*****",false),
    )
}
fun weaponSR() : MutableList<ModelItem> {
    return mutableListOf(
        // Bow
        ModelItem("bows_105","Weapon Bow","Alley Hunter","****",false),
        ModelItem("bows_106","Weapon Bow","The Viridescent Hunt","****",false),
        ModelItem("bows_107","Weapon Bow","The Stringless","****",false),
        ModelItem("bows_108","Weapon Bow","Sacrificial Bow","****",false),
        ModelItem("bows_109","Weapon Bow","Rust","****",false),
        ModelItem("bows_110","Weapon Bow","Royal Bow","****",false),
        ModelItem("bows_111","Weapon Bow","Predator","****",false),
        ModelItem("bows_112","Weapon Bow","Prototype Crescent","****",false),
        ModelItem("bows_113","Weapon Bow","Mouun's Moon","****",false),
        ModelItem("bows_114","Weapon Bow","Mitternachts Waltz","****",false),
        ModelItem("bows_115","Weapon Bow","Hamayumi","****",false),
        ModelItem("bows_116","Weapon Bow","Favonius Warbow","****",false),
        ModelItem("bows_117","Weapon Bow","Compound Bow","****",false),
        ModelItem("bows_118","Weapon Bow","Blackcliff Warbow","****",false),
        ModelItem("bows_119","Weapon Bow","Windblume Ode","****",false),
        // Catalyst
        ModelItem("catalyst_104","Weapon Catalyst","Wine and Song","****",false),
        ModelItem("catalyst_105","Weapon Catalyst","The Widsith","****",false),
        ModelItem("catalyst_106","Weapon Catalyst","Solar Pearl","****",false),
        ModelItem("catalyst_107","Weapon Catalyst","Sacrificial Fragments","****",false),
        ModelItem("catalyst_108","Weapon Catalyst","Royal Grimoire","****",false),
        ModelItem("catalyst_109","Weapon Catalyst","Prototype Amber","****",false),
        ModelItem("catalyst_110","Weapon Catalyst","Mappa Mare","****",false),
        ModelItem("catalyst_111","Weapon Catalyst","Hakushin Ring","****",false),
        ModelItem("catalyst_112","Weapon Catalyst","Frostbearer","****",false),
        ModelItem("catalyst_113","Weapon Catalyst","Favonius Codex","****",false),
        ModelItem("catalyst_114","Weapon Catalyst","Eye of Perception","****",false),
        ModelItem("catalyst_115","Weapon Catalyst","Dodoco Tales","****",false),
        ModelItem("catalyst_116","Weapon Catalyst","Blackcliff Agate","****",false),
        // Claymores
        ModelItem("claymores_105","Weapon Claymores","Akuoumaru","****",false),
        ModelItem("claymores_106","Weapon Claymores","Royal Greatsword","****",false),
        ModelItem("claymores_107","Weapon Claymores","Whiteblind","****",false),
        ModelItem("claymores_108","Weapon Claymores","The Bell","****",false),
        ModelItem("claymores_109","Weapon Claymores","Snow-Tombed Starsilver","****",false),
        ModelItem("claymores_110","Weapon Claymores","Favonius Greatsword","****",false),
        ModelItem("claymores_111","Weapon Claymores","Katsuragikiri Nagamasa","****",false),
        ModelItem("claymores_112","Weapon Claymores","Sacrificial Greatsword","****",false),
        ModelItem("claymores_113","Weapon Claymores","Serpent Spine","****",false),
        ModelItem("claymores_114","Weapon Claymores","Blackcliff Slasher","****",false),
        ModelItem("claymores_115","Weapon Claymores","Rainslasher","****",false),
        ModelItem("claymores_116","Weapon Claymores","Prototype Archaic","****",false),
        ModelItem("claymores_117","Weapon Claymores","Luxurious Sea-Lord","****",false),
        ModelItem("claymores_118","Weapon Claymores","Lithic Blade","****",false),
        // Polearms
        ModelItem("polearms_106","Weapon Polearms","Prototype Starglitter","****",false),
        ModelItem("polearms_107","Weapon Polearms","Lithic Spear","****",false),
        ModelItem("polearms_108","Weapon Polearms","Kitain Cross Spear","****",false),
        ModelItem("polearms_109","Weapon Polearms","The Catch","****",false),
        ModelItem("polearms_110","Weapon Polearms","Favonius Lance","****",false),
        ModelItem("polearms_111","Weapon Polearms","Dragonspine Spear","****",false),
        ModelItem("polearms_112","Weapon Polearms","Dragon's Bane","****",false),
        ModelItem("polearms_113","Weapon Polearms","Deathmatch","****",false),
        ModelItem("polearms_114","Weapon Polearms","Crescent Pike","****",false),
        ModelItem("polearms_115","Weapon Polearms","Blackcliff Pole","****",false),
        ModelItem("polearms_116","Weapon Polearms","Wavebreaker's Fin","****",false),
        ModelItem("polearms_117","Weapon Polearms","Royal Spear","****",false),
        // Sword
        ModelItem("sword_106","Weapon Sword","The Flute","****",false),
        ModelItem("sword_107","Weapon Sword","The Black Sword","****",false),
        ModelItem("sword_108","Weapon Sword","The Alley Flash","****",false),
        ModelItem("sword_109","Weapon Sword","Sword of Descension","****",false),
        ModelItem("sword_110","Weapon Sword","Sacrificial Sword","****",false),
        ModelItem("sword_111","Weapon Sword","Royal Longsword","****",false),
        ModelItem("sword_112","Weapon Sword","Prototype Rancour","****",false),
        ModelItem("sword_113","Weapon Sword","Amenoma Kageuchi","****",false),
        ModelItem("sword_114","Weapon Sword","Lion's Roar","****",false),
        ModelItem("sword_115","Weapon Sword","Iron Sting,","****",false),
        ModelItem("sword_116","Weapon Sword","Festering Desire","****",false),
        ModelItem("sword_117","Weapon Sword","Favonius Sword","****",false),
        ModelItem("sword_118","Weapon Sword","Cinnabar Spindle","****",false),
        ModelItem("sword_119","Weapon Sword","Blackcliff Longsword","****",false),
    )
}
fun weaponR() : MutableList<ModelItem> {
    return mutableListOf(
        // Bow
        ModelItem("bows_120","Weapon Bow","Raven Bow","*",false),
        ModelItem("bows_121","Weapon Bow","Recurve Bow","*",false),
        ModelItem("bows_122","Weapon Bow","Messenger","*",false),
        ModelItem("bows_123","Weapon Bow","Sharpshooter's Oath","*",false),
        ModelItem("bows_124","Weapon Bow","Slingshot","*",false),
        ModelItem("bows_125","Weapon Bow","Ebony Bow","*",false),
        // Catalyst
        ModelItem("catalyst_117","Weapon Catalyst","Magic Guide","*",false),
        ModelItem("catalyst_118","Weapon Catalyst","Otherworldly Story","*",false),
        ModelItem("catalyst_119","Weapon Catalyst","Emerald Orb","*",false),
        ModelItem("catalyst_120","Weapon Catalyst","Thrilling Tales of Dragon Slayers","*",false),
        ModelItem("catalyst_121","Weapon Catalyst","Twin Nephrite","*",false),
        ModelItem("catalyst_122","Weapon Catalyst","Amber Catalyst","*",false),
        // Claymores
        ModelItem("claymores_119","Weapon Claymores","Quartz","*",false),
        ModelItem("claymores_120","Weapon Claymores","Skyrider Greatsword","*",false),
        ModelItem("claymores_121","Weapon Claymores","Debate Club","*",false),
        ModelItem("claymores_122","Weapon Claymores","Bloodtainted Greatsword","*",false),
        ModelItem("claymores_123","Weapon Claymores","White Iron Greatsword","*",false),
        ModelItem("claymores_124","Weapon Claymores","Ferrous Shadow","*",false),
        // Polearms
        ModelItem("polearms_118","Weapon Polearms","Halberd","*",false),
        ModelItem("polearms_119","Weapon Polearms","Black Tassel","*",false),
        ModelItem("polearms_120","Weapon Polearms","White Tassel","*",false),
        // Sword
        ModelItem("sword_120","Weapon Sword","Harbinger of Dawn","*",false),
        ModelItem("sword_121","Weapon Sword","Fillet Blade","*",false),
        ModelItem("sword_122","Weapon Sword","Skyrider Sword","*",false),
        ModelItem("sword_123","Weapon Sword","Dark Iron Sword","*",false),
        ModelItem("sword_124","Weapon Sword","Cool Steel","*",false),
        ModelItem("sword_125","Weapon Sword","Traveler's Handy Sword","*",false),
    )
}
fun rollInUp(total : Int? = 1, type: String? = "none"){
    for(i in 1..total!!){
        val randNumber = floor(Math.random() * weight)
        var index = 0
        if(SSR < randNumber && randNumber <= EVENT){
            resultTemp = whenEvent().toMutableList()
            index = reloadNumber(resultTemp.size)
            resultItems.add(resultTemp[index])
            rateUp = resultTemp[index] != rateUpItem(type!!)
        }else if(SR < randNumber && randNumber <= SSR){
            resultTemp = whenRarity("*****").toMutableList()
            index = reloadNumber(resultTemp.size)
            resultItems.add(resultTemp[index])
            rateUp = resultTemp[index] != rateUpItem(type!!)
        }else if(R < randNumber && randNumber <= SR){
            resultTemp = whenRarity("****").toMutableList()
            index = reloadNumber(resultTemp.size)
            resultItems.add(resultTemp[index])
        }else if(randNumber <= R){
            resultTemp = whenRarity("*").toMutableList()
            index = reloadNumber(resultTemp.size)
            resultItems.add(resultTemp[index])
        }

        "[$counter] [$pity] Mendapatkan [${resultTemp[index].star}] ${resultTemp[index].type} - ${resultTemp[index].name}".println()
        when (resultTemp[index].star) {
            "*****" -> pity = 1
            "****" -> pity += 1
            else -> pity += 1
        }
        counter += 1
    }
}
fun reloadNumber(length : Int) : Int = floor(Math.random() * length).toInt()
fun whenEvent(): List<ModelItem> = items.filter { it.rateUp }.toList()
fun whenRarity(rarity : String): List<ModelItem> = items.filter { it.star == rarity }.toList()