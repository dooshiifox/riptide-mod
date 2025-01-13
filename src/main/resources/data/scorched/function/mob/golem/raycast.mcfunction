scoreboard players add temp scorched_count 1
particle flame ~ ~ ~ 0 0 0 0 1 force @a[distance=..128]
particle flame ~ ~ ~ 0 0 0 .08 2 force @a[distance=..128]
particle dust_color_transition{from_color:[1.000,0.533,0.000],scale:2,to_color:[0.612,0.000,0.122]} ~ ~ ~ 0 0 0 0 1 force @a[distance=..64]
execute unless block ~ ~ ~ #scorched:cannot_be_blown_up_solid as @s[tag=!scorched_temp] positioned ~ ~-1 ~ at @e[type=!#scorched:misc,distance=..1.25,predicate=!scorched:condition/creative_spec] run function scorched:mob/golem/raycast_hit
execute unless block ~ ~ ~ #scorched:cannot_be_blown_up_solid if score temp scorched_count matches 1..69 positioned ^ ^ ^.25 run function scorched:mob/golem/raycast
scoreboard players reset temp scorched_count
tag @s remove scorched_temp

