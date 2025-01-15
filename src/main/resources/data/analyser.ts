import {
    biomeDefsParser,
    overworldGenerator,
    overworldParser
} from "./overworldGen";
import { readFile, readJson, recursiveReadDir, snakeToPascal } from "./util";
import { PNG } from "pngjs";
import * as fs from "node:fs";

function translationGenerator(filenames: Array<string>) {
    const obj = {};
    for (const biome of filenames) {
        const mod = biome.split("/")[0];
        const biomeName = biome.split("/").at(-1).replace(".json", "");
        // obj[`biome.${mod}.${biomeName}`] = snakeToTitle(biomeName);
        obj[snakeToPascal(biomeName)] = `${mod}:${biomeName}`;
    }
    console.log(JSON.stringify(obj, null, 4));
}

function decimalToRgb(dec: number): { r: number; g: number; b: number } {
    const r = dec >> 16;
    const g = (dec & 0x00ff00) >> 8;
    const b = dec & 255;
    return { r, g, b };
}
function rgbToHex(rgb: { r: number; g: number; b: number }) {
    const fmt = (s: number) => s.toString(16).padStart(2, "0");
    return `#${fmt(rgb.r)}${fmt(rgb.g)}${fmt(rgb.b)}`;
}

function clamp(num: number, min: number, max: number) {
    return Math.min(Math.max(num, min), max);
}

async function getDecimalColorAt(
    file: string,
    temperature: number,
    downfall: number
) {
    const temp = clamp(temperature, 0, 1);
    const down = clamp(downfall, 0, 1);
    const i = Math.floor((1.0 - temp) * 255.0);
    const j = Math.floor((1.0 - down * temp) * 255.0);
    const k = (j << 8) | i;

    return new Promise<number>((res) => {
        fs.createReadStream(file)
            .pipe(new PNG())
            .on("parsed", function () {
                if (k * 4 >= this.data.length) {
                    res(-65281);
                } else {
                    res(
                        (this.data[k * 4] << 16) +
                            (this.data[k * 4 + 1] << 8) +
                            this.data[k * 4 + 2]
                    );
                }
            });
    });
}

