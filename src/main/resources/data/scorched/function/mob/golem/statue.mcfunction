
scoreboard players add @s scorched_count 1
execute if score @s scorched_count matches 0.. unless entity @p[distance=..20,gamemode=!creative,gamemode=!spectator] run scoreboard players set @s scorched_count 0

execute if score @s scorched_count matches 0..126 run tp @s ~ ~ ~ facing entity @p[gamemode=!spectator,gamemode=!creative,distance=..20]
execute if score @s scorched_count matches 147 run tp @s ~ ~ ~ facing entity @r[gamemode=!spectator,gamemode=!creative,distance=..20]
particle dust_color_transition{from_color:[1.000,0.533,0.000],scale:1,to_color:[0.612,0.000,0.122]} ~ ~.5 ~ .2 .4 .2 0 2 normal

data modify entity @s CustomName set value '{"text":"Badlands Golem"}'

execute unless score @s scorched_count matches ..-1 if predicate scorched:chance/25_percent run particle large_smoke ~ ~ ~ 0 0 0 0 1 normal
execute unless score @s scorched_count matches ..-1 run particle flame ~ ~.2 ~ 0 0 0 0 1 normal
execute unless score @s scorched_count matches ..-1 run particle flame ~ ~.4 ~ 0 0 0 0 1 normal
execute unless score @s scorched_count matches ..-1 run particle flame ~ ~.6 ~ 0 0 0 0 1 normal
execute unless score @s scorched_count matches ..-1 run particle flame ~ ~.8 ~ 0 0 0 0 1 normal
execute unless score @s scorched_count matches ..-1 run particle flame ~ ~1 ~ 0 0 0 0 1 normal

execute positioned ~ ~1 ~ if entity @e[type=potion,nbt={Item:{id:"minecraft:splash_potion",count:1,components:{"minecraft:potion_contents":{potion:"minecraft:water"}}}},distance=..2] run function scorched:mob/golem/water_bottle
execute if score @s scorched_count matches ..-1 run function scorched:mob/golem/water_bottle_aaaa



execute if score @s scorched_count matches 130.. run particle flame ~ ~.5 ~ 0 0 0 .15 4 normal
execute if score @s scorched_count matches 147 run playsound minecraft:item.armor.equip_netherite hostile @a[distance=..32] ~ ~ ~ 2 1.6
execute if score @s scorched_count matches 130.. if score 4tick scorched_clock matches 1 run playsound minecraft:block.note_block.hat hostile @a[distance=..32] ~ ~ ~ 2 1.6
execute if score @s scorched_count matches 160.. if entity @p[gamemode=!spectator,gamemode=!creative,distance=..32] positioned ~ ~.8 ~ run function scorched:mob/golem/raycast_start
execute if score @s scorched_count matches 160.. run scoreboard players set @s scorched_count 0