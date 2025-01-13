
setblock ~ ~ ~ air
place feature scorched:tree/badlands_dark_oak
execute if block ~ ~ ~ air run loot spawn ~ ~ ~ loot scorched:drop/dark_oak_sapling
kill @s
