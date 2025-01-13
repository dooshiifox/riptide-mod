particle explosion ~ ~1 ~ 0 0 0 0 1 force @a[distance=..128]
playsound entity.generic.explode hostile @a[distance=..128] ~ ~ ~ 5 1
particle large_smoke ~ ~1 ~ .3 .3 .3 .05 20 normal
fill ~ ~ ~ ~ ~ ~ fire replace air
damage @e[type=!#scorched:misc,distance=..1.25,predicate=!scorched:condition/creative_spec,sort=nearest,limit=1] 8 scorched:fire_magic by @s
tag @s add scorched_temp