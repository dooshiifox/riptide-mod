playsound entity.splash_potion.break block @a[distance=..20] ~ ~ ~ 1 1
playsound minecraft:entity.dolphin.splash block @a[distance=..32] ~ ~ ~ 1 2
playsound minecraft:block.fire.extinguish block @a[distance=..32] ~ ~ ~ 1 1.5
particle poof ~ ~.5 ~ .2 .4 .2 .05 32 normal

kill @e[sort=nearest,limit=1,nbt={Item:{id:"minecraft:splash_potion",count:1,components:{"minecraft:potion_contents":{potion:"minecraft:water"}}}},distance=..1.75]
scoreboard players set @s scorched_count -100