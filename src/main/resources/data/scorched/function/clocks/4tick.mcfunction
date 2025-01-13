
#postgen
execute if score postgen scorched_settings matches 1 as @e[type=marker,tag=scorched_postgen.sandcrab] at @s if entity @p[distance=..128] run function scorched:postgen/placer/sandcrab

#mob spawns
execute as @e[type=spider,tag=!scorched_spawnattemped,predicate=scorched:position/y63..] at @s if biome ~ ~ ~ #scorched:spawns_sandcrawlers run function scorched:mob/desert_spider/init


scoreboard players set 4tick scorched_clock 0

