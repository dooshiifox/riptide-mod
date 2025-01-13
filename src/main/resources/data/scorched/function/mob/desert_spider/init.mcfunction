execute if predicate scorched:chance/90_percent run tag @s add scorched_temp

execute as @s[tag=scorched_temp] run function scorched:mob/desert_spider/summon
execute as @s[tag=scorched_temp] run function scorched:action/despawn

particle end_rod ~ ~50 ~ 0 40 0 0 100 force @a[distance=..256,tag=scorched_debug]

tag @s add scorched_spawnattemped

