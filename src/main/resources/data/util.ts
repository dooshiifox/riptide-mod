import * as fs from "node:fs/promises";
import * as fsSync from "node:fs";

export async function readJson(path: string) {
    const p = await fs.readFile(path, "utf8");
    return JSON.parse(p);
}
export async function readFile(path: string) {
    return await fs.readFile(path, "utf8");
}
export async function writeFile(path: string, contents: string) {
    if (fsSync.existsSync(path)) {
        throw new Error(`File ${path} already exists. Delete first.`);
    }
    await fs.writeFile(path, contents);
}
export async function recursiveReadDir(path: string) {
    const files: Array<{ filename: string; content: any }> = [];
    for (const p of await fs.readdir(path, { recursive: true })) {
        if (p.endsWith(".json")) {
            files.push({
                filename: `${path}/${p}`,
                content: await readJson(`${path}/${p}`)
            });
        }
    }
    return files;
}
export function deepEqual(x: any, y: any) {
    if (x === y) {
        return true;
    } else if (
        typeof x == "object" &&
        x != null &&
        typeof y == "object" &&
        y != null
    ) {
        if (Object.keys(x).length != Object.keys(y).length) {
            return false;
        }

        for (var prop in x) {
            if (y.hasOwnProperty(prop)) {
                if (!deepEqual(x[prop], y[prop])) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    } else {
        return false;
    }
}

export const snakeToCamel = (str: string) =>
    str.replace(/([-_]\w)/g, (g) => g[1].toUpperCase());
export const snakeToPascal = (str: string) => {
    let camelCase = snakeToCamel(str);
    let pascalCase = camelCase[0].toUpperCase() + camelCase.substr(1);
    return pascalCase;
};
export const snakeToTitle = (str: string) =>
    str.replace(/^_*(.)|_+(.)/g, (s, c, d) =>
        c ? c.toUpperCase() : " " + d.toUpperCase()
    );
