execute unless score @s scorched_count matches 50.. run particle dust{color:[0.851,0.314,1.000],scale:1.5} ~ ~ ~ .4 .4 .4 0.01 1 force @a[distance=..16]
execute unless score @s scorched_count matches 50.. run particle soul_fire_flame


execute if score @s scorched_count matches 1.. run function scorched:structure/desert/spawner/anim
execute at @p[distance=..6,gamemode=!spectator] if block ~ ~-1 ~ #scorched:activates_desert_pyramid_spawner at @s unless score @s scorched_count matches 0.. run function scorched:structure/desert/spawner/anim