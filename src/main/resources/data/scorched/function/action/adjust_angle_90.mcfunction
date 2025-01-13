playsound minecraft:entity.armor_stand.hit player @a[distance=..20] ~ ~ ~ 1 1
execute as @e[distance=..32,sort=nearest,limit=1,type=!minecraft:player] at @s run tp @s ~ ~ ~ ~90 ~