async function main() {
    const biomes = [
        ...(await recursiveReadDir("terralith/worldgen/biome")),
        // ...(await recursiveReadDir("nullscape/worldgen/biome")),
        ...(await recursiveReadDir("minecraft/worldgen/biome")),
        ...(await recursiveReadDir("blooming_biosphere/worldgen/biome"))
    ].map(({ filename, content }) => ({
        filename,
        content: {
            has_precipitation: (content.has_precipitation as boolean) || false,
            temperature: content.temperature as number,
            temperature_modifier:
                (content.temperature_modifier as "frozen" | "none") || "none",
            downfall: content.downfall as number,
            effects: {
                fog_color: decimalToRgb(content.effects.fog_color || 12638463),
                sky_color: decimalToRgb(content.effects.sky_color),
                water_color: decimalToRgb(
                    content.effects.water_color || 4159204
                ),
                water_fog_color: decimalToRgb(
                    content.effects.water_fog_color || 329011
                ),
                async foliage_color() {
                    return decimalToRgb(
                        content.effects.foliage_color ||
                            (await getDecimalColorAt(
                                "./foliage.png",
                                content.temperature,
                                content.downfall
                            ))
                    );
                },
                async grass_color() {
                    if (content.effects.grass_color_modifier === "swamp") {
                        return decimalToRgb(5011004);
                    }
                    let dec =
                        content.effects.grass_color ||
                        (await getDecimalColorAt(
                            "./grass.png",
                            content.temperature,
                            content.downfall
                        ));
                    if (
                        content.effects.grass_color_modifier === "dark_forest"
                    ) {
                        dec = ((dec & 16711422) + 2634762) >> 1;
                    }
                    return decimalToRgb(dec);
                },
                grass_color_modifier:
                    (content.effects.grass_color_modifier as
                        | "none"
                        | "dark_forest"
                        | "swamp") || "none"
            }
        },
        features: content.features as string[][]
    }));

    // for (const biome of biomes) {
    //     const rgb = await biome.content.effects.foliage_color();
    //     const mod = biome.filename.split("/")[0];
    //     const biomeName = biome.filename.split("/").at(-1).replace(".json", "");
    //     console.log(
    //         `\x1b[38;2;${rgb.r};${rgb.g};${rgb.b}m ${rgbToHex(
    //             rgb
    //         )} ${mod}:${biomeName}`
    //     );
    // }

    // translationGenerator(biomes.map((b) => b.filename));
    // overworldGenerator(biomes.map((b) => b.filename));
    // writeFile("./biomes.biomedefs", await overworldParser(
    //     (await readJson("./overworld.json")).generator.biome_source.biomes
    // ););
    biomeDefsParser(await readFile("./biomes.biomedefs"));

    // /** @type {GoodMap<string>} */
    // const featureToIntIdMap = new GoodMap();
    // /** @type {GoodMap<string>} */
    // const biomeToIntIdMap = new GoodMap();
    // let nextFeatureId = 0;
    // let nextBiomeId = 0;

    // /** @type {(a: FeatureData, b: FeatureData) => number} */
    // const compareByStepThenByIndex = (a, b) =>
    //     a.step - b.step || a.featureId - b.featureId;
    // /** @type {TreeMap<FeatureData, TreeSet<FeatureData>>} */
    // const nodesToChildren = new TreeMap(compareByStepThenByIndex);

    // let maxSteps = 0;
    // /** @type {GoodMap<FeatureData, GoodMap<BiomeData, Set<number>>>} */
    // let nodesToTracebacks = new GoodMap();

    // for (const biome of biomes) {
    //     const biomeName = biome.filename.split("/").at(-1).replace(".json", "");
    //     /** @type {FeatureData[]} */
    //     const flatDataList = [];
    //     const features = biome.features;

    //     maxSteps = Math.max(maxSteps, features.length);

    //     for (let stepIndex = 0; stepIndex < features.length; ++stepIndex) {
    //         let biomeIndex = 0;
    //         for (const feature of features[stepIndex]) {
    //             /** @type {FeatureData} */
    //             const featureIdentity = {
    //                 featureId: featureToIntIdMap.insertAndGet(
    //                     feature,
    //                     () => nextFeatureId++
    //                 ),
    //                 step: stepIndex,
    //                 feature,
    //                 source: biome.filename
    //             };
    //             flatDataList.push(featureIdentity);

    //             /** @type {BiomeData} */
    //             const biomeIdentity = {
    //                 biomeId: biomeToIntIdMap.insertAndGet(
    //                     biomeName,
    //                     () => nextBiomeId++
    //                 ),
    //                 biome: biomeName,
    //                 source: biome.filename
    //             };
    //             nodesToTracebacks
    //                 .insertAndGet(featureIdentity, () => new GoodMap())
    //                 .insertAndGet(biomeIdentity, () => new Set())
    //                 .add(biomeIndex);
    //             biomeIndex++;
    //         }
    //     }

    //     for (
    //         let featureIndex = 0;
    //         featureIndex < flatDataList.length;
    //         featureIndex++
    //     ) {
    //         const children = nodesToChildren.insertAndGet(
    //             flatDataList[featureIndex],
    //             () => new TreeSet(compareByStepThenByIndex)
    //         );
    //         if (featureIndex < flatDataList.length - 1) {
    //             children.add(flatDataList[featureIndex + 1]);
    //         }
    //     }
    // }

    // /** @type {TreeSet<FeatureData>} */
    // const nonCyclicalNodes = new TreeSet(compareByStepThenByIndex);
    // /** @type {TreeSet<FeatureData>} */
    // const inProgress = new TreeSet(compareByStepThenByIndex);
    // /** @type {FeatureData[]} */
    // const sortedFeatureData = [];
    // /** @type {FeatureData[]} */
    // let featureCycle = [];

    // await fs.writeFile(
    //     "./debug.txt",
    //     JSON.stringify(nodesToChildren, null, 42)
    // );
    // await fs.writeFile(
    //     "./nodesToTracebacks.txt",
    //     JSON.stringify(nodesToTracebacks, null, 42)
    // );

    // for (const node of nodesToChildren.keySet()) {
    //     if (inProgress.size > 0) {
    //         throw new Error(
    //             "Iteration finished with non-empty in-progress vertex set."
    //         );
    //     }

    //     if (
    //         !nonCyclicalNodes.has(node) &&
    //         depthFirstSearch(
    //             nodesToChildren,
    //             nonCyclicalNodes,
    //             inProgress,
    //             (item) => sortedFeatureData.push(item),
    //             (item) => featureCycle.push(item),
    //             node
    //         )
    //     ) {
    //         if (featureCycle.length <= 1) {
    //             throw new Error(
    //                 "There was a feature cycle that involved 0 or 1 features??"
    //             );
    //         }

    //         const loop = featureCycle[0];
    //         for (let i = 1; i < featureCycle.length; i++) {
    //             if (deepEqual(featureCycle[i], loop)) {
    //                 featureCycle = featureCycle.slice(0, i + 1);
    //                 break;
    //             }
    //         }

    //         featureCycle.reverse();

    //         throw new FeatureCycleError(nodesToTracebacks, featureCycle);
    //     }
    // }

    // console.log("success!");
}

