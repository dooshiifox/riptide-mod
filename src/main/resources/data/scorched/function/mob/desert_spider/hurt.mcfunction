playsound minecraft:entity.spider.hurt hostile @a[distance=..20] ~ ~ ~ 1 1.4
playsound minecraft:entity.silverfish.hurt hostile @a[distance=..20] ~ ~ ~ .5 2
playsound minecraft:entity.silverfish.hurt hostile @a[distance=..20] ~ ~ ~ .25 .6

execute if block ~ ~-.1 ~ #scorched:can_burrow unless score @s scorched_count matches 1.. if predicate scorched:chance/80_percent run function scorched:mob/desert_spider/burrow