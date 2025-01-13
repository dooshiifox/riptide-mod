scoreboard players add @s scorched_count2 1
particle dust_color_transition{from_color:[0.902,0.000,1.000],scale:2,to_color:[1.000,0.973,0.000]} ~ ~1 ~ .5 .6 .5 1 1 normal

execute if score @s scorched_count2 matches 104 positioned ~ ~-2 ~ run function scorched:structure/badlands/altar/fire_placers_summon

execute if score @s scorched_count2 matches 104 run playsound block.stone.place hostile @a[distance=..32] ~ ~ ~ 1 1.5
execute if score @s scorched_count2 matches 106 run playsound minecraft:item.flintandsteel.use hostile @a[distance=..32] ~ ~ ~ 1 .5

execute if score @s scorched_count2 matches 125 run playsound minecraft:block.respawn_anchor.charge hostile @a[distance=..32] ~ ~ ~ .7 1
execute if score @s scorched_count2 matches 130 run playsound minecraft:block.respawn_anchor.charge hostile @a[distance=..32] ~ ~ ~ .7 1.2
execute if score @s scorched_count2 matches 135 run playsound minecraft:block.respawn_anchor.charge hostile @a[distance=..32] ~ ~ ~ .7 1.4

execute if score @s scorched_count2 matches 140 run playsound minecraft:block.respawn_anchor.charge hostile @a[distance=..32] ~ ~ ~ .7 1.6
execute if score @s scorched_count2 matches 140 run playsound minecraft:block.respawn_anchor.charge hostile @a[distance=..32] ~ ~ ~ .7 1.8
execute if score @s scorched_count2 matches 140 run playsound block.fire.extinguish hostile @a[distance=..32] ~ ~ ~ 1 1.2
execute if score @s scorched_count2 matches 140 run playsound minecraft:entity.breeze.death hostile @a[distance=..32] ~ ~ ~ .7 1.2

execute if score @s scorched_count2 matches 340 run playsound block.fire.extinguish hostile @a[distance=..32] ~ ~ ~ 1 2

execute if score @s scorched_count2 matches 400.. run scoreboard players set @s scorched_count2 0