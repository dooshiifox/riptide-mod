execute unless score @s scorched_count matches 0.. at @e[type=marker,tag=scorched_temple_lever,distance=..16] if block ~ ~ ~ lever[powered=true] run function scorched:structure/badlands/altar/altar_activate

execute if score @s scorched_count matches 0..300 run scoreboard players add @s scorched_count 1

execute if score @s scorched_count matches 0..300 at @e[type=marker,tag=scorched_temple_lever,distance=..16] if block ~ ~ ~ lever run particle electric_spark ~ ~ ~ .3 .3 .3 .1 1 normal


execute if score 4tick scorched_clock matches 1 if score @s scorched_count matches 40..300 at @e[type=marker,tag=scorched_temple_candle,distance=..16,limit=8] run particle small_flame ~ ~ ~ ^ ^ ^10000000000000 0.00000000000003 0 force @a[distance=..32]
execute if score 4tick scorched_clock matches 1 if score @s scorched_count matches 40..300 run playsound block.netherrack.place block @a[distance=..32] ~ ~ ~ .2 .8



execute if score @s scorched_count matches 1 run playsound minecraft:entity.allay.ambient_without_item block @a[distance=..32] ~ ~ ~ 2 .6
execute if predicate scorched:chance/20_percent if score @s scorched_count matches 1..20 run playsound minecraft:entity.allay.ambient_without_item block @a[distance=..32] ~ ~ ~ 2 .57
execute if score @s scorched_count matches 100..301 run particle small_flame ~ ~ ~ 0 0 0 .01 1 force @a[distance=..32]
execute if score @s scorched_count matches 100 run playsound entity.blaze.ambient hostile @a[distance=..32] ~ ~ ~ 1 1

execute if score @s scorched_count matches 200..301 run particle flame ~ ~ ~ .05 .05 .05 .02 1 force @a[distance=..32]
execute if score @s scorched_count matches 200 run playsound entity.blaze.ambient hostile @a[distance=..32] ~ ~ ~ 1 1

execute if score @s scorched_count matches 260..301 run particle flame ~ ~ ~ .05 .05 .05 .15 1 force @a[distance=..32]
execute if score @s scorched_count matches 260..301 run particle flame ~ ~ ~ .05 .05 .05 .1 1 force @a[distance=..32]

execute if score @s scorched_count matches 250 run playsound block.bell.resonate block @a[distance=..64] ~ ~ ~ 2 .8

execute if score @s scorched_count matches 300 run function scorched:structure/badlands/altar/summon_blaze
execute if score @s scorched_count matches 300..301 as @e[type=blaze,tag=scorched_blaze,distance=..16] at @s run function scorched:structure/badlands/altar/blaze


execute if score @s scorched_count matches 300.. as @e[distance=..32,type=marker,tag=scorched_fireplacer] at @s run function scorched:structure/badlands/altar/fire_placers


execute if score @s scorched_count matches 301 unless entity @e[type=blaze,tag=scorched_blaze,distance=..16] at @s run function scorched:structure/badlands/altar/defeat
execute if score @s scorched_count matches 300..301 run tp @e[type=blaze,tag=scorched_blaze,distance=..16] ~ ~-1 ~
