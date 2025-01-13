#mainly coded by LimeSplatus#4730


#clocks
scoreboard players add second scorched_clock 1
execute if score second scorched_clock matches 20.. run scoreboard players set second scorched_clock 0

scoreboard players add 4tick scorched_clock 1
execute if score 4tick scorched_clock matches 4.. run function scorched:clocks/4tick

#mobs

execute as @e[type=zombie,tag=scorched_sandcrab,tag=scorched_sandcrab_found] at @s if entity @p[distance=..64] run function scorched:mob/sandcrab/sandcrab
execute as @e[type=zombie,tag=scorched_sandcrab,tag=!scorched_sandcrab_found] at @s run function scorched:mob/sandcrab/burrow_stay

execute as @e[type=armor_stand,tag=scorched_desertgolem] at @s if entity @p[distance=..32] run function scorched:mob/desert_golem/statue
execute as @e[type=armor_stand,tag=scorched_golem] at @s if entity @p[distance=..32] run function scorched:mob/golem/statue
execute as @e[type=marker,tag=scorched_golem_block] at @s if entity @p[distance=..16] run function scorched:mob/golem/block
execute as @e[type=spider,tag=scorched_desert_spider] at @s run function scorched:mob/desert_spider/main
execute as @e[type=marker,tag=scorched_desert_spider_marker] at @s run function scorched:mob/desert_spider/marker

#sapling
execute as @e[type=marker,tag=scorched_tree_oasis] at @s run function scorched:sapling/oasis/sapling
execute as @e[type=marker,tag=scorched_tree_dark_oak] at @s run function scorched:sapling/dark_oak/sapling

execute as @e[type=marker,tag=scorched_asset] at @s if entity @p[distance=..64] run function scorched:structure/any



scoreboard players set @a[scores={scorched_death=1..}] scorched_death 0