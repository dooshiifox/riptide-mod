import { readJson, snakeToTitle, writeFile } from "./util";

function getRegistryName(filename: string): string {
    const mod = filename.split("/")[0];
    const biomeName = filename.split("/").at(-1)?.replace(".json", "") ?? "";
    return `${mod}:${biomeName}`;
}

/** Creates an array of `T` with length `L` */
export type ArrayOfLength<T, L extends number> = L extends 0
    ? []
    : L extends 1
    ? [T]
    : [T, ...ArrayOfLength<T, Decrement<L>>];
/** Subtracts 1. */
// prettier-ignore
export type Decrement<N extends number> = [never, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20][N];
// prettier-ignore
export type Increment<N extends number> = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20][N];

class Parameter<
    const L extends number,
    const T extends ArrayOfLength<[number, number], L>
> {
    params: T;

    constructor(params: T) {
        this.params = params;
    }

    start() {
        return Math.min(...this.params.map((p: [number, number]) => p[0]));
    }
    end() {
        return Math.min(...this.params.map((p: [number, number]) => p[1]));
    }

    static from<const K extends [number, number]>(
        points: K
    ): Parameter<1, [K]> {
        return new Parameter([points]);
    }

    static between<
        const A extends [number, number],
        const B extends [number, number]
    >(
        a: Parameter<1, [A]>,
        b: Parameter<1, [B]>
    ): Parameter<1, [[A[0], B[1]]]> {
        return Parameter.from([
            Math.min(a.params[0][0], b.params[0][0]),
            Math.max(a.params[0][1], b.params[0][1])
        ]);
    }

    to<const A extends [number, number]>(a: Parameter<1, [A]>) {
        return Parameter.between(this, a);
    }

    and<const A extends [number, number]>(p: Parameter<1, [A]>) {
        // @ts-expect-error yeah
        return new Parameter<Increment<L>, [...T, A]>([
            ...this.params,
            ...p.params
        ]);
    }

    negated() {
        type T<F extends number> = F;
        return new Parameter<L, ArrayOfLength<[number, number], L>>(
            this.params.map(([a, b]) => [-b, -a]) as ArrayOfLength<
                [number, number],
                L
            >
        );
    }

    firstMidPoint(): number {
        return (this.params[0][0] + this.params[0][1]) / 2;
    }
    lastMidPoint(): number {
        return (this.params.at(-1)[0] + this.params.at(-1)[1]) / 2;
    }

    matches(p: number): boolean {
        for (const param of this.params) {
            if (p >= param[0] && p <= param[1]) {
                return true;
            }
        }
        return false;
    }
}

const TEST_MODE = false;

const DEFAULT = Parameter.from([
    TEST_MODE ? -Infinity : -1,
    TEST_MODE ? Infinity : 1
]);