function depthFirstSearch(
    edges: TreeMap<FeatureData, TreeSet<FeatureData>>,
    nonCyclicalNodes: TreeSet<FeatureData>,
    pathSet: TreeSet<FeatureData>,
    onNonCyclicalNodeFound: (item: FeatureData) => void,
    onCyclicalNodeFound: (item: FeatureData) => void,
    start: FeatureData
) {
    if (nonCyclicalNodes.has(start)) return false;
    else if (pathSet.has(start)) {
        onCyclicalNodeFound(start);
        return true;
    }

    pathSet.add(start);
    for (const next of edges.get(start) ?? []) {
        if (
            depthFirstSearch(
                edges,
                nonCyclicalNodes,
                pathSet,
                onNonCyclicalNodeFound,
                onCyclicalNodeFound,
                next
            )
        ) {
            onCyclicalNodeFound(start);
            return true;
        }
    }

    pathSet.remove(start);
    nonCyclicalNodes.add(start);
    onNonCyclicalNodeFound(start);
    return false;
}

class FeatureCycleError extends Error {
    constructor(
        tracebacks: GoodMap<FeatureData, GoodMap<BiomeData, Set<number>>>,
        cycle: FeatureData[]
    ) {
        let error = "A feature cycle was found.\n\nCycle:\n";

        const start = cycle[0];
        let prevTracebacks = tracebacks.get(start);
        error += `At step ${start.step}\nFeature '${start.feature}'\n`;

        console.log(cycle[0], prevTracebacks.keys());

        for (let i = 1; i < cycle.length; i++) {
            const current = cycle[i];
            const currentTracebacks = tracebacks.get(current);
            let found = 0;
            const intersection: Set<BiomeData> = new Set(
                prevTracebacks.keys()
            ).intersection(new Set(currentTracebacks.keys()));
            for (const biome of intersection) {
                const prevTb = Math.min(...prevTracebacks.get(biome));
                const currTb = Math.max(...currentTracebacks.get(biome));
                if (prevTb < currTb) {
                    if (found === 0) {
                        error += ` must be before '${current.feature}' (defined in ${biome.biome} at index ${prevTb}, ${currTb}`;
                    }
                    found++;
                }
            }
            if (found > 1) {
                error += ` and ${found - 1} others)\n`;
            } else if (found > 0) {
                error += `)\n`;
            }

            prevTracebacks = currentTracebacks;
        }
        super(error);
    }
}

class TreeSet<K> {
    private m: TreeMap<K, 1>;

    get size() {
        return this.m.size;
    }

    constructor(cmp: (a: K, b: K) => number) {
        this.m = new TreeMap(cmp);
    }

    toJSON() {
        return this.m.keySet();
    }

    has(key: K): boolean {
        return this.m.get(key) !== undefined;
    }

    add(key: K): boolean {
        return this.m.set(key, 1) === undefined;
    }

    remove(key: K): boolean {
        return this.m.remove(key) === 1;
    }

    *[Symbol.iterator]() {
        for (const key of this.m.keySet()) {
            yield key;
        }
    }
}

class TreeMapEntry<K, V> {
    static RED = false;
    static BLACK = true;

    key: K;
    value: V;
    parent: TreeMapEntry<K, V> | null;
    left: TreeMapEntry<K, V> | null = null;
    right: TreeMapEntry<K, V> | null = null;
    color = TreeMapEntry.BLACK;

