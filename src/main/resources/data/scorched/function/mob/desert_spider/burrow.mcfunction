scoreboard players add @s scorched_count 1
data merge entity @s {Invulnerable:1b,NoAI:1b}

execute at @s if score @s scorched_count matches 1 run tp @s ~ ~ ~ ~ 0
execute at @s if score @s scorched_count matches 1..10 if block ~ ~-.2 ~ #scorched:permeable run tp @s ~ ~-.2 ~
execute at @s if score @s scorched_count matches 1..10 if block ~ ~-.2 ~ #scorched:permeable run tp @s ~ ~ ~ ~ ~10
execute at @s if score @s scorched_count matches 1..7 run tp @s ~ ~-.07 ~
execute at @s if score @s scorched_count matches 8..15 run tp @s ~ ~-.14 ~
execute at @s if score @s scorched_count matches 10..60 align zyx positioned ~.5 ~ ~.5 run function scorched:mob/desert_spider/particle
execute at @s if score @s scorched_count matches 10..60 if score 4tick scorched_clock matches 1 run playsound block.sand.step hostile @a[distance=..32] ~ ~ ~ .5 1.4
execute at @s if score @s scorched_count matches 20..59 if predicate scorched:chance/2_percent run scoreboard players set @s scorched_count 50

#further
execute at @s if score @s scorched_count matches 10.. if block ^ ^ ^.3 #scorched:permeable run tp @s ~ ~-.5 ~

execute at @s if score @s scorched_count matches 10..999 rotated ~ 0 if block ^ ^ ^.3 #scorched:can_burrow run tp @s ^ ^ ^.3
execute at @s if score @s scorched_count matches 60..999 run tp @s ~ ~.25 ~
execute at @s if score @s scorched_count matches 10..999 if block ~ ~.2 ~ #scorched:permeable run function scorched:mob/desert_spider/burrow_end