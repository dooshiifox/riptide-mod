scoreboard players add @s scorched_count 1
data merge entity @s {Invulnerable:1b,NoAI:1b}

execute at @s if score @s scorched_count matches 1010..1019 run tp @s ~ ~-.015 ~
execute at @s if score @s scorched_count matches 1020.. run tp @s ~ ~-.03 ~


execute at @s if score @s scorched_count matches 1000..1004 run tp @s ~ ~ ~ ~ ~5

#execute at @s if score @s scorched_count matches 1000..1009 run tp @s ~ ~ ~ ~4 ~
#execute at @s if score @s scorched_count matches 1010.. run tp @s ~ ~ ~ ~8 ~


execute at @s if score @s scorched_count matches 1000..1004 run tp @s ~ ~ ~ ~ ~10
execute at @s if score @s scorched_count matches 1005..1009 run tp @s ~ ~ ~ ~ ~-10
execute at @s if score @s scorched_count matches 1010..1014 run tp @s ~ ~ ~ ~ ~10
execute at @s if score @s scorched_count matches 1015..1019 run tp @s ~ ~ ~ ~ ~-10

execute at @s if score @s scorched_count matches 1060.. align zyx positioned ~.5 ~ ~.5 run function scorched:mob/desert_spider/particle
execute at @s if score @s scorched_count matches 1010..1059 align zyx positioned ~.5 ~ ~.5 run function scorched:mob/desert_spider/particle_large
execute at @s if score @s scorched_count matches 1010.. if score 4tick scorched_clock matches 1 run playsound block.sand.step hostile @a[distance=..32] ~ ~ ~ .25 1.8
execute at @s if score @s scorched_count matches 1080.. run kill @e[sort=nearest,limit=1,type=marker,tag=scorched_desert_spider_marker]
execute at @s if score @s scorched_count matches 1080.. run function scorched:action/despawn
