data merge entity @s {Invulnerable:0b,NoAI:0b}
tp @s ~ ~.5 ~
data modify entity @s Motion[1] set value 0.6
execute if predicate scorched:chance/33_percent run data modify entity @s Motion[1] set value 0.5
execute if predicate scorched:chance/33_percent run data modify entity @s Motion[1] set value 0.6


execute if block ~ ~-0.1 ~ #scorched:red_sand run particle block{block_state:"red_sand"} ~ ~ ~ .7 .3 .7 0 100 force @a[distance=..32]
execute if block ~ ~-0.1 ~ #scorched:sand run particle block{block_state:"sand"} ~ ~ ~ .7 .3 .7 0 100 force @a[distance=..32]
execute if block ~ ~-0.1 ~ #scorched:dirt run particle block{block_state:"dirt"} ~ ~ ~ .7 .3 .7 0 100 force @a[distance=..32]
execute if block ~ ~-0.1 ~ #scorched:stone run particle block{block_state:"stone"} ~ ~ ~ .7 .3 .7 0 100 force @a[distance=..32]



playsound block.sand.step hostile @a[distance=..32] ~ ~ ~ 1.2 .8
playsound block.sand.step hostile @a[distance=..32] ~ ~ ~ 1.2 1.2
playsound block.sand.step hostile @a[distance=..32] ~ ~ ~ 1.2 1.2
scoreboard players reset @s scorched_count
