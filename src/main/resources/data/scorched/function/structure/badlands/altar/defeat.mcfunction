
playsound minecraft:entity.wither_skeleton.death ambient @s ~ ~ ~ 1 .6
playsound block.bell.resonate block @a[distance=..64] ~ ~ ~ 1 2
particle flame ~ ~ ~ 0 0 0 .2 20 force @a[distance=..128]
playsound block.fire.extinguish block @a[distance=..64] ~ ~ ~ 1 2

summon experience_orb ~ ~ ~ {Value:4,Motion:[0.2,0.2,0.0]}
summon experience_orb ~ ~ ~ {Value:4,Motion:[-0.2,0.2,0.0]}
summon experience_orb ~ ~ ~ {Value:4,Motion:[-0.0,0.2,-0.2]}
summon experience_orb ~ ~ ~ {Value:4,Motion:[0.0,0.2,-0.2]}

summon experience_orb ~ ~ ~ {Value:4,Motion:[0.2,0.2,0.2]}
summon experience_orb ~ ~ ~ {Value:4,Motion:[-0.2,0.2,0.2]}
summon experience_orb ~ ~ ~ {Value:4,Motion:[-0.2,0.2,-0.2]}
summon experience_orb ~ ~ ~ {Value:4,Motion:[0.2,0.2,-0.2]}

execute positioned ~ ~.5 ~ run function scorched:structure/badlands/frame/emerald

scoreboard players set @s scorched_count 600

setblock ~ ~6 ~ air

execute at @e[type=marker,tag=scorched_temple_candle,distance=..16,limit=8] run particle large_smoke ~ ~.5 ~ 0 0 0 .02 4 normal
execute at @e[type=marker,tag=scorched_temple_candle,distance=..16,limit=8] run fill ~ ~ ~ ~ ~ ~ red_candle[lit=false,candles=2] replace red_candle[lit=true,candles=2]