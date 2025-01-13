particle dust{color:[0.812,0.620,0.239],scale:1} ~ ~ ~ .3 0 .3 0 2 normal
execute if predicate scorched:chance/3_percent run function scorched:mob/desert_spider/ambient
execute as @s[nbt={HurtTime:10s}] run function scorched:mob/desert_spider/hurt


execute unless block ~ ~-.1 ~ #scorched:permeable unless block ~ ~-.1 ~ #scorched:can_burrow run effect give @s slowness 1 1 true


execute if block ~ ~-.1 ~ #scorched:can_burrow unless score @s scorched_count matches 1.. if predicate scorched:chance/2_percent run scoreboard players set @s scorched_count 0
execute if score @s scorched_count matches 0..900 run function scorched:mob/desert_spider/burrow


execute if score @s scorched_count matches 999.. run function scorched:mob/desert_spider/good_night
execute as @s[tag=!scorched_desert_spider_sleepoff] unless block ~ ~-.1 ~ #scorched:permeable unless score @s scorched_count matches 1.. if predicate scorched:position/y63.. if predicate scorched:condition/sand_sleep_time run scoreboard players set @s scorched_count 999

team join scorched_mob @s