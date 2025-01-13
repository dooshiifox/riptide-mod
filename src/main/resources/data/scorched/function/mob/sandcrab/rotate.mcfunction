scoreboard players add @s scorched_count 1
execute if score @s scorched_count matches 1 run scoreboard players set @s scorched_count2 1
execute if score @s scorched_count matches 1 if predicate scorched:chance/50_percent run scoreboard players set @s scorched_count2 2


execute if score @s scorched_count2 matches 1 if score @s scorched_count matches 1.. run tp @s ~ ~ ~ ~10 ~
execute if score @s scorched_count2 matches 2 if score @s scorched_count matches 1.. run tp @s ~ ~ ~ ~-10 ~
execute if score @s scorched_count matches 3.. if predicate scorched:chance/20_percent run scoreboard players reset @s scorched_count