const TEMPERATURE = {
    /** Snowy biomes. */
    Icy: Parameter.from([DEFAULT.start(), -0.45]),
    /** Plains, taigas, and meadows. */
    Cold: Parameter.from([-0.45, -0.15]),
    /** Forests and meadows. */
    Mild: Parameter.from([-0.15, 0.2]),
    /** Jungles and savanna. */
    Warm: Parameter.from([0.2, 0.55]),
    /** Desert or badlands. */
    Hot: Parameter.from([0.55, DEFAULT.end()]),
    /** Biomes with a frozen environment. */
    Frozen: Parameter.from([DEFAULT.start(), -0.45]),
    /** Biomes that dont have a frozen environment. */
    Unfrozen: Parameter.from([-0.45, DEFAULT.end()]),
    Any: DEFAULT
} as const;
const TEMPERATURES = [
    TEMPERATURE.Icy,
    TEMPERATURE.Cold,
    TEMPERATURE.Mild,
    TEMPERATURE.Warm,
    TEMPERATURE.Hot
] as const;
const HUMIDITY = {
    /** Very dry. Snowy plains, plains, cherry groves, and savanna are common. */
    Arid: Parameter.from([DEFAULT.start(), -0.35]),
    /** Snowy plains, plains, savanna. */
    Dry: Parameter.from([-0.35, -0.1]),
    /** Forests, snowy taiga, and meadows. */
    Neutral: Parameter.from([-0.1, 0.1]),
    /** Taiga, birch forests, and jungles are common. */
    Wet: Parameter.from([0.1, 0.3]),
    /** Old growth taigas, dark forests, and jungles are common. */
    Humid: Parameter.from([0.3, DEFAULT.end()]),
    Any: DEFAULT
} as const;
const HUMIDITIES = [
    HUMIDITY.Arid,
    HUMIDITY.Dry,
    HUMIDITY.Neutral,
    HUMIDITY.Wet,
    HUMIDITY.Humid
] as const;
const CONTINENTALNESS = {
    MushroomFields: Parameter.from([TEST_MODE ? -Infinity : -1.2, -1.05]),
    DeepOcean: Parameter.from([-1.05, -0.455]),
    Ocean: Parameter.from([-0.455, -0.19]),
    /** Beaches, shores, rivers, sometimes middle biomes or other. */
    Coast: Parameter.from([-0.19, -0.11]),
    /** Often middle biomes, rivers, and others. */
    NearInland: Parameter.from([-0.11, 0.03]),
    /** Nearly any biome can generate here. */
    MidInland: Parameter.from([0.03, 0.3]),
    /** Plateaus, badlands, and shattered biomes are more common. */
    FarInland: Parameter.from([0.3, DEFAULT.end()]),
    /** Anything that isn't deeply inland. */
    Inland: Parameter.from([-0.11, 0.55]),
    Any: DEFAULT
} as const;
const CONTINENTS = [
    CONTINENTALNESS.Coast,
    CONTINENTALNESS.NearInland,
    CONTINENTALNESS.MidInland,
    CONTINENTALNESS.FarInland
] as const;
const EROSION = {
    /** Snowy slopes, groves, plateau biomes, jagged/frozen/stony peaks, middle biomes */
    ExtremeMountains: Parameter.from([DEFAULT.start(), -0.78]),
    /** Snowy slopes, groves, plateau biomes, jagged/frozen/stony peaks, middle biomes */
    Mountains: Parameter.from([-0.78, -0.375]),
    /** Plateau biomes, middle biomes */
    Hills: Parameter.from([-0.375, -0.2225]),
    /** Middle biomes */
    Slopes: Parameter.from([-0.2225, 0.05]),
    /** Middle biomes */
    Flat: Parameter.from([0.05, 0.45]),
    /** Beaches, windswept savanna, and shattered biomes */
    VeryFlat: Parameter.from([0.45, 0.55]),
    /** Swamps, some beaches and middle biomes */
    ExtremelyFlat: Parameter.from([0.55, DEFAULT.end()]),
    Any: DEFAULT
} as const;
const EROSIONS = [
    EROSION.ExtremeMountains,
    EROSION.Mountains,
    EROSION.Hills,
    EROSION.Slopes,
    EROSION.Flat,
    EROSION.VeryFlat,
    EROSION.ExtremelyFlat
] as const;
const DEPTH = {
    /** Normal biomes */
    Surface: Parameter.from([-0.005, 0]),
    /** Biomes that generate ~25 - ~115 blocks underground */
    Underground: Parameter.from([0.2, 0.9]),
    Shallow: Parameter.from([0.3, 0.6]),
    /** Surface but re-declared */
    AboveFloor: Parameter.from([0.995, 1.005]),
    /** Biomes that generate >120 blocks underground (generally, Deep Dark) */
    Floor: Parameter.from([1.005, 1.115]),
    VeryDeep: Parameter.from([1.3, 2]),
    Any: DEFAULT
} as const;
const RIDGE = {
    /** middle biomes, sometimes snowy slopes/grove/shattered biomes */
    MidAscending: Parameter.from([DEFAULT.start(), -0.933]),
    /** middle biomes, often snowy slopes/grove/shattered biomes */
    HighAscending: Parameter.from([-0.933, -0.766]),
    /** jagged/frozen/stony peaks, badlands, shattered biomes, sometimes middle biomes */
    Peak: Parameter.from([-0.766, -0.566]),
    /** middle biomes, often snowy slopes/grove/shattered biomes */
    HighDescending: Parameter.from([-0.566, -0.4]),
    /** middle biomes, sometimes snowy slopes/grove/shattered biomes */
    MidDescending: Parameter.from([-0.4, -0.266]),
    /** shores, beaches, swamps, middle biomes */
    LowDescending: Parameter.from([-0.266, -0.05]),
    /** rivers, swamps, badlands */
    Valley: Parameter.from([-0.05, 0.05]),
    /** shores, beaches, swamps, middle biomes */
    Low: Parameter.from([-0.266, -0.05]),
    /** middle biomes, sometimes snowy slopes/grove/shattered biomes */
    Mid: Parameter.from([DEFAULT.start(), -0.933]).and(
        Parameter.from([-0.4, -0.266])
    ),
    /** middle biomes, often snowy slopes/grove/shattered biomes */
    High: Parameter.from([-0.933, -0.766]).and(Parameter.from([-0.566, -0.4])),
    Any: DEFAULT
} as const;
const RIDGES = [
    RIDGE.Valley,
    RIDGE.Low,
    RIDGE.Mid,
    RIDGE.High,
    RIDGE.Peak
] as const;

