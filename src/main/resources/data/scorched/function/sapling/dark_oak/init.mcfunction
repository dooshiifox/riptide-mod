
execute if block ~ ~1.62 ~ minecraft:dark_oak_sapling positioned ~ ~1.62 ~ align zyx positioned ~.5 ~.5 ~.5 unless entity @e[type=marker,distance=..0.25,tag=scorched_tree] run summon marker ~ ~ ~ {Tags:["scorched_tree","scorched_tree_dark_oak"]}

particle end_rod ~ ~1.62 ~ 0 0 0 0 1 force @s[tag=scorched_debug]


execute if block ~ ~1.62 ~ #scorched:permeable unless block ~ ~1.62 ~ minecraft:dark_oak_sapling positioned ^ ^ ^.05 run function scorched:sapling/dark_oak/init





advancement revoke @s only scorched:placed/wooded_badlands