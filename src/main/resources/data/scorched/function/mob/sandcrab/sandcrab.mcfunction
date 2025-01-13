#execute as @s[tag=!scorched_sandcrab_checked] run function scorched:mob/sandcrab/sandcrab_health
#execute as @s[tag=!scorched_sandcrab_checked] run tag @s add scorched_sandcrab_checked


effect give @s invisibility 999999 1 true
execute if entity @p[distance=..32] if predicate scorched:chance/1_percent run playsound minecraft:entity.silverfish.ambient neutral @a[distance=..20] ~ ~ ~ .4 2

#death
execute store result score @s scorched_health run data get entity @s Health
execute if score @s scorched_health matches ..30 run function scorched:mob/sandcrab/death

#hurt
execute if score @s scorched_HurtTime matches 1.. run scoreboard players add @s scorched_HurtTime 1
execute if score @s scorched_HurtTime matches 11.. run function scorched:mob/sandcrab/hurt_reverse
execute if entity @p[distance=..32] as @s[nbt={HurtTime:8s}] run function scorched:mob/sandcrab/hurt

#rotate
execute unless score @s scorched_count matches 0.. if predicate scorched:chance/2_percent run scoreboard players set @s scorched_count 0
execute if score @s scorched_count matches 0.. run function scorched:mob/sandcrab/rotate



execute positioned ~ ~.89 ~ if block ^ ^ ^.7 #scorched:permeable positioned ~ ~-1 ~ unless block ^ ^ ^.5 #scorched:permeable at @s run tp @s ^ ^ ^.05

execute positioned ~ ~.89 ~ unless block ^-.3 ^ ^ #scorched:permeable at @s run tp @s ^.3 ^ ^
execute positioned ~ ~.89 ~ unless block ^.3 ^ ^ #scorched:permeable at @s run tp @s ^-.3 ^ ^



execute positioned ~ ~.8 ~ if block ~ ~ ~ #scorched:swim at @s run tp @s ^ ^ ^.01



#falling
execute at @s if block ~ ~.89 ~ water at @s run tp @s ~ ~.1 ~
execute at @s unless block ~ ~.79 ~ water if block ~ ~.69 ~ #scorched:permeable at @s run tp @s ~ ~-.2 ~