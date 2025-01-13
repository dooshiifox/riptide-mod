scoreboard players add @s scorched_count 1

execute if score @s[tag=!scorched_fireplacer_N] scorched_count matches ..40 run particle electric_spark ~ ~.1 ~ .7 0 .7 0 8 force @a[distance=..32]
execute if score @s[tag=!scorched_fireplacer_N] scorched_count matches ..40 run particle flame ~ ~.1 ~ .7 0 .7 0 1 force @a[distance=..32]

execute if score @s scorched_count matches 40 run particle large_smoke ~ ~.4 ~ .7 .4 .7 .05 20 normal @a[distance=..32]

execute if score @s scorched_count matches 40 run fill ~1 ~ ~1 ~-1 ~ ~-1 fire replace air
execute if score @s scorched_count matches 240 run fill ~1 ~ ~1 ~-1 ~ ~-1 air replace fire
execute if score @s scorched_count matches 240 run kill @s