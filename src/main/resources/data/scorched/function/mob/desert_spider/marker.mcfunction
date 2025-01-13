execute unless score @s scorched_count matches 11.. run scoreboard players add @s scorched_count 1


particle end_rod ~ ~ ~ 0 0 0 0 1 force @a[distance=..64,tag=scorched_debug]

execute at @s unless entity @e[distance=..1,type=spider,tag=scorched_desert_spider] run function scorched:mob/desert_spider/death


execute at @s run tp @s @n[distance=..1,type=spider,tag=scorched_desert_spider,limit=1,sort=nearest]

