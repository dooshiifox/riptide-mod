scoreboard players add @s scorched_count 1

execute if score @s scorched_count matches 1 as @e[distance=..3,type=glow_item_frame,tag=scorched_itemframe] run data merge entity @s {Invulnerable:1b}
execute if score @s scorched_count matches 1 run playsound block.amethyst_cluster.step block @a[distance=..64] ~ ~ ~ 3 .8
execute if score @s scorched_count matches 20 run playsound entity.zombie_villager.converted block @a[distance=..64] ~ ~ ~ 3 1.1


#execute if score @s scorched_count matches 40 as @e[distance=..3,type=glow_item_frame,tag=scorched_itemframe] run data merge entity @s {Item:{id:"minecraft:white_concrete",Count:1b}}


execute if score @s scorched_count matches 40 run function scorched:structure/desert/frame/frame_filled
execute if score @s scorched_count matches 50 run playsound block.stone.step block @a[distance=..64] ~ ~ ~ 2 .6
execute if score @s scorched_count matches 60 run playsound block.stone.step block @a[distance=..64] ~ ~ ~ 1 .6
execute if score @s scorched_count matches 70 run playsound block.stone.step block @a[distance=..64] ~ ~ ~ .8 .6
execute if score @s scorched_count matches 80 run playsound block.stone.step block @a[distance=..64] ~ ~ ~ .6 .6
execute if score @s scorched_count matches 90 run playsound block.stone.step block @a[distance=..64] ~ ~ ~ .4 .6
execute if score @s scorched_count matches 100 run playsound block.stone.step block @a[distance=..64] ~ ~ ~ .2 .6
execute if score @s scorched_count matches 110 run playsound block.stone.step block @a[distance=..64] ~ ~ ~ .05 .6
execute if score @s scorched_count matches 120 run playsound block.stone.step block @a[distance=..64] ~ ~ ~ .02 .6


execute if score @s scorched_count matches 60..320 run particle soul_fire_flame ~ ~ ~ 0 0 0 .15 5 force @a[distance=..32]

#fire

execute if score @s scorched_count matches 60..320 as @e[distance=..3,type=glow_item_frame,tag=scorched_itemframe] at @s facing entity @e[sort=nearest,limit=1,type=marker,tag=scorched_des] feet run particle soul_fire_flame ~ ~ ~ ^ ^ ^10000000000000 0.00000000000005 0 force @a[distance=..32]
execute if score 4tick scorched_clock matches 1 if score @s scorched_count matches 60..320 run playsound block.netherrack.place block @a[distance=..32] ~ ~ ~ 1.5 .8

#oooo tnt??
execute if score @s scorched_count matches 100..320 if predicate scorched:chance/10_percent at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run playsound block.fire.ambient block @a[distance=..32] ~ ~ ~ 1 .8
execute if score @s scorched_count matches 100..320 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run particle large_smoke ~ ~ ~ .05 .05 .05 .02 1 force @a[distance=..32]


execute if score @s scorched_count matches 200..320 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run particle large_smoke ~ ~ ~ .05 .05 .05 .05 3 force @a[distance=..32]


execute if score @s scorched_count matches 300..320 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run particle large_smoke ~ ~ ~ .1 .1 .1 .15 15 force @a[distance=..32]


execute if score @s scorched_count matches 240 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run playsound entity.tnt.primed block @a[distance=..64] ~ ~ ~ 2 .75

#boom!

execute if score @s scorched_count matches 319 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run effect give @a[distance=..10] resistance 1 255 true
execute if score @s scorched_count matches 320 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] positioned ~ ~-1 ~ run function scorched:structure/desert/frame/break_some_blocks_on_the_outside
execute if score @s scorched_count matches 320 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run summon tnt
execute if score @s scorched_count matches 320 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run particle large_smoke ~ ~ ~ .4 .4 .4 .02 50 normal @a[distance=..64]
execute if score @s scorched_count matches 320 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run particle campfire_signal_smoke ~ ~ ~ 1 .4 1 .02 15 force @a[distance=..64]
execute if score @s scorched_count matches 320 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run particle campfire_signal_smoke ~ ~ ~ 1 .4 1 .01 30 force @a[distance=..64]
execute if score @s scorched_count matches 320 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run playsound block.respawn_anchor.set_spawn block @a[distance=..64] ~ ~ ~ 1 .75
execute if score @s scorched_count matches 320 at @e[sort=nearest,limit=1,type=marker,tag=scorched_des] run fill ~1 ~-1 ~1 ~-1 ~ ~-1 air destroy