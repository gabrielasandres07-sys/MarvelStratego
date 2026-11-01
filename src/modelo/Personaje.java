/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER
 */



public enum Personaje {

    //HEROES
    MR_FANTASTIC     ("Mr. Fantastic",    10, Bando.HEROES, 1, "MrFantasticTA.png"),
    CAPTAIN_AMERICA  ("Captain America",   9, Bando.HEROES, 1, "CaptainAmericaTA.png"),
    PROFESSOR_X      ("Professor X",       8, Bando.HEROES, 1, "ProfessorXTA.png"),
    NICK_FURY        ("Nick Fury",         8, Bando.HEROES, 1, "NickFuryTA.png"),
    SPIDER_MAN       ("Spider-Man",        7, Bando.HEROES, 1, "SpidermanTA.png"),
    WOLVERINE        ("Wolverine",         7, Bando.HEROES, 1, "WolverineTA.png"),
    NAMOR            ("Namor",             7, Bando.HEROES, 1, "NamorTA.png"),
    DAREDEVIL        ("Daredevil",         6, Bando.HEROES, 1, "DaredevilTA.png"),
    SILVER_SURFER    ("Silver Surfer",     6, Bando.HEROES, 1, "SilverSurferTA.png"),
    HULK             ("Hulk",              6, Bando.HEROES, 1, "HulkTA.png"),
    IRON_MAN         ("Iron Man",          6, Bando.HEROES, 1, "IronManTA.png"),
    THOR             ("Thor",              5, Bando.HEROES, 1, "ThorTA.png"),
    HUMAN_TORCH      ("Human Torch",       5, Bando.HEROES, 1, "HumanTorchTA.png"),
    CYCLOPS          ("Cyclops",           5, Bando.HEROES, 1, "CyclopsTA.png"),
    INVISIBLE_WOMAN  ("Invisible Woman",   5, Bando.HEROES, 1, "InvisibleWomanTA.png"),
    GHOST_RIDER      ("Ghost Rider",       4, Bando.HEROES, 1, "GhostRiderTA.png"),
    PUNISHER         ("Punisher",          4, Bando.HEROES, 1, "PunisherTA.png"),
    BLADE            ("Blade",             4, Bando.HEROES, 1, "BladeTA.png"),
    THING            ("Thing",             4, Bando.HEROES, 1, "ThingTA.png"),
    EMMA_FROST       ("Emma Frost",        3, Bando.HEROES, 1, "EmmaFrostTA.png"),
    SHE_HULK         ("She-Hulk",          3, Bando.HEROES, 1, "SheHulkTA.png"),
    GIANT_MAN        ("Giant Man",         3, Bando.HEROES, 1, "GiantManTA.png"),
    BEAST            ("Beast",             3, Bando.HEROES, 1, "BeastTA.png"),
    COLOSSUS         ("Colossus",          3, Bando.HEROES, 1, "ColossusTA.png"),
    GAMBIT           ("Gambit",            2, Bando.HEROES, 1, "GambitTA.png"),
    SPIDER_GIRL      ("Spider-Girl",       2, Bando.HEROES, 1, "SpiderGirlTA.png"),
    ICE_MAN          ("Ice Man",           2, Bando.HEROES, 1, "IceManTA.png"),
    STORM            ("Storm",             2, Bando.HEROES, 1, "StormTA.png"),
    PHOENIX          ("Phoenix",           2, Bando.HEROES, 1, "PhoenixTA.png"),
    DR_STRANGE       ("Dr. Strange",       2, Bando.HEROES, 1, "DrStrangeTA.png"),
    ELEKTRA          ("Elektra",           2, Bando.HEROES, 1, "ElektraTA.png"),
    NIGHTCRAWLER     ("Nightcrawler",      2, Bando.HEROES, 1, "NightCrawlerTA.png"),
    BLACK_WIDOW      ("Black Widow",       1, Bando.HEROES, 1, "BlackWidowTA.png"),
    NOVA_BLAST       ("Nova Blast",        0, Bando.HEROES, 6, "NovaBlastTA.png"),
    TIERRA_HEROES    ("Planet Earth",      0, Bando.HEROES, 1, "PlanetEarthTA.png"),