const BIOME = {
    AutumnalForest: "blooming_biosphere:autumnal_forest",
    Chaparral: "blooming_biosphere:chaparrel",
    Marsh: "blooming_biosphere:marsh",
    OakWoodland: "blooming_biosphere:oak_woodland",
    Rainforest: "blooming_biosphere:rainforest",
    Tidepools: "blooming_biosphere:tidepools",
    Plains: "minecraft:plains",
    SunflowerPlains: "minecraft:sunflower_plains",
    SnowyPlains: "minecraft:snowy_plains",
    IceSpikes: "minecraft:ice_spikes",
    Desert: "minecraft:desert",
    Swamp: "minecraft:swamp",
    MangroveSwamp: "minecraft:mangrove_swamp",
    Forest: "minecraft:forest",
    FlowerForest: "minecraft:flower_forest",
    BirchForest: "minecraft:birch_forest",
    DarkForest: "minecraft:dark_forest",
    PaleGarden: "minecraft:pale_garden",
    OldGrowthBirchForest: "minecraft:old_growth_birch_forest",
    OldGrowthPineTaiga: "minecraft:old_growth_pine_taiga",
    OldGrowthSpruceTaiga: "minecraft:old_growth_spruce_taiga",
    Taiga: "minecraft:taiga",
    SnowyTaiga: "minecraft:snowy_taiga",
    Savanna: "minecraft:savanna",
    SavannaPlateau: "minecraft:savanna_plateau",
    WindsweptHills: "minecraft:windswept_hills",
    WindsweptGravellyHills: "minecraft:windswept_gravelly_hills",
    WindsweptForest: "minecraft:windswept_forest",
    WindsweptSavanna: "minecraft:windswept_savanna",
    Jungle: "minecraft:jungle",
    SparseJungle: "minecraft:sparse_jungle",
    BambooJungle: "minecraft:bamboo_jungle",
    Badlands: "minecraft:badlands",
    ErodedBadlands: "minecraft:eroded_badlands",
    WoodedBadlands: "minecraft:wooded_badlands",
    Meadow: "minecraft:meadow",
    CherryGrove: "minecraft:cherry_grove",
    Grove: "minecraft:grove",
    SnowySlopes: "minecraft:snowy_slopes",
    FrozenPeaks: "minecraft:frozen_peaks",
    JaggedPeaks: "minecraft:jagged_peaks",
    StonyPeaks: "minecraft:stony_peaks",
    River: "minecraft:river",
    FrozenRiver: "minecraft:frozen_river",
    Beach: "minecraft:beach",
    SnowyBeach: "minecraft:snowy_beach",
    StonyShore: "minecraft:stony_shore",
    WarmOcean: "minecraft:warm_ocean",
    LukewarmOcean: "minecraft:lukewarm_ocean",
    DeepLukewarmOcean: "minecraft:deep_lukewarm_ocean",
    Ocean: "minecraft:ocean",
    DeepOcean: "minecraft:deep_ocean",
    ColdOcean: "minecraft:cold_ocean",
    DeepColdOcean: "minecraft:deep_cold_ocean",
    FrozenOcean: "minecraft:frozen_ocean",
    DeepFrozenOcean: "minecraft:deep_frozen_ocean",
    MushroomFields: "minecraft:mushroom_fields",
    DripstoneCaves: "minecraft:dripstone_caves",
    LushCaves: "minecraft:lush_caves",
    DeepDark: "minecraft:deep_dark",
    AlphaIslands: "terralith:alpha_islands",
    AlphaIslandsWinter: "terralith:alpha_islands_winter",
    AlpineGrove: "terralith:alpine_grove",
    AlpineHighlands: "terralith:alpine_highlands",
    AmethystCanyon: "terralith:amethyst_canyon",
    AmethystRainforest: "terralith:amethyst_rainforest",
    AncientSands: "terralith:ancient_sands",
    AridHighlands: "terralith:arid_highlands",
    AshenSavanna: "terralith:ashen_savanna",
    BasaltCliffs: "terralith:basalt_cliffs",
    BirchTaiga: "terralith:birch_taiga",
    BloomingPlateau: "terralith:blooming_plateau",
    BloomingValley: "terralith:blooming_valley",
    Brushland: "terralith:brushland",
    BryceCanyon: "terralith:bryce_canyon",
    Caldera: "terralith:caldera",
    CloudForest: "terralith:cloud_forest",
    ColdShrubland: "terralith:cold_shrubland",
    DeepWarmOcean: "terralith:deep_warm_ocean",
    DesertCanyon: "terralith:desert_canyon",
    DesertOasis: "terralith:desert_oasis",
    DesertSpires: "terralith:desert_spires",
    EmeraldPeaks: "terralith:emerald_peaks",
    ForestedHighlands: "terralith:forested_highlands",
    FracturedSavanna: "terralith:fractured_savanna",
    FrozenCliffs: "terralith:frozen_cliffs",
    GlacialChasm: "terralith:glacial_chasm",
    GraniteCliffs: "terralith:granite_cliffs",
    GravelBeach: "terralith:gravel_beach",
    GravelDesert: "terralith:gravel_desert",
    HazeMountain: "terralith:haze_mountain",
    Highlands: "terralith:highlands",
    HotShrubland: "terralith:hot_shrubland",
    IceMarsh: "terralith:ice_marsh",
    JungleMountains: "terralith:jungle_mountains",
    LavenderForest: "terralith:lavender_forest",
    LavenderValley: "terralith:lavender_valley",
    LushDesert: "terralith:lush_desert",
    LushValley: "terralith:lush_valley",
    MirageIsles: "terralith:mirage_isles",
    MoonlightGrove: "terralith:moonlight_grove",
    MoonlightValley: "terralith:moonlight_valley",
    OrchidSwamp: "terralith:orchid_swamp",
    PaintedMountains: "terralith:painted_mountains",
    RedOasis: "terralith:red_oasis",
    RockyJungle: "terralith:rocky_jungle",
    RockyMountains: "terralith:rocky_mountains",
    RockyShrubland: "terralith:rocky_shrubland",
    SakuraGrove: "terralith:sakura_grove",
    SakuraValley: "terralith:sakura_valley",
    SandstoneValley: "terralith:sandstone_valley",
    SavannaBadlands: "terralith:savanna_badlands",
    SavannaSlopes: "terralith:savanna_slopes",
    ScarletMountains: "terralith:scarlet_mountains",
    Shield: "terralith:shield",
    ShieldClearing: "terralith:shield_clearing",
    Shrubland: "terralith:shrubland",
    SiberianGrove: "terralith:siberian_grove",
    SiberianTaiga: "terralith:siberian_taiga",
    SkylandsAutumn: "terralith:skylands_autumn",
    SkylandsSpring: "terralith:skylands_spring",
    SkylandsSummer: "terralith:skylands_summer",
    SkylandsWinter: "terralith:skylands_winter",
    SnowyBadlands: "terralith:snowy_badlands",
    SnowyCherryGrove: "terralith:snowy_cherry_grove",
    SnowyMapleForest: "terralith:snowy_maple_forest",
    SnowyShield: "terralith:snowy_shield",
    Steppe: "terralith:steppe",
    StonySpires: "terralith:stony_spires",
    TemperateHighlands: "terralith:temperate_highlands",
    TropicalJungle: "terralith:tropical_jungle",
    ValleyClearing: "terralith:valley_clearing",
    VolcanicCrater: "terralith:volcanic_crater",
    VolcanicPeaks: "terralith:volcanic_peaks",
    WarmRiver: "terralith:warm_river",
    WarpedMesa: "terralith:warped_mesa",
    WhiteCliffs: "terralith:white_cliffs",
    WhiteMesa: "terralith:white_mesa",
    WindsweptSpires: "terralith:windswept_spires",
    WintryForest: "terralith:wintry_forest",
    WintryLowlands: "terralith:wintry_lowlands",
    Yellowstone: "terralith:yellowstone",
    YosemiteCliffs: "terralith:yosemite_cliffs",
    YosemiteLowlands: "terralith:yosemite_lowlands",
    AndesiteCaves: "terralith:cave/andesite_caves",
    DeepCaves: "terralith:cave/deep_caves",
    DioriteCaves: "terralith:cave/diorite_caves",
    FrostfireCaves: "terralith:cave/frostfire_caves",
    FungalCaves: "terralith:cave/fungal_caves",
    GraniteCaves: "terralith:cave/granite_caves",
    InfestedCaves: "terralith:cave/infested_caves",
    MantleCaves: "terralith:cave/mantle_caves",
    ThermalCaves: "terralith:cave/thermal_caves",
    TuffCaves: "terralith:cave/tuff_caves",
    UndergroundJungle: "terralith:cave/underground_jungle"
};