    constructor(key: K, value: V, parent: TreeMapEntry<K, V> | null) {
        this.parent = parent;
        this.key = key;
        this.value = value;
    }

    successor(): TreeMapEntry<K, V> | null {
        if (this.right !== null) {
            let p = this.right;
            while (p.left !== null) p = p.left;
            return p;
        }
        let p = this.parent;
        let ch: TreeMapEntry<K, V> = this;
        while (p !== null && ch === p.right) {
            ch = p;
            p = p.parent;
        }
        return p;
    }

    static colorOf<K, V>(p: TreeMapEntry<K, V> | null): boolean {
        return p === null ? TreeMapEntry.BLACK : p.color;
    }
    static setColor<K, V>(p: TreeMapEntry<K, V> | null, c: boolean) {
        if (p !== null) p.color = c;
    }
    static parentOf<K, V>(
        p: TreeMapEntry<K, V> | null
    ): TreeMapEntry<K, V> | null {
        return p === null ? null : p.parent;
    }
    static leftOf<K, V>(
        p: TreeMapEntry<K, V> | null
    ): TreeMapEntry<K, V> | null {
        return p === null ? null : p.left;
    }
    static rightOf<K, V>(
        p: TreeMapEntry<K, V> | null
    ): TreeMapEntry<K, V> | null {
        return p === null ? null : p.right;
    }
}

class TreeMap<K, V> {
    root: TreeMapEntry<K, V> | null = null;
    size = 0;
    modCount = 0;
    cmp: (a: K, b: K) => number;

    constructor(cmp: (a: K, b: K) => number) {
        this.cmp = cmp;
    }

    private addEntryToEmptyMap(key: K, value: V) {
        this.root = new TreeMapEntry(key, value, null);
        this.size = 1;
        this.modCount++;
    }

    private addEntry(
        key: K,
        value: V,
        parent: TreeMapEntry<K, V>,
        addToLeft: boolean
    ) {
        const e = new TreeMapEntry(key, value, parent);
        if (addToLeft) parent.left = e;
        else parent.right = e;
        this.fixAfterInsertion(e);
        this.size++;
        this.modCount++;
    }

    private rotateLeft(p: TreeMapEntry<K, V> | null) {
        if (p === null) {
            return;
        }

        const r = p.right;
        p.right = r.left;
        if (r.left !== null) r.left.parent = p;
        r.parent = p.parent;
        if (p.parent === null) this.root = r;
        else if (p.parent.left === p) p.parent.left = r;
        else p.parent.right = r;
        r.left = p;
        p.parent = r;
    }

    private rotateRight(p: TreeMapEntry<K, V> | null) {
        if (p === null) {
            return;
        }

        const l = p.left;
        p.left = l.right;
        if (l.right !== null) l.right.parent = p;
        l.parent = p.parent;
        if (p.parent === null) this.root = l;
        else if (p.parent.right == p) p.parent.right = l;
        else p.parent.left = l;
        l.right = p;
        p.parent = l;
    }

