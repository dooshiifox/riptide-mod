execute unless score @s scorched_count matches 51.. run scoreboard players add @s scorched_count 1 

execute unless entity @e[type=husk,tag=scorched_des_husk,distance=..32] if score @s scorched_count matches 51..52 run scoreboard players add @s scorched_count 1 

execute if score @s scorched_count matches 1 run playsound block.beacon.power_select block @a[distance=..20]
execute if score @s scorched_count matches ..50 run particle soul_fire_flame ~ ~ ~ 0 0 0 .1 5 force @a[distance=..32]
execute if score @s scorched_count matches ..50 run particle soul ~ ~ ~ 0 0 0 .05 1 force @a[distance=..32]




execute if score @s scorched_count matches 50 run playsound minecraft:entity.evoker.prepare_attack block @a[distance=..20] ~ ~ ~ 1 1.5
execute if score @s scorched_count matches 50 positioned ~2 ~-1 ~2 run function scorched:structure/desert/spawner/summon
execute if score @s scorched_count matches 50 positioned ~-2 ~-1 ~2 run function scorched:structure/desert/spawner/summon
execute if score @s scorched_count matches 50 positioned ~-2 ~-1 ~-2 run function scorched:structure/desert/spawner/summon
execute if score @s scorched_count matches 50 positioned ~2 ~-1 ~-2 run function scorched:structure/desert/spawner/summon
execute if score @s scorched_count matches 50 run particle poof ~ ~ ~ .3 .3 .3 .05 15 force @a[distance=..32]
execute if score @s scorched_count matches 50 run setblock ~ ~ ~ air


execute if score @s scorched_count matches 52 run function scorched:structure/desert/spawner/chest