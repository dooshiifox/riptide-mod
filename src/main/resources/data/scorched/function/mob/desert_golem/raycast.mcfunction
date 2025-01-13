scoreboard players add temp scorched_count 1
particle soul_fire_flame ~ ~ ~ 0 0 0 0 1 force @a[distance=..128]
particle soul_fire_flame ~ ~ ~ 0 0 0 .08 2 force @a[distance=..128]
particle poof ~ ~.5 ~ .2 .4 .2 .1 1 normal
tag @e[distance=..1,type=marker,tag=scorched_des_magic] add scorched_des_magic_lit
execute unless block ~ ~ ~ #scorched:cannot_be_blown_up_solid as @s[tag=!scorched_temp] positioned ~ ~-1 ~ at @e[type=!#scorched:misc,distance=..1.25,predicate=!scorched:condition/creative_spec] run function scorched:mob/desert_golem/raycast_hit
execute unless block ~ ~ ~ #scorched:cannot_be_blown_up_solid if score temp scorched_count matches 1..69 positioned ^ ^ ^.25 run function scorched:mob/desert_golem/raycast
scoreboard players reset temp scorched_count
tag @s remove scorched_temp