    private fixAfterInsertion(x: TreeMapEntry<K, V>) {
        x.color = TreeMapEntry.RED;

        const parentOf = TreeMapEntry.parentOf;
        const leftOf = TreeMapEntry.leftOf;
        const rightOf = TreeMapEntry.rightOf;
        const colorOf = TreeMapEntry.colorOf;
        const setColor = TreeMapEntry.setColor;
        const RED = TreeMapEntry.RED;
        const BLACK = TreeMapEntry.BLACK;

        while (x !== null && x !== this.root && x.parent.color == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                const y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) === RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        this.rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    this.rotateRight(parentOf(parentOf(x)));
                }
            } else {
                const y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        this.rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    this.rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        this.root.color = BLACK;
    }

    getEntry(key: K): TreeMapEntry<K, V> | undefined {
        let p = this.root;
        while (p !== null) {
            const cmp = this.cmp(key, p.key);
            if (cmp < 0) p = p.left;
            else if (cmp > 0) p = p.right;
            else return p;
        }
        return undefined;
    }
    get(key: K): V | undefined {
        return this.getEntry(key)?.value;
    }

    set(key: K, value: V): V | undefined {
        if (this.root === null) {
            this.addEntryToEmptyMap(key, value);
            return undefined;
        }

        let cmp: number;
        let parent: TreeMapEntry<K, V>;
        let t: TreeMapEntry<K, V> = this.root;

        do {
            parent = t;
            cmp = this.cmp(key, t.key);
            if (cmp < 0) t = t.left;
            else if (cmp > 0) t = t.right;
            else {
                const oldValue = t.value;
                t.value = value;
                return oldValue;
            }
        } while (t !== null);
        this.addEntry(key, value, parent, cmp < 0);
        return undefined;
    }

    insertAndGet(key: K, cb: (key: K) => V): V {
        const existing = this.get(key);
        if (existing !== undefined) return existing;
        const val = cb(key);
        this.set(key, val);
        return val;
    }

    getFirstEntry(): TreeMapEntry<K, V> | null {
        let p = this.root;
        if (p === null) return null;
        while (p.left !== null) p = p.left;
        return p;
    }

    keySet(): K[] {
        const list = [];
        let next = this.getFirstEntry();
        while (next !== null) {
            list.push(next.key);
            next = next.successor();
        }
        return list;
    }

    toJSON() {
        const obj: Record<string, V> = {};
        let next = this.getFirstEntry();
        while (next !== null) {
            obj[JSON.stringify(next.key)] = next.value;
            next = next.successor();
        }
        return obj;
    }

    remove(key: K): V {
        const p = this.getEntry(key);
        if (p === null) return null;
        const oldValue = p.value;
        this.deleteEntry(p);
        return oldValue;
    }

    private deleteEntry(p: TreeMapEntry<K, V>) {
        this.modCount++;
        this.size--;

        if (p.left !== null && p.right !== null) {
            const s = p.successor();
            p.key = s.key;
            p.value = s.value;
            p = s;
        }

        const replacement = p.left !== null ? p.left : p.right;
        if (replacement !== null) {
            replacement.parent = p.parent;
            if (p.parent === null) this.root = replacement;
            else if (p === p.parent.left) p.parent.left = replacement;
            else p.parent.right = replacement;

            p.left = p.right = p.parent = null;

            if (p.color === TreeMapEntry.BLACK) {
                this.fixAfterDeletion(replacement);
            }
        } else if (p.parent === null) {
            this.root = null;
        } else {
            if (p.color === TreeMapEntry.BLACK) {
                this.fixAfterDeletion(p);
            }

            if (p.parent !== null) {
                if (p === p.parent.left) p.parent.left = null;
                else if (p === p.parent.right) p.parent.right = null;
                p.parent = null;
            }
        }
    }

    private fixAfterDeletion(x: TreeMapEntry<K, V>) {
        const parentOf = TreeMapEntry.parentOf;
        const leftOf = TreeMapEntry.leftOf;
        const rightOf = TreeMapEntry.rightOf;
        const colorOf = TreeMapEntry.colorOf;
        const setColor = TreeMapEntry.setColor;
        const RED = TreeMapEntry.RED;
        const BLACK = TreeMapEntry.BLACK;

        while (x != this.root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                let sib = rightOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    this.rotateLeft(parentOf(x));
                    sib = rightOf(parentOf(x));
                }

                if (
                    colorOf(leftOf(sib)) == BLACK &&
                    colorOf(rightOf(sib)) == BLACK
                ) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sib)) == BLACK) {
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        this.rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    this.rotateLeft(parentOf(x));
                    x = this.root;
                }
            } else {
                // symmetric
                let sib = leftOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    this.rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                }

                if (
                    colorOf(rightOf(sib)) == BLACK &&
                    colorOf(leftOf(sib)) == BLACK
                ) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        this.rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    this.rotateRight(parentOf(x));
                    x = this.root;
                }
            }
        }

        setColor(x, BLACK);
    }
}

class GoodMap<K, V> extends Map<K, V> {
    toJSON() {
        return Object.fromEntries(
            this.entries().map(([k, v]) => [JSON.stringify(k), v])
        );
    }

    insertAndGet(key: K, cb: (key: K) => V): V {
        const existing = this.get(key);
        if (existing !== undefined) return existing;
        const val = cb(key);
        this.set(key, val);
        return val;
    }
}

interface FeatureData {
    featureId: number;
    step: number;
    feature: string;
    source: string;
}
interface BiomeData {
    biomeId: number;
    biome: string;
    source: string;
}

main();
