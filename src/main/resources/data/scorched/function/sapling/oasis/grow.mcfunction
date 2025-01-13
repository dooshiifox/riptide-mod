
setblock ~ ~ ~ air
place feature scorched:tree/palm_tree
execute if block ~ ~ ~ air run loot spawn ~ ~ ~ loot scorched:drop/oak_sapling
kill @s