    // VILLANOS 
    DR_DOOM          ("Dr. Doom",         10, Bando.VILLANOS, 1, "DrDoomTA.png"),
    GALACTUS         ("Galactus",          9, Bando.VILLANOS, 1, "GalactusTA.png"),
    KINGPIN          ("Kingpin",           8, Bando.VILLANOS, 1, "KingpinTA.png"),
    MAGNETO          ("Magneto",           8, Bando.VILLANOS, 1, "MagnetoTA.png"),
    APOCALYPSE       ("Apocalypse",        7, Bando.VILLANOS, 1, "ApocalypseTA.png"),
    GREEN_GOBLIN     ("Green Goblin",      7, Bando.VILLANOS, 1, "GreenGoblinTA.png"),
    VENOM            ("Venom",             7, Bando.VILLANOS, 1, "VenomTA.png"),
    BULLSEYE         ("Bullseye",          6, Bando.VILLANOS, 1, "BullseyeTA.png"),
    OMEGA_RED        ("Omega Red",         6, Bando.VILLANOS, 1, "OmegaRedTA.png"),
    ONSLAUGHT        ("Onslaught",         6, Bando.VILLANOS, 1, "OnslaughtTA.png"),
    RED_SKULL        ("Red Skull",         6, Bando.VILLANOS, 1, "RedSkullTA.png"),
    MYSTIQUE         ("Mystique",          5, Bando.VILLANOS, 1, "MystiqueTA.png"),
    MYSTERIO         ("Mysterio",          5, Bando.VILLANOS, 1, "MysterioTA.png"),
    DR_OCTOPUS       ("Dr. Octopus",       5, Bando.VILLANOS, 1, "DrOctopusTA.png"),
    DEADPOOL         ("Deadpool",          5, Bando.VILLANOS, 1, "DeadpoolTA.png"),
    ABOMINATION      ("Abomination",       4, Bando.VILLANOS, 1, "AbominationTA.png"),
    THANOS           ("Thanos",            4, Bando.VILLANOS, 1, "ThanosTA.png"),
    BLACK_CAT        ("Black Cat",         4, Bando.VILLANOS, 1, "BlackCatTA.png"),
    SABRETOOTH       ("Sabretooth",        4, Bando.VILLANOS, 1, "SabretoothTA.png"),
    JUGGERNAUT       ("Juggernaut",        3, Bando.VILLANOS, 1, "JuggernautTA.png"),
    RHINO            ("Rhino",             3, Bando.VILLANOS, 1, "RhinoTA.png"),
    CARNAGE          ("Carnage",           3, Bando.VILLANOS, 1, "CarnageTA.png"),
    MOLE_MAN         ("Mole Man",          3, Bando.VILLANOS, 1, "MoleManTA.png"),
    LIZARD           ("Lizard",            3, Bando.VILLANOS, 1, "LizardTA.png"),
    MR_SINISTER      ("Mr. Sinister",      2, Bando.VILLANOS, 1, "MrSinisterTA.png"),
    SENTINEL_1       ("Sentinel 1",        2, Bando.VILLANOS, 1, "SentinelTA.png"),
    ULTRON           ("Ultron",            2, Bando.VILLANOS, 1, "UltronTA.png"),
    SANDMAN          ("Sandman",           2, Bando.VILLANOS, 1, "SandmanTA.png"),
    LEADER           ("Leader",            2, Bando.VILLANOS, 1, "LeaderTA.png"),
    VIPER            ("Viper",             2, Bando.VILLANOS, 1, "ViperTA.png"),
    SENTINEL_2       ("Sentinel 2",        2, Bando.VILLANOS, 1, "SentinelTA.png"),
    ELECTRO          ("Electro",           2, Bando.VILLANOS, 1, "ElectroTA.png"),
    BLACK_WIDOW_V    ("Black Widow",       1, Bando.VILLANOS, 1, "BlackWidowVillanoTA.png"),
    PUMPKIN_BOMB     ("Pumpkin Bomb",      0, Bando.VILLANOS, 6, "PumpkinBombTA.png"),
    TIERRA_VILLANOS  ("Planet Earth",      0, Bando.VILLANOS, 1, "PlanetEarthVillanoTA.png");

    private final String nombre;
    private final int rango;
    private final Bando bando;
    private final int cantidad;
    private final String imagen;

    Personaje(String nombre, int rango, Bando bando, int cantidad, String imagen) {
        this.nombre = nombre;
        this.rango = rango;
        this.bando = bando;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public int getRango() {
        return rango;
    }

    public Bando getBando() {
        return bando;
    }

   
    public int getCantidad() {
        return cantidad;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean esBomba() {
        return this == NOVA_BLAST || this == PUMPKIN_BOMB;
    }

    public boolean esTierra() {
        return this == TIERRA_HEROES || this == TIERRA_VILLANOS;
    }
}