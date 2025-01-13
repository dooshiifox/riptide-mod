scoreboard players set @s scorched_count 0

particle end_rod ~ ~ ~ 0 0 0 .1 20 force @a[distance=..64]
particle end_rod ~ ~ ~ 0 0 0 .2 10 force @a[distance=..64]

playsound block.beacon.power_select block @a[distance=..32] ~ ~ ~ .25 1.2
playsound block.beacon.activate block @a[distance=..32] ~ ~ ~ 2 .68