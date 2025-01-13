playsound minecraft:entity.item_frame.remove_item block @a[distance=..20] ~ ~ ~ 1 1


scoreboard players set randomizer scorched_count 5
execute if predicate scorched:chance/20_percent run scoreboard players set randomizer scorched_count 4
execute if predicate scorched:chance/20_percent run scoreboard players set randomizer scorched_count 3
execute if predicate scorched:chance/20_percent run scoreboard players set randomizer scorched_count 2
execute if predicate scorched:chance/20_percent run scoreboard players set randomizer scorched_count 1


execute if score randomizer scorched_count matches 5 run summon item ^ ^ ^.5 {PickupDelay:20,Motion:[0.0,0.15,0.0],Tags:["scorched_temp"],Item:{id:"minecraft:gold_ingot",Count:1b}}
execute if score randomizer scorched_count matches 4 run summon item ^ ^ ^.5 {PickupDelay:20,Motion:[0.0,0.2,0.0],Tags:["scorched_temp"],Item:{id:"minecraft:gold_ingot",Count:1b}}
execute if score randomizer scorched_count matches 3 run summon item ^ ^ ^.5 {PickupDelay:20,Motion:[0.0,0.25,0.0],Tags:["scorched_temp"],Item:{id:"minecraft:gold_ingot",Count:1b}}
execute if score randomizer scorched_count matches 2 run summon item ^ ^ ^.5 {PickupDelay:20,Motion:[0.0,0.3,0.0],Tags:["scorched_temp"],Item:{id:"minecraft:gold_ingot",Count:1b}}
execute if score randomizer scorched_count matches 1 run summon item ^ ^ ^.5 {PickupDelay:20,Motion:[0.0,0.35,0.0],Tags:["scorched_temp"],Item:{id:"minecraft:gold_ingot",Count:1b}}



data modify entity @e[type=item,limit=1,sort=nearest,tag=scorched_temp] Item set from entity @s Item
tag @e[type=item,limit=1,sort=nearest,tag=scorched_temp] remove scorched_temp
data merge entity @s {Item:{id:"minecraft:air"}}

scoreboard players reset randomizer scorched_count