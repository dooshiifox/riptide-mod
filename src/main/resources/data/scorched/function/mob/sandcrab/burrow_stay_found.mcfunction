


execute if score @s scorched_count2 matches 01..05 at @s run tp @s ^ ^ ^.09
execute if score @s scorched_count2 matches 06..10 at @s run tp @s ^ ^ ^.045

execute if score @s scorched_count2 matches 01..05 at @s run tp @s ~ ~.3 ~
execute if score @s scorched_count2 matches 01 run playsound entity.allay.ambient_with_item neutral @a[distance=..20] ~ ~ ~ .25 2
execute if score @s scorched_count2 matches 01 run playsound entity.silverfish.ambient neutral @a[distance=..20] ~ ~ ~ .5 1.5
execute if score @s scorched_count2 matches 01 run playsound entity.silverfish.ambient neutral @a[distance=..20] ~ ~ ~ .5 2
execute if score @s scorched_count2 matches 06..10 at @s run tp @s ~ ~.15 ~
execute if score @s scorched_count2 matches 11 at @s run tp @s ~ ~.0 ~

execute if score @s scorched_count2 matches 11 at @s run loot spawn ~ ~ ~ loot scorched:entities/sandcrab
#execute if score @s scorched_count2 matches 11 at @s run data modify entity @e[limit=1,sort=nearest,distance=..0.0001,type=item] Motion[1] set value 0.5
execute if score @s scorched_count2 matches 11 at @s run playsound entity.item.pickup neutral @a[distance=..20] ~ ~ ~ 1

execute if score @s scorched_count2 matches 13 at @s run loot spawn ~ ~ ~ loot scorched:entities/sandcrab
#execute if score @s scorched_count2 matches 13 at @s run data modify entity @e[limit=1,sort=nearest,distance=..0.0001,type=item] Motion[1] set value 0.5
execute if score @s scorched_count2 matches 13 at @s run playsound entity.item.pickup neutral @a[distance=..20] ~ ~ ~ 1

execute if score @s scorched_count2 matches 15 at @s run loot spawn ~ ~ ~ loot scorched:entities/sandcrab
#execute if score @s scorched_count2 matches 15 at @s run data modify entity @e[limit=1,sort=nearest,distance=..0.0001,type=item] Motion[1] set value 0.5
execute if score @s scorched_count2 matches 15 at @s run playsound entity.item.pickup neutral @a[distance=..20] ~ ~ ~ 1



execute if score @s scorched_count2 matches 16 run tp @s ~ ~ ~ ~ 0


execute if score @s scorched_count2 matches 16..18 at @s run tp @s ~ ~-.1 ~

execute if score @s scorched_count2 matches 01 run tag @s remove refresh_entity_misc
execute if score @s scorched_count2 matches 18 run tag @s add scorched_sandcrab_found
execute if score @s scorched_count2 matches 18 run data merge entity @s {Invulnerable:0b}
execute if score @s scorched_count2 matches 18 run scoreboard players set @s scorched_count2 0
