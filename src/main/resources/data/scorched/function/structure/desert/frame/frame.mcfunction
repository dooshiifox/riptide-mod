particle effect ^1 ^ ^-.5 0 0 0 0 1 normal
particle effect ^ ^ ^-.5 0 0 0 0 1 normal
particle effect ^-1 ^ ^-.5 0 0 0 0 1 normal

execute at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] positioned ~ ~-27 ~ as @e[distance=..16,type=armor_stand,tag=scorched_desertgolem] run tp @s ~ ~ ~

#mining fatigue
execute unless score @s scorched_count matches 320.. at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run effect give @a[distance=..24] mining_fatigue 3 3 false
execute unless score @s scorched_count matches 320.. at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] positioned ~ ~-27 ~ run effect give @a[distance=..24] mining_fatigue 3 3 false

execute if score 4tick scorched_clock matches 1 if score @s scorched_count matches 40.. run function scorched:structure/desert/frame/frame_outline_2


execute if score @s scorched_count matches 0..320 run function scorched:structure/desert/frame/anim

execute positioned ^1 ^ ^-.5 as @e[distance=..0.1,type=glow_item_frame,nbt={Item:{}},nbt=!{Item:{id:"minecraft:emerald",components:{"minecraft:custom_data":{"ScorchedGem":2}}}}] positioned as @s run function scorched:structure/desert/frame/drop_item
execute positioned ^ ^ ^-.5 as @e[distance=..0.1,type=glow_item_frame,nbt={Item:{}},nbt=!{Item:{id:"minecraft:emerald",components:{"minecraft:custom_data":{"ScorchedGem":2}}}}] positioned as @s run function scorched:structure/desert/frame/drop_item
execute positioned ^-1 ^ ^-.5 as @e[distance=..0.1,type=glow_item_frame,nbt={Item:{}},nbt=!{Item:{id:"minecraft:emerald",components:{"minecraft:custom_data":{"ScorchedGem":2}}}}] positioned as @s run function scorched:structure/desert/frame/drop_item

execute positioned ^1 ^ ^ run fill ~ ~ ~ ~ ~ ~ air destroy
execute positioned ^ ^ ^ run fill ~ ~ ~ ~ ~ ~ air destroy
execute positioned ^-1 ^ ^ run fill ~ ~ ~ ~ ~ ~ air destroy 

execute unless score @s scorched_count matches 0.. positioned ^ ^ ^-.5 as @e[distance=..0.1,type=glow_item_frame,nbt={Item:{id:"minecraft:emerald",components:{"minecraft:custom_data":{"ScorchedGem":2}}}}] positioned ^-1 ^ ^ as @e[distance=..0.1,type=glow_item_frame,nbt={Item:{id:"minecraft:emerald",components:{"minecraft:custom_data":{"ScorchedGem":2}}}}] positioned ^2 ^ ^ as @e[distance=..0.1,type=glow_item_frame,nbt={Item:{id:"minecraft:emerald",components:{"minecraft:custom_data":{"ScorchedGem":2}}}}] positioned ^-1 ^ ^ as @e[sort=nearest,limit=1,type=marker,tag=scorched_des_frames] run function scorched:structure/desert/frame/anim