type BiomeDefinition = {
    biome: string;
    weirdness: [number, number];
    continentalness: [number, number];
    erosion: [number, number];
    temperature: [number, number];
    humidity: [number, number];
    depth: [number, number];
};
// @ts-expect-error just cause of how it was set up.
type AnyParameter = Parameter<number, Array<[number, number]>>;
class OverworldGenerator {
    biomes: Array<BiomeDefinition> = [];

    addBiome(
        name: string | [string, string],
        temperature: AnyParameter,
        humidity: AnyParameter,
        continentalness: AnyParameter,
        erosion: AnyParameter,
        ridges: AnyParameter
    ) {
        this.addBiomeInternal(
            name,
            ridges,
            continentalness,
            erosion,
            temperature,
            humidity,
            DEPTH.Surface
        );
        this.addBiomeInternal(
            name,
            ridges,
            continentalness,
            erosion,
            temperature,
            humidity,
            DEPTH.AboveFloor
        );
    }
    addCaveBiome(
        name: string | [string, string],
        temperature: AnyParameter,
        humidity: AnyParameter,
        continentalness: AnyParameter,
        erosion: AnyParameter,
        ridges: AnyParameter,
        depth: AnyParameter
    ) {
        this.addBiomeInternal(
            name,
            ridges,
            continentalness,
            erosion,
            temperature,
            humidity,
            depth
        );
    }
    private addBiomeInternal(
        name: string | [string, string],
        weirdness: AnyParameter,
        continentalness: AnyParameter,
        erosion: AnyParameter,
        temperature: AnyParameter,
        humidity: AnyParameter,
        depth: AnyParameter
    ) {
        if (typeof name === "string") {
            this.addBiomeInternal(
                [name, name],
                weirdness,
                continentalness,
                erosion,
                temperature,
                humidity,
                depth
            );
            return;
        }

        for (const weirdnessParam of weirdness.params) {
            for (const continentParam of continentalness.params) {
                for (const erosionParam of erosion.params) {
                    for (const temperatureParam of temperature.params) {
                        for (const humidityParam of humidity.params) {
                            for (const depthParam of depth.params) {
                                this.biomes.push({
                                    biome: name[0],
                                    weirdness: weirdnessParam,
                                    continentalness: continentParam,
                                    erosion: erosionParam,
                                    temperature: temperatureParam,
                                    humidity: humidityParam,
                                    depth: depthParam
                                });
                                if (weirdnessParam[1] <= 0) {
                                    this.biomes.push({
                                        biome: name[1],
                                        weirdness: [
                                            -weirdnessParam[1],
                                            -weirdnessParam[0]
                                        ],
                                        continentalness: continentParam,
                                        erosion: erosionParam,
                                        temperature: temperatureParam,
                                        humidity: humidityParam,
                                        depth: depthParam
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    optimiseFor(
        key:
            | "weirdness"
            | "continentalness"
            | "erosion"
            | "temperature"
            | "humidity"
            | "depth"
    ) {
        const cmpKeys = [
            "weirdness",
            "continentalness",
            "erosion",
            "temperature",
            "humidity",
            "depth"
        ].filter((x) => x !== key);

        let i = 0;
        while (i < this.biomes.length) {
            const biome = this.biomes[i];
            // Find all biomes with the same everything-but-weirdness
            const similarBiomes = [i];
            for (let j = i + 1; j < this.biomes.length; j++) {
                const maybeSimilarBiome = this.biomes[j];
                if (
                    cmpKeys.every((k) =>
                        paramsEq(biome[k], maybeSimilarBiome[k])
                    ) &&
                    biome.biome === maybeSimilarBiome.biome
                ) {
                    similarBiomes.push(j);
                }
            }

            // Sort ascending
            similarBiomes.sort(
                (a, b) => this.biomes[a][key][0] - this.biomes[b][key][0]
            );
            const createdBiome = this.biomes[similarBiomes.shift()];
            const removeIndices = [];
            while (similarBiomes.length) {
                const next = similarBiomes.findIndex(
                    (s) => createdBiome[key][1] === this.biomes[s][key][0]
                );
                if (next === -1) break;
                // Merge
                createdBiome[key][1] = this.biomes[similarBiomes[next]][key][1];
                removeIndices.push(similarBiomes[next]);
                similarBiomes.splice(next, 1);
            }
            removeIndices.forEach((index) => this.biomes.splice(index, 1));
            this.biomes[i] = createdBiome;
            i++;
        }
    }

    optimise() {
        this.optimiseFor("weirdness");
        // Take a long time and barely reduce it by anything
        // this.optimiseFor("erosion");
        // this.optimiseFor("continentalness");
        // this.optimiseFor("humidity");
        // this.optimiseFor("temperature");
        // this.optimiseFor("depth");
    }
}

function paramsEq(a: [number, number], b: [number, number]) {
    return a[0] === b[0] && a[1] === b[1];
}

export function overworldGenerator(filenames: Array<string>) {
    const gen = new OverworldGenerator();

    const addedBiomes = new Set(gen.biomes.map((b) => b.biome));
    const knownBiomes = new Set(filenames.map(getRegistryName));
    for (const oneButNotOther of addedBiomes.symmetricDifference(knownBiomes)) {
        if (addedBiomes.has(oneButNotOther)) {
            console.log(`${oneButNotOther} was given but it doesn't exist?`);
        } else {
            console.log(`${oneButNotOther} was not generated!`);
        }
    }
}

export function biomeDefsParser(file: string) {
    const gen = new OverworldGenerator();

    for (const line of file.split("\n")) {
        if (!line) continue;
        const [env, biomes] = line.split(":").map((s) => s.trim());
        const [
            tempName,
            humidName,
            contName,
            eroName,
            ridgeName,
            depthName = "surface"
        ] = env.split(",").map((s) => s.toLowerCase().replaceAll(" ", ""));
        const temp = (
            {
                icy: TEMPERATURE.Icy,
                cold: TEMPERATURE.Cold,
                mild: TEMPERATURE.Mild,
                warm: TEMPERATURE.Warm,
                hot: TEMPERATURE.Hot,
                any: TEMPERATURE.Any
            } as const
        )[tempName];
        const humid = (
            {
                arid: HUMIDITY.Arid,
                dry: HUMIDITY.Dry,
                neutral: HUMIDITY.Neutral,
                wet: HUMIDITY.Wet,
                humid: HUMIDITY.Humid,
                // Lush caves
                extremelyhumid: Parameter.from([0.7, DEFAULT.end()]),
                any: HUMIDITY.Any
            } as const
        )[humidName];
        const cont = (
            {
                island: CONTINENTALNESS.MushroomFields,
                skyisland: Parameter.from([-0.75, -0.45]),
                deepocean: CONTINENTALNESS.DeepOcean,
                ocean: CONTINENTALNESS.Ocean,
                cliffs: Parameter.from([-0.2, -0.11]),
                coastal: CONTINENTALNESS.Coast,
                nearinland: CONTINENTALNESS.NearInland,
                midinland: CONTINENTALNESS.MidInland,
                farinland: CONTINENTALNESS.FarInland,
                // Dripstone caves
                extremelyinland: Parameter.from([0.8, DEFAULT.end()]),
                any: CONTINENTALNESS.Any
            } as const
        )[contName];
        const ero = (
            {
                extrememountains: EROSION.ExtremeMountains,
                mountains: EROSION.Mountains,
                hills: EROSION.Hills,
                slopes: EROSION.Slopes,
                flat: EROSION.Flat,
                veryflat: EROSION.VeryFlat,
                extremelyflat: EROSION.ExtremelyFlat,
                any: EROSION.Any
            } as const
        )[eroName];
        const ridge = (
            {
                valley: RIDGE.Valley,
                low: RIDGE.Low,
                mid: RIDGE.Mid,
                high: RIDGE.High,
                midascending: RIDGE.MidAscending,
                middescending: RIDGE.MidDescending,
                highascending: RIDGE.HighAscending,
                highdescending: RIDGE.HighDescending,
                peak: RIDGE.Peak,
                skyisland: Parameter.from([0.19, 1]),
                any: RIDGE.Any
            } as const
        )[ridgeName];
        if (ridge === undefined) {
            throw new Error(`Unknown ridge: ${ridgeName}\n\t${line}`);
        }

        let [
            [biomeNormal, biomeOffset = 0],
            [biomeVariant, biomeVariantOffset]
        ] = [...biomes.split("&"), ""].map((s) => {
            const noSpace = s.replaceAll(" ", "").toLowerCase();
            const [name, offset] = noSpace.split("@");
            if (!offset) return [name, undefined];
            return [name, parseFloat(offset)];
        });
        if (!biomeVariant) biomeVariant = biomeNormal;
        if (biomeVariantOffset === undefined) biomeVariantOffset = biomeOffset;

        const findBiomeNormalId = Object.entries(BIOME).find(
            ([k]) => k.toLowerCase() === biomeNormal
        );
        if (!findBiomeNormalId) {
            throw new Error(`Cannot find biome: ${biomeNormal}\n\t${line}`);
        }
        const biomeNormalId = findBiomeNormalId[1];
        const findBiomeVariantId = Object.entries(BIOME).find(
            ([k]) => k.toLowerCase() === biomeVariant
        );
        if (!findBiomeVariantId) {
            throw new Error(`Cannot find biome: ${biomeVariant}\n\t${line}`);
        }
        const biomeVariantId = findBiomeVariantId[1];

        if (depthName !== "surface") {
            const depth = (
                {
                    underground: DEPTH.Underground,
                    shallow: DEPTH.Shallow,
                    floor: DEPTH.Floor,
                    verydeep: DEPTH.VeryDeep
                } as const
            )[depthName];

            gen.addCaveBiome(
                [biomeNormalId, biomeVariantId],
                temp,
                humid,
                cont,
                ero,
                ridge,
                depth
            );
        } else {
            gen.addBiome(
                [biomeNormalId, biomeVariantId],
                temp,
                humid,
                cont,
                ero,
                ridge
            );
        }
    }

    gen.optimise();

    const addedBiomes = new Set(gen.biomes.map((b) => b.biome));
    const knownBiomes = new Set(Object.values(BIOME));
    for (const oneButNotOther of addedBiomes.symmetricDifference(knownBiomes)) {
        if (addedBiomes.has(oneButNotOther)) {
            console.log(`${oneButNotOther} was given but it doesn't exist?`);
        } else {
            console.log(`${oneButNotOther} was not generated!`);
        }
    }

    const out = [];
    for (const biome of gen.biomes) {
        out.push({
            biome: biome.biome,
            parameters: {
                continentalness: biome.continentalness,
                depth: biome.depth,
                erosion: biome.erosion,
                humidity: biome.humidity,
                offset: 0,
                temperature: biome.temperature,
                weirdness: biome.weirdness
            }
        });
    }

    // return out;

    writeFile(
        "minecraft/dimension/overworld.json",
        JSON.stringify(
            {
                type: "minecraft:overworld",
                generator: {
                    type: "minecraft:noise",
                    settings: "minecraft:overworld",
                    biome_source: {
                        type: "minecraft:multi_noise",
                        biomes: out
                    }
                }
            },
            null,
            4
        )
    );
}

export function overworldParser(file: any, ignoreUnknowns = false) {
    const biomes = (
        file as Array<{
            biome: string;
            parameters: {
                continentalness: [number, number];
                depth: [number, number] | number;
                erosion: [number, number];
                humidity: [number, number];
                offset: 0;
                temperature: [number, number];
                weirdness: [number, number];
            };
        }>
    ).map((b) => {
        if (typeof b.parameters.depth === "number") {
            if (b.parameters.depth >= 0 || b.parameters.depth === 1)
                b.parameters.depth = [-Infinity, 0];
            else b.parameters.depth = [b.parameters.depth, Infinity];
        }
        return {
            biome: b.biome,
            weirdness: b.parameters.weirdness,
            continentalness: b.parameters.continentalness,
            erosion: b.parameters.erosion,
            temperature: b.parameters.temperature,
            humidity: b.parameters.humidity,
            depth: b.parameters.depth
        };
    });

    let output = ``;
    for (let i = 0; i < TEMPERATURES.length; i++) {
        const tempName = (["Icy", "Cold", "Mild", "Warm", "Hot"] as const)[i];
        const temp = TEMPERATURES[i].firstMidPoint();
        for (let j = 0; j < HUMIDITIES.length; j++) {
            const humidName = (
                ["Arid", "Dry", "Neutral", "Wet", "Humid"] as const
            )[j];
            const humid = HUMIDITIES[j].firstMidPoint();
            for (let k = 0; k < CONTINENTS.length; k++) {
                const contName = (
                    [
                        "Coastal",
                        "Near Inland",
                        "Mid Inland",
                        "Far Inland"
                    ] as const
                )[k];
                const cont = CONTINENTS[k].firstMidPoint();
                for (let l = 0; l < EROSIONS.length; l++) {
                    const eroName = (
                        [
                            "Extreme Mountains",
                            "Mountains",
                            "Hills",
                            "Slopes",
                            "Flat",
                            "Very Flat",
                            "Extremely Flat"
                        ] as const
                    )[l];
                    const ero = EROSIONS[l].firstMidPoint();
                    for (let m = 0; m < RIDGES.length; m++) {
                        const ridgeName = (
                            ["Valley", "Low", "Mid", "High", "Peak"] as const
                        )[m];
                        const ridge = RIDGES[m];

                        const biomeAscending = getBiomeAt(
                            biomes,
                            temp,
                            humid,
                            cont,
                            ero,
                            ridge.firstMidPoint(),
                            0
                        );
                        const biomeVariantDescending = getBiomeAt(
                            biomes,
                            temp,
                            humid,
                            cont,
                            ero,
                            -ridge.firstMidPoint(),
                            0
                        );
                        const biomeDescending = getBiomeAt(
                            biomes,
                            temp,
                            humid,
                            cont,
                            ero,
                            ridge.lastMidPoint(),
                            0
                        );
                        const biomeVariantAscending = getBiomeAt(
                            biomes,
                            temp,
                            humid,
                            cont,
                            ero,
                            -ridge.lastMidPoint(),
                            0
                        );

                        if (
                            ignoreUnknowns &&
                            !biomeAscending &&
                            !biomeVariantDescending &&
                            !biomeAscending &&
                            !biomeVariantAscending
                        ) {
                            continue;
                        }

                        const biomeNameAscending = snakeToTitle(
                            biomeAscending.split(":")[1] ?? ""
                        );
                        const biomeVariantNameDescending = snakeToTitle(
                            biomeVariantDescending.split(":")[1] ?? ""
                        );
                        const biomeNameDescending = snakeToTitle(
                            biomeDescending.split(":")[1] ?? ""
                        );
                        const biomeVariantNameAscending = snakeToTitle(
                            biomeVariantAscending.split(":")[1] ?? ""
                        );

                        const hasDifference =
                            biomeNameAscending !== biomeNameDescending ||
                            biomeVariantNameAscending !==
                                biomeVariantNameDescending;
                        output += `${tempName}, ${humidName}, ${contName}, ${eroName}, ${ridgeName}${
                            hasDifference ? " Ascending" : ""
                        }: ${biomeNameAscending}${
                            biomeNameAscending !== biomeVariantNameDescending
                                ? " & " + biomeVariantNameDescending
                                : ""
                        }\n`;

                        if (hasDifference) {
                            output += `${tempName}, ${humidName}, ${contName}, ${eroName}, ${ridgeName} Descending: ${biomeNameDescending}${
                                biomeNameDescending !==
                                biomeVariantNameAscending
                                    ? " & " + biomeVariantNameAscending
                                    : ""
                            }\n`;
                        }
                    }
                }
            }
        }
    }

    return output;
}

async function overworldBiomesFor(biome: string) {
    const ow = await readJson("overworld.json");
    const biomes = ow.generator.biome_source.biomes.filter((b) =>
        b.biome.startsWith(biome)
    );

    console.log(biomes);
    return overworldParser(biomes, true);
}
// pnpm tsx ./overworldGen.ts -- terralith:frozen_cliffs
if (process.argv[2]) {
    overworldBiomesFor(process.argv[2]).then(console.log);
}
// overworldBiomesFor("").then((biomes) =>
//     writeFile("./og-biomes.biomedefs", biomes)
// );

function getBiomeAt(
    biomes: Array<BiomeDefinition>,
    temp: number,
    humid: number,
    cont: number,
    ero: number,
    ridge: number,
    depth: number
): string {
    return (
        biomes.find(
            (parameters) =>
                Parameter.from(parameters.temperature).matches(temp) &&
                Parameter.from(parameters.humidity).matches(humid) &&
                Parameter.from(parameters.continentalness).matches(cont) &&
                Parameter.from(parameters.erosion).matches(ero) &&
                Parameter.from(parameters.weirdness).matches(ridge) &&
                Parameter.from(parameters.depth).matches(depth)
        )?.biome ?? ""
    );
}

// For 100,000,000 randomly generated points (biased towards 0), calculate
// the generated biomes and their percentages.
