tp @s ~ ~ ~ ~15 ~
execute if score @s scorched_count matches -100 run playsound minecraft:entity.minecart.riding hostile @a[distance=..20] ~ ~ ~ .2 2
execute if score @s scorched_count matches -83 run playsound minecraft:entity.minecart.riding hostile @a[distance=..20] ~ ~ ~ .2 2
execute if score @s scorched_count matches -66 run playsound minecraft:entity.minecart.riding hostile @a[distance=..20] ~ ~ ~ .2 2
execute if score @s scorched_count matches -49 run playsound minecraft:entity.minecart.riding hostile @a[distance=..20] ~ ~ ~ .2 2
execute if score @s scorched_count matches -32 run playsound minecraft:entity.minecart.riding hostile @a[distance=..20] ~ ~ ~ .2 2
execute if score @s scorched_count matches -15 run playsound minecraft:entity.minecart.riding hostile @a[distance=..20] ~ ~ ~ .2 2


execute if score @s scorched_count matches -1 run stopsound @a[distance=..32] hostile minecraft:entity.minecart.riding
execute if score @s scorched_count matches -1 run playsound entity.armor_stand.break hostile @a[distance=..20] ~ ~ ~ 1 .7
execute if score @s scorched_count matches -1 run playsound block.fire.extinguish hostile @a[distance=..20] ~ ~ ~ 1 2